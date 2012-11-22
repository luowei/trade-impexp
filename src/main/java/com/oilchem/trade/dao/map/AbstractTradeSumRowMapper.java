package com.oilchem.trade.dao.map;

import com.oilchem.trade.config.ConfigUtil;
import com.oilchem.trade.domain.abstrac.TradeSum;
import jxl.Sheet;
import org.apache.commons.lang3.CharUtils;

import java.math.BigDecimal;

import static com.oilchem.trade.config.ConfigUtil.Config.*;
import static com.oilchem.trade.config.ConfigUtil.*;
import static com.oilchem.trade.config.MapperConfig.*;
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

        e.setProductName(getContents(getDefault(excel_product_name)));
        e.setNumMonth(getDecimal(getDefault(excel_num_month)));
        e.setNumSum(getDecimal(getDefault(excel_num_sum)));
        e.setMoneyMonth(getDecimal(getDefault(excel_money_month)));
        e.setMoneySum(getDecimal(getDefault(excel_money_sum)));
        e.setAvgPriceMonth(getDecimal(getDefault(excel_avg_price_month)));
        e.setAvgPriceSum(getDecimal(getDefault(excel_avg_price_sum)));
        e.setNumPreMonthIncRatio(getDecimal(getDefault(excel_num_premonth_incratio)));
        e.setNumPreYearSameMonthIncRatio(getDecimal(getDefault(excel_num_preyearsamemonth_incratio)));
        e.setNumPreYearSameQuarterInrRatio(getDecimal(getDefault(excel_num_preyearsamequarter_imcratio)));
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
