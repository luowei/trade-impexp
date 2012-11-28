package com.oilchem.trade.dao;

import com.oilchem.trade.domain.Country;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午4:40
 * To change this template use File | Settings | File Templates.
 */
public interface CountryDao extends PagingAndSortingRepository<Country, Long> {

    Country findByCountry(String country);
}
