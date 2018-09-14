package com.taotao.common;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 查询solr索引库的结果pojo
 * @author:
 * @create: 2018-09-08 21:35
 **/
public class SearchResult implements Serializable {

    private long totalPages;
    private long recordCount;
    private List<SearchItem> searchItemList;

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public List<SearchItem> getSearchItemList() {
        return searchItemList;
    }

    public void setSearchItemList(List<SearchItem> searchItemList) {
        this.searchItemList = searchItemList;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "totalPages=" + totalPages +
                ", recordCount=" + recordCount +
                ", searchItemList=" + searchItemList +
                '}';
    }
}