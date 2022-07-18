package com.atguigu.event.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * https://mp.weixin.qq.com/s/HcwXnrqYvmUB5bdaBMLrXQ 《Spring Event，贼好用的业务解耦神器！》 IT码徒 公众号 2022-07-16 21:48 发表于河南
 * Demo地址: https://gitee.com/csps/mingyue-springboot-learning
 *
 * 实际业务开发过程中，业务逻辑可能非常复杂，核心业务 + N 个子业务。如果都放到一块儿去做，代码可能会很长，耦合度不断攀升，维护起来也麻烦，甚至头疼。还有一些业务场景不需要在一次请求中同步完成，比如邮件发送、短信发送等。
 * MQ 确实可以解决这个问题，但 MQ 重啊，非必要不提升架构复杂度。针对这些问题，我们了解一下 Spring Event。
 * Spring Event（Application Event）其实就是一个观察者设计模式，一个 Bean 处理完成任务后希望通知其它 Bean 或者说一个 Bean 想观察监听另一个Bean 的行为。
 * Spring Event 用来解耦业务真的贼好用！
 *
 */
@SpringBootTest
public class OrderServiceTest {
    @Autowired private OrderService orderService;

    @Test
    public void buyOrderTest() {
        orderService.buyOrder("732171109");
    }
}
