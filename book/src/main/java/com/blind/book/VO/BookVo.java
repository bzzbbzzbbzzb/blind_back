package com.blind.book.VO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookVo {
    private String text;

    @Override
    public String toString() {
        return "{" +
                "text='" + text + '\'' +
                ", Braille='" + Braille + '\'' +
                '}';
    }

    private String Braille;
}
