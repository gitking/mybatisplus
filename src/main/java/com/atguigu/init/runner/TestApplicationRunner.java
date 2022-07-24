package com.atguigu.init.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * https://www.jianshu.com/p/5d4ffe267596 《CommandLineRunner或者ApplicationRunner接口》  二月_春风 2017.07.24 23:48:49 字数 466阅读 46,400
 * CommandLineRunner、ApplicationRunner 接口是在容器启动成功后的最后一步回调（类似开机自启动）。
 *
 * Order注解
 * 当有多个类实现了CommandLineRunner和ApplicationRunner接口时，可以通过在类上添加@Order注解来设定运行顺序。
 * 参考资料:
 *  Interface ApplicationArguments   https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/ApplicationArguments.html
 *  CommandLineRunner或者ApplicationRunner接口  https://www.jianshu.com/p/5d4ffe267596
 * ————————————————
 * 版权声明：本文为CSDN博主「小白码上飞」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/u011291072/article/details/81813662
 */
@Component
@Order(1)
public class TestApplicationRunner implements ApplicationRunner {

    /**
     * 何为ApplicationArguments
     *
     * 官方文档解释为：Provides access to the arguments that were used to run a SpringApplication.在Spring应用运行时使用的访问应用参数。
     * 即我们可以获取到SpringApplication.run(…)的应用参数。
     * 参考资料:
     *  Interface ApplicationArguments   https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/ApplicationArguments.html
     *  CommandLineRunner或者ApplicationRunner接口  https://www.jianshu.com/p/5d4ffe267596
     *  看ApplicationRunner 和 CommandLineRunner的源码doc,发现二者的官方javadoc一样，区别在于接收的参数不一样。
     *  CommandLineRunner的参数是最原始的参数，没有做任何处理。ApplicationRunner的参数是ApplicationArguments，是对原始参数做了进一步的封装。
     *  ApplicationArguments是对参数（main方法）做了进一步的处理，可以解析--name=value的，我们就可以通过name来获取value（而CommandLineRunner只是获取--name=value）
     * 作者：二月_春风
     * 链接：https://www.jianshu.com/p/5d4ffe267596
     * 来源：简书
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * ————————————————
     * 版权声明：本文为CSDN博主「小白码上飞」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/u011291072/article/details/81813662
     * @param args incoming application arguments
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++注意看ApplicationRunner是在SpringBoot的哪个阶段执行的++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("order1:TestApplicationRunner,order的值越大执行优先级越");
        System.out.println("获取到SpringApplication.run(…)的应用参数" + args.getOptionNames());
        System.out.println("com.atguigu.init.runner.TestPostConstruct这个类里面的方法，都比ApplicationRunner要优先执行。");
        System.out.println("ApplicationRunner是在 new SpringApplication(MyBatisplusApplication.class).run()方法里面的callRunners(context, applicationArguments); 里面被执行的。");
        System.out.println("是SpringBoot启动方法run里面最后一步才执行的。");
        System.out.println("ApplicationRunner要比CommandLineRunner先运行，这个待会看下源码就知道了");

        // java main 方法传参: java main类 参数 参数,SpringBoot启动给main方法传参数：java -jar mybatisplus-1.0-SNAPSHOT.jar --foo=bar --developer.name=zhihao.miao
        // idea给main方法传参可以看自己写的文档《IntelliJ IDEA.docx》
        System.out.println("获取参数的功能，可看源码org.springframework.boot.ApplicationArguments");
        System.out.println("====MyApplicationRunner===,获得传给Main方法的原始参数：" + Arrays.asList(args.getSourceArgs()));
        System.out.println("====getOptionNames=======，获得所有参数的key值" + args.getOptionNames());
        System.out.println("=======getOptionValus=====，根据key值获取参数的value:" + args.getOptionValues("foo"));
        System.out.println("=========getOptionValues=====，根据key值获取参数的value:" + args.getOptionValues("developer.name"));
        System.out.println("--------------------------------------------------------------------------");

    }
}
