package com.oilchem.trade.dao.custom.impl;

import com.oilchem.trade.dao.custom.ImpTradeSumDaoCustom;
import com.oilchem.trade.domain.sum.ImpTradeSum;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-12
 * Time: 下午3:13
 * To change this template use File | Settings | File Templates.
 */
@Resource     @Transactional
public class ImpTradeSumDaoImpl
        extends BaseDaoImpl<ImpTradeSum>
        implements ImpTradeSumDaoCustom {

    @Override
    public Integer delWithYearMonthRecord(Integer year, Integer month,
                                          Class<ImpTradeSum> idEntityClz) {
        return super.delWithYearMonthRecord(year,month, idEntityClz);
    }

    @Override
    public Long countWithYearMonth(Integer year, Integer month, Class<ImpTradeSum> idEntityClz) {
        return super.countWithYearMonth(year,month, idEntityClz);
    }
}
