package com.example.diaryapi.exception.handler;

import com.example.diaryapi.exception.DiaryArgumentNotValidException;
import com.example.diaryapi.exception.DiaryNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DiaryNotFoundException.class)
    public ResponseEntity<String> handleDiaryNotFound(DiaryNotFoundException e) {
        return ResponseEntity.status(404).body(e.getMessage());
    }

    @ExceptionHandler(DiaryArgumentNotValidException.class)
    public ResponseEntity<String> handleDiaryArgumentNotValid(DiaryArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
