package com.oilchem.trade.domain.detail;

import com.oilchem.trade.domain.abstrac.TradeDetail;

import javax.persistence.Entity;
import javax.persistence.Table;

import static com.oilchem.trade.bean.DocBean.Config.yearmonth_split;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午2:43
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_import_detail")
public class ImpTradeDetail extends TradeDetail {

    public ImpTradeDetail() {
    }

    public ImpTradeDetail(String productName) {
        super(productName);
    }

    public ImpTradeDetail(TradeDetail tradeDetail) {
        this.setYear(tradeDetail.getYear())
                .setMonth(tradeDetail.getMonth())
                .setYearMonth(tradeDetail.getYear() + yearmonth_split.value()
                        + (tradeDetail.getMonth()!=null && tradeDetail.getMonth() < 10 ?
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
