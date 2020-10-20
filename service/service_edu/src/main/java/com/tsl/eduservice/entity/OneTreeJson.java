package com.tsl.eduservice.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class OneTreeJson {
    private String id;
    private String title;
    private List children = new ArrayList<>();
}
