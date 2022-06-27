package com.blind.mail.Controller;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@RestController
public class MailController {
    @Resource
    private JavaMailSender javaMailSender;
    static String bodyAhead = "<html><head></head><body><h1><p>您的验证码是:<font color=\"blue\">";
    static String bodyAfter="</font>,验证码有效时间为五分钟</p></h1></body></html>";
    private String te;
    @GetMapping("/sendEmail")
    public int sendMail(@RequestParam("email") String email,@RequestParam("verifyCode") String code){
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setFrom("2398541732@qq.com");
            mimeMessageHelper.setSubject("账号注册");
            //true 表示启动HTML格式的邮件
            mimeMessageHelper.setText(bodyAhead+code+bodyAfter,true);
            javaMailSender.send(mimeMessage);
            return 1;
        } catch (MessagingException e) {
            return 0;
        }

    }
}

