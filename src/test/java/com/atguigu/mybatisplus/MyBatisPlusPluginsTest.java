package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MyBatisPlusPluginsTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testPage() {
        Page<User> page = new Page<>(1,3);
        userMapper.selectPage(page, null);
        // getRecords获取当前页的数据
        List<User> records = page.getRecords();
        System.out.println("总条数" + page.getTotal() + "总页数" + page.getPages() + ",当前页的页码" + page.getCurrent() + ",当前页的条数" + page.getSize());
        System.out.println("是否有下一页" + page.hasNext() + "是否有上一页" + page.hasPrevious());
    }


    /**
     * 自定义分页功能
     */
    @Test
    public void testPageVo() {
        Page<User> page = new Page<>(1, 3);
        Page<User> userPage = userMapper.selectPageVo(page, 20);
        // getRecords获取当前页的数据
        List<User> records = page.getRecords();
        records.forEach(System.out::println);
        System.out.println("总条数" + page.getTotal() + "总页数" + page.getPages() + ",当前页的页码" + page.getCurrent() + ",当前页的条数" + page.getSize());
        System.out.println("是否有下一页" + page.hasNext() + "是否有上一页" + page.hasPrevious());
    }
}

