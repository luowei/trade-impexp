package com.oilchem.trade.dao.map;

import com.oilchem.trade.domain.abstrac.AbstractTradeDetail;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * detail与access表中字段映射
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-6
 * Time: 下午1:31
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTradeDetailRowMapper<T> implements RowMapper<T> {

    public AbstractTradeDetail setTraddDetail(AbstractTradeDetail tradeDetail,
         ResultSet rs)  throws SQLException{
        //产品代码
        tradeDetail.setProductCode(rs.getString(""));
        //产品名称
        tradeDetail.setProductName(rs.getString(""));
        //企业性质
        tradeDetail.setComplanyType(rs.getString(""));
        //贸易方式
        tradeDetail.setTradeType(rs.getString(""));
        //城市
        tradeDetail.setCity(rs.getString(""));
        //产销国家
        tradeDetail.setCountry(rs.getString("PGCountryName"));
        //出口海关
        tradeDetail.setCustoms(rs.getString(""));
        //运输方式
        tradeDetail.setTransportation(rs.getString(""));
        //单位
        tradeDetail.setUnit(rs.getString(""));
        //数量
        tradeDetail.setAmount(rs.getBigDecimal(""));
        //金额
        tradeDetail.setAmountMoney(rs.getBigDecimal(""));
        return tradeDetail;
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {
        return null;
    }
}
