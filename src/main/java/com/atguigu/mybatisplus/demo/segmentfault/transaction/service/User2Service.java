package com.atguigu.mybatisplus.demo.segmentfault.transaction.service;

import com.atguigu.mybatisplus.demo.segmentfault.transaction.pojo.User2;
import com.baomidou.mybatisplus.extension.service.IService;

public interface User2Service extends IService<User2> {

    public void addRequired(User2 user2);

    public void addRequiredException(User2 user2);
}
