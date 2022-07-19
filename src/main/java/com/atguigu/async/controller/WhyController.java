package com.atguigu.async.controller;

import com.atguigu.async.service.WhyAsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/WhyController")
public class WhyController {

    @Resource
    private WhyAsyncService whyAsyncService;

    @GetMapping("/test")
    public String test() throws InterruptedException {
        log.info(Thread.currentThread().getName() + "-> WhyAsyncService");
        for (int i=0;i<99;i++) {
            whyAsyncService.testAsyncInnerMethodSolve();
        }
        return "AopContext.currentProxy()可以解决在本类中的方法调用其他方法";
    }
}
