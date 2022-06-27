package com.blind.user.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMapper {
    void addBook(@Param("bookName") String bookName,@Param("content")String content,@Param("pinyin")String pinyin);
}
