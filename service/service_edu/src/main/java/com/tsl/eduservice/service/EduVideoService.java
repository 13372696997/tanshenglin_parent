package com.tsl.eduservice.service;

import com.tsl.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-03
 */
public interface EduVideoService extends IService<EduVideo> {
void deleteVideoById(String courseId);

    void testSelect(String courseId);
}
