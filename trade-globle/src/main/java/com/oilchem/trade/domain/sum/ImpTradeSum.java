package com.oilchem.trade.domain.sum;

import com.oilchem.trade.domain.abstrac.TradeSum;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午2:48
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_import_sum")
public class ImpTradeSum extends TradeSum {

    public ImpTradeSum() {
    }

    public ImpTradeSum(String productName) {
        super(productName);
    }

    public ImpTradeSum(Integer year,
                       Integer month,
                       String yearMonth,
                       String productType) {
        super(year, month,yearMonth, productType);
    }

    public ImpTradeSum(TradeSum tradeSum) {
        this.setAvgPriceMonth(tradeSum.getAvgPriceMonth())
                .setAvgPriceSum(tradeSum.getAvgPriceSum())
                .setMoneyMonth(tradeSum.getMoneyMonth())
                .setMoneySum(tradeSum.getMoneySum())
                .setMonth(tradeSum.getMonth())
                .setYear(tradeSum.getYear())
                .setNumMonth(tradeSum.getNumMonth())
                .setNumSum(tradeSum.getNumSum())
                .setPm(tradeSum.getPm())
                .setPy(tradeSum.getPy())
                .setPq(tradeSum.getPq())
                .setProductName(tradeSum.getProductName())
                .setSumType(tradeSum.getSumType())
                .setYearMonth(tradeSum.getYearMonth());
    }
}
