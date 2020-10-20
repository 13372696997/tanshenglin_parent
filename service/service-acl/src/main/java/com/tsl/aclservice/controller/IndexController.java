package com.tsl.aclservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.tsl.aclservice.service.IndexService;

import com.tsl.commonutils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/index")
//@CrossOrigin
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 根据token获取用户名，然后根据用户名查询用户信息，返回用户名、头像、角色、权限
     */
    @GetMapping("info")
    public Result info(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Map<String, Object> userInfo = indexService.getUserInfo(username);
        return Result.ok().data(userInfo);
    }

    /**
     * 根据用户名查询用户信息，返回用户拥有的菜单列表
     * @return
     */
    @GetMapping("menu")
    public Result getMenu(){
        //获取当前登录用户用户名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<JSONObject> permissionList = indexService.getMenu(username);
        return Result.ok().data("permissionList", permissionList);
    }

    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }

}
