package com.oilchem.trade.dao.custom;

import com.oilchem.trade.bean.ProductCount;
import com.oilchem.trade.dao.BaseDao;
import com.oilchem.trade.domain.detail.ExpTradeDetail;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-12
 * Time: 下午2:31
 * To change this template use File | Settings | File Templates.
 */
@NoRepositoryBean
public interface ExpTradeDetailDaoCustom extends BaseDao<ExpTradeDetail> {

    List<ProductCount> getProductCount(String productCode, String yearMonth, String condition);

}
