package com.oilchem.trade.dao;

import com.oilchem.trade.domain.ExpTradeDetail;
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
 * Time: 下午4:55
 * To change this template use File | Settings | File Templates.
 */
public interface ExpTradeDetailDao extends CrudRepository<ExpTradeDetail,Long>,
        JpaSpecificationExecutor<ExpTradeDetail>,
        ExpTradeDetailDaoCustom {

    @Modifying
    @Transactional
    @Query("delete from ExpTradeDetail t where t.year = ?1 and t.month = ?2")
    void delRepeatImpTradeDetail(Integer year,Integer month);

}
