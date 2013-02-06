package com.oilchem.trade.dao;

import com.oilchem.trade.dao.custom.ImpTradeSumDaoCustom;
import com.oilchem.trade.domain.sum.ImpTradeSum;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午5:02
 * To change this template use File | Settings | File Templates.
 */
public interface ImpTradeSumDao extends CrudRepository<ImpTradeSum,Long>,
        JpaSpecificationExecutor<ImpTradeSum>,
        ImpTradeSumDaoCustom {

    @Modifying
    @Transactional
    @Query("delete from ImpTradeSum t where t.year = :year and t.month = :month and t.sumType=:sumType")
    void delRepeatImpTradeSum(@Param("year") Integer year, @Param("month") Integer month, @Param("sumType") String sumType);


    List<ImpTradeSum> findByProductNameAndYearMonth(String productName,String yearMonth);

    List<ImpTradeSum> findByIdAndYearMonth(Long aLong, String yearMonth);

    @Query("select count(*) from ImpTradeSum where year=?1 and month=?2 and sumType=?3 ")
    Long countByYearAndMonthAndSumType(Integer year, Integer month, String sumType);
}
