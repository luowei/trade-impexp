package com.oilchem.trade.dao;

import com.oilchem.trade.domain.abstrac.IdEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午4:18
 * To change this template use File | Settings | File Templates.
 */
public interface BaseDao<T extends IdEntity> extends Repository<T,IdEntity>{

    T save(T t);

    T findOne(Serializable id);

    List<T> findAll();

    Long count();
}
