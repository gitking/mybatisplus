package com.atguigu.mybatisplus.spring.validation.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * https://juejin.cn/post/6856541106626363399 《Spring Validation最佳实践及其实现原理，参数校验没那么简单！ 》 作者: 夜尽天明_  2020年08月03日 08:02 ·  阅读 6474
 * https://mp.weixin.qq.com/s/plHQI0dSq-I7xfZ-YcFZog 《SpringBoot 参数校验，高级特性，非常实用！》 公众号: 老炮说Java 2022-06-30 14:00 发表于山西
 * 之前也写过一篇关于Spring Validation使用的文章，不过自我感觉还是浮于表面，本次打算彻底搞懂Spring Validation。
 * 本文会详细介绍Spring Validation各种场景下的最佳实践及其实现原理，死磕到底！项目源码：https://github.com/chentianming11/spring-validation
 * 简单使用
 * Java API规范 (JSR303) 定义了Bean校验的标准validation-api，但没有提供实现。hibernate validation是对这个规范的实现，并增加了校验注解如@Email、@Length等。
 * Spring Validation是对hibernate validation的二次封装，用于支持spring mvc参数自动校验。接下来，我们以spring-boot项目为例，介绍Spring Validation的使用。
 * 对于web服务来说，为防止非法参数对业务造成影响，在Controller层一定要做参数校验的！大部分情况下，请求参数分为如下两种形式：
 *     POST、PUT请求，使用requestBody传递参数；
 *     GET请求，使用requestParam/PathVariable传递参数。
 * 下面我们简单介绍下requestBody和requestParam/PathVariable的参数校验实战！
 * requestBody参数校验
 * POST、PUT请求一般会使用requestBody传递参数，这种情况下，后端使用** DTO 对象进行接收。
 * 只要给 DTO 对象加上@Validated注解就能实现自动参数校验**。比如，有一个保存User的接口，要求userName长度是2-10，account和password字段长度是6-20。
 * 如果校验失败，会抛出MethodArgumentNotValidException异常，Spring默认会将其转为400（Bad Request）请求。
 * DTO 表示数据传输对象（Data Transfer Object），用于服务器和客户端之间交互传输使用的。在 spring-web 项目中可以表示用于接收请求参数的Bean对象。
 * 在DTO字段上声明约束注解
 *
 * @Valid和@Validated区别
 * 区别           @Valid                                                      @Validated
 * 提供者      JSR-303规范                                                       Spring
 * 是否支持分组   不支持                                                             支持
 * 标注位置     METHOD, FIELD, CONSTRUCTOR, PARAMETER, TYPE_USE     TYPE, METHOD, PARAMETER
 * 嵌套校验     支持                                                              不支持
 *
 * 实现原理
 * 1. requestBody参数校验实现原理
 * 在spring-mvc中，RequestResponseBodyMethodProcessor是用于解析@RequestBody标注的参数以及处理@ResponseBody标注方法的返回值的。
 * 显然，执行参数校验的逻辑肯定就在解析参数的方法resolveArgument()中：
 * 可以看到，resolveArgument()调用了validateIfApplicable()进行参数校验。
 * 看到这里，大家应该能明白为什么这种场景下@Validated、@Valid两个注解可以混用。我们接下来继续看WebDataBinder.validate()实现。
 * 最终发现底层最终还是调用了Hibernate Validator进行真正的校验处理。
 *
 * 2. 方法级别的参数校验实现原理
 * 上面提到的将参数一个个平铺到方法参数中，然后在每个参数前面声明约束注解的校验方式，就是方法级别的参数校验。
 * 实际上，这种方式可用于任何Spring Bean的方法上，比如Controller/Service等。
 * 其底层实现原理就是AOP，具体来说是通过MethodValidationPostProcessor动态注册AOP切面，然后使用MethodValidationInterceptor对切点方法织入增强。
 * 接着看一下MethodValidationInterceptor：
 * 实际上，不管是requestBody参数校验还是方法级别的校验，最终都是调用Hibernate Validator执行校验，Spring Validation只是做了一层封装。
 * 欢迎关注我的开源项目：一款适用于SpringBoot的轻量级HTTP调用框架 https://github.com/LianjiaTech/retrofit-spring-boot-starter
 * 作者：夜尽天明_
 * 链接：https://juejin.cn/post/6856541106626363399
 * 来源：稀土掘金
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 */
@Data
@ApiModel("用户实体类")
public class UserDTO {

    /**
     * 进阶使用
     * 分组校验
     * 在实际项目中，可能多个方法需要使用同一个DTO类来接收参数，而不同方法的校验规则很可能是不一样的。这个时候，简单地在DTO类的字段上加约束注解无法解决这个问题。
     * 因此，spring-validation支持了分组校验的功能，专门用来解决这类问题。
     * 还是上面的例子，比如保存User的时候，UserId是可空的，但是更新User的时候，UserId的值必须>=10000000000000000L；其它字段的校验规则在两种情况下一样。
     * 这个时候使用分组校验的代码示例如下：
     * 约束注解上声明适用的分组信息groups
     *  @Validated注解上指定校验分组
      * 作者：夜尽天明_
     * 链接：https://juejin.cn/post/6856541106626363399
     * 来源：稀土掘金
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    @ApiModelProperty("用户id")
    @Min(value = 10000000000000000L, groups = Update.class)
    private Long userId;

    @ApiModelProperty("用户名")
    @NotNull(groups = {Save.class, Update.class})
    @Length(min = 2, max = 10, groups = {Save.class, Update.class})
    private String userName;

    @ApiModelProperty("账号")
    @NotNull(groups = {Save.class, Update.class})
    @Length(min = 6, max = 20, groups = {Save.class, Update.class})
    private String account;

    @ApiModelProperty("密码")
    @NotNull(groups = {Save.class, Update.class})
    @Length(min = 6,max = 20, groups = {Save.class, Update.class})
    private String password;

    @ApiModelProperty("工作")
    @NotNull(groups = {Save.class, Update.class})
    @Valid // 嵌套校验，需要注意的是，此时DTO类的对应字段必须标记@Valid注解。
    private Job job;

    /**
     * 嵌套校验可以结合分组校验一起使用。还有就是嵌套集合校验会对集合里面的每一项都进行校验，例如List<Job>字段会对这个list里面的每一个Job对象都进行校验。
     */
    @ApiModelProperty("工作")
    @NotNull(groups = {Save.class, Update.class})
    @Valid // 嵌套校验，需要注意的是，此时DTO类的对应字段必须标记@Valid注解。
    private List<Job> jobList;

    /**
     * 嵌套校验
     *
     * 前面的示例中，DTO类里面的字段都是基本数据类型和String类型。但是实际场景中，有可能某个字段也是一个对象，这种情况先，可以使用嵌套校验。
     * 比如，上面保存User信息的时候同时还带有Job信息。需要注意的是，此时DTO类的对应字段必须标记@Valid注解。
     *
     * 嵌套校验可以结合分组校验一起使用。还有就是嵌套集合校验会对集合里面的每一项都进行校验，例如List<Job>字段会对这个list里面的每一个Job对象都进行校验。
     * 作者：夜尽天明_
     * 链接：https://juejin.cn/post/6856541106626363399
     * 来源：稀土掘金
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     */
    @Data
    @ApiModel("工作实体类")
    public static class Job {

        @ApiModelProperty("工作ID")
        @Min(value = 1, groups = Update.class)
        private Long jobId;

        @ApiModelProperty("工作名称")
        @NotNull(groups = {Save.class, Update.class})
        @Length(min = 2, max = 10, groups = {Save.class, Update.class})
        private String jobName;

        @ApiModelProperty("岗位")
        @NotNull(groups = {Save.class, Update.class})
        @Length(min =2, max= 10, groups = {Save.class, Update.class})
        private String position;
    }


    /**
     * 保存的时候校验分组
     */
    public interface Save {

    }

    /**
     * 更新的时候校验分组
     */
    public interface Update {

    }
}