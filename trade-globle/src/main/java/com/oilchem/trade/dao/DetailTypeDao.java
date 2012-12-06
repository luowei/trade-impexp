package com.oilchem.trade.dao;

import com.oilchem.trade.domain.DetailType;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-6
 * Time: 上午10:06
 * To change this template use File | Settings | File Templates.
 */
public interface DetailTypeDao  extends PagingAndSortingRepository<DetailType, Long> {
}
