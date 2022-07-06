package com.atguigu.mybatisplus.demo.segmentfault.transaction.service;

public interface CommonService {

    /**
     * 测试场景01:
     * 我们为User1Service和User2Service相应方法加上Propagation.REQUIRED属性。
     * 此场景外围方法没有开启事务。
     * 测试：PROPAGATION_REQUIRED，如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。这是最常见的选择。
     *
     * 验证方法1：
     */
    public void notransaction_exception_required_required();

    /**
     * 验证方法2：
     *
     */
    public void notransaction_required_required_exception();

    /**
     * 测试场景02:
     * 外围方法开启事务，这个是使用率比较高的场景。
     *
     */
    public void transaction_exception_required_required();

    /**
     * 测试场景02:
     * 外围方法开启事务，这个是使用率比较高的场景。
     *
     */
    public void transaction_required_required_exception();

    /**
     * 测试场景02:
     * 外围方法开启事务，这个是使用率比较高的场景。
     *
     */
    public void transaction_required_required_exception_try();


    public void notransaction_exception_requiresNew_requiresNew();

    public void notrandaction_requiresNew_requiresNew_exception();

    public void transaction_exception_required_requiresNew_requiresNew();
    public void transaction_required_requiresNew_requiresNew_exception();

    public void transaction_required_requiresNew_requiresNew_exception_try();
}
