package com.yonyougov.portal.engine.mapper;

import com.yonyougov.portal.engine.entity.EngThemeRefComp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/7 16:37
 * @Description
 */
public interface EngThemeRefCompMapper {
    int deleteByPrimaryKey(String id);

    int insert(EngThemeRefComp record);

    int insertSelective(EngThemeRefComp record);

    EngThemeRefComp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EngThemeRefComp record);

    int updateByPrimaryKey(EngThemeRefComp record);

    int updateBatch(List<EngThemeRefComp> list);

    int batchInsert(@Param("list") List<EngThemeRefComp> list);

    List<EngThemeRefComp> selectByThemeId(String id);

    int deleteByThemeId(String id);
}