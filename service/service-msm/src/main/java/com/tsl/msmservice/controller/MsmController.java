package com.tsl.msmservice.controller;

import com.tsl.commonutils.Result;
import com.tsl.msmservice.service.msmService;
import com.tsl.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("edumsm/msm")
@CrossOrigin
public class MsmController {
    @Autowired
    private msmService msmservice;

    @Autowired
    private RedisTemplate redisTemplate;

    //发送验证码
    @GetMapping("send/{phone}")
    public Result sendMsm(@PathVariable String phone){
        //从redis中获取验证码，如果能取到就不发送验证啊，取不到就发送
        //String code2 = (String) redisTemplate.opsForValue().get(phone);
//        if (!StringUtils.isEmpty(code2)){
//            return Result.ok();
//        }

        String code = RandomUtil.getFourBitRandom();//生成四位随机数

        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        //调用service里面发送短信的方法
        boolean isSend = msmservice.send(param,phone);
        if (isSend){
            //发送验证码成功后，把验证码存redis中，并设置有效时间,5分钟
            //redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);

            return Result.ok();
        }else {
            return Result.error().message("短信发送失败");
        }

    }

}
