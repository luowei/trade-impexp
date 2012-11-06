package com.oilchem.trade.dao.map;



import com.oilchem.trade.domain.ImpTradeDetail;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 进口access表映射
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-6
 * Time: 下午2:33
 * To change this template use File | Settings | File Templates.
 */
public class ImpTradeDetailRowMapper
        extends AbstractTradeDetailRowMapper<ImpTradeDetail> {

    @Override
    public ImpTradeDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
        ImpTradeDetail impTradeDetail = new  ImpTradeDetail();
        this.setTraddDetail(impTradeDetail,rs);
        return impTradeDetail;
    }
}
