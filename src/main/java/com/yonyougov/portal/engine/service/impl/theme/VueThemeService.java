package com.yonyougov.portal.engine.service.impl.theme;

import com.yonyougov.portal.engine.dto.EngThemeDTO;
import com.yonyougov.portal.engine.entity.EngTheme;
import com.yonyougov.portal.engine.service.EngThemeAbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @Author yindwe@yonyou.com
 * @Date 2019/7/2
 * @Description
 */
@Service
@Slf4j
public class VueThemeService extends EngThemeAbstractService {
    @Override
    public void insertToFront(EngThemeDTO record, String userId) {

    }

    @Override
    protected void saveToBackstage(EngTheme record) {
        log.info("------------执行vue的新增主题---------");
        log.info("传入参数为:{}", record);
    }

    @Override
    protected void updateToBackstage(EngTheme record) {

    }
}
