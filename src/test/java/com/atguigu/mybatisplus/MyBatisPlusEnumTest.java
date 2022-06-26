package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.enums.SexEnum;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试MyBatisPlus通用枚举类
 *
 */
@SpringBootTest
public class MyBatisPlusEnumTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testEnum() {
        User user = new User();
        user.setName("通用枚举");
        user.setAge(33);
        user.setSex(SexEnum.FEMALE);
        user.setEmail("enum@atguigu.com");
        int insert = userMapper.insert(user);
        System.out.println("通用枚举插入成功:" + insert);
    }
}
