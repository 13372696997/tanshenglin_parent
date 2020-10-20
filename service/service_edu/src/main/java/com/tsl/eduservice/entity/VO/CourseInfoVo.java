package com.tsl.eduservice.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseInfoVo {
    @ApiModelProperty(value = "课程id")
    private String id;
    @ApiModelProperty(value = "课程讲师id")
    private String teacherId;
    @ApiModelProperty(value = "课程专业id")
    private String subjectId;

    @ApiModelProperty(value = "一级课程id")
    private String subjectParentId;

    @ApiModelProperty(value = "课程标题")
    private String title;
    @ApiModelProperty(value = "课程销售价格，0为可免费观看")
    private BigDecimal price;
    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;
    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;
    @ApiModelProperty(value = "课程简介")
    private String description;
}
