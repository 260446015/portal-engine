package com.yonyougov.portal.engine.service.impl;

import com.yonyougov.portal.engine.entity.EngCompRefPart;
import com.yonyougov.portal.engine.mapper.EngCompRefPartMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
  *  @author yindwe@yonyu.com
  *  @Date 2019/4/28 17:35
  *  @Description
  */
@Service
public class EngCompRefPartService{

    @Resource
    private EngCompRefPartMapper engCompRefPartMapper;

    public int deleteByPrimaryKey(String id){
        return engCompRefPartMapper.deleteByPrimaryKey(id);
    }

    public int insert(EngCompRefPart record){
        return engCompRefPartMapper.insert(record);
    }

    public int insertSelective(EngCompRefPart record){
        return engCompRefPartMapper.insertSelective(record);
    }

    public EngCompRefPart selectByPrimaryKey(String id){
        return engCompRefPartMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(EngCompRefPart record){
        return engCompRefPartMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(EngCompRefPart record){
        return engCompRefPartMapper.updateByPrimaryKey(record);
    }

}
