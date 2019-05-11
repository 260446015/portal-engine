package com.yonyougov.portal.engine.service.impl;

import com.yonyougov.portal.engine.entity.EngThemeRefCompUser;
import com.yonyougov.portal.engine.mapper.EngThemeRefCompUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/5 9:29
 * @Description
 */
@Service
public class EngThemeRefCompUserService {

    @Resource
    private EngThemeRefCompUserMapper engThemeRefCompUserMapper;

    public int deleteByPrimaryKey(String id) {
        return engThemeRefCompUserMapper.deleteByPrimaryKey(id);
    }

    public int insert(EngThemeRefCompUser record) {
        return engThemeRefCompUserMapper.insert(record);
    }

    public int insertSelective(EngThemeRefCompUser record) {
        return engThemeRefCompUserMapper.insertSelective(record);
    }

    public EngThemeRefCompUser selectByPrimaryKey(String id) {
        return engThemeRefCompUserMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(EngThemeRefCompUser record) {
        return engThemeRefCompUserMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(EngThemeRefCompUser record) {
        return engThemeRefCompUserMapper.updateByPrimaryKey(record);
    }

    public int updateBatch(List<EngThemeRefCompUser> list) {
        return engThemeRefCompUserMapper.updateBatch(list);
    }

    public int batchInsert(List<EngThemeRefCompUser> list) {
        return engThemeRefCompUserMapper.batchInsert(list);
    }
}

