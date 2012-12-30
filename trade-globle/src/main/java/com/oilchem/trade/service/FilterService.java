package com.oilchem.trade.service;

import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.domain.condition.Product;
import com.oilchem.trade.util.QueryUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-6
 * Time: 上午10:02
 * To change this template use File | Settings | File Templates.
 */
public interface FilterService {

    /**
     * 添加detailType
     *
     * @param code
     * @param name
     */
    void addDetailType(Integer code, String name);


    Page<Product> findProdWithCriteria(
            Product product,CommonDto commonDto, YearMonthDto yearMonthDto, PageRequest pageRequest);

    List<Product> findProdWithCriteria(
            Product product,CommonDto commonDto, YearMonthDto yearMonthDto);

    List<QueryUtils.PropertyFilter> getdetailQueryProps(
            Product product, CommonDto commonDto);

    void saveProduct(Product product);

    void delProduct(Long id);

    Product findProductById(Long id);

}
