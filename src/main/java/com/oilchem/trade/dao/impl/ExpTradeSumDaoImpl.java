package com.oilchem.trade.dao.impl;

import com.oilchem.trade.dao.ExpTradeSumDaoCustom;
import com.oilchem.trade.domain.ExpTradeSum;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-12
 * Time: 下午3:09
 * To change this template use File | Settings | File Templates.
 */
@Resource   @Transactional
public class ExpTradeSumDaoImpl
        extends BaseDaoImpl<ExpTradeSum>
        implements ExpTradeSumDaoCustom {

    public Integer delWithYearMonthRecord(Integer year, Integer month, Class<ExpTradeSum> idEntityClz) {
        return super.delWithYearMonthRecord(year,month, idEntityClz);
    }

    public Long countWithYearMonth(Integer year, Integer month, Class<ExpTradeSum> idEntityClz) {
        return super.countWithYearMonth(year,month, idEntityClz);
    }
}
