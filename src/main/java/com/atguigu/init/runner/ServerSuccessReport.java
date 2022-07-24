package com.atguigu.init.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * https://www.jianshu.com/p/5d4ffe267596 《CommandLineRunner或者ApplicationRunner接口》
 * 前言
 * CommandLineRunner、ApplicationRunner 接口是在容器启动成功后的最后一步回调（类似开机自启动）。
 *
 * CommandLineRunner官方doc：
 * Interface used to indicate that a bean should run when it is contained within a SpringApplication. Multiple CommandLineRunner beans can be defined within the same application context and can be ordered using the Ordered interface or Order @Order annotation.
 * 接口被用作将其加入spring容器中时执行其run方法。多个CommandLineRunner可以被同时执行在同一个spring上下文中并且执行顺序是以order注解的参数顺序一致。
 * If you need access to ApplicationArguments instead of the raw String array
 * consider using ApplicationRunner.
 * 如果你需要访问ApplicationArguments去替换掉字符串数组，可以考虑使用ApplicationRunner类。
 * 看ApplicationRunner 和 CommandLineRunner的源码doc,发现二者的官方javadoc一样，区别在于接收的参数不一样。
 * CommandLineRunner的参数是最原始的参数，没有做任何处理。ApplicationRunner的参数是ApplicationArguments，是对原始参数做了进一步的封装。
 * ApplicationArguments是对参数（main方法）做了进一步的处理，可以解析--name=value的，我们就可以通过name来获取value（而CommandLineRunner只是获取--name=value）
 * 作者：二月_春风
 * 链接：https://www.jianshu.com/p/5d4ffe267596
 * 来源：简书
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
@Order(1)
@Component
public class ServerSuccessReport implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("===========应用已经成功启动===================,获取Main方法的入参" + Arrays.asList(args));
        System.out.println("注意看ServerSuccessReport和ServerStartedReport这俩个谁先执行。");
        System.out.println("Order(1)的值越小越先执行，如果Order的值一样,哪个类先被加载就先执行哪个，可以参考Ordered.HIGHEST_PRECEDENCE");
    }
}
