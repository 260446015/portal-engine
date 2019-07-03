package com.yonyougov.portal.engine.service;

import com.yonyougov.portal.engine.dto.EngThemeVO;
import com.yonyougov.portal.engine.entity.EngTheme;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * @Author yindwe@yonyou.com
 * @Date 2019/6/18
 * @Description
 */
public interface IEngThemeService {

    int deleteByPrimaryKey(String id);

    /**
     * 前端按照用户id查询对应主题
     * @param userId
     * @return
     */
    EngThemeVO selectByUserIdForFront(String userId);

    /**
     * 后台全查所有的主题页面
     */
    List<EngTheme> listAll();

    /**
     * 后台按id查询主题
     * @param id
     * @return
     */
    Elements selectByPrimaryKeyForBackstage(String id);

    /**
     * 查询所有主题，含组件
     * @return
     */
    List<EngThemeVO> listAllTheme();
}
