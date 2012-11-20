package com.oilchem.trade.service.impl;

import com.oilchem.trade.config.Config;
import com.oilchem.trade.config.Message;
import com.oilchem.trade.dao.*;
import com.oilchem.trade.dao.db.JdbcUtil;
import com.oilchem.trade.dao.map.AbstractTradeDetailRowMapper;
import com.oilchem.trade.dao.map.ExpTradeDetailRowMapper;
import com.oilchem.trade.dao.map.ImpTradeDetailRowMapper;
import com.oilchem.trade.domain.ExpTradeDetail;
import com.oilchem.trade.domain.ImpTradeDetail;
import com.oilchem.trade.domain.Log;
import com.oilchem.trade.domain.ProductType;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TradeDetailService;
import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.util.DynamicSpecifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.*;

import static com.oilchem.trade.config.Config.*;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static com.oilchem.trade.util.QueryUtils.*;
import static com.oilchem.trade.util.QueryUtils.Type.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午5:43
 * To change this template use File | Settings | File Templates.
 */
@Service("tradeDetailService")
public class TradeDetailServiceImpl implements TradeDetailService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    CommonService commonService;


    @Resource
    ExpTradeDetailDao expTradeDetailDao;
    @Resource
    ImpTradeDetailDao impTradeDetailDao;
    @Resource
    LogDao logDao;

    /**
     * 上传文件包
     *
     * @param file         文件
     * @param yearMonthDto
     * @return 上传后的路径
     */
    public String uploadFile(MultipartFile file, YearMonthDto yearMonthDto) {

        yearMonthDto.setTableType(Config.DETAIL);
        return commonService.uploadFile(file, UPLOAD_DETAILZIP_DIR, yearMonthDto);
    }

    /**
     * 解包
     *
     * @param logId@return 解包后的文件路径
     */
    public String unPackage(Long logId) {
        Log log = logDao.findOne(logId);
        if (log != null) {
            Map<Long, Log> map = new HashMap<Long, Log>();
            map.put(log.getId(), log);
            return commonService.unpackageFile(map.entrySet().iterator().next()
                    , UPLOAD_DETAILZIP_DIR);
        }
        return null;
    }

    public <E extends TradeDetail, T extends AbstractTradeDetailRowMapper>
    Boolean getDetailList(final CrudRepository repository,
                          final T tradeDetailMapper,
                          final YearMonthDto yearMonthDto,
                          final String accessPath,
                          final Class detailClz,
                          List<String> sqlList) {

        Boolean isSuccess = false;
        ExecutorService pool = Executors.newFixedThreadPool(Config.THREAD_POOLSIZE);

        for (String sqlStr : sqlList) {
            final String sql = sqlStr;
            List<E> subDetailList = null;

            Future<List<E>> detailListFuture = pool.submit(new Callable<List<E>>() {
                public List<E> call() throws Exception {

                    return commonService.getListFormDB(tradeDetailMapper,
                            yearMonthDto, accessPath, sql, detailClz);
                }
            });

            try {
                subDetailList = detailListFuture.get();
                Iterable<E> details = repository.save(subDetailList);
                isSuccess = isSuccess && details != null;

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
        pool.shutdown();
        return isSuccess;
    }

    /**
     * 导入Access文件
     *
     * @param logEntry
     * @param yearMonthDto 年月
     * @return
     */
    public Boolean importAccess(Map.Entry<Long, Log> logEntry,
                                YearMonthDto yearMonthDto) {

        Boolean isSuccess = false;
        String sql = "select count(*) from 结果 ";

        if (!initJdbcUtil(logEntry, sql))
            return false;
        Integer recordCount = new JdbcUtil<Integer>() {
            @Override
            public Integer constructBean(ResultSet rs, Object... obj) throws SQLException {
                return rs.getInt(1);
            }
        }.getList().get(0);

        if (recordCount < 10000) {
            THREAD_POOLSIZE = 5;
        }
        if (recordCount > 10000 && recordCount <= 100000) {
            THREAD_POOLSIZE = 10;
        }
        if (recordCount > 100000) {
            THREAD_POOLSIZE = 100;
        }





        List<String> sqlList = new ArrayList<String>();

        //导入查询条件表
        commonService.importCriteriaTab(sqlList, logEntry.getValue().getExtractPath());

        //导入进口明细总表
        if (yearMonthDto.getImpExpType().equals(Message.ImpExpType.进口.getCode())) {

            synchronized ("detailimp_lock".intern()) {
                Long count = impTradeDetailDao.countWithYearMonth(
                        yearMonthDto.getYear(), yearMonthDto.getMonth(), ImpTradeDetail.class);
                if (count != null && count > 0) {
                    impTradeDetailDao.delRepeatImpTradeDetail(
                            yearMonthDto.getYear(), yearMonthDto.getMonth());
                }
            }

            getDetailList(impTradeDetailDao, new ImpTradeDetailRowMapper(), yearMonthDto,
                    logEntry.getValue().getExtractPath(), ImpTradeDetail.class, sqlList);
            isSuccess = true;

        }

        //导入出口明细表
        else if (yearMonthDto.getImpExpType().equals(Message.ImpExpType.出口.getCode())) {
            synchronized ("detailexp_lock".intern()) {
                Long count = expTradeDetailDao.countWithYearMonth(
                        yearMonthDto.getYear(), yearMonthDto.getMonth(), ExpTradeDetail.class);
                if (count != null && count > 0) {
                    expTradeDetailDao.delRepeatImpTradeDetail(
                            yearMonthDto.getYear(), yearMonthDto.getMonth());
                }
            }

            getDetailList(expTradeDetailDao, new ExpTradeDetailRowMapper(), yearMonthDto,
                    logEntry.getValue().getExtractPath(), ExpTradeDetail.class, sqlList);
            isSuccess = true;

        }

        return isSuccess;
    }

    private Boolean initJdbcUtil(Map.Entry<Long, Log> logEntry, String sql) {
        Boolean isSuccess = false;

        Properties prop = new Properties();
        prop.put("charSet", "GBK");
        prop.put("user", "");
        prop.put("password", "");
        String url = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="
                + logEntry.getValue().getExtractPath();
        String driverClass = "sun.jdbc.odbc.JdbcOdbcDriver";
        JdbcUtil.init(driverClass, url, sql, prop);
        return isSuccess;
    }

    /**
     * 进口明细
     *
     * @param tradeDetail 页面传来的 IxpTradeDetail包含查询条件中里面
     * @param commonDto
     * @param pageRequest
     * @return
     */
    public Page<ImpTradeDetail>
    findImpWithCriteria(ImpTradeDetail tradeDetail, CommonDto commonDto,
                        PageRequest pageRequest) {
        final List<PropertyFilter> filterList = getdetailQueryProps(tradeDetail, commonDto);

        Specification<ImpTradeDetail> spec = DynamicSpecifications.<ImpTradeDetail>byPropertyFilter(filterList, ImpTradeDetail.class);
        Page<ImpTradeDetail> tradeDetailPage = impTradeDetailDao.findAll(spec, pageRequest);
        return tradeDetailPage;
    }

    /**
     * 出口明细
     *
     * @param tradeDetail 页面传来的 ExpTradeDetail，包含查询条件中里面
     * @param commonDto
     * @param pageRequest
     * @return
     */
    public Page<ExpTradeDetail>
    findExpWithCriteria(ExpTradeDetail tradeDetail, CommonDto commonDto,
                        PageRequest pageRequest) {
        final List<PropertyFilter> filterList = getdetailQueryProps(tradeDetail, commonDto);

        Specification<ExpTradeDetail> spec = DynamicSpecifications.<ExpTradeDetail>byPropertyFilter(filterList,
                ExpTradeDetail.class);
        Page<ExpTradeDetail> tradeDetailPage = expTradeDetailDao.findAll(spec, pageRequest);

        return tradeDetailPage;
    }


    @Resource
    ProductTypeDao productTypeDao;

    /**
     * 获得productType列表
     *
     * @return
     */
    public List<ProductType> getProductList() {
        return (List<ProductType>) productTypeDao.findAll();
    }

    /**
     * 获得查询属性
     *
     * @param tradeDetail
     * @param commonDto
     * @return
     */
    public List<PropertyFilter>
    getdetailQueryProps(TradeDetail tradeDetail, CommonDto commonDto) {
        List<PropertyFilter> propList = new ArrayList<PropertyFilter>();
        if (isNotBlank(tradeDetail.getCity())) {
            propList.add(new PropertyFilter("city", tradeDetail.getCity()));
        }
        if (isNotBlank(tradeDetail.getProductCode())) {
            propList.add(new PropertyFilter("productCode", tradeDetail.getProductCode(), LIKE));
        }
        if (isNotBlank(tradeDetail.getProductName())) {
            propList.add(new PropertyFilter("productName", tradeDetail.getProductName(), LIKE));
        }
        if (isNotBlank(tradeDetail.getCountry())) {
            propList.add(new PropertyFilter("country", tradeDetail.getCountry()));
        }
        if (isNotBlank(tradeDetail.getCompanyType())) {
            propList.add(new PropertyFilter("companyType", tradeDetail.getCompanyType()));
        }
        if (isNotBlank(tradeDetail.getTradeType())) {
            propList.add(new PropertyFilter("tradeType", tradeDetail.getTradeType()));
        }
        if (isNotBlank(tradeDetail.getTransportation())) {
            propList.add(new PropertyFilter("transportation", tradeDetail.getTransportation()));
        }
        if (isNotBlank(tradeDetail.getCustoms())) {
            propList.add(new PropertyFilter("customs", tradeDetail.getCustoms()));
        }
        if (tradeDetail.getMonth() != null && tradeDetail.getMonth() != 0) {
            propList.add(new PropertyFilter("month", tradeDetail.getMonth()));
        }
        if (isNotBlank(commonDto.getLowValue())) {
            propList.add(new PropertyFilter("yearMonth", commonDto.getLowValue(), GE));
        }
        if (isNotBlank(commonDto.getHighValue())) {
            propList.add(new PropertyFilter("yearMonth", commonDto.getHighValue(), LT));
        }
//        if (isNotBlank(commonDto.getLowValue())) {
//            String[] lows = commonDto.getLowValue().split(YEARMONTH_SPLIT);
//            propList.add(new PropertyFilter("year", lows[0], GE));
//            propList.add(new PropertyFilter("month", lows[1], GE));
//        }
//        if (isNotBlank(commonDto.getHighValue())) {
//            String[] highs = commonDto.getHighValue().split(YEARMONTH_SPLIT);
//            propList.add(new PropertyFilter("year", highs[0], LT));
//            propList.add(new PropertyFilter("month", highs[1], LT));
//        }
        return propList;
    }


}
