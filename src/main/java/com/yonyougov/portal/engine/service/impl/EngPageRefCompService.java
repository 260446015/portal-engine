package com.yonyougov.portal.engine.service.impl;

import com.yonyougov.portal.engine.entity.EngPageRefComp;
import com.yonyougov.portal.engine.mapper.EngPageRefCompMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
  *  @author yindwe@yonyu.com
  *  @Date 2019/4/28 17:38
  *  @Description
  */
@Service
public class EngPageRefCompService{

    @Resource
    private EngPageRefCompMapper engPageRefCompMapper;

    public int deleteByPrimaryKey(String id){
        return engPageRefCompMapper.deleteByPrimaryKey(id);
    }

    public int insert(EngPageRefComp record){
        return engPageRefCompMapper.insert(record);
    }

    public int insertSelective(EngPageRefComp record){
        return engPageRefCompMapper.insertSelective(record);
    }

    public EngPageRefComp selectByPrimaryKey(String id){
        return engPageRefCompMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(EngPageRefComp record){
        return engPageRefCompMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(EngPageRefComp record){
        return engPageRefCompMapper.updateByPrimaryKey(record);
    }

}
