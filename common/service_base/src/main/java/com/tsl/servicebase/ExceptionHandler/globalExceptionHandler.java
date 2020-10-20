package com.tsl.servicebase.ExceptionHandler;

import com.tsl.commonutils.Result;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@ControllerAdvice
@Slf4j
public class globalExceptionHandler {

    //自定义异常
    @ExceptionHandler(tanShengLinException.class)
    @ResponseBody
    public Result error(tanShengLinException e){
        log.error(e.getMessage());
        e.printStackTrace();
        return Result.error().code(e.getCode()).message(e.getMessage());
    }


    //全局异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.error().message("执行了全局异常处理...");
    }

    //特定异常
    @ExceptionHandler(IOException.class)
    @ResponseBody
    public Result IOException(Exception e){
        e.printStackTrace();
        return Result.error().message("执行了IOException异常处理...");
    }



}
