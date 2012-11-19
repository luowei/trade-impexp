package com.oilchem.trade.dao;

import com.oilchem.trade.domain.ImpTradeSum;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午5:02
 * To change this template use File | Settings | File Templates.
 */
public interface ImpTradeSumDao extends CrudRepository<ImpTradeSum,Long>,
        JpaSpecificationExecutor<ImpTradeSum>,
        ImpTradeSumDaoCustom{

    @Modifying
    @Transactional
    @Query("delete from ExpTradeSum t where t.year = :year and t.month = :month")
    void delRepeatImpTradeSum(@Param("year") Integer year,@Param("month") Integer month);
}
