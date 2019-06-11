package com.yonyougov.portal.engine.mapper;

import com.yonyougov.portal.engine.entity.EngPart;

import java.util.List;

/**
  *  @author yindwe@yonyu.com
  *  @Date 2019/5/5 9:28
  *  @Description
  */
public interface EngPartMapper {
    int deleteByPrimaryKey(String id);

    int insert(EngPart record);

    int insertSelective(EngPart record);

    EngPart selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EngPart record);

    int updateByPrimaryKeyWithBLOBs(EngPart record);

    int updateByPrimaryKey(EngPart record);

    List<EngPart> listAll();
}