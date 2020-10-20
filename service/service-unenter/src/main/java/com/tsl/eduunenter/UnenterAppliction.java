package com.tsl.eduunenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableDiscoveryClient//注册在nacos注册中心的注解
@EnableFeignClients//调用服务注解
@MapperScan("com.tsl.eduunenter.mapper")
@ComponentScan(basePackages = {"com.tsl"})//扫描这个开头的包,是个数组，可以写多个
public class UnenterAppliction {
    public static void main(String[] args){

        SpringApplication.run(UnenterAppliction.class,args);

    }
}
