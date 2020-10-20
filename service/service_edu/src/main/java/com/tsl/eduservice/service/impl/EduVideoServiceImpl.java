package com.tsl.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tsl.eduservice.client.VodClient;
import com.tsl.eduservice.entity.EduVideo;
import com.tsl.eduservice.mapper.EduVideoMapper;
import com.tsl.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-03
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;
    //TODO 根据课程id删除小节和视频

    @Override

    public void deleteVideoById(String courseId) {

        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("course_id",courseId);
        List<EduVideo> list = baseMapper.selectList(wrapper);

        //首先判断小节有没有视频信息
        for (int a = 0;a<list.size();a++){
            EduVideo video = list.get(a);
            if (!StringUtils.isEmpty(video.getVideoSourceId())){  //如果视频id不为null就远程调用删除视频
                vodClient.deleteVideo(video.getVideoSourceId());
            }
        }
        //删除课程下的所有小节信息
        baseMapper.delete(wrapper);

    }

    @Override
    public void testSelect(String courseId) {
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);
        System.out.println(eduVideoList);
    }

}
