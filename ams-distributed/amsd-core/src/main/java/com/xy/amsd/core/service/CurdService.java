package com.xy.amsd.core.service;

import com.xy.amsd.core.page.PageRequest;
import com.xy.amsd.core.page.PageResult;

import java.util.List;

public interface CurdService <T>{
    int save(T record);

    int delete(T record);

    int delete(List<T> records);

    T findById(Long id);

    /**
     * 通过page包封装了分页请求与结果，避免引入具体分页框架的对象
     * @param pageRequest 封装的分页请求
     * @return 封装的分页查询结果
     */
    PageResult findPage(PageRequest pageRequest);
}
