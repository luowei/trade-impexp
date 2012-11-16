package com.oilchem.trade.service.impl;

import com.oilchem.trade.config.Config;
import com.oilchem.trade.config.Message;
import com.oilchem.trade.dao.*;
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
import com.oilchem.trade.util.QueryUtils;
import com.sun.jndi.toolkit.dir.SearchFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.expression.spel.ast.Operator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.persistence.DynamicSpecifications;

import javax.annotation.Resource;
import javax.persistence.criteria.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.oilchem.trade.config.Config.*;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.data.jpa.domain.Specifications.*;
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
        final String sql = "select top 200 * from 结果 ";

        //导入查询条件表
        commonService.importCriteriaTab(sql, logEntry.getValue().getExtractPath());

        //导入进口明细总表
        if (yearMonthDto.getImpExpType().equals(Message.ImpExpType.进口.getCode())) {

            synchronized ("detailimp_lock".intern()) {
                Long count = impTradeDetailDao.countWithYearMonth(
                        yearMonthDto.getYear(), yearMonthDto.getMonth(), ImpTradeDetail.class);
                if (count != null && count > 0) {
                    impTradeDetailDao.delRepeatImpTradeDetail(
                            yearMonthDto.getYear(), yearMonthDto.getMonth());
                }

                commonService.importTradeDetail(
                        impTradeDetailDao,
                        impTradeDetailDao,
                        new ImpTradeDetailRowMapper(),
                        yearMonthDto,
                        logEntry.getValue().getExtractPath(), sql,
                        ImpTradeDetail.class);
                isSuccess = true;
            }
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

                commonService.importTradeDetail(
                        expTradeDetailDao,
                        expTradeDetailDao,
                        new ExpTradeDetailRowMapper(),
                        yearMonthDto,
                        logEntry.getValue().getExtractPath(), sql,
                        ExpTradeDetail.class);
                isSuccess = true;
            }
        }

        return isSuccess;
    }


    /**
     * 根据条件查询
     *
     * @param tradeDetail 页面传来的 IxpTradeDetail/ExpTradeDetail ，包含查询条件中里面
     * @param commonDto
     * @param pageRequest
     * @return
     */
    public Page<ImpTradeDetail>
    findWithCriteria(ImpTradeDetail tradeDetail, CommonDto commonDto, PageRequest pageRequest) {

//        Map<String, String> equalFieldMap = getEqualFieldMap(tradeDetail);
//        Page<ImpTradeDetail> impDetailPage = impTradeDetailDao
//                .findAll(where(
//                        this.<ImpTradeDetail>hasField(ImpTradeDetail.class,
//                                equalFieldMap, commonDto.getLowValue(), commonDto.getHighValue()))
//                        , pageRequest);




        Page<ImpTradeDetail> impDetailPage = impTradeDetailDao
                .pageByCriteria(, pageRequest);
        return impDetailPage;
    }

//    /**
//     * 创建动态查询条件组合.
//     */
//    private <T> Specification<T> buildSpecification(
//            Long userId, Map<String, Object> searchParams,Class<T> clazz) {
//        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
//        filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
//        Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), clazz);
//        return spec;
//    }






    void queryOption(ImpTradeDetail tradeDetail, CommonDto commonDto){

        String productCode = tradeDetail.getProductCode();
        String productName = tradeDetail.getProductName();
        String city = tradeDetail.getCity();
        String country = tradeDetail.getCountry();
        String companyType = tradeDetail.getCompanyType();
        String tradeType = tradeDetail.getTradeType();
        String transportation = tradeDetail.getTransportation();
        String customs = tradeDetail.getTransportation();
        String lowValue = commonDto.getLowValue();
        String highValue = commonDto.getHighValue();
        Integer lowYear = 0;
        Integer lowMonth = 0;
        Integer highYear = 2100;
        Integer highMonth = 13;


        if(isBlank(productCode))
            productCode = "%";
        if (isBlank(city))
            city = "%";
        if ( isBlank(country))
            country = "%";
        if (isBlank(companyType))
            companyType = "%";
        if (isBlank(tradeType))
            tradeType = "%";
        if (isBlank(transportation))
            transportation = "%";
        if (isBlank(customs))
            customs = "%";
        if(isBlank(productName))
            productName = "%";

        if (isNotBlank(lowValue)) {
            String[] lows = commonDto.getLowValue().split(YEARMONTH_SPLIT);
            lowYear = Integer.parseInt(lows[0]);
            lowMonth = Integer.parseInt(lows[1]);
        }
        if (isNotBlank(highValue)) {
            String[] highs = commonDto.getLowValue().split(YEARMONTH_SPLIT);
            highYear = Integer.parseInt(highs[0]);
            highMonth = Integer.parseInt(highs[1]);
        }

    }

    private void getdetailQueryProps(ImpTradeDetail tradeDetail,
                                     CommonDto commonDto,
                                     List<PropertyFilter> propList) {
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
            propList.add(new PropertyFilter("country", tradeDetail.getCountry(), null));
        }
        if (isNotBlank(tradeDetail.getCountry())) {
            propList.add(new PropertyFilter("country", tradeDetail.getCountry(), null));
        }
        if (isNotBlank(tradeDetail.getCompanyType())) {
            propList.add(new PropertyFilter("companyType", tradeDetail.getCompanyType(), null));
        }
        if (isNotBlank(tradeDetail.getCompanyType())) {
            propList.add(new PropertyFilter("tradeType", tradeDetail.getCompanyType(), null));
        }
        if (isNotBlank(tradeDetail.getCompanyType())) {
            propList.add(new PropertyFilter("transportation", tradeDetail.getCompanyType(), null));
        }
        if (isNotBlank(tradeDetail.getCompanyType())) {
            propList.add(new PropertyFilter("customs", tradeDetail.getCompanyType(), null));
        }
        if (isNotBlank(commonDto.getLowValue())) {
            String[] lows = commonDto.getLowValue().split(YEARMONTH_SPLIT);
            propList.add(new PropertyFilter("year", lows[0], GE).add("month", lows[1], GE));
        }
        if (isNotBlank(commonDto.getHighValue())) {
            String[] highs = commonDto.getLowValue().split(YEARMONTH_SPLIT);
            propList.add(new PropertyFilter("year", highs[0], LT).add("month", highs[1], LT));
        }
    }

    /**
     * 根据条件查询出口明细
     *
     * @param tradeDetail 页面传来的 IxpTradeDetail/ExpTradeDetail ，包含查询条件中里面
     * @param commonDto
     * @param pageRequest
     * @return
     */
    public Page<ExpTradeDetail>
    findWithCriteria(ExpTradeDetail tradeDetail, CommonDto commonDto,
                     PageRequest pageRequest) {

        Map<String, String> equalFieldMap = getEqualFieldMap(tradeDetail);

        Page<ExpTradeDetail> expDetailPage = expTradeDetailDao
                .findAll(where(
                        this.<ExpTradeDetail>hasField(ExpTradeDetail.class,
                                equalFieldMap, commonDto.getLowValue(), commonDto.getHighValue()))
                        , pageRequest);

        return expDetailPage;
    }


    public <T extends TradeDetail> Specification<T>
    hasField(final Class<T> clazz, final Map<String, String> equalFieldMap,
             final String lowValue, final String highValue) {

        return new Specification<T>() {
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                root = query.from(clazz);
                //设置一个条件
//                return cb.equal(root.get("city"),city);

                List<Predicate> predicates = new ArrayList<Predicate>();
//                Predicate[] predicates = new Predicate[equalFieldMap.size()+1];
                for (Map.Entry<String, String> entry : equalFieldMap.entrySet()) {
                    predicates.add(cb.equal(root.get(entry.getKey()), entry.getValue()));
                }

//                if (StringUtils.isNotBlank(lowValue)) {
//                    String[] lows = lowValue.split(YEARMONTH_SPLIT);
//                    Predicate lowYear = cb.ge((Expression<? extends Number>)
//                            root.get("year"), Integer.parseInt(lows[0]));
//                    Predicate lowMonth = cb.ge((Expression<? extends Number>)
//                            root.get("month"), Integer.parseInt(lows[1]));
//                    predicates.add(lowYear);
//                    predicates.add(lowMonth);
//                }
//                if (StringUtils.isNotBlank(highValue)) {
//                    String[] highs = highValue.split(YEARMONTH_SPLIT);
//                    Predicate highYear = cb.ge((Expression<? extends Number>)
//                            root.get("year"), Integer.parseInt(highs[0]));
//                    Predicate highMonth = cb.ge((Expression<? extends Number>)
//                            root.get("month"), Integer.parseInt(highs[1]));
//                    predicates.add(highYear);
//                    predicates.add(highMonth);
//                }

                //设置多个条件
                query.where((Predicate[]) predicates.toArray());
                return null;
            }

        };
    }

    private <T extends TradeDetail> Map<String, String>
    getEqualFieldMap(T tradeDetail) {
        Map<String, String> fieldValueMap = new HashMap<String, String>();
        String city = tradeDetail.getCity();
        String country = tradeDetail.getCountry();
        String companyType = tradeDetail.getCompanyType();
        String tradeType = tradeDetail.getTradeType();
        String transportation = tradeDetail.getTransportation();
        String customs = tradeDetail.getTransportation();

        if (city != null) {
            fieldValueMap.put("city", city);
            if (country != null)
                fieldValueMap.put("country", country);
            if (companyType != null)
                fieldValueMap.put("companyType", companyType);
            if (tradeType != null)
                fieldValueMap.put("tradeType", tradeType);
            if (transportation != null)
                fieldValueMap.put("transportation", transportation);
            if (customs != null)
                fieldValueMap.put("customs", customs);
        }
        return fieldValueMap;
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


}
