package com.yonyougov.portal.engine.controller;

import com.yonyougov.portal.engine.common.MsgConstant;
import com.yonyougov.portal.engine.dto.ApiResult;
import com.yonyougov.portal.engine.entity.EngComp;
import com.yonyougov.portal.engine.service.impl.EngCompService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/5 13:04
 * @Description
 */
@RestController
@RequestMapping("/apis/engComp")
@Api(description = "门户组件", value = "门户组件")
public class EngCompController extends BaseController {

    @Resource
    private EngCompService engCompService;

    @PostMapping
    @ApiOperation(value = "修改页面组件")
    public ApiResult update(@RequestBody EngComp engComp) {
        try {
            engCompService.updateByPrimaryKey(engComp);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success(MsgConstant.OPERATION_SUCCESS);
    }

    @GetMapping
    @ApiOperation(value = "查询页面组件")
    public ApiResult list(@RequestParam @ApiParam(value = "0bootstrap/1vue") String compType) {
        List<EngComp> result;
        try {
            result = engCompService.listAll(compType);
        } catch (Exception e) {
            return error(e.getMessage());
        }
        return success(result);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "按id查询页面组件")
    public ApiResult listById(@PathVariable String id) {
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
