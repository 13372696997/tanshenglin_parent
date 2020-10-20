package com.tsl.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//不访问数据库。所以不加数据库连接配置
@ComponentScan(basePackages = {"com.tsl"})//扫描这个开头的包,是个数组，可以写多个
public class VodApplication {
    public static void main(String[] args){

        SpringApplication.run(VodApplication.class,args);

    }
}
