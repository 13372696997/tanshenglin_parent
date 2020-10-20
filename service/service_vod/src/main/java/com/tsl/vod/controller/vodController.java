package com.tsl.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.tsl.commonutils.Result;
import com.tsl.servicebase.ExceptionHandler.tanShengLinException;
import com.tsl.vod.service.vodService;
import com.tsl.vod.utils.InitVodClient;
import com.tsl.vod.utils.vodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
@RequestMapping("/eduvod/video")
public class vodController {
    @Autowired
    public vodService vodService;

    //根据视频id获取播放凭证,前台系统用
    @GetMapping("getPlayAuth/{id}")
    public Result getPlayAuth(@PathVariable String id){
        try{
            //创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(vodUtils.ACCESS_KEY_ID,vodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();

            //向request设置视频id
            request.setVideoId(id);

            //调用方法得到凭证
            GetVideoPlayAuthResponse acsResponse = client.getAcsResponse(request);
            String playAuth = acsResponse.getPlayAuth();
            return Result.ok().data("playAuth",playAuth);
        } catch (Exception e) {
            e.printStackTrace();
            throw new tanShengLinException(20001,"获取凭证失败");
        }

    }

    //上传本地视频到阿里云
    @PostMapping("uploadALiYunVideo")
    public Result uploadALiYunVideo(MultipartFile file){
        String videoId = vodService.uploadVideo(file);
        return Result.ok().data("videoId",videoId);
    }

    //删除阿里云视频
    @DeleteMapping("deleteVideo/{id}")
    public Result deleteVideo(@PathVariable String id){
        try{
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(vodUtils.ACCESS_KEY_ID,vodUtils.ACCESS_KEY_SECRET);
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);//可以传多个
            client.getAcsResponse(request);
            return Result.ok();
        }catch (Exception e){
            e.printStackTrace();
            throw new tanShengLinException(20001,"删除视频失败");
        }


    }
}
