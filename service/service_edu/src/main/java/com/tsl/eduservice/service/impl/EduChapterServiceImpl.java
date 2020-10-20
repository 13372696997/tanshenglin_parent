package com.tsl.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tsl.eduservice.entity.EduChapter;
import com.tsl.eduservice.entity.EduCourseDescription;
import com.tsl.eduservice.entity.EduVideo;
import com.tsl.eduservice.entity.chapter.chapterVO;
import com.tsl.eduservice.entity.chapter.videoVO;
import com.tsl.eduservice.mapper.EduChapterMapper;
import com.tsl.eduservice.mapper.EduCourseDescriptionMapper;
import com.tsl.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsl.eduservice.service.EduCourseDescriptionService;
import com.tsl.eduservice.service.EduVideoService;
import com.tsl.servicebase.ExceptionHandler.tanShengLinException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-03
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    public EduVideoService eduVideoService;

    @Override
    public List<chapterVO> getChapter(String courseId) {
        QueryWrapper chapterwrapper = new QueryWrapper();
        chapterwrapper.eq("course_id",courseId);
        List<chapterVO> chapterVO = new ArrayList<>();

            List<EduChapter> chapter = baseMapper.selectList(chapterwrapper);

            List<EduVideo> videos = eduVideoService.list(null);

        for (int i=0;i<chapter.size();i++){
            EduChapter eduChapter = chapter.get(i);
            chapterVO chVo = new chapterVO();
            BeanUtils.copyProperties(eduChapter,chVo);
            chapterVO.add(chVo);
            //在这里声明要返回的第二层集合
            List<videoVO> videoVOs = new ArrayList<>();
            for (int m = 0;m<videos.size();m++){
                EduVideo eduVideo = videos.get(m);
                videoVO videovo = new videoVO();
                if (eduChapter.getId().equals(eduVideo.getChapterId())&&eduChapter.getCourseId().equals(eduVideo.getCourseId())){
                    BeanUtils.copyProperties(eduVideo,videovo);
                    videoVOs.add(videovo);
                }
            }
            chVo.setChildren(videoVOs);
        }

        return chapterVO;

    }

    @Override
    public EduChapter getOnechapter(String chapterId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id",chapterId);
        EduChapter eduChapter = baseMapper.selectOne(wrapper);
        return eduChapter;
    }

    @Override
    //删除章节，判断有没有小节
    public boolean deleteChapter(String chapterId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(wrapper);
        if (count>0){//如果有数据，就不能删除
            throw new tanShengLinException(20001,"章节里面有小节数据，不能删除");
        }else {
            int result = baseMapper.deleteById(chapterId);
            return result>0;//大于0就是成功
        }

    }

    @Override
    //根据课程信息删除章节
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }


    public List<chapterVO> getChapter2(List<EduChapter> chapters,List<EduVideo> videos) {

        List<chapterVO> chapterVO = new ArrayList<>();


        for (int i=0;i<chapters.size();i++){
            EduChapter eduChapter = chapters.get(i);
            chapterVO chVo = new chapterVO();
            BeanUtils.copyProperties(eduChapter,chVo);
            chapterVO.add(chVo);
            //在这里声明要返回的第二层集合
            List<videoVO> videoVOs = new ArrayList<>();
            for (int m = 0;m<videos.size();m++){
                EduVideo eduVideo = videos.get(m);
                videoVO videovo = new videoVO();
                if (eduChapter.getId().equals(eduVideo.getChapterId())&&eduChapter.getCourseId().equals(eduVideo.getCourseId())){
                    BeanUtils.copyProperties(eduVideo,videovo);
                    videoVOs.add(videovo);
                }
            }
            chVo.setChildren(videoVOs);
        }

        return chapterVO;

    }

    /**
     * <p>
     * 课程简介 服务实现类
     * </p>
     *
     * @author tanShengLin
     * @since 2020-09-03
     */
    @Service
    public static class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {

    }
}
