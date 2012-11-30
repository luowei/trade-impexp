package com.oilchem.trade.domain;

import com.oilchem.trade.domain.abstrac.TradeDetail;

import javax.persistence.Entity;
import javax.persistence.Table;

import static com.oilchem.trade.bean.DocBean.Config.yearmonth_split;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午2:45
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_export_detail")
public class ExpTradeDetail extends TradeDetail {
    public ExpTradeDetail() {
    }

    public ExpTradeDetail(TradeDetail tradeDetail) {
        this.setYear(tradeDetail.getYear())
                .setMonth(tradeDetail.getMonth())
                .setYearMonth(tradeDetail.getYear() + yearmonth_split.value()
                        + (tradeDetail.getMonth() != null && tradeDetail.getMonth() < 10 ?
                        "0" + tradeDetail.getMonth() : tradeDetail.getMonth()))
                .setAmountMoney(tradeDetail.getAmountMoney())
                .setCity(tradeDetail.getCity())
                .setCompanyType(tradeDetail.getCompanyType())
                .setCountry(tradeDetail.getCountry())
                .setCustoms(tradeDetail.getCustoms())
                .setProductCode(tradeDetail.getProductCode())
                .setProductName(tradeDetail.getProductName())
                .setTradeType(tradeDetail.getTradeType())
                .setTransportation(tradeDetail.getTransportation())
                .setAmount(tradeDetail.getAmount());
    }
}
