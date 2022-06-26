package com.atguigu.mybatisplus.service.impl;

import com.atguigu.mybatisplus.mapper.ProductMapper;
import com.atguigu.mybatisplus.pojo.Product;
import com.atguigu.mybatisplus.service.ProductService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @DS 可以注解在方法上或类上，同时存在就近原则 方法上注解 优先于 类上注解。没有@DS注解会使用默认数据源
 * https://www.mybatis-plus.com/guide/dynamic-datasource.html#%E6%96%87%E6%A1%A3-documentation
 */
@DS("slave_1") //指定数据源
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
}
