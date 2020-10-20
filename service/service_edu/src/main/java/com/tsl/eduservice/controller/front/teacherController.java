package com.tsl.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsl.commonutils.Result;
import com.tsl.eduservice.entity.EduTeacher;
import com.tsl.eduservice.service.EduCourseService;
import com.tsl.eduservice.service.EduTeacherService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
@RestController
@CrossOrigin
@Api(description = "讲师前端页面展示")
@RequestMapping("/eduservice/teacherfront")
/**
 * 前台展示controller
 */
public class teacherController {
    @Autowired
    private EduTeacherService eduTeacherService;

    @Autowired
    private EduCourseService eduCourseService;

    //分页查询讲师方法,官网的
    @PostMapping("getTeacherFrontList/{page}/{limit}")
    public Result getTeacherFrontList(@PathVariable long page,@PathVariable long limit){

        Page<EduTeacher> teacherPage = new Page<>(page,limit);
        Map<String,Object> map = eduTeacherService.getTeacherFront(teacherPage);

        //返回分页中的所有数据
        return Result.ok().data("data",map);
    }

    //讲师详情，前端展示
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public Result getTeacherFrontInfo(@PathVariable String teacherId){
        //根据讲师id查询讲师的信息
        EduTeacher teacher = eduTeacherService.getById(teacherId);
        //根据讲师id查询讲师的课程信息
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("teacher_id",teacherId);
        List courseList = eduCourseService.list(wrapper);

        return Result.ok().data("teacher",teacher).data("courseList",courseList);
    }

}
