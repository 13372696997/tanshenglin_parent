package com.tsl.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tsl.eduservice.entity.EduTeacher;
import com.tsl.eduservice.mapper.EduTeacherMapper;
import com.tsl.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author tanShengLin
 * @since 2020-08-19
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public Map<String, Object> getTeacherFront(Page<EduTeacher> teacherPage) {

        QueryWrapper wrapper = new QueryWrapper();

        wrapper.orderByDesc("gmt_create");

        baseMapper.selectPage(teacherPage,wrapper);

        //把分页的数据取出来，放到map集合中
        Map<String,Object> map = new HashMap();

        map.put("current",teacherPage.getCurrent());
        map.put("pages",teacherPage.getPages());
        map.put("size",teacherPage.getSize());
        map.put("total",teacherPage.getTotal());
        map.put("items",teacherPage.getRecords());
        map.put("hasNext",teacherPage.hasNext());//是否有下一页
        map.put("hasPrevious",teacherPage.hasPrevious());//是否有下一页
        return map;
    }
}
