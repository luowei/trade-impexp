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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.lang.reflect.Constructor;
import java.sql.*;
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
     *
     *
     * @param sql
     * @param accessPath
     * @return
     */
    @Transactional
    public void
    importCriteriaTab(String sql, String accessPath) {
        if (StringUtils.isBlank(sql) || StringUtils.isBlank(accessPath))
            return;

        ApplicationContext ctx = AppContextManager.getAppContext();
        List<DetailCriteria> detailCriteriaList = new ArrayList<com.oilchem.trade.util.DetailCriteria>();

        try {
            //城市
            DetailCriteria cityCri = new DetailCriteria(
                    CITY,
                    City.class,
                    CityDao.class,
                    CityDao.class.getDeclaredMethod("findByCity", String.class),
                    ctx.getBean(CityDao.class),
                    new HashSet<String>());
            detailCriteriaList.add(cityCri);

            //国家
            DetailCriteria countryCri = new DetailCriteria(
                    COUNTRY,
                    Country.class,
                    CountryDao.class,
                    CountryDao.class.getDeclaredMethod("findByCountry", String.class),
                    ctx.getBean(CountryDao.class),
                    new HashSet<String>());
            detailCriteriaList.add(countryCri);

            //企业性质
            DetailCriteria companyTypeCri = new DetailCriteria(
                    COMPANY_TYPE
                    , CompanyType.class,
                    CompanyTypeDao.class,
                    CompanyTypeDao.class.getDeclaredMethod("findByCompanyType", String.class),
                    ctx.getBean(CompanyTypeDao.class),
                    new HashSet<String>());
            detailCriteriaList.add(companyTypeCri);

            //海关
            DetailCriteria customsCri = new DetailCriteria(
                    CUSTOMS,
                    Customs.class,
                    CustomsDao.class,
                    CustomsDao.class.getDeclaredMethod("findByCustoms", String.class),
                    ctx.getBean(CustomsDao.class),
                    new HashSet<String>());
            detailCriteriaList.add(customsCri);

            //贸易类型
            DetailCriteria tradeTypeCri = new DetailCriteria(
                    TRADE_TYPE,
                    TradeType.class,
                    TradeTypeDao.class,
                    TradeTypeDao.class.getDeclaredMethod("findByTradeType", String.class),
                    ctx.getBean(TradeTypeDao.class),
                    new HashSet<String>());
            detailCriteriaList.add(tradeTypeCri);

            //运输方式
            DetailCriteria transportationCri = new DetailCriteria(
                    TRANSPORTATION,
                    Transportation.class,
                    TransportationDao.class,
                    TransportationDao.class.getDeclaredMethod("findByTransportation", String.class),
                    ctx.getBean(TransportationDao.class),
                    new HashSet<String>());
            detailCriteriaList.add(transportationCri);
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

        //匹配
        queryCriteriaRecord(detailCriteriaList, sql, accessPath);

        //导入
        cityDao.save(nameList2IdEntityList(detailCriteriaList.get(0).getRetName(), City.class));
        countryDao.save(nameList2IdEntityList(detailCriteriaList.get(1).getRetName(), Country.class));
        companyTypeDao.save(nameList2IdEntityList(detailCriteriaList.get(2).getRetName(), CompanyType.class));
        customsDao.save(nameList2IdEntityList(detailCriteriaList.get(3).getRetName(), Customs.class));
        tradeTypeDao.save(nameList2IdEntityList(detailCriteriaList.get(4).getRetName(), TradeType.class));
        transportationDao.save(nameList2IdEntityList(detailCriteriaList.get(5).getRetName(), Transportation.class));

    }

    /**
     * name list 到 实例类 list的转换
     * @param nameSet
     * @param idEntityClass
     * @param <E>
     * @return
     */
    private <E extends IdEntity> List<E>
    nameList2IdEntityList(Set<String> nameSet, Class<E> idEntityClass) {
        List<E> idEntityList = new ArrayList<E>();
        for (String name : nameSet) {
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
     * 导入明细数据
     *
     * @param repository
     * @param tradeDetailDao
     * @param tradeDetailMapper tradeDetailMapper
     * @param yearMonthDto
     * @param accessPath
     * @param sql               sql      @return  @author wei.luo
     * @param detailClz         明细抽象类
     */
    public <E extends TradeDetail, T extends AbstractTradeDetailRowMapper>
    void importTradeDetail(
            CrudRepository repository,
            BaseDao<E> tradeDetailDao,
            T tradeDetailMapper,
            YearMonthDto yearMonthDto,
            String accessPath, String sql,
            Class detailClz) {
        if (tradeDetailDao == null || tradeDetailMapper == null
                || yearMonthDto == null ||StringUtils.isBlank(sql)) return;

        synchronized ("tradeDetail_del_lock") {
            Integer count = tradeDetailDao.countWithYearMonth(
                    yearMonthDto.getYear(), yearMonthDto.getMonth(), detailClz);
            if (count != null && count > 0) {
                tradeDetailDao.delWithYearMonthRecord(yearMonthDto.getYear(), yearMonthDto.getMonth());
            }
        }

        List<E> tradeDetailList = getListFormDB(
                tradeDetailMapper, yearMonthDto, accessPath, sql, detailClz);
        repository.save(tradeDetailList);
    }


    /**
     * 导入Excel
     *
     * @param repository
     * @param tradeSumDao
     * @param logEntry
     * @param tradeSumClass       tradeSum Class
     * @param tradeSumRowMapClass tradeSumRowMap Class
     * @param yearMonthDto        @return 成功或失败
     */
    public <E extends TradeSum, M extends MyRowMapper<E>>
    Boolean importExcel(CrudRepository repository,
                        BaseDao<E> tradeSumDao,
                        Map.Entry<Long, String> logEntry,
                        Class<E> tradeSumClass,
                        Class<M> tradeSumRowMapClass, YearMonthDto yearMonthDto) {
        if (tradeSumDao == null || tradeSumClass == null
                || tradeSumRowMapClass == null || yearMonthDto == null
                || logEntry == null)
            return null;

        Boolean isSuccess = true;

        List<E> tradeSumList = getListFromExcel(logEntry,
                tradeSumClass, tradeSumRowMapClass, yearMonthDto);
        isSuccess = isSuccess && (tradeSumList!=null && !tradeSumList.isEmpty());


        //判断是否已存在当年当月的数量，执行保存
        synchronized ("synchronized_lock".intern()) {
            Integer count = (tradeSumDao.countWithYearMonth(
                    yearMonthDto.getYear(), yearMonthDto.getMonth(), tradeSumClass));
            if (count != null && count > 0)
                tradeSumDao.delWithYearMonthRecord(
                        yearMonthDto.getYear(), yearMonthDto.getMonth());
            isSuccess = isSuccess && repository.save(tradeSumList) != null;
        }

        //导入产品类型
        if (productTypeDao.findByProductType(
                yearMonthDto.getProductType()) == null)
            isSuccess = isSuccess && productTypeDao.save(
                    new ProductType(yearMonthDto.getProductType())) !=null;

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

        //找出未解压的包
        if (packageType.equals(DETAIL)) {
            logList = logDao.findByExtractFlagAndTableType(UNEXTRACT_FLAG, DETAIL);
        } else if (packageType.equals(SUM)) {
            logList = logDao.findByExtractFlagAndTableType(UNEXTRACT_FLAG, SUM);
        }

        //把未解压的包放到map中
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
     * @param tableType@return 返回记录的Id与文件的全路径组成的Map
     */
    public Map<Long, String> getUnImportFile(String tableType) {
        if (StringUtils.isBlank(tableType)) return null;

        Map<Long, String> fieMap = new HashMap<Long, String>();
        List<Log> logList = null;

        if (tableType.equals(DETAIL)) {
            logList = logDao.findByImportFlagAndTableType(UNIMPORT_FLAG, DETAIL);
        } else if (tableType.equals(SUM)) {
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
    public <T extends IdEntity> List<T> findAllIdEntityList(Class<T> tClass) {
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
     * 从Access获得过滤后查询条件数据
     * @param detailCriteriaList
     * @param sql
     * @param accessPath
     * @return
     */
    private void
    queryCriteriaRecord(List<DetailCriteria> detailCriteriaList,
                        String sql,
                        String accessPath) {

        if (accessPath == null || detailCriteriaList == null
                || StringUtils.isBlank(sql)) return;

        Connection conn = getDBConnect(accessPath);
        Statement statement = null;
        ResultSet rs = null;

        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {

                //取出每条记录中的条件字段，与条件表对应
                for (DetailCriteria detailCriteria : detailCriteriaList) {

                    String name = rs.getString(detailCriteria.getFieldName());
                    if (StringUtils.isBlank(name)) {
                        continue;
                    }
                    Object findByMethodRet = detailCriteria.getFindByMethod().invoke(detailCriteria.getDao(), name);

                    Set<String> nameSet = detailCriteria.getRetName();
                    //如果没有找到相同记录，则把name字段保存到IdEntity引用的对象中
                    if (findByMethodRet == null) {
                        nameSet.add(name);
                    }
                }
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException();
        } finally {
            closeDBResource(conn,statement, rs);
        }
    }

    /**
     * 从Access表中获得明细数据list
     *
     * @param tradeDetailMapper     tradeDetailMapper
     * @param yearMonthDto
     * @param accessPath
     * @param sql        sql
     * @param detailClz        detailClz   @return    */
    private   <E extends TradeDetail, T extends AbstractTradeDetailRowMapper> List<E>
    getListFormDB(T tradeDetailMapper, YearMonthDto yearMonthDto,
                  String accessPath, String sql, Class detailClz) {
        //查出来然后导入
        Connection conn = getDBConnect(accessPath);
        Statement statement = null;
        ResultSet rs = null;
        List<E> tradeDetailList = new ArrayList<E>();
        try {
            statement = conn.createStatement();
            rs = statement.executeQuery(sql);
            while (rs.next()) {

                //中间的这一段采用回调抽出来-------------------------------

                E e = (E) detailClz.cast(detailClz.newInstance());
                tradeDetailMapper.setTraddDetail(e, rs,
                        yearMonthDto.getYear(), yearMonthDto.getMonth());
                tradeDetailList.add(e);

                //中间的这一段采用回调抽出来-------------------------------

            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            closeDBResource(conn,statement, rs);
        }
        return tradeDetailList;
    }

    /**
     * 获得excel数据中的list
     * @param logEntry
     * @param tradeSumClass
     * @param tradeSumRowMapClass
     * @param yearMonthDto
     * @param <E>
     * @param <M>
     * @return
     */
    private <E extends TradeSum, M extends MyRowMapper<E>> List<E>
    getListFromExcel(Map.Entry<Long, String> logEntry,
                     Class<E> tradeSumClass,
                     Class<M> tradeSumRowMapClass,
                     YearMonthDto yearMonthDto) {

        //待导入的总表记录List
        List<E> tradeSumList = new ArrayList<E>();

        try {
            //从excel中取得eList
            Workbook workbook = Workbook.getWorkbook(new File(logEntry.getValue()));
            Sheet sheet = workbook.getSheet(0);
            int rows = sheet.getRows();
            int rowIdx = sheet.findCell(PRODUCT_XNAME).getRow() + 1;

            //遍历excel
            for (; rowIdx < rows; rowIdx++) {
                E tradeSum = tradeSumClass.getConstructor(
                        Integer.class, Integer.class, String.class)
                        .newInstance(yearMonthDto.getYear(),
                                yearMonthDto.getMonth(),
                                yearMonthDto.getProductType());

                Constructor<M> constructor = tradeSumRowMapClass.getConstructor(
                        int.class, tradeSumClass, Sheet.class);
                M tradeSumMyRowMapper = constructor.newInstance(rowIdx, tradeSum, sheet);
                tradeSumList.add(tradeSumMyRowMapper.getMappingInstance());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return tradeSumList;
    }

    /**
     * 建立Access连接
     *
     * @param accessPath
     * @return
     */
    private Connection getDBConnect(String accessPath) {
        Connection conn;//连接参数
        Properties prop = new Properties();
        prop.put("charSet", "GBK");
        prop.put("user", "");
        prop.put("password", "");
        String url = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="
                + accessPath;

        //创建连接
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            conn = DriverManager.getConnection(url, prop);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return conn;
    }


    /**
     * 关闭statement与ResultSet
     *
     * @param statement
     * @param rs
     */
    private void closeDBResource(
            Connection conn, Statement statement, ResultSet rs) {
        try {
            if (rs == null) {
                rs.close();
            }
            if (statement == null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException();
        }
    }

}
