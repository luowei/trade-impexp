package com.oilchem.trade.dao;

import com.oilchem.trade.bean.ProductCount;
import com.oilchem.trade.dao.custom.ExpTradeDetailDaoCustom;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.domain.detail.ExpTradeDetail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    List<ExpTradeDetail> findByProductCodeAndYearMonth(String productCode, String yearMonth);

//    @Query("select productCode,productName,?3,sum(amount) as num,sum(amountMoney) as money " +
//            " from ExpTradeDetail where productCode=?1 and yearMonth=?2 group by ?3")
//    List<ProductCount> getProductCount(String productCode, String yearMonth, String condition);
}
