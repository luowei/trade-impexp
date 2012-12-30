package com.oilchem.trade.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-12-17
 * Time: 下午12:48
 * To change this template use File | Settings | File Templates.
 */
public class Series {

    private String name;
    private String type;
    private Integer yAxis;
    private List<Double> data = new ArrayList<Double>();

    public Series() {
    }

    public Series(String name, String type, Integer yAxis, List<Double> data) {
        this.name = name;
        this.type = type;
        this.yAxis = yAxis;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public Series setName(String name) {
        this.name = name;
        return this;
    }

    public List<Double> getData() {
        return data;
    }

    public Series setData(List<Double> data) {
        this.data = data;
        return this;
    }

    public String getType() {
        return type;
    }

    public Series setType(String type) {
        this.type = type;
        return this;
    }

    public Integer getyAxis() {
        return yAxis;
    }

    public Series setyAxis(Integer yAxis) {
        this.yAxis = yAxis;
        return this;
    }
}
