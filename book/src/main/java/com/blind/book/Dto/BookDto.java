package com.blind.book.Dto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookDto {
    private String isBig;
    private String details;

    @Override
    public String toString() {
        return "{" +
                "isBig='" + isBig + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
