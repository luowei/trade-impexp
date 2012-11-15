package com.oilchem.trade.view.dto;

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
    private Integer pageNumber;
    private Integer pageSize;
    private Comparable lowValue;
    private Comparable highValue;
    private String order;
    private String[] orders;

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

    public Comparable getLowValue() {
        return lowValue;
    }

    public CommonDto setLowValue(Comparable lowValue) {
        this.lowValue = lowValue;
        return this;
    }

    public Comparable getHighValue() {
        return highValue;
    }

    public CommonDto setHighValue(Comparable highValue) {
        this.highValue = highValue;
        return this;
    }

    public String getOrder() {
        return order;
    }

    public CommonDto setOrder(String order) {
        this.order = order;
        return this;
    }

    public String[] getOrders() {
        return orders;
    }

    public CommonDto setOrders(String[] orders) {
        this.orders = orders;
        return this;
    }
}
