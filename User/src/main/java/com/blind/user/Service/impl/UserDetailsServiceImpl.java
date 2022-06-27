package com.blind.user.Service.impl;

import com.blind.user.Domain.LoginUser;
import com.blind.user.Domain.VerifyUser;
import com.blind.user.Service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    UserServiceImpl userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        VerifyUser vu= userService.verify(username);
        if(Objects.isNull(vu)){
            throw new RuntimeException("账号或密码错误");
        }
        else{
            return new LoginUser(vu);
        }
    }
}
