package com.oilchem.trade.service.impl;

import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.dao.condition.DetailTypeDao;
import com.oilchem.trade.dao.condition.ProductDao;
import com.oilchem.trade.domain.condition.DetailType;
import com.oilchem.trade.domain.condition.Product;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.FilterService;
import com.oilchem.trade.util.DynamicSpecifications;
import com.oilchem.trade.util.QueryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.oilchem.trade.util.QueryUtils.Type.LIKE;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-6
 * Time: 上午10:02
 * To change this template use File | Settings | File Templates.
 */
@Service
public class FilterServiceImpl  implements FilterService {


    Logger logger = LoggerFactory.getLogger(FilterServiceImpl.class);

    @Resource
    DetailTypeDao detailTypeDao;

    @Resource
    ProductDao productDao;

    @Autowired
    CommonService commonService;


    /**
    * 添加detailType
    * @param code
    * @param name
    */
    public void addDetailType(Integer code, String name) {
        DetailType detailType = new DetailType(code,name);
        detailTypeDao.save(detailType);
    }

    @Override
    public Page<Product> findProdWithCriteria(Product product, CommonDto commonDto,
                                              YearMonthDto yearMonthDto, PageRequest pageRequest) {

        final List<QueryUtils.PropertyFilter> filterList = getdetailQueryProps(product, commonDto);

        filterList.addAll(commonService.getYearMonthQueryProps(yearMonthDto));

        Specification<Product> spec = DynamicSpecifications.<Product>byPropertyFilter(filterList, Product.class);
        Page<Product> page = productDao.findAll(spec, pageRequest);

        return page;
    }

    public List<Product> findProdWithCriteria(Product product, CommonDto commonDto,
                                              YearMonthDto yearMonthDto) {

        final List<QueryUtils.PropertyFilter> filterList = getdetailQueryProps(product, commonDto);

        filterList.addAll(commonService.getYearMonthQueryProps(yearMonthDto));

        Specification<Product> spec = DynamicSpecifications.<Product>byPropertyFilter(filterList, Product.class);
        List<Product> list = productDao.findAll(spec);

        return list;
    }

    public List<QueryUtils.PropertyFilter> getdetailQueryProps(
            Product product, CommonDto commonDto) {

        List<QueryUtils.PropertyFilter> propList = new ArrayList<QueryUtils.PropertyFilter>();
        if (isNotBlank(product.getProductCode())) {
            propList.add(new QueryUtils.PropertyFilter("productCode", product.getProductCode(),LIKE));
        }
        if (isNotBlank(product.getProductName())) {
            propList.add(new QueryUtils.PropertyFilter("productName", product.getProductName(), LIKE));
        }
        if (isNotBlank(product.getProductType())) {
            propList.add(new QueryUtils.PropertyFilter("productType", product.getProductType(), LIKE));
        }
        if (product.getTypeCode()!=null) {
            propList.add(new QueryUtils.PropertyFilter("typeCode", product.getTypeCode()));
        }

        return propList;
    }

    @Override
    public void saveProduct(Product product) {
        productDao.save(product);
    }

    @Override
    public void delProduct(Long id) {
        productDao.delete(id);
    }

    @Override
    public Product findProductById(Long id) {
        return productDao.findById(id);
    }

}
