package com.yonyougov.portal.engine.mapper;

import com.yonyougov.portal.engine.entity.EngPage;

import java.util.List;

/**
  *  @author yindwe@yonyu.com
  *  @Date 2019/4/28 17:36
  *  @Description
  */
public interface EngPageMapper {
    int deleteByPrimaryKey(String id);

    int insert(EngPage record);

    int insertSelective(EngPage record);

    EngPage selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EngPage record);

    int updateByPrimaryKey(EngPage record);

    List<EngPage> listAll();

}