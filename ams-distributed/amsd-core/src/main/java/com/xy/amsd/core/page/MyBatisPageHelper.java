package com.xy.amsd.core.page;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xy.amsd.common.utils.ReflectionUtils;

import java.util.List;

/**
 * 封装了分页请求查询的代码
 */
public class MyBatisPageHelper {

    public static final String findPage = "findPage";

    /**
     * 默认查询的方法名为“findPage”
     */
    public static PageResult findPage(PageRequest pageRequest, Object mapper) {
        return findPage(pageRequest, mapper, findPage);
    }

    /**
     * 调用分页插件进行分页查询
     * @param pageRequest （包装过的）分页请求
     * @param mapper MyBatis的Mapper DAO对象
     * @param queryMethodName 要分页的查询方法名
     * @param args 方法的参数
     * @return pageResult
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static PageResult findPage(PageRequest pageRequest, Object mapper, String queryMethodName, Object... args) {
        int pageNum = pageRequest.getPageNum();
        int pageSize = pageRequest.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        Object result = ReflectionUtils.invoke(mapper, queryMethodName, args);
        return getPageResult(pageRequest, new PageInfo((List) result));
    }

    /**
     * 将PageHelper返回的PageInfo对象重新包装为自定义的PageResult对象
     * @param pageRequest 分页请求
     * @param pageInfo PageHelper的返回结果
     * @return pageResult对象
     */
    private static PageResult getPageResult(PageRequest pageRequest, PageInfo<?> pageInfo) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());
        return pageResult;
    }
}
