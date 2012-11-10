package com.oilchem.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @className:Page
 * @classDescription:
 * @author:luowei
 * @createTime:12-10-12
 */
public class DataTable<T> implements Serializable {
    public final int DEFAULT_PAGE_SIZE = 10;
    public final int DEFAULT_PAGE_COUNT = 10;

    private List<T> content;                        //分页数据
    private long totalElements;                     //总记录数
    private int currentPage = 1;                    //当前页
    private int pageSize = DEFAULT_PAGE_SIZE;       //每页显示记录数
    private long startIndex;                        //开始索引页
    private long endIndex;                          //结束索引页
    private long pageCount = DEFAULT_PAGE_COUNT;    //显示多少页,例如显示10页
    private String conditionUrl;                    //查询条件url


    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(long startIndex) {
        this.startIndex = startIndex;
    }

    public long getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(long endIndex) {
        this.endIndex = endIndex;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }


    public String getConditionUrl() {
        return conditionUrl;
    }

    public void setConditionUrl(String conditionUrl) {
        this.conditionUrl = conditionUrl;
    }

    /**
     * @return 返回总页数
     */
    public long getTotalPages() {
        // totalElements % pageSize == 0 ? totalElements / pageSize : totalElements / pageSize + 1

        return (totalElements + pageSize - 1) / pageSize;
    }


    /**
     * @return 返回数据集合是否为空
     */
    public boolean isHasContent() {

        return !content.isEmpty();
    }


    /**
     * @return 返回当前页是否是第一页
     */
    public boolean isFirstPage() {

        return !isHasPreviousPage();
    }


    /**
     * @return 返回是否有前一页
     */
    public boolean isHasPreviousPage() {

        return currentPage > 1;
    }


    /**
     * @return 返回是否有下一页
     */
    public boolean isHasNextPage() {

        return currentPage < getTotalPages();
    }


    /**
     * @return 返回当前页是否是最后一页
     */
    public boolean isLastPage() {

        return !isHasNextPage();
    }


    /**
     * @return 返回下一页页码
     */
    public int getNext() {
        int result = currentPage;

        if (isHasNextPage()) {
            result++;
        }

        return result;
    }


    /**
     * @return 返回前一页
     */
    public int getPrevious() {
        int result = currentPage;

        if (isHasPreviousPage()) {
            result--;
        }

        return result;
    }


    public int getLastPage() {
        return (int) getTotalPages();
    }


    /**
     * 当前页是否只有一条记录
     *
     * @return
     */
    public boolean isLastPageOnlyHaveOneElement() {

        return content.size() == 1;
    }

}