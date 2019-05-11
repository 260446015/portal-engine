package com.yonyougov.portal.engine.service.impl;

import com.yonyougov.portal.engine.entity.EngPage;
import com.yonyougov.portal.engine.mapper.EngPageMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
  *  @author yindwe@yonyu.com
  *  @Date 2019/4/28 17:36
  *  @Description
  */
@Service
public class EngPageService{

    @Resource
    private EngPageMapper engPageMapper;

    public int deleteByPrimaryKey(String id){
        return engPageMapper.deleteByPrimaryKey(id);
    }

    public int insert(EngPage record){
        return engPageMapper.insert(record);
    }

    public int insertSelective(EngPage record){
        return engPageMapper.insertSelective(record);
    }

    public EngPage selectByPrimaryKey(String id){
        return engPageMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(EngPage record){
        return engPageMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(EngPage record){
        return engPageMapper.updateByPrimaryKey(record);
    }

    public List<EngPage> listAll() {
        return engPageMapper.listAll();
    }
}
