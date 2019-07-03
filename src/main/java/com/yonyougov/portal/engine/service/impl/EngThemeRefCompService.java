package com.yonyougov.portal.engine.service.impl;

import com.yonyougov.portal.engine.entity.EngThemeRefComp;
import com.yonyougov.portal.engine.mapper.EngThemeRefCompMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/5 9:29
 * @Description
 */
@Service
public class EngThemeRefCompService {

    @Resource
    private EngThemeRefCompMapper engThemeRefCompMapper;

    public int deleteByPrimaryKey(String id) {
        return engThemeRefCompMapper.deleteByPrimaryKey(id);
    }

    public int insert(EngThemeRefComp record) {
        return engThemeRefCompMapper.insert(record);
    }

    public int insertSelective(EngThemeRefComp record) {
        return engThemeRefCompMapper.insertSelective(record);
    }

    public EngThemeRefComp selectByPrimaryKey(String id) {
        return engThemeRefCompMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(EngThemeRefComp record) {
        return engThemeRefCompMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(EngThemeRefComp record) {
        return engThemeRefCompMapper.updateByPrimaryKey(record);
    }

    public int updateBatch(List<EngThemeRefComp> list) {
        return engThemeRefCompMapper.updateBatch(list);
    }

    public int batchInsert(List<EngThemeRefComp> list) {
        return engThemeRefCompMapper.batchInsert(list);
    }
}





