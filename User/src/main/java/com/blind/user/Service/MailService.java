package com.blind.user.Service;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("mail-service")
public interface MailService {
    @GetMapping("/sendEmail")
    String sendMail(@RequestParam("email") String email, @RequestParam("verifyCode") String code);
    @PostMapping("test1")
    String test();
}
