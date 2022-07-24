package com.atguigu.init.runner;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * https://blog.csdn.net/u011291072/article/details/81813662 《探究SpringBoot启动时实现自动执行代码》
 * 小白码上飞 于 2018-08-18 22:17:49 发布 12528 收藏 16
 * ————————————————
 * 版权声明：本文为CSDN博主「小白码上飞」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/u011291072/article/details/81813662
 *
 * 前言
 * 目前开发的SpringBoot项目在启动的时候需要预加载一些资源。而如何实现启动过程中执行代码，或启动成功后执行，是有很多种方式可以选择，我们可以在static代码块中实现，也可以在构造方法里实现，也可以使用@PostConstruct注解实现，当然也可以去实现Spring的ApplicationRunner与CommandLineRunner接口去实现启动后运行的功能。在这里整理一下，在这些位置执行的区别以及加载顺序。
 * java自身的启动时加载方式
 * static代码块
 * static静态代码块，在类加载的时候即自动执行。
 * 构造方法
 * 在对象初始化时执行。执行顺序在static静态代码块之后。
 * Spring启动时加载方式
 * @PostConstruct注解
 * PostConstruct注解使用在方法上，这个方法在对象依赖注入初始化之后执行。
 * ApplicationRunner和CommandLineRunner
 * SpringBoot提供了两个接口来实现Spring容器启动完成后执行的功能，两个接口分别为CommandLineRunner和ApplicationRunner。
 * 这两个接口需要实现一个run方法，将代码在run中实现即可。这两个接口功能基本一致，其区别在于run方法的入参。ApplicationRunner的run方法入参为ApplicationArguments，为CommandLineRunner的run方法入参为String数组。
 * 何为ApplicationArguments
 * 官方文档解释为：Provides access to the arguments that were used to run a SpringApplication.在Spring应用运行时使用的访问应用参数。
 * 即我们可以获取到SpringApplication.run(…)的应用参数。
 *
 * 参考资料:
 * Interface ApplicationArguments   https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/ApplicationArguments.html
 * CommandLineRunner或者ApplicationRunner接口  https://www.jianshu.com/p/5d4ffe267596
 *
 * Order注解
 * 当有多个类实现了CommandLineRunner和ApplicationRunner接口时，可以通过在类上添加@Order注解来设定运行顺序。
 *
 * 总结
 *
 * Spring应用启动过程中，肯定是要自动扫描有@Component注解的类，加载类并初始化对象进行自动注入。加载类时首先要执行static静态代码块中的代码，之后再初始化对象时会执行构造方法。
 * 在对象注入完成后，调用带有@PostConstruct注解的方法。当容器启动成功后，再根据@Order注解的顺序调用CommandLineRunner和ApplicationRunner接口类中的run方法。
 * 因此，加载顺序为static>constructer>@PostConstruct>CommandLineRunner和ApplicationRunner.
 * CommandLineRunner、ApplicationRunner 接口是在容器启动成功后的最后一步回调（类似开机自启动）。
 * ————————————————
 * 版权声明：本文为CSDN博主「小白码上飞」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/u011291072/article/details/81813662
 */
@Component
public class TestPostConstruct {

    static {
        System.out.println("第一个执行，static静态代码块，在类加载的时候自动执行,测试之后，static是最优先执行的，比ApplicationRunner都要先执行，ApplicationRunner其实是最后执行的。");
        System.out.println("这个类里面的方法都是在 new SpringApplication(MyBatisplusApplication.class).run()方法里面的refreshContext(context); 里面被执行的。");
    }

    public TestPostConstruct() {
        System.out.println("第二个执行，构造方法，在对象初始化时执行，执行顺序在static静态代码块之后，比ApplicationRunner都要先执行，ApplicationRunner其实是最后执行的。");
        System.out.println("这个类里面的方法都是在 new SpringApplication(MyBatisplusApplication.class).run()方法里面的refreshContext(context); 里面被执行的。");
    }

    @PostConstruct
    public void init() {
        System.out.println("第三个执行，@PostConstruct,PostConstruct注解使用在方法上，这个方法在对象依赖注入初始化之后执行。由Spring调用该方法，比ApplicationRunner都要先执行，ApplicationRunner其实是最后执行的。");
        System.out.println("这个类里面的方法都是在 new SpringApplication(MyBatisplusApplication.class).run()方法里面的refreshContext(context); 里面被执行的。");
    }
}
