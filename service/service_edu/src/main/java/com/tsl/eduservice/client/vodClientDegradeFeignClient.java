package com.tsl.eduservice.client;

import com.tsl.commonutils.Result;
import org.springframework.stereotype.Component;

@Component
public class vodClientDegradeFeignClient implements VodClient{
    @Override
    public Result deleteVideo(String id) {
        System.out.println("删除视频出错了，触发了熔断机制");
        return Result.error().message("删除视频出错了，触发了熔断机制");
    }
}
