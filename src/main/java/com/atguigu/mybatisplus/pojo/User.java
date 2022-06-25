package com.atguigu.mybatisplus.pojo;


import com.baomidou.mybatisplus.annotation.*;
import lombok.*;


/**
 *
 * @Data 注解相当于下面5个注解的结合
 *
 * @NoArgsConstructor
 * @AllArgsConstructor
 * @Getter
 * @Setter
 * @EqualsAndHashCode
 *
 * MyBatisPlus当数据库表的名字和实体类名字不一样的时候有俩种解决办法：
 *  1. @TableName("user")，在实体类上面使用@TableName("user")注解标注数据库表的名字
 *  2. 全局配置,应为有的公司数据库表名的命名是有规范的，比如说表名必须以T_开头,t_user，这时候怎么办呢？
 *      第一: 在实体类上面加@TableName("t_user")就可以了，那如果有100张表,难道你要每张表都加这个@TableName("t_user")注解吗？
 *      第二: 全局配置，可以在配置文件里面配置这个东西，表前缀，mybatis-plus.global-config.db-config.table-prefix:t_
 *              这样实体类上面就可以不用添加@TableName注解了,MyBatisplus会自动根据实体类生成表名: t_ + 实体类的名字
 */
@Data
//@TableName("user")// 设置实体类所对应的表名
public class User {

    /**
     * @TableId 将此字段标注为数据库表的主键
     * 如果数据库表的主键列名叫id,实体类的这个字段名字也叫id，是不需要这个@TableId注解的。
     * 只有数据库的主键不叫id时才需要这个注解,比如说数据库的主键列叫uid，注意此时User类的字段也必须叫uid,最后必须加上@TableId这个注解才行
     * 这样mybatisplus才知道哪个字段时数据库的主键ID。
     * 但是还有一种情况,就是数据库的主键名字跟实体类的id字段名字不一样,比如数据库主键ID叫uid,实体类的字段叫tid,这时怎么办？
     * 此时需要这样做 @TableId(value = "uid")或者@TableId("uid")来告诉mybatisplus数据库的主键叫uid就行了。
     *
     * @TableId(value = "id", type = IdType.AUTO)// 设置主键为自增模式
     * MyBatisplus默认的主键ID生成策略是雪花算法，可以使用上面的主键修改MyBatisplus的主键生成策略为主键自增。
     * TODO 联合主键怎么办？问题待解决
     */
    @TableId(value = "id", type = IdType.AUTO)// 设置主键为自增模式
    private Long id;


    /**
     * 如果数据库表里面的字段是user_name，实体类的字段名字为驼峰命名userName，那么mybatisplus和mybatis
     * 是不需要任何配置，默认就支持的。
     * 如果数据库的字段跟实体类的字段完全没有规则并且对应不上，就需要使用@TableField("user_name")这个注解来标注了
     */
    @TableField("user_name")
    private String name;

    private Integer age;

    private String email;

    /**
     * 逻辑删除,MyBatisplus默认0代表未删除,1代表已删除,
     * 调用删除方法时的SQL语句为:UPDATE t_user SET is_deleted=1 WHERE id IN ( ? , ? , ? ) AND is_deleted=0
     * 其中的AND is_deleted=0 和 is_deleted=1 是MyBatisplus自动加上的。
     * 然后，你通过mybatisplus调用查询方法的时候，逻辑删除的数据也是查询不出来的。
     *
     * TODO 那么问题来了，假如我想查询所有数据呢，删除的和没删除的都要查怎么办？问题待解决
     */
    @TableLogic
    private Integer isDeleted;
}
