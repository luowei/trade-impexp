package com.oilchem.trade.service.impl;

import com.oilchem.trade.config.ImpExpType;
import com.oilchem.trade.dao.*;
import com.oilchem.trade.dao.map.ImpTradeDetailRowMapper;
import com.oilchem.trade.db.AccessDataSource;
import com.oilchem.trade.domain.*;
import com.oilchem.trade.domain.abstrac.IdEntity;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TradeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    CityDao cityDao;
    @Resource
    ComplanyTypeDao companyTypeDao;
    @Resource
    CountryDao countryDao;
    @Resource
    CustomsDao customsDao;
    @Resource
    TradeType tradeTypeDao;
    @Resource
    ProductTypeDao productTypeDao;
    @Resource
    TransportationDao transportationDao;
    @Resource
    ExpTradeDetailDao expTradeDetailDao;
    @Resource
    ImpTradeDetailDao impTradeDetailDao;
    @Resource
    LogDao logDao;

    JdbcTemplate jdbcTemplate;

    public String unZipAccess(String zipFullName, String unZipFullName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Boolean importAccess(String accessFileFullName,Date yearMonth, Enum<ImpExpType> impExpTypeEnum) {

        Boolean isSuccess = true;


//        List<City> cityList = jdbcTemplate.query("",new RowMapper<City>() {
//            @Override
//            public City mapRow(ResultSet rs, int rowNum) throws SQLException {
//                String cityName = rs.getString("cityName");
//                if(cityDao.findByCity(cityName) != null){
//                    return new City(cityName);
//                }
//                return null;
//            }
//        });

        final  String sql = "select * from 结果 ";

        //导入城市
        final City city = new City();
        List<City> cityList = (List<City>) commonService.importFiled(jdbcTemplate,
                cityDao,city,sql,"cityName");
        cityDao.save(cityList);

        //导入企业性质
        final ComplanyType complanyType = new ComplanyType();
        List<ComplanyType> complanyTypeList = (List<ComplanyType>)commonService.importFiled(jdbcTemplate,
                companyTypeDao,complanyType,sql,"E2");

        //导入海关
        final Customs customs = new Customs();
        List<Customs> customsList = (List<Customs>)commonService.importFiled(jdbcTemplate,
                customsDao, customs, sql, "CustomsName");

        //导入贸易方式
        final TradeType tradeType = new TradeType();
//        List<TradeType> tradeTypeList = (List<TradeType>)commonService.importFiled(jdbcTemplate,
//                tradeTypeDao,tradeType,sql,"TradeName")

        //导入明细总表
        if(impTradeDetailDao.findYearMonthCount(yearMonth) > 0){
            isSuccess = isSuccess & impTradeDetailDao.deleteByYearMonth(yearMonth);
        }
        jdbcTemplate = new JdbcTemplate((new AccessDataSource()).getAccessDataSource(accessFileFullName));
        List<ImpTradeDetail> impTradeDetailList = jdbcTemplate.query(sql,new ImpTradeDetailRowMapper());
        impTradeDetailDao.save(impTradeDetailList);



        return isSuccess;
    }

    public Boolean impImportAccess(String accessFileFullName){

        return null;
    }

}
