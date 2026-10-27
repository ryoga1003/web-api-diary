package com.example.diaryapi.exception;

public class DiaryArgumentNotValidException extends RuntimeException {
    public DiaryArgumentNotValidException(String message) {
        super(message);
    }
}
