package com.oilchem.trade.dao.map;

import com.oilchem.common.util.GenericsUtils;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import static com.oilchem.trade.config.MapperConfig.*;
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
         ResultSet rs)  throws SQLException{
        //产品代码
        tradeDetail.setProductCode(rs.getString(PRODUCT_CODE));
        //产品名称
        tradeDetail.setProductName(rs.getString(PRODUCT_NAME));
        //企业性质
        tradeDetail.setCompanyType(rs.getString(COMPANY_TYPE));
        //贸易方式
        tradeDetail.setTradeType(rs.getString(TRADE_TYPE));
        //城市
        tradeDetail.setCity(rs.getString(CITY));
        //产销国家
        tradeDetail.setCountry(rs.getString(COUNTRY));
        //出口海关
        tradeDetail.setCustoms(rs.getString(CUSTOMS));
        //运输方式
        tradeDetail.setTransportation(rs.getString(TRANSPORTATION));
        //单位
        tradeDetail.setUnit(rs.getString(UNIT));
        //数量
        tradeDetail.setAmount(rs.getBigDecimal(AMOUNT));
        //金额
        tradeDetail.setAmountMoney(rs.getBigDecimal(ACOUNTMONEY));
        return tradeDetail;
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        T t = null;
        try {
            t = tClass.newInstance();
            this.setTraddDetail(t,rs);
        } catch (InstantiationException e) {
            log.error(e.getMessage(),e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(),e);
        }
        return t;
    }
}
