package com.oilchem.trade.dao;

import com.oilchem.trade.domain.ImpTradeDetail;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午4:59
 * To change this template use File | Settings | File Templates.
 */
public interface ImpTradeDetailDao extends CrudRepository<ImpTradeDetail,Long>,
        BaseDao<ImpTradeDetail>{

//    /**
//     * 查义指定年月的记录数量
//     * @param yearMonth
//     * @return
//     */
//    Integer countWithYearMonth(Date yearMonth);
//
//    /**
//     * 删除指定年月的记录数量
//     * @param yearMonth
//     * @return
//     */
//    Boolean delWithYearMonthRecord(Date yearMonth);
}
