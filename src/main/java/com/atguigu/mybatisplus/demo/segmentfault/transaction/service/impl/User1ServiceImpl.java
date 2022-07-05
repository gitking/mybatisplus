package com.atguigu.mybatisplus.demo.segmentfault.transaction.service.impl;

import com.atguigu.mybatisplus.demo.segmentfault.transaction.pojo.User1;
import com.atguigu.mybatisplus.demo.segmentfault.transaction.mapper.User1Mapper;
import com.atguigu.mybatisplus.demo.segmentfault.transaction.service.User1Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class User1ServiceImpl extends ServiceImpl<User1Mapper, User1> implements User1Service {

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequired(User1 user){
        baseMapper.insert(user);
    }
}
