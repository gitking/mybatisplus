package com.atguigu.init.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

//@Order(Ordered.HIGHEST_PRECEDENCE)
@Order(2)
@Component
public class ServerStartedReport implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=====================ServerStartedReport启动了===========================" + LocalDateTime.now());
        System.out.println("注意看CommandLineRunner和ApplicationRunner 这俩个接口哪个先启动？？？？？跟@Order有关系吗？");
        System.out.println("Order(1)的值越小越先执行，如果Order的值一样,哪个类先被加载就先执行哪个，可以参考Ordered.HIGHEST_PRECEDENCE");
    }
}
