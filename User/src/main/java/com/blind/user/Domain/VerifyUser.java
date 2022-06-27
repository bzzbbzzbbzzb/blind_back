package com.blind.user.Domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VerifyUser {
    private Integer id;
    private String email;
    private String password;
}
