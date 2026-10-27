package com.example.diaryapi.exception;

public class DiaryNotFoundException extends RuntimeException {
    public DiaryNotFoundException(long id) {
        super("指定されたIDの日記が見つかりません: " + id);
    }
}
