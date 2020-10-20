package com.atguigu.demo;

import com.tsl.eduservice.service.EduVideoService;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        list.add("33");
        list.add("aa");
        StringBuffer sb = new StringBuffer();
        sb.append("aa");
        sb.append("bb");
        sb.append("cc");

        System.out.println(sb);
        // 将集合中的每个字符串对象用---拼接成一个字符串
        String join = StringUtils.join(list.toArray(), "---");
        System.out.println(join);
    }

}
