package com.blind.book.VO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookListVo {
    private Integer bookId;
    private String bookName;
    private Integer bookOffset;

    @Override
    public String toString() {
        return "{" +
                "bookId=" + bookId +
                ", bookName='" + bookName + '\'' +
                ", bookOffset=" + bookOffset +
                ", sOffset=" + sOffset +
                '}';
    }

    private Integer sOffset;

}
