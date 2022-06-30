package com.blind.book.service;

import com.blind.book.VO.BookListVo;
import com.blind.book.VO.BookVo;
import com.blind.book.VO.StudyVo;

import java.util.List;

public interface BookService {
    String getResult(String s) throws Exception;
    List<BookListVo> getBookList(Integer userId);
    BookVo getBook(Integer bookId);
    void updateOff(Integer userId,Integer bookId,Integer bookOffset);
    void updateSOff(Integer userId,Integer sOff);
    List<StudyVo> getStudy();
}
