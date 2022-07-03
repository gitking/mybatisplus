package com.atguigu.mybatisplus.spring.validation.http;

import com.atguigu.mybatisplus.spring.validation.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HttpDegradeFallback implements HttpDegradeApi {

    @Override
    public Result<Integer> test() {
        Result<Integer> fallback = new Result<>();
        fallback.setCode(100).setMsg("fallback").setBody(1000000);
        return  fallback;
    }

    @Override
    public Result<Integer> test2() {
        Result<Integer> fallback = new Result<>();
        fallback.setCode(100).setMsg("fallback").setBody(1000000);
        return fallback;
    }

    @Override
    public Result<Integer> test3() {
        Result<Integer> fallback = new Result<>();
        fallback.setCode(100).setMsg("fabllback").setBody(1000000);
        return fallback;
    }
}
