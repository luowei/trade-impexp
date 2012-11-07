package com.oilchem.trade.service.impl;

import com.oilchem.trade.config.Config;
import com.oilchem.trade.config.IMessageCode;
import com.oilchem.trade.config.ImpExpType;
import com.oilchem.trade.dao.*;
import com.oilchem.trade.dao.map.ExpTradeDetailRowMapper;
import com.oilchem.trade.dao.map.ImpTradeDetailRowMapper;
import com.oilchem.trade.db.AccessDataSource;
import com.oilchem.trade.domain.*;
import com.oilchem.trade.domain.abstrac.AbstractTradeDetail;
import com.oilchem.trade.domain.abstrac.IdEntity;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TradeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午5:43
 * To change this template use File | Settings | File Templates.
 */
@Service("tradeDetailService")
public class TradeDetailServiceImpl implements TradeDetailService {

    @Autowired
    CommonService commonService;


    @Resource
    ExpTradeDetailDao expTradeDetailDao;
    @Resource
    ImpTradeDetailDao impTradeDetailDao;
    @Resource
    LogDao logDao;
    @Resource
    JdbcTemplate accessJdbcTemplate;

    /**
     * 解包
     * @param packageSourcee    源zip文件绝对路径
     * @return
     */
    public String unPackage(String packageSourcee) {
        return commonService.unpackageFile(packageSourcee, Config.UPLOAD_ACCESSZIP_DIR);
    }

    /**
     * 导入Access文件
     * @param accessFileFullName     access文件全名，含绝对路径
     * @param yearMonth        年月
     * @param impExpType   进出口类型，1进口/2出口
     * @return
     */
    public Boolean importAccess(String accessFileFullName,Date yearMonth, Integer impExpType) {

        Boolean isSuccess = true;

        final  String sql = "select * from 结果 ";
        accessJdbcTemplate.setDataSource((new AccessDataSource()).getAccessDataSource(accessFileFullName));

        //导入查询条件表
        commonService.importCriteriaTab(accessJdbcTemplate,sql);

        //导入进口明细总表
        if(impExpType.equals(ImpExpType.进口.getCode())){
            isSuccess = isSuccess & commonService.importTradeDetail(impTradeDetailDao,impTradeDetailDao,
                    accessJdbcTemplate,new ImpTradeDetailRowMapper(),yearMonth, sql);
        }

        //导入出口明细表
        else if(impExpType.equals(ImpExpType.出口.getCode())){
            isSuccess = isSuccess & commonService.importTradeDetail(expTradeDetailDao,expTradeDetailDao,
                    accessJdbcTemplate,new ExpTradeDetailRowMapper(),yearMonth, sql);
        }

        return isSuccess;
    }


}
