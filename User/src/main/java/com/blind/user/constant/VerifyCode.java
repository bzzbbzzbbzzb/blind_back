package com.blind.user.constant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;

@Data
@NoArgsConstructor
public class VerifyCode {
    private HashSet<String> verifyCode;
}
