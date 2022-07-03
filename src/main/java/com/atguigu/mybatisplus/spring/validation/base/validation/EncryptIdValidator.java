package com.atguigu.mybatisplus.spring.validation.base.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义校验
 * 业务需求总是比框架提供的这些简单校验要复杂的多，我们可以自定义校验来满足我们的需求。自定义spring validation非常简单，假设我们自定义加密id（由数字或者a-f的字母组成，32-256长度）校验，主要分为两步：
 * 1. 自定义约束注解 EncryptId
 * 2.实现ConstraintValidator接口编写约束校验器
 * 这样我们就可以使用@EncryptId进行参数校验了！
 */
public class EncryptIdValidator implements ConstraintValidator<EncryptId, String> {

    private static final Pattern PARTTERN = Pattern.compile("^[a-f\\d]{32,256}$");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value != null) {
            Matcher matcher = PARTTERN.matcher(value);
            return matcher.find();
        }
        return true;
    }

}
