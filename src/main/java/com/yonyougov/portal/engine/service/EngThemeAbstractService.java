package com.yonyougov.portal.engine.service;

import com.yonyougov.portal.engine.dto.EngThemeDTO;
import com.yonyougov.portal.engine.entity.EngTheme;
import com.yonyougov.portal.engine.mapper.EngThemeMapper;
import com.yonyougov.portal.engine.mapper.EngThemeRefCompMapper;
import com.yonyougov.portal.engine.mapper.EngThemeRefCompUserMapper;
import com.yonyougov.portal.engine.mapper.EngThemeRefUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * @Author yindwe@yonyou.com
 * @Date 2019/6/24
 * @Description
 */
@Slf4j
@Service
public abstract class EngThemeAbstractService {

    @Resource
    protected EngThemeMapper engThemeMapper;
    @Resource
    protected EngThemeRefCompUserMapper engThemeRefCompUserMapper;
    @Resource
    protected EngThemeRefCompMapper engThemeRefCompMapper;
    @Resource
    protected EngThemeRefUserMapper engThemeRefUserMapper;

    public abstract void insertToFront(EngThemeDTO record, String userId);

    public void saveOrUpdateToBackstage(EngTheme record){
        log.info("开始进行数据处理，数据为：{}", record.toString());
        if (StringUtils.isEmpty(record.getId())) {
            saveToBackstage(record);
        } else {
            updateToBackstage(record);
        }
    }
    protected abstract void saveToBackstage(EngTheme record);
    protected abstract void updateToBackstage(EngTheme record);

    public int updateByPrimaryKeyWithBLOBs(EngTheme record) {
        return engThemeMapper.updateByPrimaryKeySelective(record);
    }
}
