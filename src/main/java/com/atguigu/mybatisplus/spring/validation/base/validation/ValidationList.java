package com.atguigu.mybatisplus.spring.validation.base.validation;

import lombok.experimental.Delegate;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 集合校验
 * 如果请求体直接传递了json数组给后台，并希望对数组中的每一项都进行参数校验。此时，如果我们直接使用java.util.Collection下的list或者set来接收数据，
 * 参数校验并不会生效！我们可以使用自定义list集合来接收参数：
 * 集合校验 最简单的方法 是在List泛型的尖括号里加@Valid （springboot2.2以上都可以，2.2以下没测试不清楚） ：www.baeldung.com https://www.baeldung.com/spring-validate-list-controller
 * @param <E>
 */
public class ValidationList<E> implements List<E> {

    @Delegate // @Delegate 是lombok注解,@Delegate注解受lombok版本限制，1.18.6以上版本可支持。如果校验不通过，会抛出NotReadablePropertyException，同样可以使用统一异常进行处理。
    @Valid // 一定要加@Valid注解
    public List<E> list = new ArrayList<>();

    @Override // 一定要记得重写toString方法
    public String toString() {
        return list.toString();
    }
}
