package com.oilchem.trade.service.impl;

import com.oilchem.trade.config.ImpExpType;
import com.oilchem.trade.dao.ExpTradeSumDao;
import com.oilchem.trade.dao.ImpTradeSumDao;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TradeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Override
    public String unZipAccess(String zipFullName, String unZipFullName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Boolean importAccess(String accessFileFullName, Enum<ImpExpType> impExpTypeEnum) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
