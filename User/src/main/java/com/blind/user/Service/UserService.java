package com.blind.user.Service;

import com.blind.api.Dto.User;
import com.blind.user.Domain.CommonResult;
import com.blind.user.Domain.VerifyUser;

import javax.servlet.http.HttpServletResponse;

public interface UserService {
    void registry(User user);
     VerifyUser verify(String username);

     int login(VerifyUser user, HttpServletResponse response);
     int getUserID(String email);
}
