package com.oilchem.trade.dao;

import com.oilchem.trade.domain.DetailType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-6
 * Time: 上午10:06
 * To change this template use File | Settings | File Templates.
 */
public interface DetailTypeDao  extends PagingAndSortingRepository<DetailType, Long> {

    @Transactional
    @Modifying
    @Query(value = "update t_import_detail   " +
            "set product_type = ( " +
            "select detail_type  " +
            "from t_detail_type  " +
            "where t_detail_type.code = t_import_detail.type_code " +
            ")  " +
            "where  " +
            "t_import_detail.year_month = ?1",nativeQuery = true)
    void updateImpDetailTypeWithYearMonth(String yearMonth);


    @Transactional
    @Modifying
    @Query(value = "update t_export_detail   " +
            "set product_type = ( " +
            "select detail_type  " +
            "from t_detail_type  " +
            "where t_detail_type.code = t_export_detail.type_code " +
            ")  " +
            "where  " +
            "t_export_detail.year_month = ?1",nativeQuery = true)
    void updateExpDetailTypeWithYearMonth(String yearMonth);
}
