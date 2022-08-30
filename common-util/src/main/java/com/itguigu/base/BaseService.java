package com.itguigu.base;

import com.github.pagehelper.PageInfo;
import java.util.List;
import java.util.Map;

/**
 * @author oldqiu
 * @param <T>
 */
public interface BaseService<T> {

    void insert(T t);

    void delete(Long id);

    void update(T t);

    T getById(Long id);

    PageInfo<T> findPage(Map filters);

    List<T> findAll();
}