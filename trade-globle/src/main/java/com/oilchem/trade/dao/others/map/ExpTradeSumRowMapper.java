package com.oilchem.trade.dao.others.map;

import com.oilchem.trade.domain.sum.ExpTradeSum;
import jxl.Sheet;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-8
 * Time: 上午10:02
 * To change this template use File | Settings | File Templates.
 */
public class ExpTradeSumRowMapper extends AbstractTradeSumRowMapper<ExpTradeSum> {
    public ExpTradeSumRowMapper(int rowIdx, ExpTradeSum expTradeSum, Sheet sheet) {
        super(rowIdx, expTradeSum, sheet);
    }

    @Override
    public ExpTradeSum getMappingInstance() {
        return super.getMappingInstance();
    }
}
