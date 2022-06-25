package com.atguigu.mybatisplus.pojo;


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
 */
@Data
public class User {
    private Long id;

    private String name;

    private Integer age;

    private String email;
}
