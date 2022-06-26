package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.ProductMapper;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.service.ProductService;
import com.atguigu.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * MyBatisPlus 测试多数据源
 */
@SpringBootTest
public class MyBatisPlusDynamicDsTest {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Test
    public void testDynamicDataSource() {
        System.out.println("从master主数据源查询到的数据:" + userService.getById(1L));
        System.out.println("从slave_1从数据源查询到的数据:" + productService.getById(1L));
    }
}
