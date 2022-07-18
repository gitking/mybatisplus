package com.atguigu.async.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class WhyAsyncService {

    @Async(value = "whyThreadPool")
    public void testAsync() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        log.info(Thread.currentThread().getName() + "你看看我是不是异步的呢？");
    }

    public void testAsyncInnerMethod() throws InterruptedException {
        log.info("我在我的方法内部调用异步方法，还有用吗？");
        // 调了这个异步方法不起作用，解决办法看testAsyncInnerMethodSolve
        testAsync();
    }

    /**
     * https://blog.csdn.net/fanxb92/article/details/81296005 《Spring是如何管理事务的之@Transactional注解详解》
     * https://blog.csdn.net/mameng1988/article/details/85548812 《Cannot find current proxy: Set 'exposeProxy' property on Advised to 'true' to 以及Spring事务失效的原因和解决方案》
     *
     * 这个还是报错，没有解决啊
     * @throws InterruptedException
     */
    public void testAsyncInnerMethodSolve() throws InterruptedException {
        log.info("我在我的方法内部调用异步方法，还有用吗？");
        // 调了这个异步方法不起作用，解决办法看testAsyncInnerMethodSolve
        WhyAsyncService currentProxy = (WhyAsyncService) AopContext.currentProxy();
        currentProxy.testAsync();
    }
}
