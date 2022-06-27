package com.blind.user.Service.impl;
import com.blind.api.Dto.User;
import com.blind.user.Domain.CommonResult;
import com.blind.user.Domain.LoginUser;
import com.blind.user.Domain.VerifyUser;
import com.blind.user.Service.UserService;
import com.blind.user.repository.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

import static com.blind.api.Utils.JWTUtil.generate;


@Service
public class UserServiceImpl implements UserService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    PasswordEncoder passwordEncoder;
    @Resource
    UserMapper userMapper;
    @Override
    public void registry(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.registry(user.getEmail(), user.getPassword());
        Integer id =userMapper.getUserId(user.getEmail());
        List<Integer> bookId = userMapper.getBookList();
        userMapper.record(id,bookId);
    }

    @Override
    public VerifyUser verify(String email) {
        return userMapper.verify(email);
    }
    @Override
    public int login(VerifyUser user, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
        //authenticationmanager 认证
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        //未通过返回
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("登陆失败");
        }
        //通过存jwt
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        int id = loginUser.getUser().getId();
        Map<String,Object> payload =new HashMap<>();
        payload.put("id",loginUser.getUser().getId());
        payload.put("authorities","[bl:read]");
        Map<String,Object> header =new HashMap<>();
        header.put("typ","JWT");
        header.put("alg","HS256");
        String s=generate(header,payload);
        response.addHeader("Authorization",s);
        return id;

    }

    @Override
    public int getUserID(String email) {
        return userMapper.getUserId(email);
    }
}
