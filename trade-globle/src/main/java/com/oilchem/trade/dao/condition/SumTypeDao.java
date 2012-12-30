package com.oilchem.trade.dao.condition;

import com.oilchem.trade.domain.condition.SumType;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午5:03
 * To change this template use File | Settings | File Templates.
 */
public interface SumTypeDao extends PagingAndSortingRepository<SumType, Long> {

    SumType findBySumType(String sumType);
}
