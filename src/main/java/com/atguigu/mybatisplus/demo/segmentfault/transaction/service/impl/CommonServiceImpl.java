package com.atguigu.mybatisplus.demo.segmentfault.transaction.service.impl;

import com.atguigu.mybatisplus.demo.segmentfault.transaction.pojo.User1;
import com.atguigu.mybatisplus.demo.segmentfault.transaction.pojo.User2;
import com.atguigu.mybatisplus.demo.segmentfault.transaction.service.CommonService;
import com.atguigu.mybatisplus.demo.segmentfault.transaction.service.User1Service;
import com.atguigu.mybatisplus.demo.segmentfault.transaction.service.User2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * https://segmentfault.com/a/1190000013341344 《Spring事务传播行为详解》 JerryTse 发布于 2018-02-23
 *
 *
 * 其他事务传播行为
 * 鉴于文章篇幅问题，其他事务传播行为的测试就不在此一一描述了，感兴趣的读者可以去源码中自己寻找相应测试代码和结果解释。
 * 传送门：https://github.com/TmTse/transaction-test
 * README.md
 * transaction-test
 * 事务的一些实例，主要用spring,Mybatis,Mysql,atomikos搭建，可用于项目脚手架或验证一些代码特性。
 * 项目采用maven编译：
 * transaction-test（父项目）
 * transaction-test-local-transaction-mybatis（本地事务，事务传播行为）
 * transaction-test-global-transaction-mybatis（全局事务，分布式事务）
 * 每个项目中src/main/resources路径下都有对应的SQL文件，运行src/test/java路径下Junit测试方法前需要先执行相应的SQL文件。
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

    /**
     * 测试场景02:
     * 外围方法开启事务，这个是使用率比较高的场景。
     * 验证方法01
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transaction_exception_required_required() {
        User1 user1 = new User1();
        user1.setName("张三，测试场景2，方法1，Propagation.REQUIRED");
        user1Service.addRequired(user1);

        User2 user2 = new User2();
        user2.setName("李四，测试场景2，方法1，Propagation.REQUIRED");
        user2Service.addRequired(user2);
        throw new RuntimeException();
    }

    /**
     * 测试场景02:
     * 外围方法开启事务，这个是使用率比较高的场景。
     * 验证方法02
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transaction_required_required_exception() {
        User1 user1 = new User1();
        user1.setName("张三，，测试场景2，方法2，Propagation.REQUIRED");
        user1Service.addRequired(user1);

        User2 user2 = new User2();
        user2.setName("李四，测试场景2，方法2，Propagation.REQUIRED");
        user2Service.addRequiredException(user2);
    }

    /**
     * 测试场景02:
     * 外围方法开启事务，这个是使用率比较高的场景。
     * 验证方法03
     *
     * 分别执行验证方法，结果：
     * 验证方法序号 	数据库结果 	                    结果分析
     * 1 	        “张三”、“李四”均未插入。 	外围方法开启事务，内部方法加入外围方法事务，外围方法回滚，内部方法也要回滚。
     * 2 	        “张三”、“李四”均未插入。 	外围方法开启事务，内部方法加入外围方法事务，内部方法抛出异常回滚，外围方法感知异常致使整体事务回滚。
     * 3 	        “张三”、“李四”均未插入。 	外围方法开启事务，内部方法加入外围方法事务，内部方法抛出异常回滚，即使方法被catch不被外围方法感知，整个事务依然回滚。
     * 结论：以上试验结果我们证明在外围方法开启事务的情况下Propagation.REQUIRED修饰的内部方法会加入到外围方法的事务中，
     * 所有Propagation.REQUIRED修饰的内部方法和外围方法均属于同一事务，只要一个方法回滚，整个事务均回滚。
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transaction_required_required_exception_try() {
        User1 user1 = new User1();
        user1.setName("张三，，测试场景2，方法3，Propagation.REQUIRED");
        user1Service.addRequired(user1);

        User2 user2 = new User2();
        user2.setName("李四，测试场景2，方法3，Propagation.REQUIRED");
        try {
            user2Service.addRequiredException(user2);
        } catch (Exception e) {
            System.out.println("测试场景2，方法3，发生异常但是被catch住，没往外抛");
        }
        /**
         * Transaction rolled back because it has been marked as rollback-only"
         *
         * walker：有个小问题，REQUIRED中的1.2场景二的验证方法3，外围方法把会发生异常的方法try...catch了，确实像楼主说的整个事务会回滚，
         * 但我测试了下回滚其实是因为抛出了另外一个异常：Transaction was marked for rollback only; cannot commit；
         * 我理解为内部方法发生异常时，就已经将该事务标记为了rollbackOnly，必须回滚，但是又因为外部方法将该异常方法捕获了，所以无法感知，就会进行事务提交，最后因为无法提交而抛出该异常。
         * 不知道有没有同学遇到类似的情况。
         * JerryTse（作者）：
         *
         * @walker 这位兄弟说的很对，确实会抛出如你所述的异常。这点我并没有在文章中叙述，感谢指摘。
         * 我简单的Debug了一下spring源码，异常由AbstractPlatformTransactionManager.commit()方法抛出。仔细看commit()的代码逻辑感觉需要全局回滚的时候就会做回滚操作，但是执行回滚操作之后，是因为事务状态为新建而抛出了异常。这里我暂且下一个结论，并不是因为这个异常造成无法提交，而是因为执行完全局回滚之后因为事务状态为新建而抛出异常，至于为什么事务状态为新建由于对源码不够深入并没有搞清楚，可以一起深入探讨。
         * 回复2018-09-18
         * walker：
         *
         * @walker 嗯嗯，我不是说因为内部方法的异常造成无法提交，是因为当前事务已经被标记为rollbackOnly了，所以无法提交。我想我已经在spring官方文档找到了答案。
         * https://docs.spring.io/spring-framework/docs/5.0.9.RELEASE/spring-framework-reference/data-access.html#tx-propagation-required
         * 1回复2018-09-18
         * JerryTse（作者）：
         *
         * @walker 已阅读文档相关部分，收益颇丰，弥补了我此处盲点，感谢分享。
         * 回复2018-09-19
         * walker：
         *
         * @walker 不客气，知识就是要分享才能更好的接受，文章写的很赞！
         * 回复2018-09-19
         * ThinkingAndBraveBoy：
         *
         * @walker 对于这个场景，我的感性的理解是：
         * （1）外部方法并不清楚内部方法抛出了异常，因为被catch掉了
         * （2）此处并不是一个独立的事务，而是运行在外部事务中，而且并没有设置保存点，一旦回滚只能回滚到事务执行之前的状态，也就是全部回滚了
         *
         * walker：
         *
         * @Daneil 这里有一个逻辑事务和物理事务的概念，catch后依然回滚了，是因为内部方法的事务有异常，已经mark为rollbackOnly了，而外围方法将其try...catch了，无法感知还会去commit，实则此时是无法提交事务的，所以会抛出UnexpectedRollbackException异常来告知事务回滚了。
         * 此处疑问可以参考spring官网的解释，很详细：https://docs.spring.io/spring-framework/docs/5.0.9.RELEASE/spring-framework-reference/data-access.html#tx-propagation-required
         * 孙继峰：
         * java try { // 执行目标方法 result = invocation.proceedWithInvocation();} catch (Throwable var17) { // rollback this.completeTransactionAfterThrowing(txInfo, var17); throw var17;} finally { this.cleanupTransactionInfo(txInfo);}这是 TransactionAspectSupport 类中的源码,` javatry {
         * user2Service.addRequiresNewException(user3);
         * } catch (Exception e) {
         * System.out.println(
         *
         * 碎瓦：
         * @Daneil spring官方文档有解释：https://docs.spring.io/spring-framework/docs/current/reference/html/data-access.html#tx-propagation-required
         * 最后一句很重要：When the propagation setting is PROPAGATION_REQUIRED,
         * a logical transaction scope is created for each method upon which the setting is applied. Each such logical transaction scope can determine rollback-only status individually, with an outer transaction scope being logically independent from the inner transaction scope. In the case of standard PROPAGATION_REQUIRED behavior, all these scopes are mapped to the same physical transaction. So a rollback-only marker set in the inner transaction scope does affect the outer transaction’s chance to actually commit.
         *
         * victor：
         *
         * @满是诱惑 参考一下springAOP的实现机制吧：https://blog.csdn.net/aya19880214/article/details/50640596
         * 回复2019-02-12
         * walker：@满是诱惑 @victor 在同一个类中是自我调用，事务注解不会生效的，所以只有外围方法的事务，try catch异常当然不会回滚
         */
    }

    /**
     * 测试PROPAGATION_REQUIRES_NEW场景01
     * 我们为User1Service和User2Service相应方法加上Propagation.REQUIRES_NEW属性。
     * PROPAGATION_REQUIRES_NEW 	新建事务，如果当前存在事务，把当前事务挂起。
     */
    @Override
    public void notransaction_exception_requiresNew_requiresNew() {
        User1 user1 = new User1();
        user1.setName("张三，，测试PROPAGATION_REQUIRES_NEW场景01，方法1，Propagation.REQUIRES_NEW");
        user1Service.addRequiresNew(user1);

        User2 user2 = new User2();
        user2.setName("李四，测试PROPAGATION_REQUIRES_NEW场景01，方法1，Propagation.REQUIRES_NEW");
        user2Service.addRequiresNew(user2);
        throw new RuntimeException();
    }

    /**
     * 测试PROPAGATION_REQUIRES_NEW场景01
     * 我们为User1Service和User2Service相应方法加上Propagation.REQUIRES_NEW属性。
     * PROPAGATION_REQUIRES_NEW 	新建事务，如果当前存在事务，把当前事务挂起。
     *
     * 场景01结论:
     * 分别执行验证方法，结果：
     * 验证方法序号 	    数据库结果 	                    结果分析
     * 1 	        “张三”插入，“李四”插入。 	外围方法没有事务，插入“张三”、“李四”方法都在自己的事务中独立运行,外围方法抛出异常回滚不会影响内部方法。
     * 2 	        “张三”插入，“李四”未插入 	外围方法没有开启事务，插入“张三”方法和插入“李四”方法分别开启自己的事务，插入“李四”方法抛出异常回滚，其他事务不受影响。
     * 结论：通过这两个方法我们证明了在外围方法未开启事务的情况下Propagation.REQUIRES_NEW修饰的内部方法会新开启自己的事务，且开启的事务相互独立，互不干扰。
     */
    @Override
    public void notrandaction_requiresNew_requiresNew_exception() {
        User1 user1 = new User1();
        user1.setName("张三，，测试PROPAGATION_REQUIRES_NEW场景01，方法2，Propagation.REQUIRES_NEW");
        user1Service.addRequiresNew(user1);

        User2 user2 = new User2();
        user2.setName("李四，测试PROPAGATION_REQUIRES_NEW场景01，方法2，Propagation.REQUIRES_NEW");
        user2Service.addRequiresNewException(user2);
    }

    /**
     * 测试PROPAGATION_REQUIRES_NEW场景02:外围方法开启事务。
     * 我们为User1Service和User2Service相应方法加上Propagation.REQUIRES_NEW属性。
     * PROPAGATION_REQUIRES_NEW 	新建事务，如果当前存在事务，把当前事务挂起。
     *
     * 分别执行验证方法，结果：
     * 验证方法序号 	        数据库结果 	                            结果分析
     * 1 	        “张三”未插入，“李四”插入，“王五”插入。 	外围方法开启事务，插入“张三”方法和外围方法一个事务，插入“李四”方法、插入“王五”方法分别在独立的新建事务中，外围方法抛出异常只回滚和外围方法同一事务的方法，故插入“张三”的方法回滚。
     * 2 	        “张三”未插入，“李四”插入，“王五”未插入。 	外围方法开启事务，插入“张三”方法和外围方法一个事务，插入“李四”方法、插入“王五”方法分别在独立的新建事务中。插入“王五”方法抛出异常，首先插入 “王五”方法的事务被回滚，异常继续抛出被外围方法感知，外围方法事务亦被回滚，故插入“张三”方法也被回滚。
     * 3 	        “张三”插入，“李四”插入，“王五”未插入。 	外围方法开启事务，插入“张三”方法和外围方法一个事务，插入“李四”方法、插入“王五”方法分别在独立的新建事务中。插入“王五”方法抛出异常，首先插入“王五”方法的事务被回滚，异常被catch不会被外围方法感知，外围方法事务不回滚，故插入“张三”方法插入成功。
     *
     * 结论：在外围方法开启事务的情况下Propagation.REQUIRES_NEW修饰的内部方法依然会单独开启独立事务，
     * 且与外部方法事务也独立，内部方法之间、内部方法和外部方法事务均相互独立，互不干扰。
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transaction_exception_required_requiresNew_requiresNew(){
        User1 user1 = new User1();
        user1.setName("张三，，测试PROPAGATION_REQUIRES_NEW场景02，方法1，Propagation.REQUIRES_NEW");
        user1Service.addRequired(user1);

        User1 user11 = new User1();
        user11.setName("李四，测试PROPAGATION_REQUIRES_NEW场景02，方法1，Propagation.REQUIRES_NEW");
        user1Service.addRequiresNew(user11);

        User2 user2 = new User2();
        user2.setName("王五，测试PROPAGATION_REQUIRES_NEW场景02，方法1，Propagation.REQUIRES_NEW");
        user2Service.addRequiresNew(user2);
        throw new RuntimeException();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transaction_required_requiresNew_requiresNew_exception(){
        User1 user1 = new User1();
        user1.setName("张三，，测试PROPAGATION_REQUIRES_NEW场景02，方法2，Propagation.REQUIRES_NEW");
        user1Service.addRequired(user1);

        User2 user2 = new User2();
        user2.setName("李四，测试PROPAGATION_REQUIRES_NEW场景02，方法2，Propagation.REQUIRES_NEW");
        user2Service.addRequiresNew(user2);

        User2 user21 = new User2();
        user21.setName("王五，测试PROPAGATION_REQUIRES_NEW场景02，方法2，Propagation.REQUIRES_NEW");
        user2Service.addRequiresNewException(user21);
    }

    /**
     * 测试PROPAGATION_REQUIRES_NEW场景02:外围方法开启事务。
     * 我们为User1Service和User2Service相应方法加上Propagation.REQUIRES_NEW属性。
     * PROPAGATION_REQUIRES_NEW 	新建事务，如果当前存在事务，把当前事务挂起。
     *
     * 分别执行验证方法，结果：
     * 验证方法序号 	        数据库结果 	                            结果分析
     * 1 	        “张三”未插入，“李四”插入，“王五”插入。 	外围方法开启事务，插入“张三”方法和外围方法一个事务，插入“李四”方法、插入“王五”方法分别在独立的新建事务中，外围方法抛出异常只回滚和外围方法同一事务的方法，故插入“张三”的方法回滚。
     * 2 	        “张三”未插入，“李四”插入，“王五”未插入。 	外围方法开启事务，插入“张三”方法和外围方法一个事务，插入“李四”方法、插入“王五”方法分别在独立的新建事务中。插入“王五”方法抛出异常，首先插入 “王五”方法的事务被回滚，异常继续抛出被外围方法感知，外围方法事务亦被回滚，故插入“张三”方法也被回滚。
     * 3 	        “张三”插入，“李四”插入，“王五”未插入。 	外围方法开启事务，插入“张三”方法和外围方法一个事务，插入“李四”方法、插入“王五”方法分别在独立的新建事务中。插入“王五”方法抛出异常，首先插入“王五”方法的事务被回滚，异常被catch不会被外围方法感知，外围方法事务不回滚，故插入“张三”方法插入成功。
     *
     * 结论：在外围方法开启事务的情况下Propagation.REQUIRES_NEW修饰的内部方法依然会单独开启独立事务，
     * 且与外部方法事务也独立，内部方法之间、内部方法和外部方法事务均相互独立，互不干扰。
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void transaction_required_requiresNew_requiresNew_exception_try() {
        User1 user1 = new User1();
        user1.setName("张三，，测试PROPAGATION_REQUIRES_NEW场景02，方法3，Propagation.REQUIRES_NEW");
        user1Service.addRequired(user1);

        User2 user2 = new User2();
        user2.setName("李四，测试PROPAGATION_REQUIRES_NEW场景02，方法3，Propagation.REQUIRES_NEW");
        user2Service.addRequiresNew(user2);

        User2 user21 = new User2();
        user21.setName("王五，测试PROPAGATION_REQUIRES_NEW场景02，方法3，Propagation.REQUIRES_NEW");
        try {
            user2Service.addRequiresNewException(user21);
        } catch (Exception e) {
            System.out.println("测试PROPAGATION_REQUIRES_NEW场景02:外围方法开启事务。抛出异常被catch住，不往外抛");
        }
    }
}
