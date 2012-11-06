package com.oilchem.trade.domain;

import com.oilchem.trade.domain.abstrac.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 贸易方式
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午12:20
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_trade_type")
public class TradeType extends IdEntity implements Serializable {
    @Column(name = "trade_type")
    private String tradeType;

    public TradeType() {
    }

    public TradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }
}
