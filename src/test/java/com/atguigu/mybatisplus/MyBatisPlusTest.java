package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MyBatisPlusTest {
    /**
     * 这里IDEA可能会提示报错，不用管。
     * 在IOC容器中只能类所对应的Bean,不能存在接口所对应的Bean,所以说在运行期间将UserMapper接口动态生成的代理实现类交给IOC容器去管理的。
     * 所以在IDEA编译的时候，IDEA可能会认为这里的userMapper是无法进行自动装配的，其实在运行阶段是没有问题的。
     *
     * 有强迫症的人，可以在UserMapper上面加上@Repository注解就可以了
     */
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelectList() {
        /**
         * 注意selectList方法有一个参数，叫 Wrapper<T> queryWrapper，Wrapper是条件构造器，也就说当我们在查询数据的时候，
         * 如果我们有条件的话，我们就可以通过Wrapper来实现where条件，如果没有条件的话，就可以传null。
         */
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);

    }
}
