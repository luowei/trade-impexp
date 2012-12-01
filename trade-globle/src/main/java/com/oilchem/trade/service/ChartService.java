package com.oilchem.trade.service;

import com.oilchem.trade.bean.YearMonthDto;
import ofc4j.model.axis.Label;

import java.util.List;

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
}
