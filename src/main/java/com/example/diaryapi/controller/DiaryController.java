package com.example.diaryapi.controller;

import com.example.diaryapi.dto.Diary;
import com.example.diaryapi.service.DiaryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/diaries")
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    // 全件取得
    @GetMapping
    public ResponseEntity<List<Diary>> findAllDiaries() {
        return ResponseEntity.ok(diaryService.findAll());
    }

    // 登録
    @PostMapping
    public ResponseEntity<Diary> saveDiary(@RequestBody Diary diary, UriComponentsBuilder uriBuilder) {
        Diary created = diaryService.saveDiary(diary);
        URI location = uriBuilder.path("/api/diaries/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).body(created);
    }

    // 1件取得
    @GetMapping("/{id}")
    public ResponseEntity<Diary> findById(@PathVariable long id) {
        return ResponseEntity.ok(diaryService.findById(id));
    }

    // 削除
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        diaryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // 更新
    @PutMapping("/{id}")
    public ResponseEntity<Diary> update(@PathVariable long id, @RequestBody Diary diary) {
        return ResponseEntity.ok(diaryService.update(diary, id));
    }
}
