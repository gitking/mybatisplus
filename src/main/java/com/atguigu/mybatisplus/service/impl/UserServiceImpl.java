package com.atguigu.mybatisplus.service.impl;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.atguigu.mybatisplus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * ServiceImpl是MyBatisPlus提供的一个类，这个类里面已经有很多好用的方法了。跟MyBatisPlus的通用BaseMapper差不多
 * MyBatisPlus的官方文档: https://www.mybatis-plus.com/guide/crud-interface.html#service-crud-%E6%8E%A5%E5%8F%A3
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>implements UserService {
}
