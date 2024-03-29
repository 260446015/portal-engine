package com.yonyougov.portal.engine.service.impl;

import com.yonyougov.portal.engine.entity.EngComp;
import com.yonyougov.portal.engine.mapper.EngCompMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
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

    @Transactional
    public int insert(EngComp record) {
        engCompMapper.insert(record);
        Document template = Jsoup.parse(record.getTemplate());
        Document templateFull = Jsoup.parse(record.getTemplateFull());
        Element portlet = template.getElementsByClass("portlet").get(0);
        portlet.attr("compid", record.getId());
        Element view = templateFull.getElementsByClass("view").get(0);
        view.html(portlet.outerHtml());
        Element portletFull = templateFull.getElementsByClass("portletFull").get(0);
        record.setTemplate(portlet.outerHtml());
        record.setTemplateFull(portletFull.outerHtml());
        return engCompMapper.updateByPrimaryKeyWithBLOBs(record);
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
        return engCompMapper.updateByPrimaryKeySelective(record);
    }

    public List<EngComp> listAll(String compType) {
        return engCompMapper.listAll(compType);
    }

    public int updateBatch(List<EngComp> list) {
        return engCompMapper.updateBatch(list);
    }

    public int batchInsert(List<EngComp> list) {
        return engCompMapper.batchInsert(list);
    }
}







