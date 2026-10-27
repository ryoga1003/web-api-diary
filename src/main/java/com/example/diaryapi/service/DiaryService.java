package com.example.diaryapi.service;

import com.example.diaryapi.dto.Diary;
import com.example.diaryapi.exception.DiaryArgumentNotValidException;
import com.example.diaryapi.exception.DiaryNotFoundException;
import com.example.diaryapi.repository.DiaryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiaryService {
    private static final int MAX_CONTENT_LENGTH = 100;

    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public List<Diary> findAll() {
        return diaryRepository.findAll();
    }

    public Diary saveDiary(Diary diary) {
        validate(diary);
        diaryRepository.insertDiary(diary);
        return diary;
    }

    public Diary findById(long id) {
        Diary diary = diaryRepository.findById(id);
        if (diary == null) {
            throw new DiaryNotFoundException(id);
        }
        return diary;
    }

    public boolean delete(long id) {
        if (!diaryRepository.delete(id)) {
            throw new DiaryNotFoundException(id);
        }
        return true;
    }

    @Transactional
    public Diary update(Diary diary, long id) {
        validate(diary);

        Diary target = diaryRepository.findById(id);
        if (target == null) {
            throw new DiaryNotFoundException(id);
        }

        diary.setId(id);
        diary.setCreatedAt(target.getCreatedAt());
        return diaryRepository.update(diary);
    }

    // 短文日記の入力チェック（空・空白のみ・文字数超過）
    private void validate(Diary diary) {
        if (diary == null || diary.getContent() == null || diary.getContent().isBlank()) {
            throw new DiaryArgumentNotValidException("content is empty");
        }
        if (diary.getContent().length() > MAX_CONTENT_LENGTH) {
            throw new DiaryArgumentNotValidException("content must be " + MAX_CONTENT_LENGTH + " characters or less");
        }
    }
}
