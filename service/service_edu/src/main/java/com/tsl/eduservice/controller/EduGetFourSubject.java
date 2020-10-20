package com.tsl.eduservice.controller;


import com.tsl.commonutils.Result;
import com.tsl.eduservice.entity.EduSubject;
import com.tsl.eduservice.entity.OneTreeJson;
import com.tsl.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@Api(description = "返回四层结构的课程信息")
@RequestMapping("/eduservice/getAllSubject")
public class EduGetFourSubject {

    @Autowired
    public EduSubjectService eduSubjectService;

    @GetMapping("fourSubject")
    public Result getFourSubject(){
        List<OneTreeJson> allSubject = eduSubjectService.getFourSubject();
        return Result.ok().data("AllSubject",allSubject);
    }
}
