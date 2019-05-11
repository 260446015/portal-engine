package com.yonyougov.portal.engine.mapper;

import com.yonyougov.portal.engine.entity.EngPageRefComp;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/7 16:38
 * @Description
 */
public interface EngPageRefCompMapper {
    int deleteByPrimaryKey(String id);

    int insert(EngPageRefComp record);

    int insertSelective(EngPageRefComp record);

    EngPageRefComp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(EngPageRefComp record);

    int updateByPrimaryKey(EngPageRefComp record);

    int updateBatch(List<EngPageRefComp> list);

    int batchInsert(@Param("list") List<EngPageRefComp> list);
}