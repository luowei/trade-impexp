package com.oilchem.trade.dao.impl;

import com.oilchem.trade.dao.ExpTradeDetailDaoCustom;
import com.oilchem.trade.domain.ExpTradeDetail;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-12
 * Time: 下午2:04
 * To change this template use File | Settings | File Templates.
 */
@Resource    @Transactional
public class ExpTradeDetailDaoImpl
        extends BaseDaoImpl<ExpTradeDetail>
        implements ExpTradeDetailDaoCustom {

    public Integer delWithYearMonthRecord(Integer year, Integer month,
                                          Class<ExpTradeDetail> idEntityClz) {
        return super.delWithYearMonthRecord(year,month, idEntityClz);
    }

    public Long countWithYearMonth(Integer year, Integer month,
                                   Class<ExpTradeDetail> idEntityClz) {
        return super.countWithYearMonth(year,month, idEntityClz);
    }
}
