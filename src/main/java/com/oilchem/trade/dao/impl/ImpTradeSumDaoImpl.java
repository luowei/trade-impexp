package com.oilchem.trade.dao.impl;

import com.oilchem.trade.dao.ImpTradeSumDaoCustom;
import com.oilchem.trade.domain.ImpTradeSum;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-12
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
@Resource
public class ImpTradeSumDaoImpl
        extends BaseDaoImpl<ImpTradeSum>
        implements ImpTradeSumDaoCustom {

    @Override
    public Boolean delWithYearMonthRecord(Integer year, Integer month) {
        return super.delWithYearMonthRecord(year,month);
    }

    @Override
    public Integer countWithYearMonth(Integer year, Integer month, Class clazz) {
        return super.countWithYearMonth(year,month, clazz);
    }
}
