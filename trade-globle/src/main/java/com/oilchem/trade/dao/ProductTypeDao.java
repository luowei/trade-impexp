package com.oilchem.trade.dao;

import com.oilchem.trade.domain.SumType;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午5:03
 * To change this template use File | Settings | File Templates.
 */
public interface ProductTypeDao extends PagingAndSortingRepository<SumType, Long> {

    SumType findByProductType(String productType);
}
