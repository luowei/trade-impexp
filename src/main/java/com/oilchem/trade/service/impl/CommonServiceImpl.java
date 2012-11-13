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
import com.oilchem.trade.util.DetailCriteria;
import com.oilchem.trade.view.dto.YearMonthDto;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    CompanyTypeDao companyTypeDao;
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
     * @param file         MultipartFile的文件
     * @param realDir      目标目录的物理路径
     * @param yearMonthDto
     * @return 返回上传之后文件的url
     * @author wei.luo
     * @createTime 2012-11-7
     */
    public String uploadFile(MultipartFile file, String realDir, YearMonthDto yearMonthDto) {
        if (file == null || StringUtils.isBlank(realDir)) return null;

        String fileUrl = FileUtil.upload(file, realDir, ROOT_URL);
        return fileUrl;
    }

    /**
     * 解包
     *
     * @param logEntry
     * @param unPackageDir 解压目录
     * @return 解压后的文件路径
     */
    //@Before加锁
    //@After解锁
    public String unpackageFile(Map.Entry<Long, String> logEntry, String unPackageDir) {

        if (logEntry == null || StringUtils.isBlank(unPackageDir))
            return null;

        String type = FileUtil.getFileSuffix(logEntry.getValue());

        //判断文件类型
        if (type.equals(".zip")) {
            return ZipUtil.unZip(logEntry.getValue(), unPackageDir, null);
        } else if (type.equals(".rar")) {
            return ZipUtil.unRar(logEntry.getValue(), unPackageDir);
        } else return null;
    }

    /**
     * 导入查询条件表
     *
     * @param jdbcTemplate
     * @param sql
     * @param conn
     * @return
     */
    public Boolean
    importCriteriaTab(JdbcTemplate jdbcTemplate, String sql, Connection conn){
        if (StringUtils.isBlank(sql)) return null;

        Boolean isSuccess = true;

        ApplicationContext ctx = AppContextManager.getAppContext();
        List<DetailCriteria> detailCriteriaList = new ArrayList<com.oilchem.trade.util.DetailCriteria>();

        try {
            //城市
            DetailCriteria cityCri = new DetailCriteria(
                    CITY,
                    City.class,
                    CityDao.class,
                    CityDao.class.getDeclaredMethod("findByCity",String.class),
                    ctx.getBean(CityDao.class),
                    new ArrayList<String>());
            detailCriteriaList.add(cityCri);

            //国家
            DetailCriteria countryCri = new DetailCriteria(
                    COUNTRY,
                    Country.class,
                    CountryDao.class,
                    CountryDao.class.getDeclaredMethod("findByCountry",String.class),
                    ctx.getBean(CountryDao.class),
                    new ArrayList<String>());
            detailCriteriaList.add(countryCri);

            //企业性质
            DetailCriteria companyTypeCri = new DetailCriteria(
                    COMPANY_TYPE
                    ,CompanyType.class,
                    CompanyTypeDao.class,
                    CompanyTypeDao.class.getDeclaredMethod("findByCompanyType",String.class),
                    ctx.getBean(CompanyTypeDao.class),
                    new ArrayList<String>());
            detailCriteriaList.add(companyTypeCri);

            //海关
            DetailCriteria customsCri = new DetailCriteria(
                    CUSTOMS,
                    Customs.class,
                    CustomsDao.class,
                    CustomsDao.class.getDeclaredMethod("findByCustoms",String.class),
                    ctx.getBean(CustomsDao.class),
                    new ArrayList<String>());
            detailCriteriaList.add(customsCri);

            //贸易类型
            DetailCriteria tradeTypeCri = new DetailCriteria(
                    TRADE_TYPE,
                    TradeType.class,
                    TradeTypeDao.class,
                    TradeTypeDao.class.getDeclaredMethod("findByTradeType",String.class),
                    ctx.getBean(TradeTypeDao.class),
                    new ArrayList<String>());
            detailCriteriaList.add(tradeTypeCri);

            //运输方式
            DetailCriteria transportationCri = new DetailCriteria(
                    TRANSPORTATION,
                    Transportation.class,
                    TransportationDao.class,
                    TransportationDao.class.getDeclaredMethod("findByTransportation",String.class),
                    ctx.getBean(TransportationDao.class),
                    new ArrayList<String>());
            detailCriteriaList.add(transportationCri);
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        queryCriteriaRecord(detailCriteriaList, sql, conn);

        //导入
        cityDao.save(nameList2IdEntityList(detailCriteriaList.get(0).getRetName(), City.class));
        countryDao.save(nameList2IdEntityList(detailCriteriaList.get(1).getRetName(), Country.class));
        companyTypeDao.save(nameList2IdEntityList(detailCriteriaList.get(2).getRetName() , CompanyType.class));
        customsDao.save(nameList2IdEntityList(detailCriteriaList.get(3).getRetName(), Customs.class));
        tradeTypeDao.save(nameList2IdEntityList(detailCriteriaList.get(4).getRetName(), TradeType.class));
        transportationDao.save(nameList2IdEntityList(detailCriteriaList.get(5).getRetName(), Transportation.class));

        return isSuccess;
    }

    private <E extends IdEntity> List<E>
    nameList2IdEntityList(List<String> nameList, Class<E> idEntityClass) {
        List<E> idEntityList = new ArrayList<E>();
        for (String name : nameList) {
            try {
                idEntityList.add(idEntityClass.getConstructor(String.class).newInstance(name));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
        return idEntityList;
    }

    /**
     * 过滤条件
     *
     * @param detailCriteriaList
     * @param sql
     * @param conn
     * @return
     */
    public <E extends IdEntity> void
    queryCriteriaRecord(List<DetailCriteria> detailCriteriaList,
                        String sql,
                        Connection conn) {

        if (conn == null || detailCriteriaList == null
                || StringUtils.isBlank(sql)) return ;

        Statement statement = null;
        ResultSet rs = null;

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);

            while (rs.next()) {
                //取出每条记录中的条件字段，与条件表对应
                for (DetailCriteria detailCriteria : detailCriteriaList) {

                    String name = rs.getString(detailCriteria.getFieldName());
                    E findByMethodRet = (E) detailCriteria.getFindByMethod().invoke(detailCriteria.getDao(), name);

                    List<String> nameList = detailCriteria.getRetName();

                    //如果没有找到相同记录，则把name字段保存到IdEntity引用的对象中
                    if (findByMethodRet == null || findByMethodRet.getId() == null ||
                            !nameList.contains(name)) {
                        nameList.add(name);
                    }
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException();
        } finally {
            try {
                statement.close();
                rs.close();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException();
            }
        }
    }


    /**
     * 获得有效的查询条件表的记录List
     *
     *
     * @param jdbcTemplate  jdbcTemplate
     * @param dao           mondel dao
     * @param idEntityClass model bean
     * @param sql           jdbcTemplate's query sql
     * @param filedName     access table's filed name
     * @param conn
     * @return idEntity列表
     */
//        public<E extends IdEntity>List<E>
//        queryCriteriaRecord(JdbcTemplate jdbcTemplate, final Repository<E, Long> dao,
//        final Class<E> idEntityClass, String sql,final String filedName, Connection conn){
//
//            if (jdbcTemplate == null || dao == null || idEntityClass == null
//                    || StringUtils.isBlank(sql) || StringUtils.isBlank(filedName)) return null;
//
//            List<E> eList = jdbcTemplate.query(sql, new RowMapper<E>() {
//                public E mapRow(ResultSet rs, int rowNum) throws SQLException {
//                    String name = rs.getString(filedName);
//                    String suffix = dao.getClass().getSimpleName();
//                    String methodName = "getBy" + suffix;
//                    try {
//                        //获得根据名字查找的方法，并执行查找
////                    WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
////                    Repository<E,Long> dao = context.getBean(daoClass);
//                        Method method = dao.getClass().getDeclaredMethod(methodName, String.class);
//                        E findByMethodRet = (E) method.invoke(dao, name);
//
//                        //如果没有找到相同记录，则指导name字段保存到IdEntity引用的对象中
//                        if (findByMethodRet.getId() == null) {
//                            E idEntity = idEntityClass.getConstructor(String.class).newInstance(name);
//                            return idEntity;
//                        }
//                    } catch (Exception e) {
//                        logger.error(e.getMessage(), e);
//                        throw new RuntimeException(e);
//                    }
//                    return null;
//                }
//            });
//            return eList;
//        }

    /**
     * 导入贸易明细
     *
     * @param repository
     * @param tradeDetailDao
     * @param jdbcTemplate      jdbcTemplate
     * @param tradeDetailMapper tradeDetailMapper
     * @param year              year
     * @param month             month
     * @param sql               sql      @return
     */

    public synchronized <E extends TradeDetail, T extends AbstractTradeDetailRowMapper>
    Boolean importTradeDetail(
            CrudRepository repository,
            BaseDao<E> tradeDetailDao,
            JdbcTemplate jdbcTemplate,
            T tradeDetailMapper,
            Integer year,
            Integer month,
            String sql) {
        if (tradeDetailDao == null || jdbcTemplate == null || tradeDetailMapper == null
                || year == null || month == null || StringUtils.isBlank(sql)) return null;

        Boolean success = true;
        if (tradeDetailDao.countWithYearMonth(year, month) > 0) {
            success = success & tradeDetailDao.delWithYearMonthRecord(year, month);
        }

        //查出来然后导入
        List<ExpTradeDetail> expTradeDetailList = jdbcTemplate.query(sql, tradeDetailMapper);
        repository.save(expTradeDetailList);
        return success;
    }


    /**
     * 导入Excel
     *
     * @param repository
     * @param tradeSumDao
     * @param excelSource         excel文件源目录
     * @param tradeSumClass       tradeSum Class
     * @param tradeSumRowMapClass tradeSumRowMap Class
     * @param year                数据所在年
     * @param month               数据所有月
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
        if (tradeSumDao == null || tradeSumClass == null || tradeSumRowMapClass == null || year == null || month == null
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
            throw new RuntimeException(e);
        }

        //判断是否已存在当年当月的数量，执行保存
        synchronized ("synchronized_lock".intern()) {
            if ((tradeSumDao.countWithYearMonth(year, month)) > 0)
                tradeSumDao.delWithYearMonthRecord(year, month);
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
        if (packageType == null) return null;

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
        if (StringUtils.isBlank(fileType)) return null;

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
        if (tClass == null) return null;

        List<T> idEntityList = null;
        Class<?>[] classes = IdEntity.class.getClasses();
        for (Class clz : classes) {
            if (clz.isInstance(tClass)) {
                T t = ContextLoader.getCurrentWebApplicationContext().getBean(tClass);
                try {
                    idEntityList = (List<T>) tClass.getMethod("findAll").invoke(t);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            }
        }
        return idEntityList;
    }

    /**
     * 获得productType列表
     *
     * @return
     */
    public Iterable<ProductType> getProductList() {
        return productTypeDao.findAll();
    }

}
