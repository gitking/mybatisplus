package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.ProductMapper;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.Product;
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

    @Autowired
    private ProductMapper productMapper;

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

    /**
     * 测试MyBatisPlus的乐观锁
     */
    @Test
    public void testOptimisticLocking() {
        // 小李查询商品价格
        Product productLi = productMapper.selectById(1L);
        System.out.println("小李查询到的商品价格：" + productLi.getPrice());

        //小王查询商品价格
        Product prodctWang = productMapper.selectById(1L);
        System.out.println("小王查询到的商品价格:" + prodctWang.getPrice());

        System.out.println("MyBatisPlus会使用缓存吗？这俩个对象应该是同一个对象(实际上不是):" + (prodctWang == productLi));

        // 小李将商品价格+50，UPDATE t_product SET name=?, price=?, version=? WHERE id=? AND version=?
        productLi.setPrice(productLi.getPrice() + 50);
        productMapper.updateById(productLi);

        // 小王将商品价格-30，UPDATE t_product SET name=?, price=?, version=? WHERE id=? AND version=?
        prodctWang.setPrice(prodctWang.getPrice() - 30);
        int result = productMapper.updateById(prodctWang);
        if (result == 0) {
            // 操作失败之后重试,这里是重试一次,你可以改成do while循环,限制循环次数，然后抛出异常
            Product productNew = productMapper.selectById(1L);
            productNew.setPrice(productNew.getPrice() - 30);
            int resultNew = productMapper.updateById(productNew);
            System.out.println("失败之后重试更新成功了" + resultNew);
        }

        // 老板查询商品价格
        Product productBoss = productMapper.selectById(1L);
        System.out.println("老板查到的价格为:" + productBoss.getPrice());
    }
}

