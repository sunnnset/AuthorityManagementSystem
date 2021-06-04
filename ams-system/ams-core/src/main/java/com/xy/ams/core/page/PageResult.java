
package com.xy.ams.core.page;

import lombok.Data;

import java.util.List;

/**
 * 包装的分页返回结果
 */
@Data
public class PageResult {

    private int pageNum;

    private int pageSize;

    private long totalSize;

    private int totalPages;

    private List<?> content;
}
