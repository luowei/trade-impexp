package com.oilchem.trade.dao;

import com.oilchem.trade.domain.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午3:45
 * To change this template use File | Settings | File Templates.
 */
//@RepositoryDefinition(domainClass = City.class,idClass = Integer.class)
public interface CityDao extends CrudRepository<City,Long>,BaseDao<City> {

    City findByCity(String city);
}
