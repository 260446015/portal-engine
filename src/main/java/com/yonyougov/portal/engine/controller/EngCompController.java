package com.yonyougov.portal.engine.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yonyougov.portal.engine.common.MsgConstant;
import com.yonyougov.portal.engine.dto.ApiResult;
import com.yonyougov.portal.engine.entity.EngComp;
import com.yonyougov.portal.engine.service.impl.EngCompService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/5 13:04
 * @Description
 */
@RestController
@RequestMapping("/apis/engComp")
@Api(description = "门户组件", value = "门户组件")
public class EngCompController extends BaseController{

    @Resource
    private EngCompService engCompService;

    @PostMapping
    @ApiOperation(value = "增加页面组件")
    public ApiResult add(@RequestBody EngComp engComp) {
        try {
            engCompService.insert(engComp);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success(MsgConstant.OPERATION_SUCCESS);
    }

    @GetMapping
    @ApiOperation(value = "查询页面组件")
    public ApiResult list(int pageNum) {
        Page<EngComp> page;
        try {
            page = PageHelper.startPage(pageNum, MsgConstant.PAGE_SIZE, true);
            engCompService.listAll();
        } catch (Exception e) {
            return error(e.getMessage());
         }
        return successPages(page);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "按id查询页面组件")
    public ApiResult list(@PathVariable String id) {
        EngComp engComp;
        try {
            engComp = engCompService.selectByPrimaryKey(id);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success(engComp);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "删除页面组件")
    public ApiResult delete(@PathVariable String id) {
        try {
            engCompService.deleteByPrimaryKey(id);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success(MsgConstant.OPERATION_SUCCESS);
    }
}
