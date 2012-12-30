package com.oilchem.trade.dao.count;

import com.oilchem.trade.domain.count.ExpDetailCount;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-7
 * Time: 上午10:44
 * To change this template use File | Settings | File Templates.
 */
public interface ExpDetailCountDao extends PagingAndSortingRepository<ExpDetailCount, Long>,
        JpaSpecificationExecutor<ExpDetailCount> {

    @Modifying
    @Transactional
    @Query(value = " delete from " +
            " t_export_detail_count " +
            "where " +
            " year_month = ?1  ",nativeQuery = true)
    void deleteByYearMonth(String yearMonth);


    @Modifying
    @Transactional
    @Query(value = " insert into  " +
            " t_export_detail_count(col_year,col_month,year_month, product_code , product_name , num, unit , money, avg_price) " +
            "select " +
            " convert(int,substring(ltrim(rtrim(year_month)), 1, 4)) as col_year, " +
            " convert(int,substring(ltrim(rtrim(year_month)), 6, 2)) as col_month, "+
            " case " +
            "len(substring(ltrim(rtrim(year_month)),6,2)) " +
            "when 1 then  substring(ltrim(rtrim(year_month)),1,5) " +
            "        +'0'+substring(ltrim(rtrim(year_month)),6,2) " +
            "else ltrim(rtrim(year_month)) " +
            "end as year_month ," +
            " product_code , product_name , " +
            "round(sum(amount),2) as num, unit , round(sum(amount_money),2) as money, " +
            "case sum(amount) " +
            "     when 0 then 0 " +
            "     else round(sum(amount_money)/sum(amount),2) " +
            "end as avg_price " +
            "from  t_export_detail " +
            " where year_month= ?1 "+
            "group by  year_month,product_code,product_name,unit  " ,nativeQuery = true)
    void insertByYearMonth(String yearMonth);


    @Modifying
    @Transactional
    @Query(value = " insert into  " +
            " t_export_detail_count(col_year,col_month,year_month, product_code , product_name , num, unit , money, avg_price) " +
            "select " +
            " convert(int,substring(ltrim(rtrim(year_month)), 1, 4)) as col_year, " +
            " convert(int,substring(ltrim(rtrim(year_month)), 6, 2)) as col_month, " +
            " case " +
            "len(substring(ltrim(rtrim(year_month)),6,2)) " +
            "when 1 then  substring(ltrim(rtrim(year_month)),1,5) " +
            "        +'0'+substring(ltrim(rtrim(year_month)),6,2) " +
            "else ltrim(rtrim(year_month)) " +
            "end as year_month ," +
            " product_code , product_name , " +
            "round(sum(amount),2) as num, unit , round(sum(amount_money),2) as money, " +
            "case sum(amount) " +
            "     when 0 then 0 " +
            "     else round(sum(amount_money)/sum(amount),2) " +
            "end as avg_price " +
            "from  t_export_detail " +
            "group by  year_month,product_code,product_name,unit  " ,nativeQuery = true)
    void insertAll();


    List<ExpDetailCount> findByProductCodeAndYearMonth(String code, String yearMonth);

    List<ExpDetailCount> findByProductNameAndYearMonth(String name, String yearMonth);
}
