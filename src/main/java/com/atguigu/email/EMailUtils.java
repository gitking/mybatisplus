package com.atguigu.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class EMailUtils {

    /**
     * org.springframework.boot.autoconfigure.mail.MailProperties spring-boot-autoconfigure-2.6.3.jar
     * spring.factories
     * org.springframework.boot.autoconfigure.EnableAutoConfiguration下面有俩个跟mail有关的类
     * org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration,\
     * org.springframework.boot.autoconfigure.mail.MailSenderValidatorAutoConfiguration,\
     *
     * JavaMailSenderImpl这个类是由org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration自动创建的,
     * org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration这个类自动导入了MailSenderAutoConfiguration
     * spring-boot-autoconfigure-2.6.3.jar
     */
    @Autowired
    private JavaMailSenderImpl javaMailSender;

}
