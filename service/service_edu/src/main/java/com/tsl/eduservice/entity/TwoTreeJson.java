package com.tsl.eduservice.entity;

import lombok.Data;

import java.util.List;


@Data
public class TwoTreeJson {
    private String id;
    private String title;
    private List children;
}
