package com.tensquare.base.controller;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author:潘佳伟
 * @Date：2019/5/18 16:24
 */
@RestControllerAdvice
public class BaseExceptionHandler {
    //如果有错误异常，拦截下来显示错误信息，
    @ExceptionHandler(value = Exception.class)
    public Result exception(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR,e.getMessage());
    }
}

