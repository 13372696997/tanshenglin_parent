package com.tsl.eduunenter.service;

import com.tsl.eduunenter.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tsl.eduunenter.entity.VO.userVO;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-17
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember user);

    void register(userVO user);

    UcenterMember getOpenIdMember(String openid);

    Integer countRegisterDay(String day);
}
