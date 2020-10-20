package com.tsl.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsl.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tsl.eduservice.entity.VO.CourseInfoVo;
import com.tsl.eduservice.entity.VO.CoursePublishVo;
import com.tsl.eduservice.entity.frontVO.CourseFrontVo;
import com.tsl.eduservice.entity.frontVO.CourseWebVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-03
 */

public interface EduCourseService extends IService<EduCourse> {
    public String saveCourseInfo(CourseInfoVo courseInfoVo);
    //根据课程id查询课程基本信息
    CourseInfoVo getCourseInfo(String courseId);

    //修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程确认信息
    CoursePublishVo publishCourseInfo(String id);
    void deleteCourse(String id);

    Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getCourseInfoById(String courseId);
}
