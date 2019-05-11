package com.yonyougov.portal.engine.mapper;

import com.yonyougov.portal.engine.entity.EngThemeRefUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/8 11:22
 * @Description
 */
public interface EngThemeRefUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(EngThemeRefUser record);

    int insertSelective(EngThemeRefUser record);

    EngThemeRefUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EngThemeRefUser record);

    int updateByPrimaryKey(EngThemeRefUser record);

    int updateBatch(List<EngThemeRefUser> list);

    int batchInsert(@Param("list") List<EngThemeRefUser> list);

    List<EngThemeRefUser> selectByUserId(String userId);

    List<EngThemeRefUser> selectByUserIdAndActive(@Param("userId")String userId,@Param("active")String active);

}