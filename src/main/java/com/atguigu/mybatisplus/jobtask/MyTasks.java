package com.atguigu.mybatisplus.jobtask;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 *
 * B站: https://www.bilibili.com/video/BV12U4y157TW?spm_id_from=333.999.0.0&vd_source=acb9b66ebe2b9150f655070a930aeb12
 * 《【千锋教育Java公开课】分布式任务调度平台XXL-JOB》
 * B站: https://www.bilibili.com/video/BV13Y411p7BC/?spm_id_from=333.788.recommend_more_video.-1&vd_source=acb9b66ebe2b9150f655070a930aeb12
 * 《B站最牛,独家教程】-分布式任务调度平台 XXL-JOB教程全集【干货讲解】》
 * SpringBoot自带的定时任务有几个弊端
 * 1. 分布式领域中服务器集群的情况下,如何保证job的幂等性?
 * 2. 任务异常结束怎么办？
 * 3. 前一个任务执行超时了，下一个任务怎么办？
 * 4. 没有一个友好的可视化界面
 */
@Component
public class MyTasks {

    private int count;
    /**
     * 注意cron里周的1-7对应周日至周六
     * 直接用SUN-SAT代替1-7
     * 1:SUN 2:MON 3:TUE 4:WED 5:THU 6:FRI 7:SAT
     * https://blog.csdn.net/weixin_43090494/article/details/105294823 《cron表达式：按周执行时需留意》
     */
    @Scheduled(cron="0 6 19 ? * SUN")
    public void task01() {
        /**
         * 1. 在主启动类上面加上注解@EnableScheduling
         * 2. 在这个类上面加上@Component
         * 3. 在这个方法上面加上@Scheduled(cron="0 6 19 ? * SUN")
         * 4. 启动项目，到时间这个定时任务就会被执行了。
         */
        System.out.println("hello scheduled 定时任务");
    }

    @Scheduled(cron = "* */2 * * * ?")
    public void task02() {
        count = count + 1;
        System.out.println("开始执行了                     " + new Date() + ":" + count);
        try {
            // TODO 问题待解决，如果执行超过3秒,会暂停本次执行，等待下一次定时任务来临时，继续执行上一次的任务。
            Thread.sleep(3100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("*/3 * * * * ?,每隔3秒钟就执行一次" + new Date() + ":" + count);
    }
}
