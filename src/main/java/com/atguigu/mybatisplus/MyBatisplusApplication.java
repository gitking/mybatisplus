package com.atguigu.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
// 扫描mapper接口所在的包
@MapperScan("com.atguigu.mybatisplus.mapper")
public class MyBatisplusApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyBatisplusApplication.class, args);
    }
}
