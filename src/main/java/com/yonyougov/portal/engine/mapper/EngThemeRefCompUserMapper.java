package com.yonyougov.portal.engine.mapper;

import com.yonyougov.portal.engine.entity.EngThemeRefCompUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/8 10:36
 * @Description
 */
public interface EngThemeRefCompUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(EngThemeRefCompUser record);

    int insertSelective(EngThemeRefCompUser record);

    EngThemeRefCompUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EngThemeRefCompUser record);

    int updateByPrimaryKey(EngThemeRefCompUser record);

    int updateBatch(List<EngThemeRefCompUser> list);

    int batchInsert(@Param("list") List<EngThemeRefCompUser> list);

    List<EngThemeRefCompUser> selectByThemeUserId(@Param("themeUserId")String themeUserId);

    int deleteByThemeUserId(String themeUserId);
}