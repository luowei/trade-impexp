package com.oilchem.common.view.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @className:BaseDTO
 * @classDescription:
 * @author:chen long
 * @createTime:12-10-19
 */
public class BaseCommand implements Serializable {
    private Long id;
    private String page = "1";
    private String searchKey1;
    private String searchKey2;
    private String orders;
    private Long[] ids;
    private Date startTime;
    private Date endTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSearchKey1() {
        return searchKey1;
    }

    public void setSearchKey1(String searchKey1) {
        this.searchKey1 = searchKey1;
    }

    public String getSearchKey2() {
        return searchKey2;
    }

    public void setSearchKey2(String searchKey2) {
        this.searchKey2 = searchKey2;
    }

    public String getOrders() {
        return orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}

