package com.oilchem.trade.dao;

import com.oilchem.trade.domain.abstrac.IdEntity;

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
     *
     *
     * @param year year
     * @param month month
     * @param idEntityClz
     * @return
     */
    Integer delWithYearMonthRecord(Integer year, Integer month, Class<T> idEntityClz);

    /**
     * 查找指年月的数量
     *
     *
     *
     *
     * @param year year
     * @param month month
     * @param idEntityClz
     * @return
     */
    Long countWithYearMonth(Integer year, Integer month, Class<T> idEntityClz);

      void excuteSql(String sql);

}
