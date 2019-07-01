package com.yonyougov.portal.engine.mapper;

import com.yonyougov.portal.engine.dto.EngThemeVO;
import com.yonyougov.portal.engine.entity.EngTheme;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author yindwe@yonyou.com
 * @Date 2019/6/14
 * @Description
 */
public interface EngThemeMapper {
    int deleteByPrimaryKey(String id);

    int insert(EngTheme record);

    int insertSelective(EngTheme record);

    EngTheme selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EngTheme record);

    int updateByPrimaryKey(EngTheme record);

    int updateBatch(List<EngTheme> list);

    int batchInsert(@Param("list") List<EngTheme> list);

    int updateByPrimaryKeyWithBLOBs(EngTheme record);

    List<EngTheme> listAll();

    EngTheme selectByPrimaryKeyWithOutBlob(String id);

    EngTheme selectByDefaultTheme(@Param("defaultTheme") String defaultTheme);

    List<EngThemeVO> listAllTheme();
}