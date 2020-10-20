package com.tsl.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tsl.educms.entity.CrmBanner;
import com.tsl.educms.mapper.CrmBannerMapper;
import com.tsl.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author tanShengLin
 * @since 2020-09-14
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Override
    @Cacheable(value = "banner",key = "'selectIndexList'")
    public List<CrmBanner> selectAllBanner() {

        //根据sort字段倒序，查询倒序后的前两条数据
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("sort");
        //用last拼接sql语句
        wrapper.last("limit 2");


        List<CrmBanner> bannerList = baseMapper.selectList(wrapper);

        return bannerList;
    }
}
