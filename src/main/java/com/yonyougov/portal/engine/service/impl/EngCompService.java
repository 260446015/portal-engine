package com.yonyougov.portal.engine.service.impl;

import com.yonyougov.eng.entity.EngComp;
import com.yonyougov.eng.mapper.EngCompMapper;
import com.yonyougov.portal.engine.entity.EngComp;
import com.yonyougov.portal.engine.mapper.EngCompMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/4/28 17:31
 * @Description
 */
@Service
public class EngCompService {

    @Resource
    private EngCompMapper engCompMapper;

    public int deleteByPrimaryKey(String id) {
        return engCompMapper.deleteByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int insert(EngComp record) {
        return engCompMapper.insert(record);
    }

    public int insertSelective(EngComp record) {
        return engCompMapper.insertSelective(record);
    }

    public EngComp selectByPrimaryKey(String id) {
        return engCompMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(EngComp record) {
        return engCompMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(EngComp record) {
        return engCompMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int updateByPrimaryKey(EngComp record) {
        return engCompMapper.updateByPrimaryKey(record);
    }

    public List<EngComp> listAll() {
        return engCompMapper.listAll();
    }

    public int updateBatch(List<EngComp> list) {
        return engCompMapper.updateBatch(list);
    }

    public int batchInsert(List<EngComp> list) {
        return engCompMapper.batchInsert(list);
    }
}




