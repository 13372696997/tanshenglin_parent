package com.tsl.eduservice.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class excelUser {
    @ExcelProperty(value="学生编号",index = 0)
    private Integer uid;
    @ExcelProperty(value="学生姓名",index = 1)
    private String uname;
}
