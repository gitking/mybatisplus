package com.atguigu.mybatisplus.demo.segmentfault.transaction.service.impl;

import com.atguigu.mybatisplus.demo.segmentfault.transaction.pojo.User2;
import com.atguigu.mybatisplus.demo.segmentfault.transaction.mapper.User2Mapper;
import com.atguigu.mybatisplus.demo.segmentfault.transaction.service.User2Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class User2ServiceImpl extends ServiceImpl<User2Mapper, User2> implements User2Service {


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequired(User2 user) {
        baseMapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addRequiredException(User2 user){
        baseMapper.insert(user);
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequiresNew(User2 user2) {
        baseMapper.insert(user2);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addRequiresNewException(User2 user2){
        baseMapper.insert(user2);
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void addNested(User2 user){
        baseMapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void addNestedException(User2 user2){
        baseMapper.insert(user2);
        throw new RuntimeException();
    }
}
