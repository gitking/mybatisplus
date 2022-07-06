package com.atguigu.mybatisplus.demo.segmentfault.transaction.service;

import com.atguigu.mybatisplus.demo.segmentfault.transaction.pojo.User1;
import com.baomidou.mybatisplus.extension.service.IService;

public interface User1Service extends IService<User1> {

    public void addRequired(User1 user1);

    public void addRequiresNew(User1 user1);
}
