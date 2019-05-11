package com.yonyougov.portal.engine.service.impl;

import com.yonyougov.portal.engine.entity.EngPart;
import com.yonyougov.portal.engine.mapper.EngPartMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
  *  @author yindwe@yonyu.com
  *  @Date 2019/5/5 9:28
  *  @Description
  */
@Service
public class EngPartService{

    @Resource
    private EngPartMapper engPartMapper;

    public int deleteByPrimaryKey(String id){
        return engPartMapper.deleteByPrimaryKey(id);
    }

    public int insert(EngPart record){
        return engPartMapper.insert(record);
    }

    public int insertSelective(EngPart record){
        return engPartMapper.insertSelective(record);
    }

    public EngPart selectByPrimaryKey(String id){
        return engPartMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(EngPart record){
        return engPartMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(EngPart record){
        return engPartMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(EngPart record){
        return engPartMapper.updateByPrimaryKey(record);
    }

}
