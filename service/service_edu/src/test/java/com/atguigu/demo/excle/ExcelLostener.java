package com.atguigu.demo.excle;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.tsl.eduservice.entity.excelUser;

import java.util.Map;

public class ExcelLostener extends AnalysisEventListener<excelUser> {

    @Override//一行行读取excel表中的内容
    public void invoke(excelUser exceluser, AnalysisContext analysisContext) {
        System.out.println("***"+exceluser);
    }

    //读取表头的内容
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头"+headMap);
    }
    @Override//读取完成后做点啥
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
