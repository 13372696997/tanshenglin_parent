package com.tsl.commonutils;

import com.baomidou.mybatisplus.extension.api.R;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class Result {

    private Boolean success;

    private Integer code;

    private String message;

    private Map<String,Object> data = new HashMap<String, Object>();

    //私有化，不能new这个类
    private Result() {
    }
    //成功的静态方法
    public static Result ok(){
        Result R = new Result();
        R.setSuccess(true);
        R.setCode(RestCode.SUCCESS);
        R.setMessage("成功");
        return R;
    }
    //失败的静态方法
    public static Result error(){
        Result R = new Result();
        R.setSuccess(false);
        R.setCode(RestCode.ERROR);
        R.setMessage("失败");
        return R;
    }
    //链式编程：Result.ok().code().message()...
    public Result success(Boolean success){
        this.setSuccess(success);
        return this;//返回当前类对象
    }
    public Result message(String message){
        this.setMessage(message);
        return this;//返回当前类对象
    }
    public Result code(Integer code){
        this.setCode(code);
        return this;//返回当前类对象
    }
    public Result data(String key,Object value){
        this.data.put(key,value);
        return this;//返回当前类对象
    }
    public Result data(Map<String, Object> map){
        this.setData(map);
        return this;
    }


}
