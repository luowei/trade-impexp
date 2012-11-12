package com.oilchem.trade.dao.impl;

import com.oilchem.trade.dao.ImpTradeDetailDaoCustom;
import com.oilchem.trade.domain.ImpTradeDetail;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-12
 * Time: 下午3:11
 * To change this template use File | Settings | File Templates.
 */
@Resource
public class ImpTradeDetailDaoImpl
        extends BaseDaoImpl<ImpTradeDetail>
        implements ImpTradeDetailDaoCustom {

    @Override
    public Boolean delWithYearMonthRecord(Integer year, Integer month) {
        return super.delWithYearMonthRecord(year,month);
    }

    @Override
    public Integer countWithYearMonth(Integer year, Integer month) {
        return super.countWithYearMonth(year,month);
    }
}
