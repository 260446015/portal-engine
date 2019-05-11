package com.yonyougov.portal.engine.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yonyougov.portal.engine.common.MsgConstant;
import com.yonyougov.portal.engine.util.PageUtil;
import com.yonyougov.portal.engine.entity.EngPage;
import com.yonyougov.portal.engine.service.impl.EngPageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author yindwe@yonyu.com
 * @Date 2019/5/5 19:03
 * @Description
 */
@RestController
@RequestMapping("apis/engPage")
@Api(value = "门户页面", description = "门户页面")
public class EngPageController {

    @Resource
    private EngPageService engPageService;

    @ApiOperation(value = "新增页面")
    @PostMapping
    public ResponseEntity add(@RequestBody EngPage engPage) {
        try {
            engPageService.insert(engPage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MsgConstant.OPERATION_ERROR);
        }
        return ResponseEntity.ok().body(MsgConstant.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "删除页面")
    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable String id) {
        try {
            engPageService.deleteByPrimaryKey(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MsgConstant.OPERATION_ERROR);
        }
        return ResponseEntity.ok().body(MsgConstant.OPERATION_SUCCESS);
    }

    @ApiOperation(value = "查询页面列表")
    @GetMapping
    public ResponseEntity list(int pageNum) {
        Page<EngPage> page;
        try {
            page = PageHelper.startPage(pageNum, MsgConstant.PAGE_SIZE, true);
            engPageService.listAll();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok().body(PageUtil.successPage(page));
    }

    @ApiOperation(value = "按id查询页面列表")
    @GetMapping("{id}")
    public ResponseEntity list(@PathVariable String id) {
        EngPage engPage;
        try {
            engPage = engPageService.selectByPrimaryKey(id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok().body(engPage);
    }
}
