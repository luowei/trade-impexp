package com.oilchem.trade.dao;

import com.oilchem.trade.domain.ComplanyType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午4:05
 * To change this template use File | Settings | File Templates.
 */
//@RepositoryDefinition(domainClass = ComplanyType.class,idClass = Integer.class)
public interface ComplanyTypeDao extends CrudRepository<ComplanyType,Long>{

    ComplanyType findByComplanyType(String complanyType);

}
