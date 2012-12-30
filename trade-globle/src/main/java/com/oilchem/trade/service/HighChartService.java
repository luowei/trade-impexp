package com.oilchem.trade.service;

import com.oilchem.trade.bean.ProductCount;
import com.oilchem.trade.bean.Series;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.domain.detail.ImpTradeDetail;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-12-14
 * Time: 下午4:11
 * To change this template use File | Settings | File Templates.
 */
public interface HighChartService {


    List<ProductCount> getProductCount(String productCode, String condition, String yearMonth, Integer impExp);

    List<String> getYearMonthLabels(YearMonthDto yearMonthDto);

    List<String> getSeriesJson(
            YearMonthDto yearMonthDto, ProductCount productCount,
            List<String> yearMonths, String productCode, List<ProductCount> productCountList);

    public List<String> getSMSeriesJson(
            YearMonthDto yearMonthDto, ProductCount productCount,
            List<String> yearMonths, String productCode, List<ProductCount> productCountList);

    /**
     * 把Series map转换成 seriesJson
     *
     *
     * @param typeSet
     * @param seriesMaps
     * @return
     */
    List<String> getSeriesJson(Set<String> typeSet, Map<String, Series>... seriesMaps);

    List<String> getChartDetailCountList(
            List<String> yearMonths, List<String> codes, Integer impExpType);

    Map<String, List<String>> getTradeSumSeries(List<String> yearMonths, List<String> codes, Integer impExpType);


    List<String> getSameMonthLabels(YearMonthDto yearMonthDto);
}
