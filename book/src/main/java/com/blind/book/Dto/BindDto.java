package com.blind.book.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class BindDto {
    private Integer userId;
    private String deviceId;
}
