package com.yonyougov.portal.engine.mapper;

import com.yonyougov.portal.engine.entity.EngComp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author yindwe@yonyou.com
 * @Date 2019/6/14
 * @Description
 */
public interface EngCompMapper {
    int deleteByPrimaryKey(String id);

    int insert(EngComp record);

    int insertSelective(EngComp record);

    EngComp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EngComp record);

    int updateByPrimaryKey(EngComp record);

    int updateBatch(List<EngComp> list);

    int batchInsert(@Param("list") List<EngComp> list);

    int updateByPrimaryKeyWithBLOBs(EngComp record);

    List<EngComp> listAll(String compType);

    List<EngComp> getThemeRefCompsFromDb(List<String> ids);
}