package com.onejane.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// 请求处理失败，不走spring默认的处理
@ControllerAdvice
public class ErrorHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // 任何异常以500抛出
    @ExceptionHandler(Exception.class)
    public Map<String,Object> handle(Exception ex){
        Map<String,Object> info=new HashMap<>();
        info.put("message",ex.getMessage());
        info.put("item",new Date().getTime());
        return  info;
    }
}
