package com.oilchem.trade.dao.others.map;

import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.util.GenericsUtils;
import org.apache.commons.lang3.StringUtils;
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
        //产品类型码
        String code = tradeDetail.getProductCode().substring(0,1).equals("0")?tradeDetail.getProductCode().substring(1,2)
                :tradeDetail.getProductCode().substring(0,2);
        tradeDetail.setTypeCode(Integer.parseInt(code));
        //企业性质
        tradeDetail.setCompanyType(getString(access_company_type.getValue(),rs));
        //贸易方式
        tradeDetail.setTradeType(getString(access_trade_type.getValue(),rs));
        //城市
        tradeDetail.setCity(getString(access_city.getValue(),rs));
        //产销国家
        tradeDetail.setCountry(getString(access_country.getValue(),rs));
        //出口海关
        String customs = getString(access_customs.getValue(),rs);
        if(!customs.endsWith("海关")){
            customs = customs+"海关";
        }
        tradeDetail.setCustoms(customs);
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
        if(amount.intValue()==0){
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
            String val = rs.getString(columnName);
            return val==null?"":val.trim();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            throw new RuntimeException("在access文件中找不到 " + columnName+" 的值", e);
        }
    }

    private BigDecimal getBigDecimal(String columnName, ResultSet rs){
        String pcode=null;
        try {
            String strVal = rs.getString(columnName);
            if(StringUtils.isBlank(strVal)){
                strVal = "0";
            }
//            BigDecimal val = rs.getBigDecimal(columnName);
//            return val==null?BigDecimal.valueOf(0):val;
            return BigDecimal.valueOf(Double.valueOf(strVal));
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
