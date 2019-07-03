package com.yonyougov.portal.engine.service.impl;

import com.yonyougov.portal.engine.common.MsgConstant;
import com.yonyougov.portal.engine.dto.EngThemeVO;
import com.yonyougov.portal.engine.entity.*;
import com.yonyougov.portal.engine.mapper.*;
import com.yonyougov.portal.engine.service.IEngThemeService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/5 9:29
 * @Description
 */
@Service
@Slf4j
public class EngThemeService implements IEngThemeService {

    @Resource
    private EngThemeMapper engThemeMapper;
    @Resource
    private EngCompMapper engCompMapper;
    @Resource
    private EngThemeRefUserMapper engThemeRefUserMapper;
    @Resource
    private EngThemeRefCompUserMapper engThemeRefCompUserMapper;
    @Resource
    private EngThemeRefCompMapper engThemeRefCompMapper;

    @Transactional
    @Override
    public int deleteByPrimaryKey(String id) {
        engThemeRefCompMapper.deleteByThemeId(id);
        return engThemeMapper.deleteByPrimaryKey(id);
    }


    @Override
    public EngThemeVO selectByUserIdForFront(String userId) {
        EngThemeVO vo = new EngThemeVO();
        //获取ENG_THEME_REF_USER表中用户启用的主题(查询T的时候数据库必定只有一条)
        List<EngThemeRefUser> engThemeRefUserList = engThemeRefUserMapper.selectByUserIdAndActive(userId, MsgConstant.ACTIVE_TRUE);
        EngTheme engTheme;
        if (engThemeRefUserList.size() == 0) {
            engTheme = engThemeMapper.selectByDefaultTheme(MsgConstant.ACTIVE_TRUE);
            Elements elements = selectByPrimaryKeyForFront(engTheme.getId());
            vo.setSuccessAssemblyHtml(elements.outerHtml()).setThemeId(engTheme.getId());
            return vo;
        }
        EngThemeRefUser engThemeRefUser = engThemeRefUserList.get(0);
        //获取当前用户使用的主题
        engTheme = engThemeMapper.selectByPrimaryKey(engThemeRefUser.getThemeId());
        Document html = Jsoup.parse(engTheme.getTemplate());
        Elements containers = html.getElementsByClass(MsgConstant.CONTAINER);
        String engThemeRefUserId = engThemeRefUser.getId();
        //获取当前用户当前主题下所有组件的信息并组装最后的页面
        getCurrentThemeWithCompsAndBuildTheme(engThemeRefUserId, containers);
        vo.setThemeId(engTheme.getId()).setSuccessAssemblyHtml(containers.outerHtml());
        return vo;
    }

    private Elements selectByPrimaryKeyForFront(String id) {
        EngTheme engTheme = engThemeMapper.selectByPrimaryKey(id);
        Assert.notNull(engTheme, MsgConstant.DATA_NOT_FOUNT);
        Document document = Jsoup.parse(engTheme.getTemplate());
        Elements containers = document.getElementsByClass(MsgConstant.CONTAINER);
        //从数据库中取出与之对应的组件并替换相应的组件
        getElementsByIdFromDbAndReplaceForBackstage(id, containers);
        return containers;
    }

    private void getCurrentThemeWithCompsAndBuildTheme(String engThemeRefUserId, Elements containers) {
        List<EngThemeRefCompUser> engThemeRefCompUsers = engThemeRefCompUserMapper.selectByThemeUserId(engThemeRefUserId);
        List<String> compIds = engThemeRefCompUsers.stream().map(EngThemeRefCompUser::getCompid).collect(Collectors.toList());
        List<EngComp> themeRefCompsFromDb = engCompMapper.getThemeRefCompsFromDb(compIds);
        Elements elementsByTag = containers.get(0).getElementsByTag(MsgConstant.COMP_ID);
        elementsByTag.forEach(element -> engThemeRefCompUsers.forEach(engThemeRefCompUser -> {
            if (null != element.parent()) {
                if (element.parent().id().equalsIgnoreCase(engThemeRefCompUser.getParentId())) {
                    EngComp engComp = themeRefCompsFromDb.stream().filter(p -> p.getId()
                            .equalsIgnoreCase(engThemeRefCompUser.getCompid())).findFirst().get();
                    Document portlet = Jsoup.parse(engComp.getTemplate());
                    Element portletElement = portlet.getElementsByClass(MsgConstant.PORTLET).get(0);
                    portletElement.attr(MsgConstant.DATA_INTERFACE, StringUtils.isEmpty(engThemeRefCompUser.getUrl()) ?
                            engComp.getUrl() : engThemeRefCompUser.getUrl());
                    portletElement.attr(MsgConstant.ID, engThemeRefCompUser.getId());
                    element.replaceWith(portletElement);
                    log.info("portletElement:--------->" + portletElement);
                }
            }
        }));
        log.info("containers:--------->" + containers);
    }

    public int updateByPrimaryKeySelective(EngTheme record) {
        return engThemeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKeyWithBLOBs(EngTheme record) {
        return engThemeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(EngTheme record) {
        return engThemeMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<EngTheme> listAll() {
        return engThemeMapper.listAll();
    }

    @Override
    public Elements selectByPrimaryKeyForBackstage(String id) {
        EngTheme engTheme = engThemeMapper.selectByPrimaryKey(id);
        Assert.notNull(engTheme, MsgConstant.DATA_NOT_FOUNT);
        Document document = Jsoup.parse(engTheme.getTemplateFull());
        Elements lyrows = document.getElementsByClass(MsgConstant.LYROW);
        //从数据库中取出与之对应的组件并替换相应的组件
        getElementsByIdFromDbAndReplaceForBackstage(id, lyrows);
        return lyrows;
    }

    private void getElementsByIdFromDbAndReplaceForBackstage(String id, Elements lyrows) {
        List<EngThemeRefComp> engThemeRefComps = engThemeRefCompMapper.selectByThemeId(id);
        List<String> ids = engThemeRefComps.stream().map(EngThemeRefComp::getCompid).collect(Collectors.toList());
        List<EngComp> engComps = engCompMapper.getThemeRefCompsFromDb(ids);
        engThemeRefComps.forEach(engThemeRefComp -> engComps.forEach(engComp -> {
            if (engThemeRefComp.getCompid().equalsIgnoreCase(engComp.getId())) {
                lyrows.forEach(lyrow -> {
                    Element parent = lyrow.getElementsByAttributeValue(MsgConstant.LAYOUTID, engThemeRefComp.getParentId()).first();
                    if (parent != null) {
                        Elements elements = parent.getElementsByTag(MsgConstant.COMP_ID);
                        if(elements.size() != 0){
                            Element element = elements.first();
                            Element portletFull = Jsoup.parse(engComp.getTemplate()).getElementsByClass(MsgConstant.PORTLET).get(0);
                            portletFull.attr(MsgConstant.DATA_INTERFACE, StringUtils.isEmpty(engThemeRefComp.getUrl())
                                    ? engComp.getUrl() : engThemeRefComp.getUrl());
                            portletFull.attr(MsgConstant.ID, engThemeRefComp.getId());
                            element.replaceWith(portletFull);
                        }
                    }
                });
            }
        }));
    }


    public int insert(EngTheme record) {
        return engThemeMapper.insert(record);
    }

    public EngTheme selectByPrimaryKey(String id) {
        return engThemeMapper.selectByPrimaryKey(id);
    }

    public int updateBatch(List<EngTheme> list) {
        return engThemeMapper.updateBatch(list);
    }

    public int batchInsert(List<EngTheme> list) {
        return engThemeMapper.batchInsert(list);
    }

    @Override
    public List<EngThemeVO> listAllTheme() {
        return engThemeMapper.listAllTheme();
    }
}

