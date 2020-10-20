package com.tsl.servicebase.ExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor//生成有参数的构造方法
@NoArgsConstructor//生成无参数的构造方法
public class tanShengLinException extends RuntimeException{

    private Integer code;

    private String message;

}
