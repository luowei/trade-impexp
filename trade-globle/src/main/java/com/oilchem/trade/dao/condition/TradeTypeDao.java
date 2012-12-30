package com.oilchem.trade.dao.condition;

import com.oilchem.trade.domain.condition.TradeType;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午5:05
 * To change this template use File | Settings | File Templates.
 */
public interface TradeTypeDao extends PagingAndSortingRepository<TradeType, Long> {

    TradeType findByTradeType(String tradeType);
}
