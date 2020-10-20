package com.atguigu.demo.excle;

import com.alibaba.excel.EasyExcel;
import com.tsl.eduservice.entity.excelUser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ExcelTest {
    @Test
    public void writeExcel(){//实现对excel表格的写操作

        String filename = "D:\\u\\write.xlsx";
        //创建一个集合，把这个集合写到表中去
        List list = new ArrayList();
        excelUser user = new excelUser();
        for (int a = 0;a<10;a++){
            user.setUid(a);
            user.setUname("cuisen"+a);
            list.add(user);
        }

        //调用eastexcel里面的方法实现写,用两个参数那个方法，一个是文件路径，一个是写入的实体类
        EasyExcel.write(filename,excelUser.class).sheet("用户列表").doWrite(list);
    }
    @Test
    public void readExcel(){//实现对excel表格的读操作
        String filename = "D:\\u\\write.xlsx";
        EasyExcel.read(filename,excelUser.class,new ExcelLostener()).sheet().doRead();

    }


}
