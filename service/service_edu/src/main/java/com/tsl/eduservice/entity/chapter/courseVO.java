package com.tsl.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class courseVO {
    private String id;
    private String title;
    private List children = new ArrayList<>();
}
