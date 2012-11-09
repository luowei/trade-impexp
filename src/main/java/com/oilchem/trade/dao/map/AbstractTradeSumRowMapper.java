package com.oilchem.trade.dao.map;

import com.oilchem.trade.domain.abstrac.TradeSum;
import jxl.Sheet;

import java.math.BigDecimal;
import static com.oilchem.trade.config.MapperConfig.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-8
 * Time: 上午9:25
 * To change this template use File | Settings | File Templates.
 */
public class AbstractTradeSumRowMapper<E extends TradeSum> implements MyRowMapper{

    Sheet sheet;
    int rowIdx;
    E e;


    public AbstractTradeSumRowMapper(int rowIdx,E e,Sheet sheet) {
        this.sheet = sheet;
        this.rowIdx = rowIdx;

        e.setProductName(getContents(PRODUCT_XNAME));
        e.setNumMonth(BigDecimal.valueOf(Double.parseDouble(getContents(NUM_MONTH))));
        e.setNumSum(BigDecimal.valueOf(Double.parseDouble(getContents(NUM_SUM))));
        e.setMoneyMonth(BigDecimal.valueOf(Double.parseDouble(getContents(MONTH_MONEY))));
        e.setMoneySum(BigDecimal.valueOf(Double.parseDouble(getContents(MONTH_SUM))));
        e.setAvgPriceMonth(BigDecimal.valueOf(Double.parseDouble(getContents(AVG_PRICE_MONTH))));
        e.setAvgPriceSum(BigDecimal.valueOf(Double.parseDouble(getContents(AVG_PRICE_SUM))));
        e.setNumPreMonthIncRadio(BigDecimal.valueOf(Double.parseDouble(getContents(NUM_PREMONTH_INCRADIO))));
        e.setNumPreYearSameMonthIncRatio(BigDecimal.valueOf(Double.parseDouble(getContents(NUM_PREYEARSAMEMONTH_INCRADIO))));
        e.setNumPreYearSameQuarterInrRatio(BigDecimal.valueOf(Double.parseDouble(getContents(NUM_PREYEARSAMEQUARTER_INCRATIO))));
        this.e = e;
    }

    private String getContents(String name){
        return sheet.getCell(sheet.findCell(name).getColumn(),rowIdx).getContents();
    }

    public E getMappingInstance() {
        return e;
    }
}
