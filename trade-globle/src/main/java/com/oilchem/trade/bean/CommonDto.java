package com.oilchem.trade.bean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-9
 * Time: 上午12:00
 * To change this template use File | Settings | File Templates.
 */
public class CommonDto implements Serializable {

    private Long id;
    private Long[] ids;
    private String[] codes;
    private Integer pageNumber;
    private Integer pageSize;
    private String keyword;
    private String lowValue;
    private String highValue;
    private String sort;
    private String[] sorts;

    public Long getId() {
        return id;
    }

    public CommonDto setId(Long id) {
        this.id = id;
        return this;
    }

    public Long[] getIds() {
        return ids;
    }

    public CommonDto setIds(Long[] ids) {
        this.ids = ids;
        return this;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public String[] getCodes() {
        return codes;
    }

    public CommonDto setCodes(String[] codes) {
        this.codes = codes;
        return this;
    }

    public CommonDto setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public CommonDto setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLowValue() {
        return lowValue;
    }

    public CommonDto setLowValue(String lowValue) {
        this.lowValue = lowValue;
        return this;
    }

    public String getHighValue() {
        return highValue;
    }

    public CommonDto setHighValue(String highValue) {
        this.highValue = highValue;
        return this;
    }

    public String getSort() {
        return sort;
    }

    public CommonDto setSort(String sort) {
        this.sort = sort;
        return this;
    }

    public String[] getSorts() {
        return sorts;
    }

    public CommonDto setSorts(String[] sorts) {
        this.sorts = sorts;
        return this;
    }
}
