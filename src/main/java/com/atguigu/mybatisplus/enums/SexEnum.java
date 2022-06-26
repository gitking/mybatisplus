package com.atguigu.mybatisplus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * 通用枚举
 *  性别枚举
 */
@Getter
public enum SexEnum {

    MALE(1, "男"),
    FEMALE(2,"女");

    @EnumValue //mybatisplus将注解所标识的值存储到数据库中
    private Integer sex;
    private String sexName;

    SexEnum(Integer sex, String sexName){
        this.sex = sex;
        this.sexName = sexName;
    }
}
