package com.atguigu.mybatisplus.spring.validation.controller;

import com.atguigu.mybatisplus.spring.validation.base.Result;
import com.atguigu.mybatisplus.spring.validation.http.HttpDegradeApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RequestMapping("/api/degrade")
@RestController
public class DegradeController {

    @Autowired
    HttpDegradeApi httpDegradeApi;

    @GetMapping("/test")
    public Result test() throws InterruptedException {
        // 随机数
        Random random = new Random(System.currentTimeMillis());
        int i = random.nextInt(1_000);
        Thread.sleep(i);
        return Result.ok();
    }
}
