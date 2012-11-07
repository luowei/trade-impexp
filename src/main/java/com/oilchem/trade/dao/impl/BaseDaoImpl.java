package com.oilchem.trade.dao.impl;

import com.oilchem.common.util.GenericsUtils;
import com.oilchem.trade.dao.BaseDao;
import com.oilchem.trade.domain.abstrac.IdEntity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-7
 * Time: 上午10:13
 * To change this template use File | Settings | File Templates.
 */
public class BaseDaoImpl<T extends IdEntity> implements BaseDao<T> {

    private Class<T> entityClass = GenericsUtils.getSuperClassGenricType(getClass());

    public Integer countWithYearMonth(Date yearMonth) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Boolean delWithYearMonthRecord(Date yearMonth) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
