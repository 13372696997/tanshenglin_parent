package com.tsl.eduservice.controller;


import com.tsl.commonutils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
@Api(description = "用户登录service")
@CrossOrigin//解决跨域
public class EduLoginController {

    @ApiOperation("登录返回token")
    @PostMapping("login")
    public Result login(){
        return Result.ok().data("token","admin");
    }

    @ApiOperation("登录返回用户觉得。名称、头像")
    @GetMapping("info")
    public Result info(){
        return Result.ok().data("roles","[admin]").data("name","admin").data("avatar","https://cuisen.oss-cn-shenzhen.aliyuncs.com/36.jpg");
    }


}
