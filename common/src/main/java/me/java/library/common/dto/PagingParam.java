package me.java.library.common.dto;


import me.java.library.common.AbstractPojo;

/**
 * @author :  sylar
 * @FileName :
 * @CreateDate :  2017/11/08
 * @Description : 分页请求参数
 * @ReviewedBy :
 * @ReviewedOn :
 * @VersionHistory :
 * @ModifiedBy :
 * @ModifiedDate :
 * @Comments :
 * @CopyRight : COPYRIGHT(c) xxx.com All Rights Reserved
 * *******************************************************************************************
 */
public class PagingParam extends AbstractPojo {
    /**
     * 请求的分页索引
     */
    private Integer pageIndex;
    /**
     * 分页大小
     */
    private Integer pageSize;

    public PagingParam(Integer pageIndex, Integer pageSize) {
        //前端pageIndex是从1开始计算
        if (pageIndex == null || pageIndex < 1) {
            pageIndex = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = Integer.MAX_VALUE;
        }

        //后端pageIndex 是从0开始
        this.pageIndex = pageIndex - 1;
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

