package com.tsl.eduunenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tsl.commonutils.JwtUtils;
import com.tsl.commonutils.MD5;
import com.tsl.eduunenter.entity.UcenterMember;
import com.tsl.eduunenter.entity.VO.userVO;
import com.tsl.eduunenter.mapper.UcenterMemberMapper;
import com.tsl.eduunenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tsl.servicebase.ExceptionHandler.tanShengLinException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-17
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String login(UcenterMember user) {
        //获取登录的手机号和密码
        String mobile = user.getMobile();
        String password = user.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            throw new tanShengLinException(20001,"登录失败，请输入用户名和密码");
        }
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("mobile",mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        //根据手机号码判断存不存在
        if (ucenterMember==null){
            throw new tanShengLinException(20001,"登录失败，手机号码不存在");
        }
        //判断加密后的密码对不对
        if (!MD5.encrypt(password).equals(ucenterMember.getPassword())){
            throw new tanShengLinException(20001,"登录失败，密码错误");
        }
        //判断是否禁用
        if (ucenterMember.getIsDeleted().equals(1)){
            throw new tanShengLinException(20001,"登录失败，账号禁用");
        }
        //使用JWT生成token然后返回
        String token = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

        return token;
    }

    @Override
    //注册方法，判断验证码
    public void register(userVO user) {
        String code = user.getCode();
        String mobile = user.getMobile();
        String nickname = user.getNickname();
        String password = user.getPassword();

        //非空判断
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) ||StringUtils.isEmpty(nickname) ||StringUtils.isEmpty(password) ){
            throw new tanShengLinException(20001,"必须全部填写");
        }

        //判断验证码正不正确
//        String redisCode = (String) redisTemplate.opsForValue().get(mobile);
//        if (!redisCode.equals(code)){
//            throw new tanShengLinException(20001,"验证码错误");
//        }

        //判断手机号码是否重复
        QueryWrapper mobileWrapper = new QueryWrapper();
        mobileWrapper.eq("mobile",mobile);
        UcenterMember ucenterMember = baseMapper.selectOne(mobileWrapper);
        if (!StringUtils.isEmpty(ucenterMember)){
            throw new tanShengLinException(20001,"该手机号码已存在");
        }

        //新增进去
        UcenterMember userMenmber = new UcenterMember();
        userMenmber.setIsDeleted(false);
        userMenmber.setMobile(mobile);
        userMenmber.setPassword(MD5.encrypt(password));
        userMenmber.setNickname(nickname);

        baseMapper.insert(userMenmber);

    }

    @Override
    /**
     * 根据用户微信id获取到用户信息
     */
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);
        return member;
    }

    @Override
    /**
     * 统计某天的注册人数
     */
    public Integer countRegisterDay(String day) {

        return baseMapper.countRegister(day);
    }
}
