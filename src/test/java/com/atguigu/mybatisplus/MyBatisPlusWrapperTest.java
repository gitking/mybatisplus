package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class MyBatisPlusWrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectWrapper() {
        // 查询用户名包含a, 年龄在20到30之间, 邮箱信息不为null的用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name", "三")
                .between("age",20, 30)
                .isNotNull("email");
        //ge: great equals 大于等于
        //le: less equals 小于等于
        List<User> userList = userMapper.selectList(queryWrapper);

        /**
         * 注意自动生成的SQL语句，这次还必须注意生成的参数
         * SELECT id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (user_name LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
         * %a%(String), 20(Integer), 30(Integer)
         * 注意第一个参数前后都有%符号，说明like默认的模糊查询时前后都模糊查询的。
         * 还有likeLeft和likeRight方法
         */
        userList.forEach(System.out::println);

        // 只查询部分字段
        System.out.println("只查询部分字段========================================");
        QueryWrapper<User> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.select("user_name name", "age", "email")
                .eq("user_name","ybc9");

        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper1);
        maps.forEach(System.out::println);

        System.out.println("只查询部分字段，并封装城User对象###########################################################");
        List<User> userList1 = userMapper.selectList(queryWrapper1);
        // TODO 注意，只查询部分字段,返回的user对象name属性为空，原因是因为user的name属性跟表里面的字段属性不一样，问题待解决
        // 解决办法就是: queryWrapper1.select("user_name name", "age", "email");
        // 给字段起一个别名就行了。问题已解决
        userList1.forEach(System.out::println);
    }


    /**
     * 测试子查询
     */
    @Test
    public void testSubSelect() {
        // 查询id小于等于100的用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.inSql("id", "select id from t_user where id <= 100");
        List<User> list = userMapper.selectList(queryWrapper);
        // SELECT id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (id IN (select id from t_user where id <= 100))
        list.forEach(System.out::println);
    }

    @Test
    public void testOrderBy() {
        // 查询用户信息，按照年龄降序排列，如果年龄相同，则按照ID升序排列
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("age").orderByAsc("id");
        List<User> userList = userMapper.selectList(queryWrapper);
        // 注意SQL语句:SELECT id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 ORDER BY age DESC,id ASC
        userList.forEach(System.out::println);
    }

    @Test
    public void testDel1() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email");

        // UPDATE t_user SET is_deleted=1 WHERE is_deleted=0 AND (email IS NULL)
        int delete = userMapper.delete(queryWrapper);
        System.out.println("删除邮箱字段为空的数据,删除了多少行:" + delete);
    }

    @Test
    public void testUpdate() {
        /**
         * 将年龄大于20并且用户名中包含有a或者邮箱为null的用户名字改为:更改
         * gt:grandthan 大于的意思
         */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age", 20)
                .like("user_name","a")
                .or()
                .isNull("email");
        User user = new User();
        user.setName("小明");
        user.setEmail("test@atguigu.com");
        int update = userMapper.update(user, queryWrapper);
        // UPDATE t_user SET user_name=?, email=? WHERE is_deleted=0 AND (age > ? AND user_name LIKE ? OR email IS NULL)
        System.out.println("更新成功" + update + "条数据");
    }

    @Test
    public void testUpdate01() {
        // 将用户名中包含有a并且年龄大于20或者邮箱为null的用户信息修改
        // lambda中的条件优先执行
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name", "a")
                .and(i->i.gt("age", 20).or().isNull("email"));

        User user = new User();
        user.setName("小红");
        user.setEmail("test@atguigu.com");
        int update = userMapper.update(user, queryWrapper);
        // UPDATE t_user SET user_name=?, email=? WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
        System.out.println("更新成功" + update + "条数据");

    }


    /**
     * 测试UpdateWrapper
     */
    @Test
    public void testUpdaterWrapper() {
        // 将用户名中包含a并且(年龄大于20或邮箱为null)的用户信息修改
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.like("user_name", "a")
                .and(i -> i.gt("age", 20).or().isNull("email"));
        updateWrapper.set("user_name", "小黑");
        updateWrapper.set("email", "abc@atguigu.com");
        int result = userMapper.update(null, updateWrapper);
        // UPDATE t_user SET user_name=?,email=? WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
        System.out.println("UpdateWrapper更新成功几条数据:" + result);
    }

    @Test
    public void test09() {
        String username = "";
        Integer ageBegin = 20;
        Integer ageEnd = 30;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("user_name", username);
        }
        if (ageBegin != null) {
            queryWrapper.ge("age", ageBegin);
        }
        if (ageEnd != null) {
            queryWrapper.le("age", ageEnd);
        }
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    /**
     * 动态SQL,condition
     */
    @Test
    public void testCondition() {
        String username = "";
        Integer ageBegin = 20;
        Integer ageEnd = 30;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username), "user_name", username)
            .ge(ageBegin != null, "age", ageBegin)
                .le(ageEnd != null, "age", ageEnd);
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void testLambdaQueryWrapper() {
        String username = "a";
        Integer ageBegin = null;
        Integer ageEnd = 30;
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        // 注意第二个参数, 第二个参数是为了防止你把列名写错,之前的QueryWrapper第二个参数是字符串列名,手写的，很容易写错
        queryWrapper.like(StringUtils.isNotBlank(username), User::getName, username)
                .ge(ageBegin != null, User::getAge, ageBegin)
                .le(ageEnd != null, User::getAge, ageEnd);

        // SELECT id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (user_name LIKE ? AND age <= ?)
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    @Test
    public void testUpdateLambdaWrapper() {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.like(User::getName, "a").and(i -> i.gt(User::getAge, 20).or().isNull(User::getEmail));
        updateWrapper.set(User::getName, "小黑").set(User::getEmail, "abc@atguigu.com");
        int result = userMapper.update(null, updateWrapper);
        System.out.println("result" + result);
    }
}
