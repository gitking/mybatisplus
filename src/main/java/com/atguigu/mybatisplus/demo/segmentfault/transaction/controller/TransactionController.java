package com.atguigu.mybatisplus.demo.segmentfault.transaction.controller;

import com.atguigu.mybatisplus.demo.segmentfault.transaction.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * https://segmentfault.com/a/1190000013341344 《Spring事务传播行为详解》 JerryTse 发布于 2018-02-23
 *
 * Spring在TransactionDefinition接口中规定了7种类型的事务传播行为。事务传播行为是Spring框架独有的事务增强特性，他不属于的事务实际提供方数据库行为。
 * 这是Spring为我们提供的强大的工具箱，使用事务传播行可以为我们的开发工作提供许多便利。但是人们对他的误解也颇多，你一定也听过“service方法事务最好不要嵌套”的传言。
 * 要想正确的使用工具首先需要了解工具。本文对七种事务传播行为做详细介绍，内容主要代码示例的方式呈现。
 * 基础概念
 *  1. 什么是事务传播行为？
 *      事务传播行为用来描述由某一个事务传播行为修饰的方法被嵌套进另一个方法的时事务如何传播。
 *      用伪代码说明：
 *       public void methodA(){
 *              methodB();
 *              //doSomething
 *          }
 *      @Transaction(Propagation=XXX)
 *     public void methodB(){
 *          //doSomething
 *      }
 *  代码中methodA()方法嵌套调用了methodB()方法，methodB()的事务传播行为由@Transaction(Propagation=XXX)设置决定。这里需要注意的是methodA()并没有开启事务，某一个事务传播行为修饰的方法并不是必须要在开启事务的外围方法中调用。
 * 2. Spring中七种事务传播行为
 *      事务传播行为类型 	                    说明
 *      PROPAGATION_REQUIRED 	如果当前没有事务，就新建一个事务，如果已经存在一个事务中，加入到这个事务中。这是最常见的选择。
 *      PROPAGATION_SUPPORTS 	支持当前事务，如果当前没有事务，就以非事务方式执行。
 *      PROPAGATION_MANDATORY 	使用当前的事务，如果当前没有事务，就抛出异常。
 *      PROPAGATION_REQUIRES_NEW 	新建事务，如果当前存在事务，把当前事务挂起。
 *      PROPAGATION_NOT_SUPPORTED 	以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。
 *      PROPAGATION_NEVER 	以非事务方式执行，如果当前存在事务，则抛出异常。
 *      PROPAGATION_NESTED 	如果当前存在事务，则在嵌套事务内执行。如果当前没有事务，则执行与PROPAGATION_REQUIRED类似的操作。
 *  定义非常简单，也很好理解，下面我们就进入代码测试部分，验证我们的理解是否正确。
 *  代码验证
 *  文中代码以传统三层结构中两层呈现，即Service和Dao层，由Spring负责依赖注入和注解式事务管理，DAO层由Mybatis实现，你也可以使用任何喜欢的方式，例如，Hibernate,JPA,JDBCTemplate等。数据库使用的是MySQL数据库，你也可以使用任何支持事务的数据库，并不会影响验证结果。
 *  首先我们在数据库中创建两张表：
 *  user1
 *  CREATE TABLE `user1` (
 *   `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
 *   `name` VARCHAR(45) NOT NULL DEFAULT '',
 *   PRIMARY KEY(`id`)
 * )
 * ENGINE = InnoDB;
 *
 * user2
 * CREATE TABLE `user2` (
 *   `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
 *   `name` VARCHAR(45) NOT NULL DEFAULT '',
 *   PRIMARY KEY(`id`)
 * )
 * ENGINE = InnoDB;
 *
 * 然后编写相应的Bean和DAO层代码：
 *
 */
@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private CommonService commonService;

    @PostMapping("/test01")
    public void testTransaction01() {
        commonService.notransaction_exception_required_required();
    }

    @PostMapping("/test02")
    public void testTransaction02() {
        commonService.notransaction_required_required_exception();
    }

    /**
     * 测试场景02:
     * 外围方法开启事务，这个是使用率比较高的场景。
     *
     */
    @PostMapping("/testCase02_01")
    public void testCase02_01(){
        commonService.transaction_exception_required_required();
    }

    /**
     * 测试场景02:
     * 外围方法开启事务，这个是使用率比较高的场景。
     *
     */
    @PostMapping("/testCase02_02")
    public void testCase02_02(){
        commonService.transaction_required_required_exception();
    }

    /**
     * 测试场景02:
     * 外围方法开启事务，这个是使用率比较高的场景。
     *
     */
    @PostMapping("/testCase02_03")
    public void testCase02_03(){
        commonService.transaction_required_required_exception_try();
    }

    @PostMapping("/testRequiresNewCase01_01")
    public void testRequiresNewCase01_01() {
        commonService.notransaction_exception_requiresNew_requiresNew();
    }

    @PostMapping("/testRequiresNewCase01_02")
    public void testRequiresNewCase01_02() {
        commonService.notrandaction_requiresNew_requiresNew_exception();
    }

    @PostMapping("/testRequiresNewCase02_01")
    public void testRequiresNewCase02_01() {
        commonService.transaction_exception_required_requiresNew_requiresNew();
    }

    @PostMapping("/testRequiresNewCase02_02")
    public void testRequiresNewCase02_02() {
        commonService.transaction_required_requiresNew_requiresNew_exception();
    }

    @PostMapping("/testRequiresNewCase02_03")
    public void testRequiresNewCase02_03() {
        commonService.transaction_required_requiresNew_requiresNew_exception_try();
    }

    @PostMapping("/testNestedCase01_01")
    public void testNestedCase01_01(){
        commonService.notransaction_exception_nested_nested();
    }

    @PostMapping("/testNestedCase01_02")
    public void testNestedCase01_02(){
        commonService.notransaction_nested_nested_exception();
    }

    @PostMapping("/testNestedCase02_01")
    public void testNestedCase02_01() {
        commonService.transaction_exception_nested_nested();
    }

    @PostMapping("/testNestedCase02_02")
    public void testNestedCase02_02() {
        commonService.transaction_nested_nested_exception();
    }

    @PostMapping("/testNestedCase02_03")
    public void testNestedCase02_03() {
        commonService.transaction_nested_nested_exception_try();
    }
}
