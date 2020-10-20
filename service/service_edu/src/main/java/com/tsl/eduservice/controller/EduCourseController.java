package com.tsl.eduservice.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.tsl.commonutils.Result;
import com.tsl.eduservice.entity.EduCourse;
import com.tsl.eduservice.entity.VO.CourseInfoVo;
import com.tsl.eduservice.entity.VO.CoursePublishVo;
import com.tsl.eduservice.service.EduCourseDescriptionService;
import com.tsl.eduservice.service.EduCourseService;
import com.tsl.eduservice.service.EduVideoService;
import com.tsl.servicebase.ExceptionHandler.tanShengLinException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-03
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/course")
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;



    @PostMapping("addCourseInfo")
    public Result addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String cid = eduCourseService.saveCourseInfo(courseInfoVo);
        return Result.ok().data("courseId",cid);
    }
    //根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public Result getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return Result.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public Result updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        eduCourseService.updateCourseInfo(courseInfoVo);
        return Result.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public Result getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = eduCourseService.publishCourseInfo(id);
        return Result.ok().data("publishCourse",coursePublishVo);
    }

    /**
     * 最终发布，修改课程信息的可见状态
     * @param id
     * @return
     */
    @PostMapping("publishCourse/{id}")
    public Result publishCourse(@PathVariable String id){
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        boolean b = eduCourseService.updateById(course);
        if (b){
            return Result.ok();
        }else {
            return Result.error().message("最终发布失败");
        }
    }
    @GetMapping("getCourseList")
    public Result getCourseList(){
        List<EduCourse> list = eduCourseService.list(null);
        return Result.ok().data("list",list);
    }
    //删除课程
    @DeleteMapping("deleteCourse/{id}")
    public Result deleteCourse(@PathVariable String id){
        eduCourseService.deleteCourse(id);
        return Result.ok();
    }


}

