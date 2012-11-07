package com.oilchem.trade.service.impl;

import com.oilchem.trade.config.Config;
import com.oilchem.trade.config.ImpExpType;
import com.oilchem.trade.dao.ExpTradeSumDao;
import com.oilchem.trade.dao.ImpTradeSumDao;
import com.oilchem.trade.dao.LogDao;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TradeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午5:44
 * To change this template use File | Settings | File Templates.
 */
@Service("tradeSumService")
public class TradeSumServiceImpl implements TradeDetailService {

    @Autowired
    CommonService commonService;

    @Resource
    ImpTradeSumDao impTradeSumDao;
    @Resource
    ExpTradeSumDao expTradeSumDao;
    @Resource
    LogDao logDao;

    /**
     * 解包
     * @param packageSource    源zip文件绝对路径
     * @return  上传后的url
     */
    public String unPackage(String packageSource,Boolean packageType) {
        return commonService.unpackageFile(packageSource, Config.UPLOAD_EXCELZIP_DIR);
    }

    @Override
    public Boolean importAccess(String accessFileFullName, Date yearMonth, Integer impExpType) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
