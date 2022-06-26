CREATE DATABASE `mybatis_plus`;
use `mybatis_plus`;
create table `user` (
	`id` bigint(20) not null comment '主键ID',
	`name` varchar(30) default null comment '姓名',
	`age` int(11) default null comment '年龄',
	`email` varchar(50) default null comment '邮箱',
	primary key (`id`)
) ENGINE=InnoDB default charset=utf8;


use `mybatis_plus`;

create table t_product (
	id bigint(20) not null comment '主键ID',
    NAME varchar(30) null default null comment '商品名称',
    price int(11) default 0 comment '价格',
    version int(11) default 0 comment '乐观锁版本号',
    primary key(id)
);