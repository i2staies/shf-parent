package com.itguigu.controller;

import com.itguigu.base.BaseService;
import com.itguigu.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictService extends BaseService<Dict> {

    /**
     * 根据parentId获取zTree节点数据
     * @param id ： parentId
     * @return
     */
    List<Map<String, Object>> findZnodesByParentId(Long id);

    /**
     * 根据parentId获取Dict信息
     * @param parentId
     * @return
     */
    List<Dict> findListByParentId(long parentId);

    /**
     * 根据parentCode获取Dict信息
     * @param parentCode
     * @return
     */
    List<Dict> findListByParentCode(String parentCode);
}
