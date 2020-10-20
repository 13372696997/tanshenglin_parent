package com.tsl.eduunenter.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.tsl.commonutils.JwtUtils;
import com.tsl.commonutils.Result;
import com.tsl.commonutils.orderVO.UcenterMemberOrder;
import com.tsl.eduunenter.entity.UcenterMember;
import com.tsl.eduunenter.entity.VO.userVO;
import com.tsl.eduunenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-17
 */
@RestController
@CrossOrigin
@RequestMapping("/eduunenter/member")
public class UcenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    /** 生成订单的时候调用的方法
     *
     * 根据用户id获取用户信息,直接返回用户对象，不返回Result
     * @param
     * @return
     */
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){
        UcenterMember ucenterMember = ucenterMemberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        BeanUtils.copyProperties(ucenterMember,ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    @PostMapping("login")
    public Result loginUser(@RequestBody UcenterMember user){

        //返回一个token值，在service中用jwt实现
        String token = ucenterMemberService.login(user);

        return Result.ok().data("token",token);
    }

    //注册
    @PostMapping("register")
    public Result registerUser(@RequestBody userVO user){
        ucenterMemberService.register(user);
        return Result.ok();
    }

    //根据token获取用户信息
    @GetMapping("getMemberInfo")
    public Result getMemberInfo(HttpServletRequest request) {
        //调用jwt工具类的方法。根据request对象获取头信息，返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //查询数据库根据用户id获取用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);
        return Result.ok().data("userInfo",member);
    }
    //查询某一天的注册人数
    @GetMapping("countRegister/{day}")
    public Result countRegister(@PathVariable String day){
        Integer count = ucenterMemberService.countRegisterDay(day);
        return Result.ok().data("countRegister",count);
    }


}

