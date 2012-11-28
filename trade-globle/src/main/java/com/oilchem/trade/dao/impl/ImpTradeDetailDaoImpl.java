package com.oilchem.trade.dao.impl;

import com.oilchem.trade.dao.ImpTradeDetailDaoCustom;
import com.oilchem.trade.domain.ImpTradeDetail;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-12
 * Time: 下午3:11
 * To change this template use File | Settings | File Templates.
 */
@Resource   @Transactional
public class ImpTradeDetailDaoImpl
        extends BaseDaoImpl<ImpTradeDetail>
        implements ImpTradeDetailDaoCustom {

    @Override
    public Integer delWithYearMonthRecord(Integer year, Integer month,
                                          Class<ImpTradeDetail> idEntityClz) {
        return super.delWithYearMonthRecord(year,month, idEntityClz);
    }

    @Override
    public Long countWithYearMonth(Integer year, Integer month,
                                   Class<ImpTradeDetail> idEntityClz) {
        return super.countWithYearMonth(year,month, idEntityClz);
    }
}
