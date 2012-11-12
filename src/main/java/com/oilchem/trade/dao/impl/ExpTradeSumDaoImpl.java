package com.oilchem.trade.dao.impl;

import com.oilchem.trade.dao.ExpTradeSumDaoCustom;
import com.oilchem.trade.domain.ExpTradeSum;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-12
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
@Resource
public class ExpTradeSumDaoImpl
        extends BaseDaoImpl<ExpTradeSum>
        implements ExpTradeSumDaoCustom {

    public Boolean delWithYearMonthRecord(Integer year, Integer month) {
        return super.delWithYearMonthRecord(year,month);
    }

    public Integer countWithYearMonth(Integer year, Integer month) {
        return super.countWithYearMonth(year,month);
    }
}
