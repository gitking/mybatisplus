package com.atguigu.demo.java.stopwatch;

import com.google.common.base.Stopwatch;
import org.springframework.util.StopWatch;

import java.util.concurrent.TimeUnit;

/**
 * https://ningyu1.github.io/20190505/116-stop-watch.html
 *
 * 有时我们在做开发的时候需要记录每个任务执行时间，或者记录一段代码执行时间，最简单的方法就是打印当前时间与执行完时间的差值，
 * 然后这样如果执行大量测试的话就很麻烦，并且不直观，如果想对执行的时间做进一步控制，则需要在程序中很多地方修改，
 * 目前spring-framework提供了一个StopWatch类可以做类似任务执行时间控制，也就是封装了一个对开始时间，结束时间记录工具
 *
 * 更多用法参考Spring文档:https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/StopWatch.html
 * 以后我们统计代码执行效率建议大家都使用这个工具来进行输出，不需要在starttime、endtime再相减计算，用优雅的方式来完成这件事情。
 * 最后更新时间：2021-02-02 04:40:20
 * 原始链接：https://ningyu1.github.io/20190505/116-stop-watch.html
 * 版权声明：本文由 凝雨-Yun 原创，采用知识共享署名-相同方式共享 4.0 国际许可协议进行许可。
 * 转载请注明作者及出处！知识共享许可协议
 *
 * https://blog.csdn.net/duleilewuhen/article/details/114379693 《Java计时新姿势StopWatch》
 * 事实上该方法通过获取执行完成时间与执行开始时间的差值得到程序的执行时间，简单直接有效，但想必写多了也是比较烦人的，尤其是碰到不可描述的代码时，
 * 会更加的让人忍不住多写几个bug聊表敬意，而且如果想对执行的时间做进一步控制，则需要在程序中很多地方修改。此时会想是否有一个工具类，提供了这些方法，
 * 刚好可以满足这种场景？我们可以利用已有的工具类中的秒表，
 * 常见的秒表工具类有 org.springframework.util.StopWatch、org.apache.commons.lang.time.StopWatch以及谷歌提供的guava中的秒表（这个我没怎么用过）。这里重点讲下基于spring、Apache的使用
 * spring 用法
 * StopWatch 是位于 org.springframework.util 包下的一个工具类，通过它可方便的对程序部分代码进行计时(ms级别)，适用于同步单线程代码块。简单总结一句，Spring提供的计时器StopWatch对于秒、毫秒为单位方便计时的程序，尤其是单线程、顺序执行程序的时间特性的统计输出支持比较好。也就是说假如我们手里面有几个在顺序上前后执行的几个任务，而且我们比较关心几个任务分别执行的时间占用状况，希望能够形成一个不太复杂的日志输出，StopWatch提供了这样的功能。而且Spring的StopWatch基本上也就是仅仅为了这样的功能而实现。
 * 想要使用它，首先你需要在你的 Maven 中引入 Spring 核心包，当然 Spring MVC 和 Spring Boot 都已经自动引入了该包：
 * <!-- https://mvnrepository.com/artifact/org.springframework/spring-core -->
 * <dependency>
 *     <groupId>org.springframework</groupId>
 *     <artifactId>spring-core</artifactId>
 *     <version>${spring.version}</version>
 * </dependency>
 * ————————————————
 * 版权声明：本文为CSDN博主「独泪了无痕」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/duleilewuhen/article/details/114379693
 */
public class StopWatchDemo {

    public static void main(String[] args) throws InterruptedException {
        testTotalTimeMillis();
        System.out.println("==================================================");
        testLastTaskTimeMillis();
        System.out.println("==================================================");
        testPrettyPrint();
        System.out.println("==================================================");
        testTotalTimeSeconds();
        System.out.println("==================================================");
        testShortSummary();

        System.out.println("CSDN教程如下：");
        testCsdnSpringStopWatch();

        System.out.println("#####################ApacheCommonsLang的使用方法############################");
        testApacheCommonsLang3();

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~测试谷歌Guava的秒表StopWatch使用~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        testGoogleGuavaStopWatch();
    }

    public static void testTotalTimeMillis() throws InterruptedException {
        StopWatch sw = new StopWatch();
        sw.start();
        Thread.sleep(1000);
        sw.stop();
        System.out.println("示例1：统计输出总耗时" + sw.getTotalTimeMillis());
    }

    public static void testLastTaskTimeMillis() throws InterruptedException {
        StopWatch sw = new StopWatch();
        // 设置任务的名字
        sw.start("A task name");
        Thread.sleep(1000);
        sw.stop();
        System.out.println("示例2：输出最后一个任务的耗时" + sw.getLastTaskTimeMillis());
    }

    /**
     * 以优雅的格式打印出所有任务的耗时以及占比
     */
    public static void testPrettyPrint() throws InterruptedException {
        StopWatch sw = new StopWatch();
        sw.start("A");
        Thread.sleep(1000);
        sw.stop();
        sw.start("B");
        Thread.sleep(500);
        sw.stop();
        sw.start("C");
        Thread.sleep(800);
        sw.stop();
        System.out.println("示例3：以优雅的格式打印出所有任务的耗时以及占比->" + sw.prettyPrint());
        System.out.println("返回统计时间任务的数量" + sw.getTaskCount());
        System.out.println("返回最后一个任务TaskInfo对象的名称" + sw.getLastTaskInfo().getTaskName());
    }

    public static void testTotalTimeSeconds() throws InterruptedException {
        StopWatch sw = new StopWatch();
        sw.start("获取总耗时秒");
        Thread.sleep(1000);
        sw.stop();
        System.out.println("示例4: 获取总耗时秒->" + sw.getTotalTimeSeconds());
        System.out.println("示例4: 获取总耗时毫秒->" + sw.getTotalTimeMillis());
        System.out.println("示例4: 获取总耗时纳秒->" + sw.getTotalTimeNanos());
    }

    public static void testShortSummary() throws InterruptedException {
        StopWatch sw = new StopWatch();
        sw.start("获得简短的总耗时描述");
        Thread.sleep(1000);
        sw.stop();
        System.out.println("示例5：获得简短的总耗时描述" + sw.shortSummary());
    }

    /**
     * https://blog.csdn.net/duleilewuhen/article/details/114379693 《Java计时新姿势StopWatch》
     * 对一切事物的认知，都是从使用开始，那就先来看看它的用法，会如下所示：
     */
    public static void testCsdnSpringStopWatch() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();

        // 任务一：模拟休眠3秒钟
        stopWatch.start("TaskOneName");
        Thread.sleep(3000);
        System.out.println("当前任务名称:" + stopWatch.currentTaskName());
        stopWatch.stop();

        // 任务二： 模拟休眠10秒钟
        stopWatch.start("TaskTwoName");
        Thread.sleep(1000 * 10);
        System.out.println("当前任务名称:" + stopWatch.currentTaskName());
        stopWatch.stop();

        // 任务三： 模拟休眠10秒钟
        stopWatch.start("TaskThreadName");
        Thread.sleep(1000 * 10);
        System.out.println("当前任务名称:" + stopWatch.currentTaskName());
        stopWatch.stop();

        // 打印耗时
        System.out.println("漂亮格式打印耗时:->" + stopWatch.prettyPrint());
        System.out.println("简短打印总耗时信息:->" + stopWatch.shortSummary());
        System.out.println("当前任务名称,stop后它的值为null:" + stopWatch.currentTaskName());

        // 最后一个任务的相关信息
        System.out.println("最后一个任务的名字为：" + stopWatch.getLastTaskName());
        System.out.println("最后一个任务的对象TaskInfo:" + stopWatch.getLastTaskInfo());

        // 任务总耗时 如果你想获取到每个任务详情(包括它的任务名，耗时等等)可使用
        System.out.println("所有任务总耗时：" + stopWatch.getTotalTimeMillis());
        System.out.println("任务总数：" + stopWatch.getTaskCount());
        System.out.println("所有任务详情:" + stopWatch.getTaskInfo());
        System.out.println("StopWatch的实例id,用于在日志或控制台输出时区分的" + stopWatch.getId());

        //如图所示，StopWatch 不仅正确记录了上个任务的执行时间，并且在最后还可以给出精确的任务执行时间（纳秒级别）和耗时占比，这或许就会比我们自己输出要优雅那么一些。
        /**
         *
         * 接下来，我们看一下StopWatch类的构造器和一些关键方法
         * 方法	                            说明
         * new StopWatch()	        构建一个新的秒表，不开始任何任务。
         * new StopWatch(String id)	创建一个指定了id的StopWatch
         * String getId()	            返回此秒表的ID
         * void start(String taskName)	不传入参数，开始一个无名称的任务的计时。 传入String类型的参数来开始指定任务名的任务计时
         * void stop()	停止当前任务的计时
         * boolean isRunning()	是否正在计时某任务
         * String currentTaskName()	当前正在运行的任务的名称（如果有）
         * long getTotalTimeMillis()	所有任务的总体执行时间(毫秒单位)
         * double getTotalTimeSeconds()	所有任务的总时间（以秒为单位）
         * String getLastTaskName()	上一个任务的名称
         * long getLastTaskTimeMillis()	上一个任务的耗时(毫秒单位)
         * int getTaskCount()	定时任务的数量
         * String shortSummary()	总运行时间的简短描述
         * String prettyPrint()	优美地打印所有任务的详细耗时情况
         * 注意事项
         *     StopWatch对象不是设计为线程安全的，并且不使用同步。
         *     一个StopWatch实例一次只能开启一个task，不能同时start多个task
         *     在该task还没stop之前不能start一个新的task，必须在该task stop之后才能开启新的task
         *     若要一次开启多个，需要new不同的StopWatch实例
         * ————————————————
         * 版权声明：本文为CSDN博主「独泪了无痕」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
         * 原文链接：https://blog.csdn.net/duleilewuhen/article/details/114379693
         */
    }

    /**
     * apache 用法
     * StopWath是 apache commons lang3 包下的一个任务执行时间监视器，与我们平时常用的秒表的行为比较类似，我们先看一下其中的一些重要方法：
     * 方法	说明
     * new StopWatch()	构建一个新的秒表，不开始任何任务。
     * static StopWatch createStarted()
     * void start()	开始计时
     * void stop()	停止当前任务的计时
     * void reset()	重置计时
     * void split()	设置split点
     * void unsplit()
     * void suspend()	暂停计时, 直到调用resume()后才恢复计时
     * void resume()	恢复计时
     * long getTime()	统计从start到现在的计时
     * long getTime(final TimeUnit timeUnit)
     * long getNanoTime()
     * long getSplitTime()	获取从start 到 最后一次split的时间
     * long getSplitNanoTime()
     * long getStartTime()
     * boolean isStarted()
     * boolean isSuspended()
     * boolean isStopped()
     * <dependency>
     *     <groupId>org.apache.commons</groupId>
     *     <artifactId>commons-lang3</artifactId>
     *     <version>3.6</version>
     * </dependency>
     * ————————————————
     * 版权声明：本文为CSDN博主「独泪了无痕」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/duleilewuhen/article/details/114379693
     */
    public static void testApacheCommonsLang3() throws InterruptedException {
        // Apache提供的这个任务执行监视器功能丰富强大，灵活性强，如下经典实用案例：
        // 创建后立即start,常用
        org.apache.commons.lang3.time.StopWatch watch = org.apache.commons.lang3.time.StopWatch.createStarted();

        // StopWatch watch = new StopWatch();
        // watch.start();

        Thread.sleep(1000);
        System.out.println("testApacheCommonsLang3,统计从开始到现在运行的时间:" + watch.getTime() + "ms,毫秒");

        Thread.sleep(1000);
        watch.split();
        System.out.println("从start到此刻为止的时间:" + watch.getTime() + "ms,毫秒");
        System.out.println("从开始到第一个split切入点运行时间:" + watch.getSplitTime() + "ms,毫秒");

        Thread.sleep(1000);
        watch.split();
        System.out.println("从开始到第二个切入点split运行时间" + watch.getSplitTime() + "ms,毫秒");

        // 复位后，重新计时
        watch.reset();
        watch.start();
        Thread.sleep(1000);
        System.out.println("重新开始计时后当当前运行时间是:" + watch.getTime() + "ms,毫秒");

        // 暂停与恢复
        watch.suspend();
        System.out.println("暂停suspend2秒钟");
        Thread.sleep(2000);

        // 上面suspend暂停后，这里想要重新统计，需要恢复一下
        watch.resume();
        System.out.println("恢复后执行的时间是： " + watch.getTime() + "ms,毫秒");

        Thread.sleep(1000);
        watch.stop();

        System.out.println("花费的时间》》》" + watch.getTime() + "ms,毫秒");
        // 直接转成秒s
        System.out.println("testApacheCommonsLang3,花费的时间》》》" + watch.getTime(TimeUnit.SECONDS) + "s,秒");
    }


    /**
     * Google之Stopwatch 计时器
     * 返回值 	方法 	Desc
     * Stopwatch 	static createStarted() 	使用System.nanoTime()其时间源创建（并启动）新的Stopwatch
     * Stopwatch 	static createStarted(Ticker ticker) 	使用指定的时间源创建（并启动）新的Stopwatch
     * Stopwatch 	static createUnstarted() 	使用System.nanoTime()作为其时间源创建（但不启动）新的Stopwatch
     * Stopwatch 	static createUnstarted(Ticker ticker) 	使用指定的时间源创建（但不启动）新的Stopwatch
     * Duration 	elapsed() 	以秒为单位返回此Stopwatch经过时间Duration
     * long 	elapsed(TimeUnit desiredUnit) 	用返回此Stopwatch经过时间，以所需的时间单位表示，并且向下取整
     * boolean 	isRunning() 	若 start 方法被调用，stop 方法还没有调用，返回真
     * Stopwatch 	reset() 	把 Stopwatch 经过的时间设置为零，状态设置为停止
     * Stopwatch 	start() 	启动 Stopwatch
     * Stopwatch 	stop() 	停止 Stopwatch
     * String 	toString() 	返回字符串形式的elapsed time
     *
     * TimeUnit
     * 枚举常量 	描述
     * DAYS 	天
     * HOURS 	小时
     * MINUTES 	分钟
     * SECONDS 	秒
     * MILLISECONDS 	毫秒
     * MICROSECONDS 	微秒
     * NANOSECONDS 	纳秒
     *
     * 作者：方穹轩
     * 链接：https://www.jianshu.com/p/06915946d640
     * 来源：简书
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    public static void testGoogleGuavaStopWatch() throws InterruptedException {
        // 创建StopWatch并开始计时,谷歌官方文档:https://guava.dev/releases/27.1-jre/api/docs/com/google/common/base/Stopwatch.html
        com.google.common.base.Stopwatch stopwatch = Stopwatch.createStarted();
        System.out.println("-- Google Guava开始计时 --");
        Thread.sleep(1950L);
        System.out.println(stopwatch);
        // 向下取整 单位:秒
        System.out.println("向下取整 单位秒:" + stopwatch.elapsed(TimeUnit.SECONDS));

        // 停止计时
        System.out.println("-- 停止计时 --");
        stopwatch.stop();
        Thread.sleep(2000L);
        // stop()不再计时
        System.out.println(stopwatch);
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS));

        // 再次计时
        System.out.println("--再次计时,等于恢复计时的意思，--");
        stopwatch.start();
        Thread.sleep(100L);
        System.out.println(stopwatch);
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS));

        // 重置并开始
        System.out.println("--重置开始，等于从0开始重新计时了--");
        stopwatch.reset().start();
        Thread.sleep(1500);
        System.out.println(stopwatch);
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS));

        // 检查isRunning
        System.out.println("-- 检查isRunning--");
        System.out.println("是否还在计时:" + stopwatch.isRunning());

        // 打印
        System.out.println("-- 打印 --");
        System.out.println(stopwatch.toString());

    }
}
