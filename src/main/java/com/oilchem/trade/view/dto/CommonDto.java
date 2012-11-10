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
    private Comparable<Serializable> lowValue;
    private Comparable<Serializable> highValue;
    private String order;
    private String[] orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Comparable<Serializable> getLowValue() {
        return lowValue;
    }

    public void setLowValue(Comparable<Serializable> lowValue) {
        this.lowValue = lowValue;
    }

    public Comparable<Serializable> getHighValue() {
        return highValue;
    }

    public void setHighValue(Comparable<Serializable> highValue) {
        this.highValue = highValue;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String[] getOrders() {
        return orders;
    }

    public void setOrders(String[] orders) {
        this.orders = orders;
    }
}
