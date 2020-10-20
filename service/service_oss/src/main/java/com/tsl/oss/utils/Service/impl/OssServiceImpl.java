package com.tsl.oss.utils.Service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.tsl.oss.utils.ConstantPropertiesUtils;
import com.tsl.oss.utils.Service.OssService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKE_NAME;

        // 上传文件流。
        try{
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //获取上传文件的输入流
            InputStream inputStream = file.getInputStream();

            //三个参数：bucketName，文件名称，文件输入流
            //获取文件名称
            String filename = file.getOriginalFilename();

            //(不重名文件名方法一)在文件名称添加惟一值
            String s = UUID.randomUUID().toString().replaceAll("-", "");
            //filename=s+filename;

            //(不重名文件名方法二)使用日期处理类，按照日期分
            String datePath = new DateTime().toString("yyyy/MM/dd");
            filename = datePath+"/"+s+filename;

            ossClient.putObject(bucketName, filename, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();
            //把上传之后的文件路径返回
            String url = "https://"+bucketName+"."+endpoint+"/"+filename;
            return url;

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
