package com.tsl.eduservice.service;

import com.tsl.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tsl.eduservice.entity.chapter.chapterVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-03
 */

public interface EduChapterService extends IService<EduChapter> {
    public List<chapterVO> getChapter(String courseId);
    EduChapter getOnechapter(String chapterId);
    boolean deleteChapter(String chapterId);
    void removeChapterByCourseId(String courseId);
}
