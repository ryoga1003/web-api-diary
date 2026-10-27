const API_BASE_URL = '/api/diaries';

const $ = (id) => document.getElementById(id);

function showError(message) {
    $('error-message').textContent = message ? 'エラー: ' + message : '';
}

function formatDate(value) {
    return new Date(value).toLocaleString('ja-JP');
}

// 日記1件分のDOMを作る（textContentでXSS対策）
function createDiaryElement(diary, withActions) {
    const item = document.createElement('div');
    item.className = 'diary-item';

    const meta = document.createElement('small');
    meta.textContent = `ID: ${diary.id} / ${formatDate(diary.createdAt)}`;
    item.appendChild(meta);

    const text = document.createElement('p');
    text.textContent = diary.content;
    item.appendChild(text);

    if (withActions) {
        const editBtn = document.createElement('button');
        editBtn.type = 'button';
        editBtn.textContent = '編集';
        editBtn.addEventListener('click', () => startEdit(diary));

        const delBtn = document.createElement('button');
        delBtn.type = 'button';
        delBtn.textContent = '削除';
        delBtn.addEventListener('click', () => deleteDiary(diary.id));

        item.appendChild(editBtn);
        item.appendChild(delBtn);
    }
    return item;
}

// 全件取得
async function fetchDiaries() {
    showError('');
    const list = $('diary-list');
    list.textContent = 'ロード中...';
    try {
        const res = await fetch(API_BASE_URL);
        if (!res.ok) throw new Error(`HTTPエラー ${res.status}`);
        const diaries = await res.json();

        list.textContent = '';
        if (diaries.length === 0) {
            list.textContent = '日記はまだありません。';
            return;
        }
        diaries.forEach((d) => list.appendChild(createDiaryElement(d, true)));
    } catch (e) {
        list.textContent = '';
        showError(`一覧の取得に失敗しました: ${e.message}`);
    }
}

// 登録 / 更新（edit-idがあればPUT、なければPOST）
$('diary-form').addEventListener('submit', async (event) => {
    event.preventDefault();
    showError('');

    const editId = $('edit-id').value;
    const body = JSON.stringify({ content: $('content').value });
    const isEdit = editId !== '';

    try {
        const res = await fetch(isEdit ? `${API_BASE_URL}/${editId}` : API_BASE_URL, {
            method: isEdit ? 'PUT' : 'POST',
            headers: { 'Content-Type': 'application/json' },
            body
        });

        if (res.ok) {
            resetForm();
            fetchDiaries();
        } else if (res.status === 400 || res.status === 404) {
            showError(await res.text());
        } else {
            throw new Error(`HTTPエラー ${res.status}`);
        }
    } catch (e) {
        showError(`保存に失敗しました: ${e.message}`);
    }
});

// 1件取得
$('search-btn').addEventListener('click', async () => {
    showError('');
    const result = $('search-result');
    result.textContent = '';
    const id = $('search-id').value;
    if (!id) {
        showError('IDを入力してください。');
        return;
    }
    try {
        const res = await fetch(`${API_BASE_URL}/${id}`);
        if (res.status === 404) {
            showError(`ID ${id} の日記が見つかりません。`);
        } else if (!res.ok) {
            throw new Error(`HTTPエラー ${res.status}`);
        } else {
            result.appendChild(createDiaryElement(await res.json(), false));
        }
    } catch (e) {
        showError(`取得に失敗しました: ${e.message}`);
    }
});

// 削除
async function deleteDiary(id) {
    if (!confirm(`ID ${id} の日記を削除しますか？`)) return;
    showError('');
    try {
        const res = await fetch(`${API_BASE_URL}/${id}`, { method: 'DELETE' });
        if (res.status === 204) {
            fetchDiaries();
        } else if (res.status === 404) {
            showError(`ID ${id} の日記が見つかりません。`);
        } else {
            throw new Error(`HTTPエラー ${res.status}`);
        }
    } catch (e) {
        showError(`削除に失敗しました: ${e.message}`);
    }
}

// 編集モード切り替え
function startEdit(diary) {
    $('edit-id').value = diary.id;
    $('content').value = diary.content;
    $('submit-btn').textContent = '日記を更新';
    $('cancel-btn').hidden = false;
    updateCount();
    $('content').focus();
}

function resetForm() {
    $('diary-form').reset();
    $('edit-id').value = '';
    $('submit-btn').textContent = '日記を保存';
    $('cancel-btn').hidden = true;
    updateCount();
}

function updateCount() {
    $('count').textContent = $('content').value.length;
}

$('cancel-btn').addEventListener('click', resetForm);
$('reload-btn').addEventListener('click', fetchDiaries);
$('content').addEventListener('input', updateCount);

window.addEventListener('load', fetchDiaries);
