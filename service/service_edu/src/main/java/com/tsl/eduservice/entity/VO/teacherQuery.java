package com.tsl.eduservice.entity.VO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
@Data
public class teacherQuery implements Serializable {
    @ApiModelProperty(value = "教室名称，模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔：1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间",example = "2020-01-01 12:12:12")
    private String beginTime;

    @ApiModelProperty(value = "查询结束时间")
    private String endTime;
}
