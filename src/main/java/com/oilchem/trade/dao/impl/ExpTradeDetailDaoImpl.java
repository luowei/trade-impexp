package com.oilchem.trade.dao.impl;

import com.oilchem.trade.dao.ExpTradeDetailDaoCustom;
import com.oilchem.trade.domain.ExpTradeDetail;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-12
 * Time: 下午2:04
 * To change this template use File | Settings | File Templates.
 */
@Resource
public class ExpTradeDetailDaoImpl
        extends BaseDaoImpl<ExpTradeDetail>
        implements ExpTradeDetailDaoCustom {

    public Boolean delWithYearMonthRecord(Integer year, Integer month) {
        return super.delWithYearMonthRecord(year,month);
    }

    public Integer countWithYearMonth(Integer year, Integer month) {
        return super.countWithYearMonth(year,month);
    }
}
