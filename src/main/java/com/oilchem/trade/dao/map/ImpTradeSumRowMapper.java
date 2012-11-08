package com.oilchem.trade.dao.map;

import com.oilchem.trade.domain.ImpTradeSum;
import jxl.Sheet;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-8
 * Time: 上午10:00
 * To change this template use File | Settings | File Templates.
 */
public class ImpTradeSumRowMapper extends AbstractTradeSumRowMapper<ImpTradeSum> {
    public ImpTradeSumRowMapper(int rowIdx, ImpTradeSum impTradeSum, Sheet sheet) {
        super(rowIdx, impTradeSum, sheet);
    }

    @Override
    public ImpTradeSum getMappingInstance() {
        return super.getMappingInstance();
    }
}
