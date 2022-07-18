package com.atguigu.async.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 线程池配置
 * https://juejin.cn/post/7004673495774789640 《@Async注解其实也就这么回事。 》  why技术 lv-5  2021年09月06日 12:31 ·  阅读 6292
 * 线程池确实不能滥用，但是一个项目里面确实是可以有多个自定义线程池的。
 * 根据你的业务场景来划分。
 * 比如举个简单的例子，业务主流程上可以用一个线程池，但是当主流程中的某个环节出问题了，假设需要发送预警短信。
 * 发送预警短信的这个操作，就可以用另外一个线程池来做。
 * 它们可以共用一个线程池吗？
 * 可以，能用。
 * 但是会出现什么问题呢？
 * 假设项目中某个业务出问题了，在不断的，疯狂的发送预警短信，甚至把线程池都占满了。
 * 这个时候如果主流程的业务和发送短信用的是同一个线程池，会出现什么美丽的场景？
 * 是不是一提交任务，就直接走到拒绝策略里面去了？
 * 预警短信发送这个附属功能，导致了业务不可以，本末倒置的了吧？
 * 所以，建议使用两个不同的线程池，各司其职。
 * 这其实就是听起来很高大上的线程池隔离技术。
 * 那么落到 @Async 注解上是怎么回事呢？
 * 其实就是这样的：
 * 作者：why技术
 * 链接：https://juejin.cn/post/7004673495774789640
 * 来源：稀土掘金
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
@Configuration
@ConfigurationProperties(prefix = "threadpool")
public class ThreadPoolConfig {


    @Bean("whyThreadPool")
    public Executor whyThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程池大小
        executor.setCorePoolSize(8);
        // 最大线程数
        executor.setMaxPoolSize(12);
        // 队列容量
        executor.setQueueCapacity(100);
        // 线程活跃时间
        executor.setKeepAliveSeconds(60);
        // 线程名字前缀
        executor.setThreadNamePrefix("whyThreadPool-");
        executor.initialize();
        return executor;
    }
}
