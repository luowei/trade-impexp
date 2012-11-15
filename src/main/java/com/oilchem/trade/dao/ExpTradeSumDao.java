package com.oilchem.trade.dao;

import com.oilchem.trade.domain.ExpTradeDetail;
import com.oilchem.trade.domain.ExpTradeSum;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午4:57
 * To change this template use File | Settings | File Templates.
 */
public interface ExpTradeSumDao extends CrudRepository<ExpTradeSum,Long>,
        JpaSpecificationExecutor<ExpTradeSum>,
        ExpTradeSumDaoCustom {

    @Modifying
    @Transactional
    @Query("delete from ExpTradeSum t where t.year = :year and t.month = :month")
    void delRepeatExpTradeSum(@Param("year") Integer year,@Param("month") Integer month);
}
