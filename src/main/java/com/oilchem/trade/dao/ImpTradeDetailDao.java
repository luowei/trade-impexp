package com.oilchem.trade.dao;

import com.oilchem.trade.domain.ImpTradeDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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

    @Query(value = " select o from ImpTradeDetail o where " +
            "    o.productCode=:productCode and" +
            "    o.city=:city and" +
            "    o.country=:country and" +
            "    o.customs=:customs and" +
            "    o.tradeType=:tradeType and" +
            "    o.transportation=:transportation and" +
            "    o.companyType=:companyType and" +
            "    o.year >=:lowYear and" +
            "    o.month >=:lowMonth and" +
            "    o.year <:highYear and" +
            "    o.month <:highMonth and" +
            "    o.productName like :productName")
    Page<ImpTradeDetail> pageByCriteria(
            @Param("productCode") String productCode,
            @Param("city") String city,
            @Param("country") String country,
            @Param("customs") String customs,
            @Param("tradeType") String tradeType,
            @Param("transportation") String transportation,
            @Param("companyType") String companyType,
            @Param("lowYear") Integer lowYear,
            @Param("lowMonth") Integer lowMonth,
            @Param("highYear") Integer highYear,
            @Param("highMonth") Integer highMonth,
            @Param("productName") String productName,
            Pageable pageable);

}
