package com.oilchem.trade.dao;

import com.oilchem.trade.domain.ExpTradeDetail;
import com.oilchem.trade.domain.ImpTradeDetail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
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
        JpaSpecificationExecutor<ImpTradeDetail>,
        ImpTradeDetailDaoCustom{

}
