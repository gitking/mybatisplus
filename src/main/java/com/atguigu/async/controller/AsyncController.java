package com.atguigu.async.controller;

import com.atguigu.async.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * https://juejin.cn/post/7004673495774789640 《@Async注解其实也就这么回事。 》  why技术 lv-5  2021年09月06日 12:31 ·  阅读 6292
 *
 */
@Slf4j
@RestController
@RequestMapping("/asnyc")
public class AsyncController {

    @Autowired
    private AsyncService asyncService;

    @PostMapping("testAsync")
    public String testAsync(int num) throws InterruptedException {
        log.info(Thread.currentThread().getName() + "-->AsyncControoler");
        for (int i =0; i<num; i++) {
            asyncService.testAsync();
        }
        return "AsyncControoler执行已经成功了，异步方法还在执行,说明真的异步执行了";
    }

    @PostMapping("/testAsyncInnerMethod")
    public String testAsyncInnerMethod(int num) throws InterruptedException {
        log.info(Thread.currentThread().getName() + "--> testAsyncInnerMethod");
        for (int i =0; i<num; i++) {
            asyncService.testAsyncInnerMethod();
        }
        return "这个异步方法没有生效，再调用本类中的其他方法method2时，那么method2方法上的@Async注解是不！会！生！效！的！";
    }

    @PostMapping("/testAsyncInnerMethodSolve")
    public String testAsyncInnerMethodSolve(int num) throws InterruptedException {
        log.info(Thread.currentThread().getName() + "--> testAsyncInnerMethod");
        for (int i =0; i<num; i++) {
            asyncService.testAsyncInnerMethodSolve();
        }
        return "AopContext.currentProxy()可以解决在本类中的方法调用其他方法";
    }
}
