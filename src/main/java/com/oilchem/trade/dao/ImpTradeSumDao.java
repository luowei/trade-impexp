package com.oilchem.trade.dao;

import com.oilchem.trade.domain.ImpTradeSum;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

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
}
