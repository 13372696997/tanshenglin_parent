package com.tsl.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//因为只是做存储，所以不需要连接数据库，所以加这个属性，不然会启动会报错
@ComponentScan(basePackages = {"com.tsl"})//扫描这个开头的包,是个数组，可以写多个
public class OssApplication {
    public static void main(String[] args){

        SpringApplication.run(OssApplication.class,args);

    }
}
