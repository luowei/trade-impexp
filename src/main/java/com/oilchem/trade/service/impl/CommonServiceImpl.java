package com.oilchem.trade.service.impl;

import com.oilchem.common.util.FileUtil;
import com.oilchem.common.util.ZipUtil;
import com.oilchem.trade.config.Config;
import com.oilchem.trade.config.ImpExpType;
import com.oilchem.trade.dao.*;
import com.oilchem.trade.dao.map.AbstractTradeDetailRowMapper;
import com.oilchem.trade.domain.*;
import com.oilchem.trade.domain.abstrac.AbstractTradeSum;
import com.oilchem.trade.domain.abstrac.IdEntity;
import com.oilchem.trade.service.CommonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
 * Time: 下午5:42
 * To change this template use File | Settings | File Templates.
 */
@Service("commonService")
public class CommonServiceImpl implements CommonService {

    @Resource
    CityDao cityDao;
    @Resource
    ComplanyTypeDao companyTypeDao;
    @Resource
    CountryDao countryDao;
    @Resource
    CustomsDao customsDao;
    @Resource
    TradeTypeDao tradeTypeDao;
    @Resource
    TransportationDao transportationDao;

    @Resource
    ProductTypeDao productTypeDao;


    /**
     * 导入数据的方法
     * @param jdbcTemplate   jdbcTemplate
     * @param repository    mondel dao
     * @param e     model bean
     * @param sql  jdbcTemplate's query sql
     * @param filedName  access table's filed name
     * @return
     */
    public <E extends IdEntity> List<E> queryDiffRecord(JdbcTemplate jdbcTemplate,
                                          final Repository repository, final E e,
                                          String sql, final String filedName){

        List<E> eList = jdbcTemplate.query(sql, new RowMapper<E>() {
            public E mapRow(ResultSet rs, int rowNum) throws SQLException {
                String name = rs.getString(filedName);
                String suffix = repository.getClass().getSimpleName();
                String methodName = "getBy" + suffix;
                try {
                    Method method = repository.getClass().getDeclaredMethod(methodName, String.class);
                    E findByMethodRet = (E) method.invoke(repository, name);  //根据名字查找

                    //如果没有找到相同记录
                    if (findByMethodRet.getId() == null) {
                        Method setMethod = e.getClass().getDeclaredMethod("set" + suffix, String.class);
                        setMethod.invoke(e, name);
                        return e;
                    }
                } catch (NoSuchMethodException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                } catch (InvocationTargetException e1) {
                    e1.printStackTrace();
                }

                return null;
            }
        });
        return eList;
    }

    /**
     * 导入查询条件表
     * @param jdbcTemplate
     * @param sql
     * @return
     */
    public Boolean importCriteriaTab(JdbcTemplate jdbcTemplate,String sql){
        Boolean isSuccess = true;

        //导入城市
        List<City> cityList = (List<City>)queryDiffRecord(jdbcTemplate,
                cityDao, new City(), sql, "cityName");
        cityDao.save(cityList);

        //导入企业性质
        List<ComplanyType> complanyTypeList = (List<ComplanyType>)queryDiffRecord(jdbcTemplate,
                companyTypeDao, new ComplanyType(), sql, "E2");
        companyTypeDao.save(complanyTypeList);

        //导入海关
        List<Customs> customsList = (List<Customs>)queryDiffRecord(jdbcTemplate,
                customsDao, new Customs(), sql, "CustomsName");
        customsDao.save(customsList);

        //导入贸易方式
        List<TradeType> tradeTypeList = (List<TradeType>)queryDiffRecord(jdbcTemplate,
                tradeTypeDao, new TradeType(), sql, "TradeName");
        tradeTypeDao.save(tradeTypeList);

        //导入运输方式
        List<Transportation> transportationList = (List<Transportation>)queryDiffRecord(jdbcTemplate,
                transportationDao, new Transportation(), sql, "TransName");
        transportationDao.save(transportationList);

        return isSuccess;
    }

    /**
     * 导入贸易明细
     * @param crudRepository  crudRepository与baseDaoDao传相同对象
     * @param baseDaoDao        crudRepository与baseDaoDao传相同对象
     * @param jdbcTemplate
     * @param tradeDetailMapper
     * @param yearMonth
     * @param sql
     * @param <T>
     * @return
     */
    public <T extends AbstractTradeDetailRowMapper> Boolean importTradeDetail(
            CrudRepository crudRepository,BaseDao baseDaoDao,JdbcTemplate jdbcTemplate,
            T tradeDetailMapper,Date yearMonth, String sql) {
        Boolean success = true;
        if(baseDaoDao.countWithYearMonth(yearMonth) > 0){
            success = success & baseDaoDao.delWithYearMonthRecord(yearMonth);
        }
        List<ExpTradeDetail> expTradeDetailList = jdbcTemplate.query(sql,tradeDetailMapper);
        crudRepository.save(expTradeDetailList);
        return success;
    }

    /**
     * 上传文件
     * @author wei.luo
     * @createTime 2012-3-24
     * @param file  	MultipartFile的文件
     * @param realDir	目标目录的物理路径
     * @return		返回上传之后文件的url
     */
    public String uploadFile(MultipartFile file,String realDir){
        String fileUrl = FileUtil.upload(file,realDir, Config.ROOT_URL);
        return fileUrl;
    }

    /**
     * 解包
     * @param packageSource    源zip文件绝对路径
     * @param unPackageDir     解压目录
     * @return   上传后的url
     */
    public String unpackageFile(String packageSource, String unPackageDir){

        if(StringUtils.isBlank(packageSource) || StringUtils.isBlank(unPackageDir))
                return null;

        String type = FileUtil.getFileSuffix(packageSource);

        if(type.equals(".zip")){
            return ZipUtil.unRar(packageSource,unPackageDir);
        }else if(type.equals(".rar")) {
            return ZipUtil.unZip(packageSource,unPackageDir,null);
        }else return null;
    }

    public <E extends AbstractTradeSum> Boolean importExcel(
            CrudRepository<E,Long> repository,String excelSource,Date yearMonth){

        List<E> eList = null;
        List<ProductType> productTypeList = null;
        List<ProductType> originProductList = (List<ProductType>)productTypeDao.findAll();

        //从excel中取得eList与筛选productTypeList

        if((repository.findByYearMonthCount()) > 0)
            repository.deleteWithYearMonth(yearMonth);
        repository.save(eList);

        productTypeDao.save(productTypeList);
        return null;
    }

}
