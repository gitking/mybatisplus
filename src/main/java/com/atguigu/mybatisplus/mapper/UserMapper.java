package com.atguigu.mybatisplus.mapper;

import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * 在MyBatis中Mapper接口里面的方法以及SQL都是需要我们自己去编写的，而MyBatisplus就不需要自己写了
 *
 * 注意这个BaseMapper是MyBatisplus提供的，默认有很多方法。BaseMapper支持泛型，BaseMapper是通用Mapper,MyBatis还有通用Service
 *
 * 在IOC容器中只能类所对应的Bean,不能存在接口所对应的Bean,所以说在运行期间将UserMapper接口动态生成的代理实现类交给IOC容器去管理的。
 *
 * 注意,此时我们只创建了一个User实体类，然后让UserMapper继承了BaseMapper这个类，
 * 我们什么SQL也没有编写，更没有指定User实体类对应的是哪张表，但是运行MyBatisPlusTest.testSelectList方法，就能将数据库里面的数据查询出来。
 * 这说明MyBatis默认查询的表名就是User实体类的名字，列名对应的就是User实体类的字段名字。太强大了，MyBatisPlus。
 */
//@Repository的意思是将一个类或者接口标识为一个持久层组件.其实这个类不添加@Repository也可以，没有影响的。为了Test类不报错才添加的。
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据id查询用户信息为map集合
     * @param id 用户ID
     * @return Map<String, Object> 结果对象map
     */
    Map<String, Object> selectMapById(Long id);


    /**
     * 利用MyBatisPlus的分页插件功能，自定义分页查询SQL,
     * 注意第一个参数必须是MyBatisPlus的Page对象，返回值页必须是Page对象.
     *
     * 注意这个方法有俩个参数，MyBatis如果有俩个参数我们可以使用MyBatis所提供的访问方式，
     * 也可以通过@Param来设置命名参数，来规定当前这个参数的访问规则。
     * @param page MyBatis-Plus所提供的分页对象，必须位于第一个参数的位置
     * @param age
     * @return
     */
    Page<User> selectPageVo(@Param("page") Page<User> page, @Param("age") Integer age);
}
