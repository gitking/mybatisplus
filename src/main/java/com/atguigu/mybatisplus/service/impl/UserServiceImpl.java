package com.atguigu.mybatisplus.service.impl;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.atguigu.mybatisplus.service.UserService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * ServiceImpl是MyBatisPlus提供的一个类，这个类里面已经有很多好用的方法了。跟MyBatisPlus的通用BaseMapper差不多
 * MyBatisPlus的官方文档: https://www.mybatis-plus.com/guide/crud-interface.html#service-crud-%E6%8E%A5%E5%8F%A3
 *
 * @DS 可以注解在方法上或类上，同时存在就近原则 方法上注解 优先于 类上注解。没有@DS注解会使用默认数据源
 * https://www.mybatis-plus.com/guide/dynamic-datasource.html#%E6%96%87%E6%A1%A3-documentation
 *
 */
@DS("master")//指定数据源
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>implements UserService {
}
