package com.tsl.eduservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableDiscoveryClient//注册在nacos注册中心的注解
@EnableFeignClients//调用服务注解
@ComponentScan(basePackages = {"com.tsl"})//扫描这个开头的包,是个数组，可以写多个
public class EduAppliction {
    public static void main(String[] args){

        SpringApplication.run(EduAppliction.class,args);

    }
}
