package com.itguigu.controller;

import com.itguigu.base.BaseController;
import com.itguigu.entity.Dict;
import com.itguigu.result.Result;
import org.apache.dubbo.config.annotation.Reference;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/dict")
public class DictController extends BaseController {


    private static final String PAGE_INDEX = "dict/index";


    /**
     * 跳转'dict/index.html'页面
     *
     * @return
     */
    @RequestMapping
    public String index() {
        return PAGE_INDEX;
    }

    @Reference
    private DictService dictService;

    /**
     * 获取zTree节点数据
     * @param id ：dict的parentId
     * @return
     */
    @ResponseBody
    @GetMapping("/findZnodes")
    public Result findZnodes(@RequestParam(defaultValue = "0") Long id) {
        List<Map<String, Object>> zNodes = dictService.findZnodesByParentId(id);
        return Result.ok(zNodes);
    }


    /**
     * 根据areaId获取板块列表
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/findDictListByParentId/{id}")
    public Result findDictListByParentId(@PathVariable Long id) {
        List<Dict> plateList = dictService.findListByParentId(id);
        return Result.ok(plateList);
    }

}
