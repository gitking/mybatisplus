package com.atguigu.mybatisplus.spring.validation.base;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

/**
 * 统一异常处理
 *
 * 前面说过，如果校验失败，会抛出MethodArgumentNotValidException或者ConstraintViolationException异常。
 * 在实际项目开发中，通常会用统一异常处理来返回一个更友好的提示。比如我们系统要求无论发送什么异常，http的状态码必须返回200，由业务码去区分系统的异常情况。
 *
 * 作者：夜尽天明_
 * 链接：https://juejin.cn/post/6856541106626363399
 * 来源：稀土掘金
 * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
 * @author 陈添明
 * @date 2022/6/30
 */
@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {


    /**
     * 使用注解@Validated和@Valid做入参校验，结果报错：
     * "JSR-303 validated property 'xxx.id' does not have a corresponding accessor for Spring data binding - check your DataBinder's configuration (bean property versus direct field access)"
     *
     * https://blog.csdn.net/Muscleheng/article/details/118597527
     * 如果不加这个activateDirectFieldAccess东西，list校验报错会返回下面这个错误
     * JSR-303 validated property 'list[1].userName' does not have a corresponding accessor for Spring data binding - check your DataBinder's configuration (bean property versus direct field access)
     * 加了activateDirectFieldAccess东西，list校验报错会返回下面这个错误
     * SpringBoot异常统一处理@RestControllerAdvice，Post请求，requestBody参数校验,校验失败:list[1].userName:长度需要在2和10之间,
     * @param dataBinder
     */
    // 将Spring DataBinder配置为使用直接字段访问
    @InitBinder
    private void activateDirectFieldAccess(DataBinder dataBinder) {
        dataBinder.initDirectFieldAccess();
    }

    /**
     * POST、PUT请求，使用requestBody传递参数；
     *
     * requestBody参数校验
     * POST、PUT请求一般会使用requestBody传递参数，这种情况下，后端使用DTO对象进行接收。只要给DTO对象加上@Validated注解就能实现自动参数校验。
     * 比如，有一个保存User的接口，要求userName长度是2-10，account和password字段长度是6-20。
     * 如果校验失败，会抛出MethodArgumentNotValidException异常，Spring默认会将其转为400（Bad Request）请求。
     *
     * 作者：夜尽天明_
     * 链接：https://juejin.cn/post/6856541106626363399
     * 来源：稀土掘金
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param exception
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        StringBuilder sb = new StringBuilder("SpringBoot异常统一处理@RestControllerAdvice，Post请求，requestBody参数校验,校验失败:");
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            sb.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(",");
        }
        String msg = sb.toString();
        return Result.fail(BusinessCode.参数校验失败, msg);
    }

    /**
     * GET请求，使用requestParam/PathVariable传递参数。
     *
     * requestParam/PathVariable参数校验
     * GET请求一般会使用requestParam/PathVariable传参。如果参数比较多(比如超过6个)，还是推荐使用DTO对象接收。
     * 否则，推荐将一个个参数平铺到方法入参中。在这种情况下，必须在Controller类上标注@Validated注解，并在入参上声明约束注解(如@Min等)。
     * 如果校验失败，会抛出ConstraintViolationException异常。代码示例如下：
     * @param exception
     * @return
     */
    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result handleConstraintViolationException(ConstraintViolationException exception) {
        return Result.fail(BusinessCode.参数校验失败, "SpringBoot异常统一处理@RestControllerAdvice，GET请求，ConstraintViolationException:" + exception.getMessage());
    }

    /**
     * 集合校验
     * 如果请求体直接传递了json数组给后台，并希望对数组中的每一项都进行参数校验。此时，如果我们直接使用java.util.Collection下的list或者set来接收数据，参数校验并不会生效！我们可以使用自定义list集合来接收参数：
     * 包装List类型，并声明@Valid注解
     *
     * @Delegate注解受lombok版本限制，1.18.6以上版本可支持。如果校验不通过，会抛出NotReadablePropertyException，同样可以使用统一异常进行处理。
     * @param exception
     * @return
     */
    @ExceptionHandler({NotReadablePropertyException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result handleNotReadablePropertyException(NotReadablePropertyException exception) {
        return Result.fail(BusinessCode.参数校验失败, exception.getMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Result handleException(Exception exception) {
        log.error("未知系统错误", exception);
        return Result.fail(BusinessCode.未知系统错误, exception.getMessage());
    }
}
