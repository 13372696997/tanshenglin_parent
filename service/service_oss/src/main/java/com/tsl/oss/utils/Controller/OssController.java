package com.tsl.oss.utils.Controller;

import com.tsl.commonutils.Result;
import com.tsl.oss.utils.Service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    //上传头像方法
    @PostMapping
    public Result uploadOssFile(MultipartFile file){
        //获取上传文件 MultipartFile
        String url = ossService.uploadFileAvatar(file);
        return Result.ok().data("url",url);
    }
}
