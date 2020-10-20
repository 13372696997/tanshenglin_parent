package com.tsl.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;



@EnableDiscoveryClient//注册在nacos注册中心的注解
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//不访问数据库。所以不加数据库连接配置
@EnableFeignClients//调用服务注解
@ComponentScan(basePackages = {"com.tsl"})//扫描这个开头的包,是个数组，可以写多个
public class msmAppliction {
    public static void main(String[] args){

        SpringApplication.run(msmAppliction.class,args);

    }
}
