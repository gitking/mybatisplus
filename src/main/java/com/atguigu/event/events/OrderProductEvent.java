package com.atguigu.event.events;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * https://mp.weixin.qq.com/s/HcwXnrqYvmUB5bdaBMLrXQ 《Spring Event，贼好用的业务解耦神器！》 IT码徒 公众号 2022-07-16 21:48 发表于河南
 * Demo地址: https://gitee.com/csps/mingyue-springboot-learning
 * 自定义事件
 * 定义事件，继承 ApplicationEvent 的类成为一个事件类：
 */
@ToString
public class OrderProductEvent extends ApplicationEvent {

    /**
     * 该类型事件携带的信息
     */
    @Getter
    @Setter
    private String orderId;

    public OrderProductEvent(Object source, String orderId){
        super(source);
        this.orderId = orderId;
    }
}
