package com.oilchem.trade.dao.map;

import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.util.GenericsUtils;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.oilchem.trade.bean.DocBean.AccessField.*;
import static com.oilchem.trade.bean.DocBean.Config.yearmonth_split;

/**
 * detail与access表中字段映射
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-6
 * Time: 下午1:31
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTradeDetailRowMapper<T extends TradeDetail> implements RowMapper<T> {

    Class<T> tClass = GenericsUtils.getSuperClassGenricType(getClass());
    Logger log = LoggerFactory.getLogger(tClass);

    public TradeDetail setTraddDetail(TradeDetail tradeDetail,
                                      ResultSet rs,
                                      Integer year,
                                      Integer month) throws SQLException {

        //产品代码
        tradeDetail.setProductCode(getString(access_product_code.getValue(), rs));
        //产品名称
        tradeDetail.setProductName(getString(access_product_name.getValue(),rs));
        //企业性质
        tradeDetail.setCompanyType(getString(access_company_type.getValue(),rs));
        //贸易方式
        tradeDetail.setTradeType(getString(access_trade_type.getValue(),rs));
        //城市
        tradeDetail.setCity(getString(access_city.getValue(),rs));
        //产销国家
        tradeDetail.setCountry(getString(access_country.getValue(),rs));
        //出口海关
        tradeDetail.setCustoms(getString(access_customs.getValue(),rs));
        //运输方式
        tradeDetail.setTransportation(getString(access_transportation.getValue(),rs));
        //单位
        tradeDetail.setUnit(getString(access_unit.getValue(),rs));
        //数量
        BigDecimal amount = getBigDecimal(access_amount.getValue(),rs);
        tradeDetail.setAmount(amount);
        //金额
        BigDecimal amountMoney = getBigDecimal(access_acountmoney.getValue(),rs);
        tradeDetail.setAmountMoney(amountMoney);
        //平均价格
        if(!amount.equals(0)){
            tradeDetail.setUnitPrice(BigDecimal.valueOf(0));
        } else {
            tradeDetail.setUnitPrice(amountMoney.divide(amount,2, BigDecimal.ROUND_HALF_UP));
        }

        //年
        tradeDetail.setYear(year);
        //月
        tradeDetail.setMonth(month);
        //年月
        tradeDetail.setYearMonth(year + yearmonth_split.value() + (month < 10 ? "0" + month : month));
        return tradeDetail;
    }

    private String getString(String columnName, ResultSet rs) {
        try {
            return rs.getString(columnName);
        } catch (Exception e) {
            throw new RuntimeException("Can't found " + access_product_code.getValue()
                    + " column in the importing access file", e);
        }
    }

    private BigDecimal getBigDecimal(String columnName, ResultSet rs) {
        try {
            return rs.getBigDecimal(columnName);
        } catch (Exception e) {
            throw new RuntimeException("Can't found " + access_product_code.getValue()
                    + " column in the importing access file", e);
        }
    }


    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        T t = null;
        try {
            t = tClass.newInstance();
            this.setTraddDetail(t, rs, null, null);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return t;
    }
}
