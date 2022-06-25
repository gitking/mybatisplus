package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.pojo.User;
import com.atguigu.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MyBatisPlusServiceTest {

    @Autowired
    private UserService userService;


    @Test
    public void testGetCountService() {
        long count = userService.count();
        // 注意SQL语句: SELECT COUNT( * ) FROM user
        System.out.println("查询表里面总共几条数据: " + count);
    }

    /**
     * 批量添加/批量插入功能,注意BaseMapper里面没有提供批量添加或者批量插入的功能,只有IService里面有批量加或者批量插入的功能.
     *
     */
    @Test
    public void testInsertMore() {
        List<User> list = new ArrayList<>();
        for (int i=1; i<=10; i++) {
            User user = new User();
            user.setName("ybc" + i);
            user.setAge(20 + i);
            list.add(user);
        }
        boolean b = userService.saveBatch(list);
        // 注意SQL语句: INSERT INTO user ( id, name, age ) VALUES ( ?, ?, ? )
        System.out.println("批量添加/批量插入是否成功:" + b);

        list.forEach(user -> {
            System.out.println("批量插入后生成的ID为:" + user.getId());
        });
    }
}
