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

    private Integer year;
    private Integer month;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
}
