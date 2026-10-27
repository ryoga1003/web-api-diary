package com.example.diaryapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Diary {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
}
