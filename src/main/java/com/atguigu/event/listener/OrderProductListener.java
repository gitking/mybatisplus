package com.atguigu.event.listener;

import com.atguigu.event.events.OrderProductEvent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * 定义监听器
 * 实现ApplicationListener接口，并指定监听的事件类型
 *
 * 监听并处理事件，实现 ApplicationListener 接口或者使用 @EventListener 注解：
 */
@Slf4j
@Component
public class OrderProductListener implements ApplicationListener<OrderProductEvent> {

    /**
     * 使用onApplicationEvent方法对消息进行接收处理
     * @param event the event to respond to
     */
    @SneakyThrows
    @Override
    public void onApplicationEvent(OrderProductEvent event) {
        String orderId = event.getOrderId();
        StopWatch sw = new StopWatch();
        sw.start();
        Thread.sleep(2000);
        sw.stop();
        log.info("{}:校验订单商品价格耗时：({})毫秒", orderId, sw.getTotalTimeMillis());
    }
}
