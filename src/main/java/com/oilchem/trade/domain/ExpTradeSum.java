package com.oilchem.trade.domain;

import com.oilchem.trade.domain.abstrac.TradeSum;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午2:50
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_export_sum")
public class ExpTradeSum extends TradeSum {
    public ExpTradeSum() {
    }

    public ExpTradeSum(Integer year,
                       Integer month,
                       String yearMonth,
                       String productType) {
        super(year, month,yearMonth, productType);
    }
}
