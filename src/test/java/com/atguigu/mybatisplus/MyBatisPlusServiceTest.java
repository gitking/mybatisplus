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


    @Test
    public void testLambdaQuery(){
        /**
         * 注意lambdaQuery() 和  query() 方法都是在IService接口里面的
         *
         */
        User user = userService.lambdaQuery().eq(User::getName, "ybc").one();
        System.out.println("lambdaQuery()可以直接自动创建一个LambdaQueryWrapper对象" + user);

        User queryUser = userService.query().eq("user_name", "ybc").one();
        System.out.println("query()可以直接自动创建一个QueryWrapper对象" + queryUser);
    }

    @Test
    public void testLambdaQueryLast(){
        /**
         * 注意lambdaQuery() 和  query() 方法都是在IService接口里面的
         * .last()方面后面可以增加一个limit 1 自定义的SQL语句
         */
        User user = userService.lambdaQuery().eq(User::getName, "ybc").last(" limit 1 ").one();
        System.out.println("lambdaQuery()可以直接自动创建一个LambdaQueryWrapper对象" + user);

        User orderUser = userService.lambdaQuery().eq(User::getName, "ybc").orderByAsc(User::getName).last(" limit 1 ").one();
        System.out.println("lambdaQuery()可以直接自动创建一个LambdaQueryWrapper对象，先排序再Limit" + orderUser);

        User queryUser = userService.query().eq("user_name", "ybc").last(" limit 1 ").one();
        System.out.println("query()可以直接自动创建一个QueryWrapper对象" + queryUser);

        User orderByQueryUser = userService.query().eq("user_name", "ybc").orderByAsc("user_name").last(" limit 1 ").one();
        System.out.println("query()可以直接自动创建一个QueryWrapper对象,排序后再Limit" + orderByQueryUser);
    }
}
