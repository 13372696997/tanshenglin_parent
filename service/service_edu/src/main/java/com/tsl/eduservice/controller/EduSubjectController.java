package com.tsl.eduservice.controller;


import com.tsl.commonutils.Result;
import com.tsl.eduservice.entity.OneTreeJson;
import com.tsl.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-02
 */
@RestController
@CrossOrigin//解决跨域
@RequestMapping("/eduservice/subject")
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;
    //添加课程分类
    //获取传过来的文件，然后读取文件的内容
    @PostMapping("addSuject")
    public Result addSuject(MultipartFile file){
        eduSubjectService.savaSubject(file,eduSubjectService);
        return Result.ok();
    }

    @GetMapping("findSubject")
    public Result findSubJect(){

        List<OneTreeJson> list = eduSubjectService.getAllSubject();

        return Result.ok().data("data2",list);
    }

    @GetMapping("getAllSubject")
    public Result getAllSubject(){

        List<OneTreeJson> list = eduSubjectService.getAllSubject();

        return Result.ok().data("list",list);
    }

}

