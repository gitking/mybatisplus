package com.atguigu.mybatisplus.spring.validation.base;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 陈添明
 * @date 2022/6/30
 * @param <T>
 */
@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -4513623442666762805L;

    private int code;
    private String msg;
    private String exceptionMsg;
    private T body;

    public static <T> Result ok(T body) {
        return new Result<>().setBody(body).setCode(BusinessCode.成功.code()).setMsg(BusinessCode.成功.message());
    }

    public static Result ok() {
        return new Result<>().setCode(BusinessCode.成功.code()).setMsg(BusinessCode.成功.message());
    }

    public static Result fail(ReturnCode returnCode){
        return new Result<>().setCode(returnCode.code()).setMsg(returnCode.message());
    }

    public static Result fail(ReturnCode returnCode, String exceptionMsg) {
        return new Result<>().setCode(returnCode.code()).setMsg(returnCode.message()).setExceptionMsg(exceptionMsg);
    }
}
