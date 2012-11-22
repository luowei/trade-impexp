package com.oilchem.trade.dao.map;

import com.oilchem.trade.domain.abstrac.TradeSum;
import jxl.Sheet;
import org.apache.commons.lang3.CharUtils;

import java.math.BigDecimal;

import static com.oilchem.trade.util.ConfigUtil.ExcelFiled.*;
import static org.springframework.util.StringUtils.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-8
 * Time: 上午9:25
 * To change this template use File | Settings | File Templates.
 */
public class AbstractTradeSumRowMapper<E extends TradeSum> implements MyRowMapper {

    Sheet sheet;
    int rowIdx;
    E e;

    public AbstractTradeSumRowMapper() {
    }


    public AbstractTradeSumRowMapper(int rowIdx, E e, Sheet sheet) {
        this.sheet = sheet;
        this.rowIdx = rowIdx;

        e.setProductName(getContents(excel_product_name.value()));
        e.setNumMonth(getDecimal(excel_num_month.value()));
        e.setNumSum(getDecimal(excel_num_sum.value()));
        e.setMoneyMonth(getDecimal(excel_money_month.value()));
        e.setMoneySum(getDecimal(excel_money_sum.value()));
        e.setAvgPriceMonth(getDecimal(excel_avg_price_month.value()));
        e.setAvgPriceSum(getDecimal(excel_avg_price_sum.value()));
        e.setNumPreMonthIncRatio(getDecimal(excel_num_premonth_incratio.value()));
        e.setNumPreYearSameMonthIncRatio(getDecimal(excel_num_preyearsamemonth_incratio.value()));
        e.setNumPreYearSameQuarterInrRatio(getDecimal(excel_num_preyearsamequarter_imcratio.value()));
        this.e = e;
    }

    /**
     * 获得decimal数据
     *
     * @param fieldName
     * @return
     */
    private BigDecimal getDecimal(String fieldName) {
        if (fieldName == null || fieldName.equals(""))
            return null;

        String content = getContents(fieldName);
        if (content == null || content.equals(""))
            return null;


        CharUtils.isAsciiAlphanumeric('$');  //$%-

        return BigDecimal.valueOf(
                Double.parseDouble(setContent(content)
                        .delSymbols(",").delSymbols("$")
                        .delSymbols("%").delSymbols("0-")
                        .delSymbols("[").delSymbols("]")
                        .getRetStr()
                )
        );
    }


    private String getContents(String name) {
        return sheet.getCell(sheet.findCell(name).getColumn(), rowIdx).getContents();
    }

    //特殊字符串处理
    private String sourceString;
    private AbstractTradeSumRowMapper setContent(String sourceString) {
        this.sourceString = sourceString;
        return this;
    }
    private String getRetStr() {
        return sourceString;
    }
    private AbstractTradeSumRowMapper delSymbols(String symbol) {
        if (sourceString != null)
            this.sourceString = deleteAny(sourceString, symbol);
        return this;
    }

    public E getMappingInstance() {
        return e;
    }
}
