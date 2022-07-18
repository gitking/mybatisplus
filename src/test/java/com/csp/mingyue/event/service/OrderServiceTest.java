package com.csp.mingyue.event.service;

import com.atguigu.event.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 这个测试类的包路径com.csp.mingyue.event.service 跟OrderService的包路径不一样，所以单测启动失败，必须一样才能启动成。
 * com.atguigu.event.service.OrderServiceTest这个单测就可以启动成功。
 */
@SpringBootTest
public class OrderServiceTest {
    @Autowired private OrderService orderService;

    @Test
    public void buyOrderTest() {
        orderService.buyOrder("732171109");
    }
}
