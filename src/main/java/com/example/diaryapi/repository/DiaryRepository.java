package com.example.diaryapi.repository;

import com.example.diaryapi.dto.Diary;
import com.example.diaryapi.exception.DiaryNotFoundException;
import com.example.diaryapi.mapper.DiaryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DiaryRepository {
    private final DiaryMapper diaryMapper;

    public DiaryRepository(DiaryMapper diaryMapper) {
        this.diaryMapper = diaryMapper;
    }

    public List<Diary> findAll() {
        return diaryMapper.findAll();
    }

    public int insertDiary(Diary diary) {
        return diaryMapper.insert(diary);
    }

    public Diary findById(long id) {
        return diaryMapper.findById(id);
    }

    public boolean delete(long id) {
        return diaryMapper.delete(id) == 1;
    }

    public Diary update(Diary diary) {
        if (diaryMapper.update(diary) == 0) {
            throw new DiaryNotFoundException(diary.getId());
        }
        return diary;
    }
}
