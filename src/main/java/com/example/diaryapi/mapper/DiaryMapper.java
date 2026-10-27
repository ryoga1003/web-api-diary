package com.example.diaryapi.mapper;

import com.example.diaryapi.dto.Diary;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface DiaryMapper {
    @Select("SELECT id, content, created_at FROM diaries ORDER BY created_at DESC, id DESC")
    List<Diary> findAll();

    @Insert("INSERT INTO diaries(content) VALUES (#{content})")
    @Options(useGeneratedKeys = true, keyProperty = "id,createdAt", keyColumn = "id,created_at")
    int insert(Diary diary);

    @Select("SELECT id, content, created_at FROM diaries WHERE id = #{id}")
    Diary findById(long id);

    @Delete("DELETE FROM diaries WHERE id = #{id}")
    int delete(long id);

    @Update("UPDATE diaries SET content = #{content} WHERE id = #{id}")
    int update(Diary diary);
}
