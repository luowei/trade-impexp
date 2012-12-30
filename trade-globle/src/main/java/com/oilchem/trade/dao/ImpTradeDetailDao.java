package com.oilchem.trade.dao;

import com.oilchem.trade.bean.ProductCount;
import com.oilchem.trade.dao.custom.ImpTradeDetailDaoCustom;
import com.oilchem.trade.domain.detail.ImpTradeDetail;
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
 * Time: 下午4:59
 * To change this template use File | Settings | File Templates.
 */
public interface ImpTradeDetailDao extends CrudRepository<ImpTradeDetail, Long>,
        JpaSpecificationExecutor<ImpTradeDetail>,
        ImpTradeDetailDaoCustom {

    @Modifying
    @Transactional
    @Query("delete from ImpTradeDetail  t where t.year = ?1 and t.month = ?2")
    void delRepeatImpTradeDetail(Integer year, Integer month);

//    @Query(value = "from ImpTradeDetail where :wheres")
//    Page<ImpTradeDetail> pageByCriteria(@Param("wheres")String wheres,Pageable pageable);

    List<ImpTradeDetail> findByProductCodeAndYearMonth(String productCode, String yearMonth);

//    @Query("select yearMonth,productCode,productName,companyType,sum(amount) as num,unit,sum(amountMoney) as money " +
//            " from ImpTradeDetail where productCode=?1 and yearMonth=?2 group by yearMonth,productCode,productName,unit,companyType")
//    List<ProductCount> getCompanyTypeProductCount(String productCode, String yearMonth);
//
//    @Query("select yearMonth,productCode,productName,tradeType,sum(amount) as num,unit,sum(amountMoney) as money " +
//            " from ImpTradeDetail where productCode=?1 and yearMonth=?2 group by yearMonth,productCode,productName,unit,tradeType")
//    List<ProductCount> getTradeTypeProductCount(String productCode, String yearMonth);
//
//    @Query("select yearMonth,productCode,productName,customs,sum(amount) as num,unit,sum(amountMoney) as money " +
//            " from ImpTradeDetail where productCode=?1 and yearMonth=?2 group by yearMonth,productCode,productName,unit,customs")
//    List<ProductCount> getCustomsProductCount(String productCode, String yearMonth);
//
//    @Query("select yearMonth,productCode,productName,transportation,sum(amount) as num,unit,sum(amountMoney) as money " +
//            " from ImpTradeDetail where productCode=?1 and yearMonth=?2 group by yearMonth,productCode,productName,unit,transportation")
//    List<ProductCount> getTransportationProductCount(String productCode, String yearMonth);
//
//    @Query("select yearMonth,productCode,productName,country,sum(amount) as num,unit,sum(amountMoney) as money " +
//            " from ImpTradeDetail where productCode=?1 and yearMonth=?2 group by yearMonth,productCode,productName,unit,country")
//    List<ProductCount> getCountryProductCount(String productCode, String yearMonth);
//
//    @Query("select yearMonth,productCode,productName,city,sum(amount) as num,unit,sum(amountMoney) as money " +
//            " from ImpTradeDetail where productCode=?1 and yearMonth=?2 group by yearMonth,productCode,productName,unit,city")
//    List<ProductCount> getCityProductCount(String productCode, String yearMonth);

}
