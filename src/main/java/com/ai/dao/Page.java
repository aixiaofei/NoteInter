package com.ai.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Page implements Serializable {
    private static final int DEFAULT_PAGE_SIZE = 10;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private int pageNum;
    private List data;
    private int totalCount;

    public Page(){
        this(1, 0, DEFAULT_PAGE_SIZE, new ArrayList());
    }

    public Page(int pageNum, int totalCount, int pageSize, List data){
        this.pageNum = pageNum;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.data = data;
    }

    public int getStart(){
        return (pageNum-1)*pageSize;
    }

    public boolean isPage(){
        return pageNum<=getTotalPageCount();
    }

    /*
       取总页数
     */
    public int getTotalPageCount(){
        return totalCount/pageSize+1;
    }

    /**
     * 该页是否有下一页.
     */
    public boolean isHasNextPage() {
        return this.getPageNum() < this.getTotalPageCount();
    }

    /**
     * 该页是否有上一页.
     */
    public boolean isHasPreviousPage() {
        return this.getPageNum() > 1;
    }


    /**
     * 取每页数据容量.
     */
    public int getPageSize() {
        return pageSize;
    }


    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
    /**
     * 取总记录数.
     */
    public long getTotalCount() {
        return this.totalCount;
    }


    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
