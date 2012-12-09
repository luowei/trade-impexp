package com.oilchem.trade.service;

import com.oilchem.trade.bean.ChartData;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.domain.abstrac.DetailCount;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.domain.abstrac.TradeSum;
import ofc4j.model.axis.Label;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-1
 * Time: 上午10:07
 * To change this template use File | Settings | File Templates.
 */
public interface ChartService {

    /**
     * 获得年月的label
     * @param yearMonthDto
     * @return
     */
    public List<Label> getYearMonthLabels(YearMonthDto yearMonthDto);


    /**
     * 获得detailChart List
     * @param labels
     * @param codes
     * @param yearMonthDto  @return     获得由月份组合而成的 list<TradeDetail>的集合
     */
    public Map<String, ChartData<TradeDetail>> getChartDetailList(
            List<Label> labels,
            List<String> codes, YearMonthDto yearMonthDto);

    /**
     * 获得图表数据
     * @param labels
     * @param names
     * @param yearMonthDto
     * @return
     */
    Map<String, ChartData<TradeSum>> getChartSumList(
            List<Label> labels, List<String> names,
            YearMonthDto yearMonthDto);


    Map<String,ChartData<DetailCount>> getChartDetailCountList(List<Label> labels, List<String> codes, YearMonthDto yearMonthDto);

}
