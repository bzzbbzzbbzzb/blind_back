package com.blind.api.Dto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {
    private String code;
    private String password;
    private String email;
}
