package com.tsl.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsl.commonutils.Result;
import com.tsl.educms.entity.CrmBanner;
import com.tsl.educms.service.CrmBannerService;
import io.swagger.annotations.Api;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-14
 */
@Api(description = "后台轮播图服务")
@RestController
@CrossOrigin
@MapperScan("com.tsl.educms.mapper")//扫描mapper，edu是写在了配置类中
@RequestMapping("/educms/banner")
public class AdminBannerController {

    @Autowired
    private CrmBannerService crmBannerService;

    //轮播图分页查询
    @GetMapping("pageBanner/{page}/{limit}")
    public Result pageBanner(@PathVariable long page,@PathVariable long limit){
        Page<CrmBanner> pageBanner = new Page<>(page,limit);
        crmBannerService.page(pageBanner,null);
        return Result.ok().data("items",pageBanner.getRecords()).data("total",pageBanner.getTotal());
    }

    //添加banner
    @PutMapping("addBanner")
    public Result addBanner(@RequestBody CrmBanner banner){
        boolean save = crmBannerService.save(banner);
        if (save){
            return Result.ok().message("新增banner成功");
        }else {
            return Result.error().message("新增banner失败");
        }

    }

    //修改前的根据id查询，返回一个banner对象
    @GetMapping("findBannerById/{bannerId}")
    public Result findBannerById(@PathVariable String bannerId){
        CrmBanner banner = crmBannerService.getById(bannerId);
        return Result.ok().data("banner",banner);
    }

    //修改banner
    @PostMapping("updetaBanner")
    public Result updetaBanner(@RequestBody CrmBanner banner){
        boolean b = crmBannerService.updateById(banner);
        if (b){
            return Result.ok().message("修改banner成功");
        }else {
            return Result.error().message("修改banner失败");
        }

    }

    //删除banner
    @DeleteMapping("deletaBanner/{bannerId}")
    public Result deletaBeannerById(@PathVariable String bannerId){
        boolean b = crmBannerService.removeById(bannerId);
        if (b){
            return Result.ok().message("删除banner成功");
        }else {
            return Result.error().message("删除banner失败");
        }

    }


}

