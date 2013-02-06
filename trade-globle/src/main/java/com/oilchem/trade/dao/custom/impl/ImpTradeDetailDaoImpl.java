package com.oilchem.trade.dao.custom.impl;

import com.oilchem.trade.bean.ProductCount;
import com.oilchem.trade.dao.custom.ImpTradeDetailDaoCustom;
import com.oilchem.trade.dao.others.map.ProductCountRowMapper;
import com.oilchem.trade.domain.detail.ImpTradeDetail;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-12
 * Time: 下午3:11
 * To change this template use File | Settings | File Templates.
 */
@Resource
@Transactional
public class ImpTradeDetailDaoImpl
        extends BaseDaoImpl<ImpTradeDetail>
        implements ImpTradeDetailDaoCustom {


    @Override
    public Integer delWithYearMonthRecord(Integer year, Integer month,
                                          Class<ImpTradeDetail> idEntityClz) {
        return super.delWithYearMonthRecord(year, month, idEntityClz);
    }

    @Override
    public Long countWithYearMonth(Integer year, Integer month,
                                   Class<ImpTradeDetail> idEntityClz) {
        return super.countWithYearMonth(year, month, idEntityClz);
    }

    public List<ProductCount> getProductCount(String condition, String productCode, String yearMonth) {
        condition = handCondition(condition);
        String sql = " select year_month,product_code,product_name," + condition + " as condition," +
                " sum(amount) as num,unit,sum(amount_money) as money " +
                " from t_import_detail where product_code = ? and year_month = ? " +
                " group by year_month," + condition + ",unit,product_code,product_name order by num desc";
        return getJdbcTemplate().query(sql, new ProductCountRowMapper(), productCode, yearMonth);
    }

    private String handCondition(String condition) {
        //tradeType -> trade_type
        for (int i = 0; i < condition.length(); i++) {
            char c = condition.charAt(i);
            if (!Character.isLowerCase(c)) {
                String preStr = condition.substring(0, i) + "_";
                String suffStr = condition.substring(i).toLowerCase();
                condition = preStr + suffStr;
                break;
            }
        }
        return condition;
    }


}
