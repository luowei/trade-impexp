package com.oilchem.trade.service.impl;

import com.oilchem.common.util.FileUtil;
import com.oilchem.common.util.ZipUtil;
import com.oilchem.trade.dao.*;
import com.oilchem.trade.dao.map.AbstractTradeDetailRowMapper;
import com.oilchem.trade.dao.map.MyRowMapper;
import com.oilchem.trade.domain.*;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.domain.abstrac.IdEntity;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.view.dto.YearMonthDto;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.oilchem.trade.config.Config.*;
import static com.oilchem.trade.config.MapperConfig.*;

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
    @Resource
    LogDao logDao;

    //日志记录器
    Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 上传文件
     *
     *
     * @param file    MultipartFile的文件
     * @param realDir 目标目录的物理路径
     * @param yearMonthDto
     * @return 返回上传之后文件的url
     * @author wei.luo
     * @createTime 2012-11-7
     */
    public String uploadFile(MultipartFile file, String realDir, YearMonthDto yearMonthDto) {
        if(file==null || StringUtils.isBlank(realDir)) return null;

        String fileUrl = FileUtil.upload(file, realDir, ROOT_URL);
        return fileUrl;
    }

    /**
     * 解包
     *
     * @param packageSource 源zip文件绝对路径
     * @param unPackageDir  解压目录
     * @return 解压后的文件路径
     */
    //@Before加锁
    //@After解锁
    public String unpackageFile(String packageSource, String unPackageDir) {

        if (StringUtils.isBlank(packageSource) || StringUtils.isBlank(unPackageDir))
            return null;

        String type = FileUtil.getFileSuffix(packageSource);

        //判断文件类型
        if (type.equals(".zip")) {
            return ZipUtil.unRar(packageSource, unPackageDir);
        } else if (type.equals(".rar")) {
            return ZipUtil.unZip(packageSource, unPackageDir, null);
        } else return null;
    }

    /**
     * 导入查询条件表
     *
     * @param jdbcTemplate
     * @param sql
     * @return
     */
    public Boolean importCriteriaTab(JdbcTemplate jdbcTemplate, String sql) {
        if(jdbcTemplate==null || StringUtils.isBlank(sql)) return null;

        Boolean isSuccess = true;

        //导入城市
        List<City> cityList = (List<City>) queryCriteriaRecord(jdbcTemplate,
                cityDao, City.class, sql, CITY);
        cityDao.save(cityList);

        //导入企业性质
        List<ComplanyType> complanyTypeList = (List<ComplanyType>) queryCriteriaRecord(jdbcTemplate,
                companyTypeDao, ComplanyType.class, sql, COMPANY_TYPE);
        companyTypeDao.save(complanyTypeList);

        //导入海关
        List<Customs> customsList = (List<Customs>) queryCriteriaRecord(jdbcTemplate,
                customsDao, Customs.class, sql, CUSTOMS);
        customsDao.save(customsList);

        //导入贸易方式
        List<TradeType> tradeTypeList = (List<TradeType>) queryCriteriaRecord(jdbcTemplate,
                tradeTypeDao, TradeType.class, sql, TRADE_TYPE);
        tradeTypeDao.save(tradeTypeList);

        //导入运输方式
        List<Transportation> transportationList = (List<Transportation>) queryCriteriaRecord(jdbcTemplate,
                transportationDao, Transportation.class, sql, TRANSPORTATION);
        transportationDao.save(transportationList);

        return isSuccess;
    }

    /**
     * 获得有效的查询条件表的记录List
     *
     * @param jdbcTemplate  jdbcTemplate
     * @param dao           mondel dao
     * @param idEntityClass model bean
     * @param sql           jdbcTemplate's query sql
     * @param filedName     access table's filed name
     * @param <E>           idEntity
     * @return idEntity列表
     */
    public <E extends IdEntity> List<E>
    queryCriteriaRecord(JdbcTemplate jdbcTemplate,final Repository<E, Long> dao,
                        final Class<E> idEntityClass,String sql, final String filedName) {

        if(jdbcTemplate==null || dao==null || idEntityClass==null
                || StringUtils.isBlank(sql) || StringUtils.isBlank(filedName)) return null;

        List<E> eList = jdbcTemplate.query(sql, new RowMapper<E>() {
            public E mapRow(ResultSet rs, int rowNum) throws SQLException {
                String name = rs.getString(filedName);
                String suffix = dao.getClass().getSimpleName();
                String methodName = "getBy" + suffix;
                try {
                    //获得根据名字查找的方法，并执行查找
//                    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
//                    Repository<E,Long> dao = context.getBean(daoClass);
                    Method method = dao.getClass().getDeclaredMethod(methodName, String.class);
                    E findByMethodRet = (E) method.invoke(dao, name);

                    //如果没有找到相同记录，则指导name字段保存到IdEntity引用的对象中
                    if (findByMethodRet.getId() == null) {
                        E idEntity = idEntityClass.getConstructor(String.class).newInstance(name);
                        return idEntity;
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                return null;
            }
        });
        return eList;
    }

    /**
     * 导入贸易明细
     *
     *
     * @param repository
     * @param tradeDetailDao
     * @param jdbcTemplate      jdbcTemplate
     * @param tradeDetailMapper     tradeDetailMapper
     * @param year               year
     * @param month              month
     * @param sql                sql      @return          */
    public synchronized <E extends TradeDetail,T extends AbstractTradeDetailRowMapper>
    Boolean importTradeDetail(
            CrudRepository repository,
            BaseDao<E> tradeDetailDao,
            JdbcTemplate jdbcTemplate,
            T tradeDetailMapper,
            Integer year,
            Integer month,
            String sql) {
        if(tradeDetailDao ==null || jdbcTemplate==null || tradeDetailMapper==null
                || year==null || month==null || StringUtils.isBlank(sql)) return null;

        Boolean success = true;
        if (tradeDetailDao.countWithYearMonth(year,month) > 0) {
            success = success & tradeDetailDao.delWithYearMonthRecord(year,month);
        }

        //查出来然后导入
        List<ExpTradeDetail> expTradeDetailList = jdbcTemplate.query(sql, tradeDetailMapper);
        repository.save(expTradeDetailList);
        return success;
    }


    /**
     * 导入Excel
     *
     *
     *
     *
     *
     *
     * @param repository
     * @param tradeSumDao
     * @param excelSource         excel文件源目录
     * @param tradeSumClass       tradeSum Class
     * @param tradeSumRowMapClass tradeSumRowMap Class
     * @param year                 数据所在年
     * @param month                数据所有月
     * @param productType         产品类型
     * @return 成功或失败
     */
    public <E extends TradeSum, M extends MyRowMapper<E>>
    Boolean importExcel(CrudRepository repository,
                        BaseDao<E> tradeSumDao,
                        String excelSource,
                        Class<E> tradeSumClass,
                        Class<M> tradeSumRowMapClass,
                        Integer year,
                        Integer month,
                        String productType) {
        if(tradeSumDao ==null || tradeSumClass==null || tradeSumRowMapClass==null || year==null || month==null
                || StringUtils.isBlank(excelSource) || StringUtils.isBlank(productType)) return null;

        Boolean isSuccess = true;

        //待导入的总表记录List
        List<E> tradeSumList = new ArrayList<E>();

        try {
            //从excel中取得eList
            Workbook workbook = Workbook.getWorkbook(new File(excelSource));
            Sheet sheet = workbook.getSheet(0);
            int rows = sheet.getRows();
            int rowIdx = sheet.findCell(PRODUCT_XNAME).getRow() + 1;

            //遍历excel
            for (; rowIdx < rows; rowIdx++) {
                E tradeSum = tradeSumClass.newInstance();
                Constructor<M> constructor = tradeSumRowMapClass.getConstructor(
                        int.class, tradeSumClass, Sheet.class);
                M tradeSumMyRowMapper = constructor.newInstance(rowIdx, tradeSum, sheet);
                tradeSumList.add(tradeSumMyRowMapper.getMappingInstance());
            }
        } catch (Exception e) {
            isSuccess = false;
            logger.error(e.getMessage(), e);
        }

        //判断是否已存在当年当月的数量，执行保存
        synchronized ("synchronized_lock".intern()) {
            if ((tradeSumDao.countWithYearMonth(year,month)) > 0)
                tradeSumDao.delWithYearMonthRecord(year,month);
            repository.save(tradeSumList);
        }

        //导入产品类型
        if (productTypeDao.findByProductType(productType) != null)
            productTypeDao.save(new ProductType(productType));

        return isSuccess;
    }

    /**
     * 获得未解压的文件列表
     *
     * @param packageType 包类型
     * @return 返回记录的Id与包的全路径组成的Map
     */
    public Map<Long, String> getUnExtractPackage(String packageType) {
        if(packageType==null) return null;

        Map<Long, String> packaeMap = new HashMap<Long, String>();
        List<Log> logList = null;

        if (packageType.equals(DETAIL)) {
            logList = logDao.findByExtractFlagAndTableType(UNEXTRACT_FLAG, DETAIL);
        } else if (packageType.equals(SUM)) {
            logList = logDao.findByExtractFlagAndTableType(UNEXTRACT_FLAG, SUM);
        }

        if (logList != null && !logList.isEmpty()) {
            for (Log log : logList) {
                packaeMap.put(log.getId(), log.getUploadPath());
            }
        }

        return packaeMap;
    }

    /**
     * 获得未导入的文件列表
     *
     * @param fileType 文件类型
     * @return 返回记录的Id与文件的全路径组成的Map
     */
    public Map<Long, String> getUnImportFile(String fileType) {
        if(StringUtils.isBlank(fileType)) return null;

        Map<Long, String> fieMap = new HashMap<Long, String>();
        List<Log> logList = null;

        if (fileType.equals(DETAIL)) {
            logList = logDao.findByImportFlagAndTableType(UNIMPORT_FLAG, DETAIL);
        } else if (fileType.equals(SUM)) {
            logList = logDao.findByImportFlagAndTableType(UNIMPORT_FLAG, SUM);
        }

        if (logList != null && !logList.isEmpty()) {
            for (Log log : logList) {
                fieMap.put(log.getId(), log.getExtractPath());
            }
        }

        return fieMap;
    }

    /**
     * 获得数据模型的数据列表
     *
     * @param tClass tClass
     * @param <T>    数据模型映射的java类
     * @return
     */
    public <T extends IdEntity> List<T> getModelList(Class<T> tClass) {
        if(tClass==null) return null;

        List<T> idEntityList = null;
        Class<?>[] classes = IdEntity.class.getClasses();
        for (Class clz : classes) {
            if (clz.isInstance(tClass)) {
                T t = ContextLoader.getCurrentWebApplicationContext().getBean(tClass);
                try {
                    idEntityList = (List<T>) tClass.getMethod("findAll").invoke(t);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return idEntityList;
    }

}
