package com.oilchem.trade.dao.condition;

import com.oilchem.trade.domain.condition.City;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午3:45
 * To change this template use File | Settings | File Templates.
 */
//@RepositoryDefinition(domainClass = City.class,idClass = Integer.class)
public interface CityDao extends PagingAndSortingRepository<City, Long> {

    City findByCity(String city);

}
