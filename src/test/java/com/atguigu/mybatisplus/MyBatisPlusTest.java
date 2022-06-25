package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试MyBatisPlus的BaseMapper自带的方法
 */
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
        List<User> userList2 = userMapper.selectList(null);
        userList2.forEach(System.out::println);

        User user = userMapper.selectById(1L);
        // SELECT id,name,age,email FROM user WHERE id=?
        System.out.println("根据ID查询数据,查到的数据为:ID," + user.getId() + ",name" + user.getName());

        // 批量查询IN,SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? )
        List<Long> list = Arrays.asList(1L, 2L, 3L);
        List<User> userList = userMapper.selectBatchIds(list);
        userList.forEach(System.out::println);

        // 根据Map查询, map条件里面的值就是where条件,值都是and的关系。
        // SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Jack");
        map.put("age", 20);
        List<User> userList1 = userMapper.selectByMap(map);
        userList1.forEach(System.out::println);

        /**
         * Mybatisplus自定义功能
         * 我想用的功能mybatisplus没有怎么办或者我想自定义一个查询SQL怎么办？其实很简单，一定要注意，MyBatisPlus它就是一个增强工具，注意是增强工具，它并不会改变MyBatis原有的功能，它
         * 只是在MyBatis的基础之上做增强而已。只做增强，而不做改变。
         * 所以你想自定义SQL，在mybatis里面怎么办，在这里就怎么办，跟mybatis完全一样。
         */
        Map<String, Object> mapSelf = userMapper.selectMapById(1L);
        System.out.println("MyBatis的功能,这里使用的SQL语句是UserMapper.xml里面定义的SQL:" + mapSelf);

    }

    @Test
    public void testInsert() {
        User user = new User();

        /**
         * 注意如果你自己主动设置了主键id的值,那么就以你设置的值为准,不会再使用mybatisplus的雪花ID了,也不会使用数据库的自增ID了。
         * 如果数据库的主键ID为自增,但是目前数据库里面ID最大的值为10,然后你这里插入了一个ID为100数据,那么下次自增就从100开始自增了。
         */
        //user.setId(100L);
        user.setName("张三");
        user.setAge(23);
        user.setEmail("zhangsan@atguigu.com");
        int result = userMapper.insert(user);
        System.out.println("插入成功多少条:" + result);
        System.out.println("MyBatisPlus自动生成的主键ID，注意使用的雪花算法，并不是自增的ID:-->" + user.getId());
    }

    @Test
    public void testDelete() {
        Long id = 1540633159794610178L;
        int result = userMapper.deleteById(id);
        System.out.println("根据ID删除数据，删除成功多少行?->" + result);

        /**
         * 注意,当使用ByMap这类方法时,map的key值必须跟数据库的字段名保持一致,mybatisplus不会帮你自动转换的
         * 即使你在实体类上面加了注解也一样不会帮你转换的。
         */
        Map<String, Object> delMap = new HashMap<>();
        delMap.put("user_name", "张三");
        delMap.put("age", 23);
        int resMap = userMapper.deleteByMap(delMap);
        //DELETE FROM user WHERE name = ? AND age = ?
        System.out.println("根据Map里面的值去删除数据,注意观察SQL语句,删除成功多少条数据:" + resMap);

        /**
         * 注意批量删除有俩种方式
         * 1. where id in ()
         * 2. where id ='1' or id = '2'
         */
        int batchRes = userMapper.deleteBatchIds(Arrays.asList(1L, 2L, 3L));
        //DELETE FROM user WHERE id IN ( ? , ? , ? )
        System.out.println("批量删除，注意观察SQL语句，删除成功多少条数据->" + batchRes);
    }

    @Test
    public void testUpdate() {
        User user = new User();
        // updateById注意我们是根据ID更新值的，所以user对象的ID必须有值
        user.setId(4L);
        // 除了ID,这个user对象有值的字段，注意是有值的字段，字段值都会被更新到数据库里面,user对象的age字段没值就不会更新
        user.setName("更新");
        user.setEmail("gengxin@email.com");
        user.setAge(null);
        int result = userMapper.updateById(user);
        // UPDATE user SET name=?, email=? WHERE id=?
        System.out.println("注意SQL语句,根据id字段更新值,更新成功的条数:" + result);

        User updateUser = new User();
        // TODO userMapper.update(updateUser,);

        // TODO ,那么问题来了，假如我想把数据库里面的某一列更新为空,应该怎么办呢? 问题待解决
    }

}
