package com.oilchem.trade.service.impl;

import com.oilchem.trade.dao.*;
import com.oilchem.trade.domain.*;
import com.oilchem.trade.service.FilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import java.lang.reflect.InvocationTargetException;

import static org.springframework.web.context.ContextLoader.getCurrentWebApplicationContext;

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

    //    @Resource
//    CityDao cityDao;
//    @Resource
//    CompanyTypeDao companyTypeDao;
//    @Resource
//    CountryDao countryDao;
//    @Resource
//    CustomsDao customsDao;
//    @Resource
//    TradeTypeDao tradeTypeDao;
//    @Resource
//    TransportationDao transportationDao;
//    @Resource
//    DetailTypeDao detailTypeDao;
//
//    @Resource
//    SumTypeDao sumTypeDao;
//
//
//    public String list(Model model) {
//
//        return null;
//    }




//    if ("city".equals(type)) {
//        City city = cityDao.findOne(id);
//        city.setCity(name);
//        updateEntity(CityDao.class, city);
//    }
//    if ("companyType".equals(type)) {
//        CompanyType companyType = companyTypeDao.findOne(id);
//        companyType.setCompanyType(name);
//        updateEntity(CompanyTypeDao.class, companyType);
//    }
//    if ("country".equals(type)) {
//        Country country = countryDao.findOne(id);
//        country.setCountry(name);
//        updateEntity(CountryDao.class, country);
//    }
//    if ("customs".equals(type)) {
//        Customs customs = customsDao.findOne(id);
//        customs.setCustoms(name);
//        updateEntity(CustomsDao.class, customs);
//    }
//    if ("tradeType".equals(type)) {
//        TradeType tradeType = tradeTypeDao.findOne(id);
//        tradeType.setTradeType(name);
//        updateEntity(TradeTypeDao.class, tradeType);
//    }
//    if ("transportation".equals(type)) {
//        Transportation transportation = transportationDao.findOne(id);
//        transportation.setTransportation(name);
//        updateEntity(TransportationDao.class, transportation);
//    }
//    if ("detailType".equals(type)) {
//        DetailType detailType = detailTypeDao.findOne(id);
//        detailType.setDetailType(name);
//        updateEntity(DetailTypeDao.class, detailType);
//    }
//    if ("sumType".equals(type)) {
//        SumType sumType = sumTypeDao.findOne(id);
//        sumType.setSumType(name);
//        updateEntity(SumTypeDao.class, sumType);
//    }
//
//    return null;

}
