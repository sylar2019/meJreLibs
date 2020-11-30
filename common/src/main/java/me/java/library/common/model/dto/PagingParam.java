package me.java.library.common.model.dto;


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
public class PagingParam extends AbstractDTO {
    /**
     * 请求的分页索引
     */
    private Integer pageIndex;
    /**
     * 分页大小
     */
    private Integer pageSize;

    public PagingParam(Integer pageIndex, Integer pageSize) {
        if (pageIndex == null || pageIndex < 0) {
            pageIndex = 0;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = Integer.MAX_VALUE;
        }

        //后端pageIndex 是从0开始
        this.pageIndex = pageIndex;
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

