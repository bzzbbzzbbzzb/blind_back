package com.blind.book.repository;

import com.blind.book.VO.BookListVo;
import com.blind.book.VO.BookVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookMapper {
    List<BookListVo> getBookList(@Param("userId") Integer userId);
    BookVo getBook(@Param("bookId") Integer bookId);
}
