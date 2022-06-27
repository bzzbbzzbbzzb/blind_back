package com.blind.user.Controller;
import com.blind.api.Dto.User;
import com.blind.user.Domain.CommonResult;
import com.blind.user.Domain.VerifyUser;
import com.blind.user.Service.MailService;
import com.blind.user.Service.impl.SearchUserServiceImpl;
import com.blind.user.Service.impl.UserServiceImpl;
import com.blind.user.repository.BookMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    MailService mailService;
    @Resource
    StringRedisTemplate stringRedisTemplate;
    @Resource
    SearchUserServiceImpl searchUserService;
    @Resource
    UserServiceImpl userService;

    @PostMapping("/registry")
    public CommonResult registry(@RequestBody User user) {
        int i = new Random().nextInt(1000000);
        String code = String.format("%06d", i);
        stringRedisTemplate.opsForValue().set(user.getEmail(), code, 300, TimeUnit.SECONDS);
        mailService.sendMail(user.getEmail(), code);
        return new CommonResult().success().message("发送成功");
    }

    @PostMapping("/verify")
    public CommonResult verify(@RequestBody User user) {
        if (searchUserService.searchUser(user.getEmail()) == 0) { //无重复邮箱
            if (user.getCode().equals(stringRedisTemplate.opsForValue().get(user.getEmail()))) {

                    userService.registry(user);
                    return new CommonResult().success().message("注册成功");
            } else {
                return new CommonResult().fail().message("验证码过期");
            }
        } else {
            return new CommonResult().fail().message("邮箱重复");
        }
    }

    @PostMapping("login")
    public CommonResult login(@RequestBody VerifyUser user, HttpServletResponse response) {
        int id = userService.login(user,response);
        return new CommonResult().success().message("登陆成功").put("userId",id);
    }

    @PostMapping("/test")
    @PreAuthorize("hasAuthority('bl:read')")
    public CommonResult test() {
        return new CommonResult().success().message("测试成功");
    }

}
