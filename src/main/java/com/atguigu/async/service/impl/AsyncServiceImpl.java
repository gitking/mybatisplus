package com.atguigu.async.service.impl;

import com.atguigu.async.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AsyncServiceImpl implements AsyncService {

    /**
     * 这个异步方法不加 @Transactional(readOnly = true)这个注解就会报这个错误，并且启动类还得加上@EnableAspectJAutoProxy(exposeProxy = true,proxyTargetClass = true)这个注解
     * // 暴露当前代理对象到当前线程绑定,https://cloud.tencent.com/developer/article/1497700 《从@Async案例找到Spring框架的bug：exposeProxy=true不生效原因大剖析+最佳解决方案【享学Spring】》
     * java.lang.IllegalStateException: Cannot find current proxy: Set 'exposeProxy' property on Advised to 'true' to make it available,
     * and ensure that AopContext.currentProxy() is invoked in the same thread as the AOP invocation context.
     *
     * Spring源码见：org.springframework.scheduling.annotation.ProxyAsyncConfiguration的这个方法 public AsyncAnnotationBeanPostProcessor asyncAdvisor() {
     * 解决
     * 由于项目已经设置了@EnableAspectJAutoProxy(exposeProxy = true)，所以可以给C方法加@Transactional注解，这样它会先交给AbstractAutoProxyCreator把代理对象生成好，再交给后面的处理器执行。
     * 由于AbstractAutoProxyCreator先执行，所以AsyncAnnotationBeanPostProcessor执行的时候此时Bean已经是代理对象了，此时它会沿用这个代理，只需要把切面添加进去即可。
     *
     * 后续
     * 后来在另一个controller时又出现了同样问题。
     * controller中接口调用代码如下
     * cluSerBuildAlarmStrategyService.cluSerAddAlarmStrategy(monitorAlarmStrategyVo,monitorAlarmStrategyVo.getUserId());
     *
     * CluSerBuildAlarmStrategyService接口中的cluSerAddAlarmStrategy方法加了@Async注解，且代码中通过AopContext.currentProxy()获取自身来调用另一个加了事务注解的方法，如下
     *     @Override
     *     @Async
     *     public Result<String> cluSerAddAlarmStrategy(MonitorAlarmStrategyVo monitorAlarmStrategyVo, String userId) {
     * 		      CluSerBuildAlarmStrategyService cluSerBuildAlarmStrategyService = (CluSerBuildAlarmStrategyService)AopContext.currentProxy();
     *      }
     * 结果报了与上面同样的错误。
     * 既然本类中存在加了事务注解的方法，那么最终spring就会为该类生成代理，为啥这里还是报错呢？
     * 原因是，外层controller通过接口调用时，确实先走的是接口代理类，代理方法里也确实把代理对象放入AopContext了，但是由于被调用方法是异步方法，相当于开启了子线程，而之前放入AopContext是主线程操作的，所以子线程当然获取不到了。
     * (参考：从@Async案例找到Spring框架的bug中的案例三)
     * 所以虽然都是报相同的错，但是问题产生的场景不一样。
     * 解决办法参考文章：https://blog.csdn.net/qq_39002724/article/details/107975389?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522165823894916782184619545%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fall.%2522%257D&request_id=165823894916782184619545&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_ecpm_v1~rank_v31_ecpm-1-107975389-null-null.142^v32^down_rank,185^v2^control&utm_term=%E4%BD%BF%E7%94%A8%40Async%E6%97%B6%E5%87%BA%E7%8E%B0Cannot%20find%20current&spm=1018.2226.3001.4187
     * 《使用@Async时出现Cannot find current proxy: Set ‘exposeProxy‘ property on》 cyh男 于 2021-02-01 21:47:21 发布 387 收藏 1
     * ————————————————
     * 版权声明：本文为CSDN博主「cyh男」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/qq_39002724/article/details/107975389
     * @throws InterruptedException
     */
    @Async(value = "whyThreadPool")
    @Override
    @Transactional(readOnly = true)
    public void testAsync() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        log.info(Thread.currentThread().getName() + "你看看我是不是异步的呢？");
    }

    @Override
    public void testAsyncInnerMethod() throws InterruptedException {
        log.info("我在我的方法内部调用异步方法，还有用吗？");
        // 调了这个异步方法不起作用，解决办法看testAsyncInnerMethodSolve
        testAsync();
    }

    /**
     * https://blog.csdn.net/fanxb92/article/details/81296005 《Spring是如何管理事务的之@Transactional注解详解》
     * https://blog.csdn.net/mameng1988/article/details/85548812 《Cannot find current proxy: Set 'exposeProxy' property on Advised to 'true' to 以及Spring事务失效的原因和解决方案》
     *
     * @throws InterruptedException
     */
    @Override
    public void testAsyncInnerMethodSolve() throws InterruptedException {
        log.info("我在我的方法内部调用异步方法，还有用吗？");
        // 调了这个异步方法不起作用，解决办法看testAsyncInnerMethodSolve
        AsyncService currentProxy = (AsyncService)AopContext.currentProxy();
        currentProxy.testAsync();
    }
}
