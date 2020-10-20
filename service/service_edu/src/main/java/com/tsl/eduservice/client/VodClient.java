package com.tsl.eduservice.client;

import com.tsl.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(name = "service-vod",fallback = vodClientDegradeFeignClient.class)
public interface VodClient {
    //这里直接复制要调用的方法
    //@PathVariable注解一定要指定参数名称，否则报错
    @DeleteMapping("/eduvod/video/deleteVideo/{id}")
    public Result deleteVideo(@PathVariable("id") String id);
}
