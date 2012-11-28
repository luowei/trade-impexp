package com.oilchem.trade.dao;

import com.oilchem.trade.domain.Transportation;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午5:07
 * To change this template use File | Settings | File Templates.
 */
public interface TransportationDao extends PagingAndSortingRepository<Transportation, Long> {

    Transportation findByTransportation(String transportation);
}
