package com.atguigu.event.events;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Spring Event 异步使用
 * 有些业务场景不需要在一次请求中同步完成，比如邮件发送、短信发送等。
 * 自定义事件
 */
@Data
@AllArgsConstructor
public class MsgEvent {

    /**
     * 该类型事件携带的信息
     */
    public String orderId;
}
