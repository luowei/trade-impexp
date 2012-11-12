package com.oilchem.trade.dao;

import com.oilchem.trade.domain.ExpTradeDetail;
import com.oilchem.trade.domain.ExpTradeSum;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

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
}
