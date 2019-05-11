package com.yonyougov.portal.engine.mapper;

import com.yonyougov.portal.engine.entity.EngComp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/7 16:39
 * @Description
 */
public interface EngCompMapper {
    int deleteByPrimaryKey(String id);

    int insert(EngComp record);

    int insertSelective(EngComp record);

    EngComp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EngComp record);

    int updateByPrimaryKeyWithBLOBs(EngComp record);

    int updateByPrimaryKey(EngComp record);

    int updateBatch(List<EngComp> list);

    int batchInsert(@Param("list") List<EngComp> list);

    List<EngComp> listAll();

    List<EngComp> getThemeRefCompsFromDb(List<String> ids);
}