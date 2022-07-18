package com.atguigu.event.listener;

import com.atguigu.event.events.MsgEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * https://mp.weixin.qq.com/s/RNsuFaONmruEnyJGKad5sA
 * https://ningyu1.github.io/20190505/116-stop-watch.html 《使用StopWatch优雅的输出执行耗时》
 *
 * 定义监听器
 * 推荐使用 @EventListener 注解：
 */
@Slf4j
@Component
public class MsgListener {

    @Async
    @SneakyThrows
    @EventListener(MsgEvent.class)
    public void sendMsg(MsgEvent event){
        String orderId = event.getOrderId();
        StopWatch sw = new StopWatch();
        sw.start();
        log.info("开始异步发送短信");
        log.info("开始异步发送邮件");
        Thread.sleep(4000);
        sw.stop();
        log.info("{}:发送短信，邮件耗时：({})毫秒", orderId, sw.getTotalTimeMillis());
    }
}
