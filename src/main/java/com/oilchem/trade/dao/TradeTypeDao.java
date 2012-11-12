package com.oilchem.trade.dao;

import com.oilchem.trade.domain.TradeType;
import org.springframework.data.repository.CrudRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午5:05
 * To change this template use File | Settings | File Templates.
 */
public interface TradeTypeDao extends CrudRepository<TradeType, Long>{

    TradeType findByTradeType(String tradeType);
}
