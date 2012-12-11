package com.oilchem.trade.dao.map;

import com.oilchem.trade.domain.abstrac.TradeSum;
import jxl.Sheet;

import java.math.BigDecimal;

import static com.oilchem.trade.bean.DocBean.ExcelFiled.*;
import static org.codehaus.plexus.util.StringUtils.isBlank;
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

        e.setProductName(getContents(excel_product_name.getValue()));
        e.setNumMonth(getDecimal(excel_num_month.getValue()));
        e.setNumSum(getDecimal(excel_num_sum.getValue()));
        e.setMoneyMonth(getDecimal(excel_money_month.getValue()));
        e.setMoneySum(getDecimal(excel_money_sum.getValue()));
        e.setAvgPriceMonth(getDecimal(excel_avg_price_month.getValue()));
        e.setAvgPriceSum(getDecimal(excel_avg_price_sum.getValue()));
        e.setPm(getPersentData(excel_pm.getValue()));
        e.setPy(getPersentData(excel_py.getValue()));
        e.setPq(getPersentData(excel_pq.getValue()));
        this.e = e;
    }

    private BigDecimal getPersentData(String fieldName) {
        if (fieldName == null || fieldName.equals(""))
            return null;

        String content = getContents(fieldName);
        if (content == null || content.equals(""))
            return null;
        if (content.indexOf("$") != -1) {
            content = content.substring(0, content.indexOf("$"));
        }

        String retStr = setContent(content)
                .delSymbols("[").delSymbols("]")
                .delSymbols("-84").delSymbols("-8")
                .delSymbols(",").delSymbols("$")
                .delSymbols("%").delSymbols("0-")
                .getRetStr();
        retStr = isBlank(content) ? "0" : retStr;

        BigDecimal bigDecimal = null;
        bigDecimal = BigDecimal.valueOf(
                Double.parseDouble(retStr)
        ).divide(BigDecimal.valueOf(100));
        return bigDecimal;

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

        if (content.indexOf("$") != -1) {
            content = content.substring(0, content.indexOf("$"));
        }

        String retStr = setContent(content)
                .delSymbols(",").delSymbols("$")
                .delSymbols("%").delSymbols("0-")
                .delSymbols("[").delSymbols("]")
                .getRetStr();
        retStr = isBlank(retStr) ? "0" : retStr;

        return BigDecimal.valueOf(
                Double.parseDouble(retStr)
        );
    }


    private String getContents(String name) {
        return sheet.getCell(sheet.findCell(name).getColumn(), rowIdx).getContents().trim();
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
            this.sourceString = delete(sourceString, symbol);
        return this;
    }

    public E getMappingInstance() {
        return e;
    }
}
