package com.yonyougov.portal.engine.service.impl.theme;

import com.alibaba.fastjson.JSONObject;
import com.yonyougov.portal.engine.ThemeOccupiedException;
import com.yonyougov.portal.engine.common.MsgConstant;
import com.yonyougov.portal.engine.entity.EngTheme;
import com.yonyougov.portal.engine.entity.EngThemeRefComp;
import com.yonyougov.portal.engine.entity.EngThemeRefUser;
import com.yonyougov.portal.engine.mapper.EngThemeMapper;
import com.yonyougov.portal.engine.mapper.EngThemeRefCompMapper;
import com.yonyougov.portal.engine.mapper.EngThemeRefUserMapper;
import com.yonyougov.portal.engine.service.EngThemeAbstractService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author yindwe@yonyou.com
 * @Date 2019/7/2
 * @Description
 */
@Service
@Slf4j
public class BootstrapThemeService extends EngThemeAbstractService {

    @Resource
    private EngThemeMapper engThemeMapper;
    @Resource
    private EngThemeRefCompMapper engThemeRefCompMapper;
    @Resource
    private EngThemeRefUserMapper   engThemeRefUserMapper;
    @Override
    protected void updateToBackstage(EngTheme record, List<JSONObject> jsonObjects) {
        log.info("执行更新开始------>{}", record);
        updateByPrimaryKeyWithBLOBs(record);
        //检查主题是否被占用，抛出异常
        checkIfThemeUsed(record.getId());
        //修改之前关联的comp
        updateEngThemeRefComp(record.getId(), jsonObjects);
    }

    protected void saveToBackstage(EngTheme record, List<JSONObject> jsonObjects) {
        log.info("执行新增开始------>{}", record);
        record.setDefaultTheme("N");
        String themeId = saveTheme(record);
        record.setId(themeId);
        //存储用户与主题之间的对应关系表
        saveEngThemeRefComp(record.getId(), jsonObjects);
    }

    private String saveTheme(EngTheme theme) {
        engThemeMapper.insert(theme);
        return theme.getId();
    }

    private void saveEngThemeRefComp(String themeId, List<JSONObject> jsonObjects) {
        jsonObjects.forEach(p -> {
            String parentId = p.getString(MsgConstant.LAYOUTID);
            Element element = (Element) p.get(MsgConstant.COMP);
            EngThemeRefComp engThemeRefComp = new EngThemeRefComp();
            engThemeRefComp.setCompid(element.attr(MsgConstant.COMP_ID));
            engThemeRefComp.setThemeId(themeId);
            engThemeRefComp.setParentId(parentId);
            //icon和url不做处理，交给组件设计器进行
//            engThemeRefComp.setIcon("待定");
//            engThemeRefComp.setUrl(element.attr(MsgConstant.DATA_INTERFACE));
            engThemeRefCompMapper.insert(engThemeRefComp);
        });
    }



    private void checkIfThemeUsed(String themeId) {
        List<EngThemeRefUser> engThemeRefUserList = engThemeRefUserMapper.selectByThemeId(themeId);
        if(engThemeRefUserList.size() != 0){
            throw new ThemeOccupiedException("主题已被使用");
        }
    }

    private void updateEngThemeRefComp(String id, List<JSONObject> jsonObjects) {
        List<EngThemeRefComp> engThemeRefComps = engThemeRefCompMapper.selectByThemeId(id);
        jsonObjects.forEach(jsonObject -> engThemeRefComps.forEach(engThemeRefComp -> {
            if(engThemeRefComp.getParentId().equalsIgnoreCase(jsonObject.getString(MsgConstant.LAYOUTID))){
                engThemeRefComp.setCompid(jsonObject.getString(MsgConstant.COMP_ID));
            }
        }));
        engThemeRefCompMapper.updateBatch(engThemeRefComps);
    }
}
