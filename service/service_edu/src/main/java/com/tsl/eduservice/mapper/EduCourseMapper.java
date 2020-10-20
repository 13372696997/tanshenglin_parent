package com.tsl.eduservice.mapper;

import com.tsl.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tsl.eduservice.entity.VO.CoursePublishVo;
import com.tsl.eduservice.entity.frontVO.CourseWebVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-03
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getPublishCourseInfo(String courseId);

    CourseWebVo getBaseCourseInfo(String courseId);
}
