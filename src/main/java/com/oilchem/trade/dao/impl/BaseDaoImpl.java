package com.oilchem.trade.dao.impl;

import com.oilchem.common.util.GenericsUtils;
import com.oilchem.trade.dao.BaseDao;
import com.oilchem.trade.domain.abstrac.IdEntity;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-7
 * Time: 上午10:13
 * To change this template use File | Settings | File Templates.
 */
@Resource
public class BaseDaoImpl<T extends IdEntity> implements BaseDao<T> {

    private Class<T> entityClass = GenericsUtils.getSuperClassGenricType(getClass());

    @PersistenceContext
    private EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    /**
     * 根据年月删除记录
     * @param year year
     * @param month month
     * @return
     */
    public Boolean delWithYearMonthRecord(Integer year, Integer month) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 查出指定年月的记录数
     * @param year year
     * @param month month
     * @return
     */
    public Integer countWithYearMonth(Integer year, Integer month) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
