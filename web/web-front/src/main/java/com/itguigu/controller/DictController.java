package com.itguigu.controller;

import com.itguigu.base.BaseController;
import com.itguigu.entity.Dict;
import com.itguigu.result.Result;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController extends BaseController {
    @Reference
    private DictService dictService;

    @GetMapping("/findDictListByParentDictCode/{dictCode}")
    public Result findDictListByParentDictCode(@PathVariable String dictCode) {
        List<Dict> dictList = dictService.findListByParentCode(dictCode);
        return Result.ok(dictList);
    }

    @GetMapping("/findDictListByParentId/{parentId}")
    public Result findDictListByParentId(@PathVariable("parentId") Long parentId) {
        List<Dict> dictList = dictService.findListByParentId(parentId);
        return Result.ok(dictList);
    }
}
