package com.tsl.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
//注意：项目启动的时候spring会加载这个类，但是因为属性是private的，所以其他人访问不了，
// 于是就实现InitializingBean这个接口，初始化对象后会调用afterPropertiesSet这个方法
public class ConstantPropertiesUtils implements InitializingBean {
    //读取配置文件中的内容
    @Value("${aliyun.oss.file.endpoint}")//注意要选择spring框架的value，这个注解让spring创建这个对象的时候自动把值注入进去
    private String endpoint;
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;
    @Value("${aliyun.oss.file.keysecret}")
    private String keySecret;
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketName;


    //定义公开静态常量
    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKE_NAME;
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
        BUCKE_NAME = bucketName;
    }
}
