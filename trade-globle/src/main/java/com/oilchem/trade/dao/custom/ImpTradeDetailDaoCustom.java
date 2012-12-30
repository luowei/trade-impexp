package com.oilchem.trade.dao.custom;

import com.oilchem.trade.bean.ProductCount;
import com.oilchem.trade.dao.BaseDao;
import com.oilchem.trade.domain.detail.ImpTradeDetail;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-12
 * Time: 下午2:33
 * To change this template use File | Settings | File Templates.
 */
@NoRepositoryBean
public interface ImpTradeDetailDaoCustom extends BaseDao<ImpTradeDetail> {

    List<ProductCount> getProductCount(String condition, String productCode, String yearMonth);
}
