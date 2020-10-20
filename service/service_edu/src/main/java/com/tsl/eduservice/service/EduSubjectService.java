package com.tsl.eduservice.service;

import com.tsl.eduservice.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tsl.eduservice.entity.OneTreeJson;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-02
 */

public interface EduSubjectService extends IService<EduSubject> {
    public void savaSubject(MultipartFile file,EduSubjectService subjectService);

    List<OneTreeJson> getAllSubject();
    List<OneTreeJson> getFourSubject();
}
