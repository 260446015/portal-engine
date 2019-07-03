package com.yonyougov.portal.engine.service.impl.theme;

import com.alibaba.fastjson.JSONObject;
import com.yonyougov.portal.engine.dto.EngThemeDTO;
import com.yonyougov.portal.engine.dto.VueThemeInputDTO;
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
        //将传入的json进行处理对象化
        VueThemeInputDTO vueThemeInputDTO = JSONObject.parseObject(record.getTemplate(), VueThemeInputDTO.class);
        //抽取页面主题并将数据存入到数据库中
        saveThemeAndThemeRefComp(vueThemeInputDTO);
    }

    private void saveThemeAndThemeRefComp(VueThemeInputDTO vueThemeInputDTO) {
        EngTheme engTheme = new EngTheme();
//        engTheme.setDefaultTheme(MsgConstant.ACTIVE_FALSE).setTemplate(vueThemeInputDTO.get)
//        vueThemeInputDTO.
    }

    @Override
    protected void updateToBackstage(EngTheme record) {

    }
}
