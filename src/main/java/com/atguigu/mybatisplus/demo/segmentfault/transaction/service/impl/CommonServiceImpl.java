package com.atguigu.mybatisplus.demo.segmentfault.transaction.service.impl;

import com.atguigu.mybatisplus.demo.segmentfault.transaction.pojo.User1;
import com.atguigu.mybatisplus.demo.segmentfault.transaction.pojo.User2;
import com.atguigu.mybatisplus.demo.segmentfault.transaction.service.CommonService;
import com.atguigu.mybatisplus.demo.segmentfault.transaction.service.User1Service;
import com.atguigu.mybatisplus.demo.segmentfault.transaction.service.User2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * https://segmentfault.com/a/1190000013341344 《Spring事务传播行为详解》 JerryTse 发布于 2018-02-23
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private User1Service user1Service;

    @Autowired
    private User2Service user2Service;

    /**
     * 测试场景01:
     *
     * 我们为User1Service和User2Service相应方法加上Propagation.REQUIRED属性。
     * 此场景外围方法没有开启事务。
     * 测试：PROPAGATION_REQUIRED，如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。这是最常见的选择。
     *
     * 验证方法1：
     * 分别执行验证方法，结果：
     * “张三”、“李四”均插入。
     * 外围方法未开启事务，插入“张三”、“李四”方法在自己的事务中独立运行，外围方法异常不影响内部插入“张三”、“李四”方法独立的事务。
     *
     * 结论：通过这两个方法我们证明了在外围方法未开启事务的情况下Propagation.REQUIRED修饰的内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰。
     */
    @Override
    public void notransaction_exception_required_required() {
        User1 user1 = new User1();
        user1.setName("张三,验证方法1,Propagation.REQUIRED");
        user1Service.addRequired(user1);

        User2 user2 = new User2();
        user2.setName("李四,验证方法1,Propagation.REQUIRED");
        user2Service.addRequired(user2);
        throw new RuntimeException();
    }

    /**
     * 测试场景01:
     * 验证方法2：
     *
     * 分别执行验证方法，结果：
     *  “张三”插入，“李四”未插入。
     *  外围方法没有事务，插入“张三”、“李四”方法都在自己的事务中独立运行,所以插入“李四”方法抛出异常只会回滚插入“李四”方法，插入“张三”方法不受影响。
     * 结论：通过这两个方法我们证明了在外围方法未开启事务的情况下Propagation.REQUIRED修饰的内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰。
     */
    @Override
    public void notransaction_required_required_exception() {
        User1 user1 = new User1();
        user1.setName("张三,验证方法2,Propagation.REQUIRED");
        user1Service.addRequired(user1);

        User2 user2 = new User2();
        user2.setName("李四,验证方法2,Propagation.REQUIRED");
        user2Service.addRequiredException(user2);
    }
}
