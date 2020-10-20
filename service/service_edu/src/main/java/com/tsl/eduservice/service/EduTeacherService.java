package com.tsl.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsl.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author tanShengLin
 * @since 2020-08-19
 */
public interface EduTeacherService extends IService<EduTeacher> {

    Map<String, Object> getTeacherFront(Page<EduTeacher> teacherPage);
}
