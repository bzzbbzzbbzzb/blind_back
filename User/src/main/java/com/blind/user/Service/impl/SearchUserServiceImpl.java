package com.blind.user.Service.impl;

import com.blind.user.Service.SearchUserService;
import com.blind.user.repository.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SearchUserServiceImpl implements SearchUserService {
    @Resource
    UserMapper userMapper;
    @Override
    public int searchUser(String email) {
        return userMapper.ifExistUser(email);
    }
}
