package com.yonyougov.portal.engine.mapper;

import com.yonyougov.portal.engine.entity.EngCompRefPart;

/**
  *  @author yindwe@yonyu.com
  *  @Date 2019/4/28 17:35
  *  @Description
  */
public interface EngCompRefPartMapper {
    int deleteByPrimaryKey(String id);

    int insert(EngCompRefPart record);

    int insertSelective(EngCompRefPart record);

    EngCompRefPart selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EngCompRefPart record);

    int updateByPrimaryKey(EngCompRefPart record);
}