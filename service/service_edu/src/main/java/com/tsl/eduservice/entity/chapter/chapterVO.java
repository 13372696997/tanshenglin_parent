package com.tsl.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 章节
 */
@Data
public class chapterVO {
    private String id;
    private String title;
    private List<videoVO> children = new ArrayList<>();
}
