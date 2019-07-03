package com.yonyougov.portal.engine.service.impl.theme;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.yonyougov.portal.engine.common.MsgConstant;
import com.yonyougov.portal.engine.dto.Container;
import com.yonyougov.portal.engine.dto.EngThemeDTO;
import com.yonyougov.portal.engine.dto.VueThemeInputDTO;
import com.yonyougov.portal.engine.entity.EngTheme;
import com.yonyougov.portal.engine.entity.EngThemeRefComp;
import com.yonyougov.portal.engine.service.EngThemeAbstractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


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
        engTheme.setDefaultTheme(MsgConstant.ACTIVE_FALSE).setName(vueThemeInputDTO.getName());
        engThemeMapper.insert(engTheme);
        //这里处理的是频道容器，相当于EngThemeRefComp对象
        List<Container> portletList = vueThemeInputDTO.getPortletList();
        List<EngThemeRefComp> engThemeRefCompList = Lists.newArrayList();
        portletList.forEach(container -> {
            EngThemeRefComp engThemeRefComp = new EngThemeRefComp();
            engThemeRefComp.setCompid(container.getContent().getCompid()).setThemeId(engTheme.getId()).setXpoint(container.getXpoint())
                    .setYpoint(container.getYpoint()).setHeight(container.getHeight()).setWidth(container.getWidth()).setIndex(container.getIndex())
                    .setParentId(container.getIndex());
            engThemeRefCompList.add(engThemeRefComp);
        });
        engThemeRefCompMapper.batchInsert(engThemeRefCompList);
    }

    @Override
    protected void updateToBackstage(EngTheme record) {
        VueThemeInputDTO vueThemeInputDTO = JSONObject.parseObject(record.getTemplate(), VueThemeInputDTO.class);
        //获取所有已存在的engThemeRefComp对象
        List<EngThemeRefComp> engThemeRefCompList = engThemeRefCompMapper.selectByThemeId(record.getId());
        List<Container> portletList = vueThemeInputDTO.getPortletList();
        //循环拿到相应的index角标，然后将数据替换进去
        engThemeRefCompList.forEach(engThemeRefComp -> portletList.forEach(container -> {
            if(engThemeRefComp.getIndex().equals(container.getIndex())){
                engThemeRefComp.setCompid(container.getContent().getCompid());
            }
        }));
        engThemeRefCompMapper.updateBatch(engThemeRefCompList);
    }
}
