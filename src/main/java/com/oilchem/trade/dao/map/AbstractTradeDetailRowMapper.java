package com.oilchem.trade.dao.map;

import com.oilchem.trade.util.GenericsUtils;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.oilchem.trade.util.ConfigUtil.AccessField.*;
import static com.oilchem.trade.util.ConfigUtil.Config.yearmonth_split;

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
        tradeDetail.setProductCode(rs.getString(access_product_code.value()));
        //产品名称
        tradeDetail.setProductName(rs.getString(access_product_name.value()));
        //企业性质
        tradeDetail.setCompanyType(rs.getString(access_company_type.value()));
        //贸易方式
        tradeDetail.setTradeType(rs.getString(access_trade_type.value()));
        //城市
        tradeDetail.setCity(rs.getString(access_city.value()));
        //产销国家
        tradeDetail.setCountry(rs.getString(access_country.value()));
        //出口海关
        tradeDetail.setCustoms(rs.getString(access_customs.value()));
        //运输方式
        tradeDetail.setTransportation(rs.getString(access_transportation.value()));
        //单位
        tradeDetail.setUnit(rs.getString(access_unit.value()));
        //数量
        tradeDetail.setAmount(rs.getBigDecimal(access_amount.value()));
        //金额
        tradeDetail.setAmountMoney(rs.getBigDecimal(access_acountmoney.value()));
        //年
        tradeDetail.setYear(year);
        //月
        tradeDetail.setMonth(month);
        //年月
        tradeDetail.setYearMonth(year+ yearmonth_split.value() +(month<10 ? "0"+month:month));
        return tradeDetail;
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
