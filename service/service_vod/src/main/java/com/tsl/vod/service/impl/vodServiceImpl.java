package com.tsl.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.tsl.vod.service.vodService;
import com.tsl.vod.utils.vodUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;




@Service
public class vodServiceImpl implements vodService {
    @Override
    public String uploadVideo(MultipartFile file) {
        //上传的文件转成流
        try {

            //要判断文件是不是视频，如果穿图片就直接没了，找不到！！！！！！！
            //原始文件名
            String fileName = file.getOriginalFilename();
            //lastIndexOf,文件名有多个.也没关系，截取得是最后一个.
            String title=fileName.substring(0,fileName.lastIndexOf("."));
            InputStream inputStream= file.getInputStream();
            UploadStreamRequest request = new UploadStreamRequest(vodUtils.ACCESS_KEY_ID,vodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);



            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);


            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
                System.out.println("上传成功，VideoId=" + response.getVideoId());
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
                System.out.println("上传失败，VideoId=" + response.getVideoId());
            }
            return videoId;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }
}
