package com.atguigu.mybatisplus.spring.validation.controller;

import com.atguigu.mybatisplus.pojo.User;
import com.atguigu.mybatisplus.spring.validation.base.Result;
import com.atguigu.mybatisplus.spring.validation.base.validation.ValidationList;
import com.atguigu.mybatisplus.spring.validation.http.HttpTestAPI;
import com.atguigu.mybatisplus.spring.validation.pojo.dto.UserDTO;
import com.atguigu.mybatisplus.spring.validation.pojo.dto.UserValidateDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 *
 * requestParam/PathVariable参数校验
 * GET请求一般会使用requestParam/PathVariable传参。如果参数比较多(比如超过6个)，还是推荐使用DTO对象接收。
 * 否则，推荐将一个个参数平铺到方法入参中。
 * 在这种情况下，必须在Controller类上标注@Validated注解，并在入参上声明约束注解(如@Min等)。如果校验失败，会抛出ConstraintViolationException异常。代码示例如下：
 *
 * 作者：夜尽天明_
 * 链接：https://juejin.cn/post/6856541106626363399
 * 来源：稀土掘金
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * @author 陈添明
 */
@RequestMapping("/api/user")
@RestController
@Validated
@Api("用户管理")
public class UserController {

    /**
     * 编程式校验
     * 上面的示例都是基于注解来实现自动校验的，在某些情况下，我们可能希望以编程方式调用验证。这个时候可以注入javax.validation.Validator对象，然后再调用其api。
     */
    @Autowired
    private javax.validation.Validator globalValidator;

    @Autowired
    private HttpTestAPI httpTestAPI;

    // 编程式校验
    @PostMapping("/saveWithCodingValidate")
    @ApiOperation("编程式校验保存")
    public Result saveWithCodingValidate(@RequestBody @Valid UserDTO userDTO) {
        Set<ConstraintViolation<UserDTO>> validate = globalValidator.validate(userDTO, UserDTO.Save.class);
        // 如果校验通过，validate为空;否则,validate包含未校验通过项
        if (validate.isEmpty()) {
            // 校验通过，才会执行业务逻辑处理

        } else {
            for (ConstraintViolation<UserDTO> userDTOConstraintViolation : validate){
                // 校验失败,做其他逻辑
                System.out.println("请求参数非法" + userDTOConstraintViolation);
            }
        }
        return Result.ok();
    }

    /**
     * requestBody参数校验
     * POST、PUT请求一般会使用requestBody传递参数，这种情况下，后端使用DTO对象进行接收。
     * 只要给DTO对象加上@Validated注解就能实现自动参数校验。比如，有一个保存User的接口，要求userName长度是2-10，account和password字段长度是6-20。如果校验失败，会抛出MethodArgumentNotValidException异常，Spring默认会将其转为400（Bad Request）请求。
     *
     * 在方法参数上声明校验注解, 这种情况下，使用@Valid和@Validated都可以。
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/saveValidated")
    @ApiOperation("保存用户")
    public Result saveUser1(@RequestBody @Validated UserValidateDTO userDTO) {
        // 校验通过，才会执行业务逻辑处理
        return Result.ok();
    }

    /**
     * requestBody参数校验
     * POST、PUT请求一般会使用requestBody传递参数，这种情况下，后端使用DTO对象进行接收。
     * 只要给DTO对象加上@Validated注解就能实现自动参数校验。比如，有一个保存User的接口，要求userName长度是2-10，account和password字段长度是6-20。如果校验失败，会抛出MethodArgumentNotValidException异常，Spring默认会将其转为400（Bad Request）请求。
     *
     * 在方法参数上声明校验注解, 这种情况下，使用@Valid和@Validated都可以。
     *
     * @param userDTO
     * @return
     */
    @PostMapping("/saveValid")
    @ApiOperation("保存用户")
    public Result saveUserValid(@RequestBody @Valid UserValidateDTO userDTO) {
        // 校验通过，才会执行业务逻辑处理
        return Result.ok();
    }


    /**
     * 分组校验
     *
     * @Validated注解上指定校验分组， @Validated(UserDTO.Save.class)
      * @param userDTO
     * @return
     */
    @PostMapping("/save")
    @ApiOperation("保存用户")
    public Result saveUser(@RequestBody @Validated(UserDTO.Save.class)UserDTO userDTO) {
        // 校验通过，才会执行业务逻辑处理
        return Result.ok();
    }

    /**
     * 集合校验
     *
     * 比如，我们需要一次性保存多个User对象，Controller层的方法可以这么写：
     *
     * @param userList
     * @return
     */
    @PostMapping("/saveList")
    @ApiOperation("批量保存")
    public Result saveList(@RequestBody @Validated(UserDTO.Save.class) ValidationList<UserDTO> userList){
        // 校验通过，才会执行业务逻辑
        return Result.ok();
    }

    @PostMapping("/update")
    @ApiOperation("更新用户信息")
    public Result updateUser(@RequestBody @Validated(UserDTO.Update.class) UserDTO userDTO) {
        //校验通过，才会执行业务逻辑
        return Result.ok();
    }


    /**
     * requestParam/PathVariable参数校验
     * GET请求一般会使用requestParam/PathVariable传参。如果参数比较多(比如超过6个)，还是推荐使用DTO对象接收。
     * 否则，推荐将一个个参数平铺到方法入参中。在这种情况下，必须在Controller类上标注@Validated注解，并在入参上声明约束注解(如@Min等)。
     * 如果校验失败，会抛出ConstraintViolationException异常。代码示例如下：
     *
     * @param userId
     * @return
     */
    // 路径变量校验
    @GetMapping("{userId}")
    @ApiOperation("根据userId查询用户信息")
    public Result detail(@PathVariable("userId") @Min(10000000000000000L) @ApiParam("用户名id") Long userId) {
        // 校验通过，才会执行业务逻辑处理
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setAccount("111111111111111");
        userDTO.setUserName("xixi");
        userDTO.setAccount("1111111111");
        return Result.ok(userDTO);
    }

    // 查询参数
    @GetMapping("getByAccount")
    @ApiOperation("根据account查询用户信息")
    public Result getByAccount(@Length(min = 6, max = 20) @NotNull @ApiParam("账号") String account){
        // 校验通过，才会执行业务逻辑处理
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(10000000000000003L);
        userDTO.setAccount(account);
        userDTO.setUserName("xixi");
        userDTO.setAccount("11111111111111111");
        return Result.ok(userDTO);
    }

    @GetMapping("/httpTest")
    public Result httpTest() {
        Result<UserDTO> account123 = httpTestAPI.getByAccount("account123");
        System.out.println(account123);
        return Result.ok(account123);
    }

    @GetMapping("/throw404Exception")
    public Result throw404Exception() {
        Result<UserDTO> account123 = null;
        try {
            account123 = httpTestAPI.throw404Exception("account123");
        } catch (Throwable e) {
            while (true) {
                if (e != null) {
                    System.out.println(e.getClass());
                    e = e.getCause();
                }
            }
        }
        return Result.ok(account123);
    }
}
