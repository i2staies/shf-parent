package com.itguigu.service.impl;

import com.alibaba.fastjson.JSON;
import com.itguigu.base.BaseMapper;
import com.itguigu.base.BaseServiceImpl;
import com.itguigu.entity.Dict;
import com.itguigu.mapper.DictMapper;
import com.itguigu.controller.DictService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DictServiceImpl extends BaseServiceImpl<Dict> implements DictService {

    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public BaseMapper<Dict> getBaseMapper() {
        return dictMapper;
    }

    @Override
    public List<Map<String, Object>> findZnodesByParentId(Long id) {
        List<Map<String, Object>> zNodes = new ArrayList<>();
        //得到父节点id为{id}的节点信息
        List<Dict> dictList = dictMapper.findListByParentId(id);
        for (Dict dict : dictList) {
            Map<String, Object> zNode = new HashMap<>();
            zNode.put("id", dict.getId());
            zNode.put("name", dict.getName());
            //根据当前获得的节点id作为父节点id，看是否有父节点为当前节点id的节点
            zNode.put("isParent", dictMapper.countByParentId(dict.getId()) > 0);
            zNodes.add(zNode);
        }
        return zNodes;
    }

    @Override
    public List<Dict> findListByParentId(long parentId) {
        Jedis jedis = jedisPool.getResource();
        String key = "shf:dict:parentid:"+parentId;

        String jsonStr = jedis.get(key);
        if(jsonStr != null){
//            redis里有dictList
            List<Dict> dictList = JSON.parseObject(jsonStr, List.class);
            jedis.close();
            return dictList;
        }
//            redis里面没有dictList，查询数据库，并存储到redis
        List<Dict> dictList = dictMapper.findListByParentId(parentId);
        //String类型
        jedis.set(key, JSON.toJSONString(dictList));
        jedis.close();
        return dictList;
    }

    @Override
    public List<Dict> findListByParentCode(String parentCode) {
        //根据dictCode获取数据字典id
        Long parentId = dictMapper.getIdByCode(parentCode);
        return dictMapper.findListByParentId(parentId);
    }
}
