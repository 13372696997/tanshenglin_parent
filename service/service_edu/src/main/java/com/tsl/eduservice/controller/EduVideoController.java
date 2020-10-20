package com.tsl.eduservice.controller;


import com.tsl.commonutils.Result;
import com.tsl.eduservice.client.VodClient;
import com.tsl.eduservice.entity.EduVideo;
import com.tsl.eduservice.service.EduVideoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-03
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/video")
public class EduVideoController {
    @Autowired
    public EduVideoService eduVideoService;

    @Autowired
    private VodClient vodClient;

    @PostMapping("addvideo")
    public Result addvideo(@RequestBody EduVideo eduVideo){
        boolean a = eduVideoService.save(eduVideo);
        if (a){
            return Result.ok().message("添加小节成功");
        }else {
            return Result.error().message("添加小节失败");
        }

    }
    //删除单个小节和视频
    @DeleteMapping("{id}")
    public Result deletevideo(@PathVariable String id){

        //首先判断小节有没有视频信息
        String videoSourceId = eduVideoService.getById(id).getVideoSourceId();
        //如果视频id不为null
        if (!StringUtils.isEmpty(videoSourceId)){
            Result result = vodClient.deleteVideo(videoSourceId);
        }

        boolean a =  eduVideoService.removeById(id);
        if (a){
            return Result.ok().message("删除小节成功");
        }else {
            return Result.error().message("删除小节失败");
        }
    }
    //删除课程，并删除小节下的视频
    @DeleteMapping("deleteVideosByCourseId/{courseId}")
    public Result deleteVideosByCourseId(@PathVariable String courseId){

        return Result.ok();
    }

    @ApiOperation("传入课程id，只查询该课程下，video表中视频字段的值")
    @GetMapping("test/{courseId}")
    public void test(@PathVariable String courseId){

        eduVideoService.testSelect(courseId);
    }

}

