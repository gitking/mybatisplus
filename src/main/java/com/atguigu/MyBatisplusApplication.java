package com.atguigu;

import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 学习MyBatis-Plus的使用
 * 学习视频资料: https://www.bilibili.com/video/BV12R4y157Be?p=7&spm_id_from=pageDriver&vd_source=acb9b66ebe2b9150f655070a930aeb12
 * 《【尚硅谷】2022版MyBatisPlus教程（一套玩转mybatis-plus）》
 *
 * 在SpringBoot中使用MyBatis的时候，一定要设置我们当前Mapper接口所在的包以及mapper映射文件所在的包，所以需要在启动类上面添加@MapperScan
 * @author wyl
 * @date 2022-06-25 15:45:00
 */
@SpringBootApplication
@EnableScheduling// 启动定时任务
@EnableAsync // 启动异步任务
@EnableAspectJAutoProxy(exposeProxy = true,proxyTargetClass = true) // 暴露当前代理对象到当前线程绑定,https://cloud.tencent.com/developer/article/1497700 《从@Async案例找到Spring框架的bug：exposeProxy=true不生效原因大剖析+最佳解决方案【享学Spring】》
public class MyBatisplusApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyBatisplusApplication.class, args);
    }

    /**
     * 快速失败(Fail Fast)
     * Spring Validation默认会校验完所有字段，然后才抛出异常。可以通过一些简单的配置，开启Fali Fast模式，一旦校验失败就立即返回。
     * @return
     */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 快速失败模式
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
