package com.oilchem.trade.dao;

import com.oilchem.trade.domain.ImpDetailCount;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-7
 * Time: 上午10:41
 * To change this template use File | Settings | File Templates.
 */
public interface ImpDetailCountDao extends PagingAndSortingRepository<ImpDetailCount, Long>,
        JpaSpecificationExecutor<ImpDetailCount> {

    @Modifying
    @Transactional
    @Query(value = " delete from " +
            " t_import_detail_count " +
            "where " +
            " year_month = ?1  ",nativeQuery = true)
    void deleteByYearMonth(String yearMonth);

    @Modifying
    @Transactional
    @Query(value = " insert into  " +
            " t_import_detail_count(col_year,col_month,year_month, product_code , product_name , num, unit , money, avg_price) " +
            "select " +
            " substring(convert(char(19),ltrim(rtrim(year_month)),20), 1, 4) as col_year, " +
            " substring(convert(char(19),ltrim(rtrim(year_month)),20), 6, 2) as col_month, "+
            " ltrim(rtrim(year_month)) as year_month  ," +
            " product_code , product_name , " +
            " round(sum(amount),2) as num, unit , round(sum(amount_money),2) as money, " +
            "case sum(amount) " +
            "     when 0 then 0 " +
            "     else round(sum(amount_money)/sum(amount),2) " +
            "end as avg_price " +
            "from  t_import_detail " +
            " where year_month= ?1 "+
            "group by  year_month,product_code,product_name,unit  " ,nativeQuery = true)
    void insertByYearMonth(String yearMonth);


    @Modifying
    @Transactional
    @Query(value = " insert into  " +
            " t_import_detail_count(col_year,col_month,year_month, product_code , product_name , num, unit , money, avg_price) " +
            "select " +
            " substring(convert(char(19),ltrim(rtrim(year_month)),20), 1, 4) as col_year, " +
            " substring(convert(char(19),ltrim(rtrim(year_month)),20), 6, 2) as col_month, "+
            " ltrim(rtrim(year_month)) as year_month ," +
            " product_code , product_name , " +
            "round(sum(amount),2) as num, unit , round(sum(amount_money),2) as money, " +
            "case sum(amount) " +
            "     when 0 then 0 " +
            "     else round(sum(amount_money)/sum(amount),2) " +
            "end as avg_price " +
            "from  t_import_detail " +
            "group by  year_month,product_code,product_name,unit  " ,nativeQuery = true)
    void insertAll();


}


