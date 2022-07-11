package com.atguigu.mybatisplus.jobtask.xxljob;

import com.atguigu.mybatisplus.pojo.User;
import com.atguigu.mybatisplus.service.UserService;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * https://juejin.cn/post/6923508824758288398  小杰博士 《xxl-job快速入门指南》
 *
 * 开发步骤:
 * 1. 在SpringBoot实例中,开发job方法,方式格式要求为: “public ReturnT<String> execute(String param)”
 * 2. 为job方法添加注解"@XxlJob(value="自定义jobhandler名称", init = "JobHandler初始化方法", destroy="JobHandler销毁方法")"，注解value值对应
 * 3. 执行日志: 需要通过"XxlJobLogger.log" 打印执行日志
 * @since 2022年6月27日22:25:29
 */
@Component
public class SimpleJobHandler {

    @Value("${server.port:没有获取到端口}")
    private String port;

    @Autowired
    UserService userService;

    /**
     * 简单任务示例(Bean模式)
     * @throws InterruptedException
     */
    @XxlJob(value = "demoJobHandler", init = "", destroy = "")
    public void testXxlJob() throws InterruptedException {
        // 模拟业务的执行
//        for(int i=0; i<5; i++){
////            XxlJobLogger.log("hello xxl-job 许雪里JOB" + i);
//            // 打印日志的标准方式
//            XxlJobHelper.log("hello xxl-job 许雪里JOB-牛逼啊" + i);
//            TimeUnit.SECONDS.sleep(2);
//            System.out.println("XXL-JOB定时任务被调度中心执行了" + port);
//        }
                    TimeUnit.SECONDS.sleep(6);
        XxlJobHelper.log("hello xxl-job 许雪里JOB-牛逼啊,定时任务执行超时会怎么办？" );
    }

    /**
     * XxlJob默认支持Spring事务的传播性
     */
    @XxlJob(value = "testTransaction")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTransaction() {
        try {
            XxlJobHelper.log("hello xxl-job 加上SpringBoot @Transactional 事务");
            User user = new User();
            user.setName("测试XXL-job事务异常");
            userService.save(user);
            int ss = 1/0;
        } catch (Exception e) {
            XxlJobHelper.log("hello xxl-job 加上SpringBoot @Transactional 事务，抛异常", e);
            throw e;
        }
    }

    @XxlJob(value = "testTransaction01")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTransaction01() {
        try {
            XxlJobHelper.log("hello xxl-job 加上SpringBoot @Transactional 事务,调用私有方法,testTransaction01");
            User user = new User();
            user.setName("测试XXL-job事务调用私有方法，异常啊");
            userService.save(user);
            saveUser();
        } catch (Exception e) {
            XxlJobHelper.log("hello xxl-job 加上SpringBoot @Transactional 事务，抛异常，异常啊testTransaction01", e);
            throw e;
        }
    }

    private void saveUser() {
        XxlJobHelper.log("hello xxl-job 加上SpringBoot @Transactional 事务");
        User user = new User();
        user.setName("测试XXL-job事务私有方法里面的能保存进行吗，异常啊");
        userService.save(user);
//        int ss = 1/0;
    }
}
