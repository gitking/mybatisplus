package com.atguigu.mybatisplus.spring.validation.controller;

import com.atguigu.mybatisplus.spring.validation.base.Result;
import com.atguigu.mybatisplus.spring.validation.pojo.PersonVO;
import com.atguigu.mybatisplus.spring.validation.pojo.SavePersonDTO;
import com.atguigu.mybatisplus.spring.validation.pojo.UpdatePersonDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/persion")
@Validated
@Api(tags = "Person管理")
public class PersonController {

    @ApiOperation("保存用户")
    @PostMapping("savePerson")
    public Result<PersonVO> savePerson(@RequestBody @Valid SavePersonDTO personDTO) {
        PersonVO personVO = new PersonVO().setAge(10).setEmail("xxxx").setId(1L).setName("哈哈");
        return Result.ok(personVO);
    }

    @ApiOperation("更新用户")
    @PostMapping("updatePerson")
    public Result<PersonVO> updatePerson(@RequestBody @Valid UpdatePersonDTO personDTO) {
        PersonVO personVO = new PersonVO().setAge(10).setEmail("xxxx").setId(1L).setName("哈哈");
        return Result.ok(personVO);
    }

    @ApiOperation("查询person")
    @GetMapping("queryPerson")
    public Result<List<PersonVO>> queryPerson(
            @ApiParam("用户id") @Min(1000) @Max(1000000) Long id,
            @ApiParam("姓名") @NotNull @Size(min = 2, max = 10) String name,
            @ApiParam("年龄") @NotNull @Max(100) Integer age,
            @ApiParam("邮箱") @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$") String email) {
        List<PersonVO> list = new ArrayList<>();
        list.add(new PersonVO().setAge(10).setEmail("xxxx").setId(1L).setName("哈哈"));
        return Result.ok(list);
    }
}
