package com.oilchem.trade.service.impl;

import com.oilchem.trade.config.Config;
import com.oilchem.trade.config.ImpExpType;
import com.oilchem.trade.dao.*;
import com.oilchem.trade.dao.map.ExpTradeDetailRowMapper;
import com.oilchem.trade.dao.map.ImpTradeDetailRowMapper;
import com.oilchem.trade.dao.spec.Spec;
import com.oilchem.trade.domain.ExpTradeDetail;
import com.oilchem.trade.domain.ImpTradeDetail;
import com.oilchem.trade.domain.Log;
import com.oilchem.trade.domain.ProductType;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TradeDetailService;
import com.oilchem.trade.view.dto.CommonDto;
import com.oilchem.trade.view.dto.YearMonthDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.oilchem.trade.config.Config.*;

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

        //更新日志文件
        //......
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
        if (yearMonthDto.getImpExpType().equals(ImpExpType.进口.getCode())) {

            synchronized ("synchronized_detailimp_lock".intern()) {
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
        else if (yearMonthDto.getImpExpType().equals(ImpExpType.出口.getCode())) {
            synchronized ("synchronized_detailexp_lock".intern()) {
                Long count = impTradeDetailDao.countWithYearMonth(
                        yearMonthDto.getYear(), yearMonthDto.getMonth(), ImpTradeDetail.class);
                if (count != null && count > 0) {
                    impTradeDetailDao.delRepeatImpTradeDetail(
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
    public <T extends TradeDetail> Page<T>
    findWithCriteria(T tradeDetail, CommonDto commonDto, PageRequest pageRequest) {

        if (tradeDetail instanceof ImpTradeDetail) {
            Page<ImpTradeDetail> pageImpDetail = impTradeDetailDao
                    .findAll(Specifications
                            .where(Spec.<ImpTradeDetail>hasField("", tradeDetail.getCountry()))
                            .and(Spec.<ImpTradeDetail>hasField("", tradeDetail.getCity()))
                            .and(Spec.<ImpTradeDetail>hasField("", tradeDetail.getCustoms()))
                            .and(Spec.<ImpTradeDetail>hasField("", tradeDetail.getCompanyType()))
                            .and(Spec.<ImpTradeDetail>hasField("", tradeDetail.getTransportation()))
                            .and(Spec.<ImpTradeDetail>hasField("", tradeDetail.getTradeType()))
                            .and(Spec.<ImpTradeDetail>hasField("", tradeDetail.getYear()))
                            .and(Spec.<ImpTradeDetail>hasField("", tradeDetail.getMonth()))
                            , pageRequest);
            return (Page<T>) pageImpDetail;
        }

        if (tradeDetail instanceof ExpTradeDetail) {
            Page<ExpTradeDetail> pageExpDetail = expTradeDetailDao
                    .findAll(Specifications
                            .where(Spec.<ExpTradeDetail>hasField("", tradeDetail.getCountry()))
                            .and(Spec.<ExpTradeDetail>hasField("", tradeDetail.getCity()))
                            .and(Spec.<ExpTradeDetail>hasField("", tradeDetail.getCustoms()))
                            .and(Spec.<ExpTradeDetail>hasField("", tradeDetail.getCompanyType()))
                            .and(Spec.<ExpTradeDetail>hasField("", tradeDetail.getTransportation()))
                            .and(Spec.<ExpTradeDetail>hasField("", tradeDetail.getTradeType()))
                            .and(Spec.<ExpTradeDetail>hasField("", tradeDetail.getYear()))
                            .and(Spec.<ExpTradeDetail>hasField("", tradeDetail.getMonth()))
                            , pageRequest);
            return (Page<T>) pageExpDetail;
        }

        return null;
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
