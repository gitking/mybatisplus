package com.atguigu.mybatisplus.service;

import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * MyBatisplus提供的通用Service接口IService,这个IService接口mybatisplus有一个默认的实现类ServiceImpl。
 * MyBatisPlus的官方文档: https://www.mybatis-plus.com/guide/crud-interface.html#service-crud-%E6%8E%A5%E5%8F%A3
 *
 */
public interface UserService extends IService<User> {

}
