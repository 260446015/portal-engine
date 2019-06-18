package com.yonyougov.portal.engine.controller;

import com.yonyougov.portal.engine.common.MsgConstant;
import com.yonyougov.portal.engine.dto.ApiResult;
import com.yonyougov.portal.engine.dto.EngThemeDTO;
import com.yonyougov.portal.engine.entity.EngTheme;
import com.yonyougov.portal.engine.entity.User;
import com.yonyougov.portal.engine.service.IEngThemeService;
import com.yonyougov.portal.engine.util.UserUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jsoup.select.Elements;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/5 19:19
 * @Description
 */
@RestController
@RequestMapping("apis/engTheme")
@Api(value = "门户主题", description = "门户主题")
public class EngThemeController extends BaseController {

    @Resource
    private IEngThemeService engThemeService;

    @ApiOperation(value = "后台管理员新增主题")
    @PostMapping("backstage")
    public ApiResult addForBackstage(@RequestBody EngTheme engTheme) {
        try {
            Assert.notNull(engTheme.getName(), "名称不能为空");
            Assert.notNull(engTheme.getTemplate(), "模板内容不能为空");
            engThemeService.saveOrUpdateToBackstage(engTheme);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success(MsgConstant.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "前台新增主题")
    @PostMapping("front")
    public ApiResult addForFront(@RequestBody EngThemeDTO engThemeDTO) {
        try {
            Assert.notNull(engThemeDTO.getName(), "名称不能为空");
            Assert.notNull(engThemeDTO.getInnerData(), "组件与parentId不能为空");
            User currentUser = UserUtil.getCurrentUser();
            engThemeService.insertToFront(engThemeDTO, currentUser.getId());
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success(MsgConstant.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "删除主题")
    @DeleteMapping("{id}")
    public ApiResult delete(@PathVariable String id) {
        try {
            engThemeService.deleteByPrimaryKey(id);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success(MsgConstant.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "查询主题列表")
    @GetMapping
    public ApiResult list() {
        List<EngTheme> engThemes;
        try {
            engThemes = engThemeService.listAll();
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success(engThemes);
    }

    @ApiOperation(value = "按id查询主题列表")
    @GetMapping("/backstage/{id}")
    public ApiResult listForBackstage(@PathVariable String id) {
        Elements document;
        try {
            document = engThemeService.selectByPrimaryKeyForBackstage(id);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success(document.outerHtml());
    }

    @ApiOperation(value = "查询当前用户主题")
    @GetMapping("/front/{userId}")
    public ApiResult listForFront(@PathVariable String userId) {
        Elements document;
        try {
//            User currentUser = UserUtil.getCurrentUser();
            document = engThemeService.selectByUserIdForFront(userId);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success(document.outerHtml());
    }
}
