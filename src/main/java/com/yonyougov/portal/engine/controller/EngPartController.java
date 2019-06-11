package com.yonyougov.portal.engine.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yonyougov.portal.engine.common.MsgConstant;
import com.yonyougov.portal.engine.dto.ApiResult;
import com.yonyougov.portal.engine.entity.EngComp;
import com.yonyougov.portal.engine.entity.EngPart;
import com.yonyougov.portal.engine.service.impl.EngPartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author yindwe@yonyou.com
 * @Date 2019/6/11
 * @Description
 */
@RestController
@RequestMapping("apis/engPart")
@Api(value = "门户部件", description = "门户部件")
public class EngPartController extends BaseController{

    @Resource
    private EngPartService engPartService;

    @PostMapping
    @ApiOperation(value = "增加部件")
    public ApiResult add(@RequestBody EngPart engPart) {
        try {
            engPartService.insert(engPart);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success(MsgConstant.OPERATION_SUCCESS);
    }

    @GetMapping
    @ApiOperation(value = "查询部件")
    public ApiResult list(int pageNum) {
        Page<EngComp> page;
        try {
            page = PageHelper.startPage(pageNum, MsgConstant.PAGE_SIZE, true);
            engPartService.listAll();
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return successPages(page);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "按id查询部件")
    public ApiResult list(@PathVariable String id) {
        EngPart engPart;
        try {
            engPart = engPartService.selectByPrimaryKey(id);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success(engPart);
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "删除部件")
    public ApiResult delete(@PathVariable String id) {
        try {
            engPartService.deleteByPrimaryKey(id);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success(MsgConstant.OPERATION_SUCCESS);
    }
}
