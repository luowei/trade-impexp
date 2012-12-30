package com.oilchem.trade.dao.others.map;

import com.oilchem.trade.bean.ProductCount;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-12-17
 * Time: 下午4:06
 * To change this template use File | Settings | File Templates.
 */
public class ProductCountRowMapper implements ParameterizedRowMapper<ProductCount> {
    @Override
    public ProductCount mapRow(ResultSet resultSet, int i) throws SQLException {
        ProductCount productCount = new ProductCount();

        productCount.setYearMonth(resultSet.getString("year_month"));
        productCount.setProductCode(resultSet.getString("product_code"));
        productCount.setProductName(resultSet.getString("product_name"));
        productCount.setCondition(resultSet.getString("condition"));
        productCount.setNum(resultSet.getBigDecimal("num"));
        productCount.setMoney(resultSet.getBigDecimal("money"));
        productCount.setUnit(resultSet.getString("unit"));

        return productCount;
    }
}
