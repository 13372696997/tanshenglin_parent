package com.tsl.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsl.eduservice.entity.EduCourse;
import com.tsl.eduservice.entity.EduCourseDescription;
import com.tsl.eduservice.entity.VO.CourseInfoVo;
import com.tsl.eduservice.entity.VO.CoursePublishVo;
import com.tsl.eduservice.entity.frontVO.CourseFrontVo;
import com.tsl.eduservice.entity.frontVO.CourseWebVo;
import com.tsl.eduservice.mapper.EduCourseMapper;
import com.tsl.eduservice.service.EduChapterService;
import com.tsl.eduservice.service.EduCourseDescriptionService;
import com.tsl.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsl.eduservice.service.EduVideoService;
import com.tsl.servicebase.ExceptionHandler.tanShengLinException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-03
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    @Autowired//因为vo数据要存多张表，所以就把他们的service注入，因为service里面能链接数据库做持久化
    private EduCourseDescriptionService courseDescriptionService;

    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //注意转换！baseMapper.insert里面传的是跟单表对应的实体类
        EduCourse course = new EduCourse();

        BeanUtils.copyProperties(courseInfoVo,course);
        int i = baseMapper.insert(course);
        if(i==0){
            throw new tanShengLinException(20001,"添加课程信息失败");
        }
        //添加课程简介
        EduCourseDescription courseDescription = new EduCourseDescription();
        //设置一对一关系,需要改变courseDescription表中id的生成策略，input
        String cid = course.getId();
        courseDescription.setId(cid);
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.save(courseDescription);
        return cid;
    }
    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2 查询描述表
        EduCourseDescription courseDescription = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update == 0) {
            throw new tanShengLinException(20001,"修改课程信息失败");
        }

        //2 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(description);
    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }

    @Override
    //删除课程
    public void deleteCourse(String courseId) {
        //删除课程小节,要在eduVideoService中定义这个方法
        eduVideoService.deleteVideoById(courseId);
        //删除课程章节
        eduChapterService.removeChapterByCourseId(courseId);
        //删除课程描述,这个一对一，所以直接删除就行了
        courseDescriptionService.removeById(courseId);
        //删除课程本身
        int i = baseMapper.deleteById(courseId);
        if (i==0){
            throw new tanShengLinException(20001,"删除失败");
        }

    }

    @Override
    /**
     * 前端展示带条件查询课程列表
     */
    public Map<String, Object> getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper();
        //判断条件为不为null
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())){
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getSubjectId())){
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())){
            wrapper.orderByDesc("buy_count",courseFrontVo.getBuyCountSort());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())){
            wrapper.orderByDesc("gmt_create",courseFrontVo.getGmtCreateSort());
        }
        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())){
            wrapper.orderByDesc("price",courseFrontVo.getPriceSort());
        }

        baseMapper.selectPage(coursePage,wrapper);

        List<EduCourse> records = coursePage.getRecords();
        long current = coursePage.getCurrent();
        long pages = coursePage.getPages();
        long size = coursePage.getSize();
        long total = coursePage.getTotal();
        boolean hasNext = coursePage.hasNext();//下一页
        boolean hasPrevious = coursePage.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    @Override
    /**
     * 课程详情信息查询
     */
    public CourseWebVo getCourseInfoById(String courseId) {


        return baseMapper.getBaseCourseInfo(courseId);
    }
}
