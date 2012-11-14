package com.oilchem.trade.dao;

import com.oilchem.trade.domain.abstrac.IdEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午4:18
 * To change this template use File | Settings | File Templates.
 */
public interface BaseDao<T extends IdEntity> {


    /**
     * 删除指定年月的记录
     * @param year year
     * @param month month
     * @return
     */
    Boolean delWithYearMonthRecord(Integer year,Integer month);

    /**
     * 查找指年月的数量
     * @param year year
     * @param month month
     * @return
     */
    Integer countWithYearMonth(Integer year, Integer month);

}
