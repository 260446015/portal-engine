package com.yonyougov.portal.engine.service.impl;

import com.yonyougov.portal.engine.entity.EngThemeRefUser;
import com.yonyougov.portal.engine.mapper.EngThemeRefUserMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import java.util.List;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/5 9:29
 * @Description
 */
@Service
public class EngThemeRefUserService {

    @Resource
    private EngThemeRefUserMapper engThemeRefUserMapper;

    public int deleteByPrimaryKey(String id) {
        return engThemeRefUserMapper.deleteByPrimaryKey(id);
    }

    public int insert(EngThemeRefUser record) {
        return engThemeRefUserMapper.insert(record);
    }

    public int insertSelective(EngThemeRefUser record) {
        return engThemeRefUserMapper.insertSelective(record);
    }

    public EngThemeRefUser selectByPrimaryKey(String id) {
        return engThemeRefUserMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(EngThemeRefUser record) {
        return engThemeRefUserMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(EngThemeRefUser record) {
        return engThemeRefUserMapper.updateByPrimaryKey(record);
    }

    public int updateBatch(List<EngThemeRefUser> list) {
        return engThemeRefUserMapper.updateBatch(list);
    }

    public int batchInsert(List<EngThemeRefUser> list) {
        return engThemeRefUserMapper.batchInsert(list);
    }
}

