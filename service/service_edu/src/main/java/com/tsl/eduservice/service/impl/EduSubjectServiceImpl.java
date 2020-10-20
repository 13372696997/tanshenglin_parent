package com.tsl.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tsl.eduservice.Listener.SubjectExcleListener;
import com.tsl.eduservice.entity.*;
import com.tsl.eduservice.entity.Excel.subjectData;
import com.tsl.eduservice.entity.chapter.chapterVO;
import com.tsl.eduservice.entity.chapter.courseVO;
import com.tsl.eduservice.mapper.EduSubjectMapper;
import com.tsl.eduservice.service.EduChapterService;
import com.tsl.eduservice.service.EduCourseService;
import com.tsl.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsl.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.sql.Wrapper;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-02
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Autowired
    public EduVideoService eduVideoService;
    @Autowired
    public EduChapterService eduChapterService;
    @Autowired
    public EduCourseService eduCourseService;

    public void savaSubject(MultipartFile file,EduSubjectService eduSubjectService){
        try{
            //文件输入流
            InputStream in = file.getInputStream();
            EasyExcel.read(in,subjectData.class,new SubjectExcleListener(eduSubjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<OneTreeJson> getAllSubject() {
        //1.查询出所有的一级分类和二级分类
        QueryWrapper parentQueryWrapper = new QueryWrapper();
        parentQueryWrapper.eq("parent_id","0");
        List<EduSubject> parents = baseMapper.selectList(parentQueryWrapper);


        QueryWrapper childrenQueryWrapper = new QueryWrapper();
        childrenQueryWrapper.ne("parent_id","0");
        List<EduSubject> childrens = baseMapper.selectList(childrenQueryWrapper);

        //将eduSubject集合转换成OneTreeJson集合
        List<OneTreeJson> TreeData = new ArrayList<>();


        for(int i =0;i<parents.size();i++){
            EduSubject subject = parents.get(i);
            OneTreeJson oneTreeJson = new OneTreeJson();
            //oneTreeJson.setId(subject.getId());
            //oneTreeJson.setTitle(subject.getTitle());
            //简写,BeanUtils是spring包下的
            BeanUtils.copyProperties(subject,oneTreeJson);
            TreeData.add(oneTreeJson);

            List<TwoTreeJson> TwoTreeData = new ArrayList<>();
            for (int i1 = 0; i1 < childrens.size(); i1++) {

                EduSubject subjectTwo = childrens.get(i1);
                //判断二级的父类id是不是一级的id
                if (subjectTwo.getParentId().equals(subject.getId())){
                    TwoTreeJson TwoTreeJson = new TwoTreeJson();
                    BeanUtils.copyProperties(subjectTwo,TwoTreeJson);
                    TwoTreeData.add(TwoTreeJson);
                }
            }
            oneTreeJson.setChildren(TwoTreeData);
        }
        return TreeData;
    }

    @Override
    public List<OneTreeJson> getFourSubject() {

        List<OneTreeJson> oneTreeJson = getAllSubject();
        EduChapterServiceImpl chapterService = new EduChapterServiceImpl();
        List<EduCourse> eduCourse = eduCourseService.list(null);

        List<chapterVO> chaptervo2 = new ArrayList<>();
        for(int a=0;a<oneTreeJson.size();a++){
            OneTreeJson one = oneTreeJson.get(a);
            List<TwoTreeJson> two = one.getChildren();

            for (int b=0;b<two.size();b++){
                TwoTreeJson Two = two.get(b);

                //这里声明要返回的course集合
                List<courseVO> thressCourse = new ArrayList<>();
                for (int i =0;i<eduCourse.size();i++){
                    EduCourse course = eduCourse.get(i);

                    if(Two.getId().equals(course.getSubjectId())){
                        //注意！！！以下两行一定要在if判断中进行，不然加不进去第三层
                        courseVO coursevo = new courseVO();
                        BeanUtils.copyProperties(course,coursevo);

                        thressCourse.add(coursevo);


                        //调方法对第四五层进行填充
                        /**
                         * 我在其他service里面写了返回最后两层数据的接口，在这里直接调用是不行的，因为这个service中的baseMapper在那个类中永不了
                         * 所以只能重写下那个方法
                         */

                        QueryWrapper chapterwrapper = new QueryWrapper();
                        chapterwrapper.eq("course_id",course.getId());
                        List<EduChapter> chaps = eduChapterService.list(chapterwrapper);
                        List<EduVideo> vides = eduVideoService.list(null);
                        List<chapterVO> chaptervo = chapterService.getChapter2(chaps,vides);
                        coursevo.setChildren(chaptervo);
                    }
                }
                //然后在第三层的数据装进第二层里面去
                Two.setChildren(thressCourse);

            }
        }

        return oneTreeJson;
    }
}
