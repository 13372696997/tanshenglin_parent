package com.tsl.eduservice.Listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tsl.eduservice.entity.EduSubject;
import com.tsl.eduservice.entity.Excel.subjectData;
import com.tsl.eduservice.service.EduSubjectService;
import com.tsl.servicebase.ExceptionHandler.tanShengLinException;

public class SubjectExcleListener extends AnalysisEventListener<subjectData> {
    //因为这个类是在service中new的，所以并没有让spring进行管理，所以不能实现对数据库的操作
    //解决方式:Listener直接引用service，这样创建Listener的时候也创建了service
    public EduSubjectService eduSubjectService;
    //生成有参数和五参数构造
    public SubjectExcleListener() {
    }
    public SubjectExcleListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }



    @Override
    public void invoke(subjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            throw  new tanShengLinException(20001,"文件数据为空");
        }
        //一级分类如果不存在就新增一个一级课程
        EduSubject oneSubject = this.existOneSubject(eduSubjectService,subjectData.getOneSubjectName());
        if(oneSubject == null){
            oneSubject = new EduSubject();
            oneSubject.setParentId("0");
            oneSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(oneSubject);
        }
        //二级分类如果不存在就新增一个二级课程
        String pid = oneSubject.getId();//一行行读取，所以能取到
        EduSubject twoSubject = this.existTwoSubject(eduSubjectService,subjectData.getOneSubjectName(),pid);
        if(twoSubject == null){
            twoSubject = new EduSubject();
            twoSubject.setParentId(pid);
            twoSubject.setTitle(subjectData.getTwoSubjectName());
            eduSubjectService.save(twoSubject);
        }
    }
    //一级分类重复就返回一个父类的课程对象，不是空就返回一个之前存在的一级课程
    private EduSubject existOneSubject(EduSubjectService SubjectService,String name){
        QueryWrapper<EduSubject> warpper= new QueryWrapper<>();
        warpper.eq("title",name);
        warpper.eq("parent_id","0");
        EduSubject parentSubject = new EduSubject();
        parentSubject = SubjectService.getOne(warpper);

        return parentSubject;
    }
    //二级分类重复就返回一个重复的二级课程对象，不为空就返回一个存在的二级课程
    private EduSubject existTwoSubject(EduSubjectService SubjectService,String name,String pid){
        QueryWrapper<EduSubject> warpper= new QueryWrapper<>();
        warpper.eq("title",name);
        warpper.eq("parent_id",pid);
        EduSubject twoSubject = SubjectService.getOne(warpper);
        return twoSubject;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
