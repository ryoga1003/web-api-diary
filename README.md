# diary-api（短文日記API）

Spring Boot + MyBatis + PostgreSQL 製の短文日記CRUD API。

## 起動手順
1. `schema.sql` をPostgreSQLで実行して `diaries` テーブルを作成
2. 環境変数を設定: `DATABASE_HOST` `DATABASE_PORT` `DATABASE_NAME` `DATABASE_USER` `DATABASE_PASSWORD`
3. `./mvnw spring-boot:run`
4. ブラウザで http://localhost:8080/ を開く

## API
| メソッド | パス | 内容 | 成功時 |
|---|---|---|---|
| GET | /api/diaries | 全件取得 | 200 |
| POST | /api/diaries | 登録 `{"content":"..."}` | 201 |
| GET | /api/diaries/{id} | 1件取得 | 200 / 404 |
| PUT | /api/diaries/{id} | 更新 | 200 / 400 / 404 |
| DELETE | /api/diaries/{id} | 削除 | 204 / 404 |

content は必須・100文字以内（違反は400）。
