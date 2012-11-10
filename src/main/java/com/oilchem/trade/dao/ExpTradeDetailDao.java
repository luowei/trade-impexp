package com.oilchem.trade.dao;

import com.oilchem.trade.domain.ExpTradeDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午4:55
 * To change this template use File | Settings | File Templates.
 */
public interface ExpTradeDetailDao extends CrudRepository<ExpTradeDetail, Long>,
        BaseDao<ExpTradeDetail> {
//    /**
//     * 查找指年月的明细数量
//     * @param yearMonth
//     * @return
//     */
//    Integer countWithYearMonth(Date yearMonth);
//
//    /**
//     * 删除指定年月的出口明细记录
//     * @param yearMonth
//     * @return
//     */
//    Boolean delWithYearMonthRecord(Date yearMonth);
}
