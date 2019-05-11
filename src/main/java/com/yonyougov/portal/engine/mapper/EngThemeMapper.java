package com.yonyougov.portal.engine.mapper;

import com.yonyougov.portal.engine.entity.EngTheme;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/7 16:25
 * @Description
 */
public interface EngThemeMapper {
    int deleteByPrimaryKey(String id);

    int insert(EngTheme record);

    int insertSelective(EngTheme record);

    EngTheme selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EngTheme record);

    int updateByPrimaryKeyWithBLOBs(EngTheme record);

    int updateByPrimaryKey(EngTheme record);

    int updateBatch(List<EngTheme> list);

    int batchInsert(@Param("list") List<EngTheme> list);

    List<EngTheme> listAll();

    EngTheme selectByPrimaryKeyWithOutBlob(String id);

    EngTheme selectByDefaultTheme(@Param("defaultTheme")String defaultTheme);

}