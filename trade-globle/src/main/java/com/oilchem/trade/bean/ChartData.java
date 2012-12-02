package com.oilchem.trade.bean;

import ofc4j.model.axis.Label;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-1
 * Time: 下午4:09
 * To change this template use File | Settings | File Templates.
 */
public class ChartData<T> {

    String x_legend;
    String y_legend;
    Integer minRang = 0;
    Map<String,BigDecimal> minRangMap = new HashMap<String, BigDecimal>();
    Integer maxRang = 10000;
    Map<String,BigDecimal> maxRangMap = new HashMap<String, BigDecimal>();
    Integer step = 1000;

    //图表中的元素list
    List<T> elementList = new ArrayList<T>();

    //图表标签轴上的标签list
   List<Label> labels = new ArrayList<Label>();


    public ChartData() {
    }


    public ChartData(Integer minRang, Integer maxRang, Integer step,
                     List<T> elementList, List<Label> labels) {
        this.minRang = minRang;
        this.maxRang = maxRang;
        this.step = step;
        this.elementList = elementList;
        this.labels = labels;
    }


    public String getX_legend() {
        return x_legend;
    }

    public ChartData setX_legend(String x_legend) {
        this.x_legend = x_legend;
        return this;
    }

    public String getY_legend() {
        return y_legend;
    }

    public ChartData setY_legend(String y_legend) {
        this.y_legend = y_legend;
        return this;
    }

    public Integer getMinRang() {
        return minRang;
    }

    public ChartData setMinRang(Integer minRang) {
        this.minRang = minRang;
        return this;
    }

    public Integer getMaxRang() {
        return maxRang;
    }

    public ChartData setMaxRang(Integer maxRang) {
        this.maxRang = maxRang;
        return this;
    }

    public Integer getStep() {
        return step;
    }

    public ChartData setStep(Integer step) {
        this.step = step;
        return this;
    }

    public List<T> getElementList() {
        return elementList;
    }

    public ChartData setElementList(List<T> elementList) {
        this.elementList = elementList;
        return this;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public ChartData setLabels(List<Label> labels) {
        this.labels = labels;
        return this;
    }

    public Map<String, BigDecimal> getMinRangMap() {
        return minRangMap;
    }

    public ChartData setMinRangMap(Map<String, BigDecimal> minRangMap) {
        this.minRangMap = minRangMap;
        return this;
    }

    public Map<String, BigDecimal> getMaxRangMap() {
        return maxRangMap;
    }

    public ChartData setMaxRangMap(Map<String, BigDecimal> maxRangMap) {
        this.maxRangMap = maxRangMap;
        return this;
    }
}
