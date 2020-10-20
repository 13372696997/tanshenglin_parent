package com.tsl.educms.controller;


import com.tsl.commonutils.Result;
import com.tsl.educms.entity.CrmBanner;
import com.tsl.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-14
 */
@Api(description = "前台轮播图服务")
@RestController
@CrossOrigin
@MapperScan("com.tsl.educms.mapper")//扫描mapper，edu是写在了配置类中
@RequestMapping("/educms/banner")
public class clientBannerController {

    @Autowired
    private CrmBannerService crmBannerService;

    //查询所有的banner,并且添加到redis中
    @GetMapping("findAllBanner")
    public Result getAllBanner(){
        List<CrmBanner> bannerList = crmBannerService.selectAllBanner();
        return Result.ok().data("list",bannerList);
    }


}

