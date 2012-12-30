package com.oilchem.trade.service.impl;

import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.DocBean;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.dao.count.*;
import com.oilchem.trade.domain.count.*;
import com.oilchem.trade.domain.abstrac.DetailCount;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.DetailCountService;
import com.oilchem.trade.util.DynamicSpecifications;
import com.oilchem.trade.util.QueryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.oilchem.trade.bean.DocBean.ImpExpType.export_type;
import static com.oilchem.trade.bean.DocBean.ImpExpType.import_type;
import static com.oilchem.trade.util.QueryUtils.Type.LIKE;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-7
 * Time: 上午10:49
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DetailCountServiceImpl implements DetailCountService {

    Logger logger = LoggerFactory.getLogger(DetailCountServiceImpl.class);

    @Resource
    ImpDetailCountDao impDetailCountDao;
    @Resource
    ExpDetailCountDao expDetailCountDao;
    @Resource
    ExpDetailCompanytypeDao expDetailCompanytypeDao;
    @Resource
    ExpDetailTradetypeDao expDetailTradetypeDao;
    @Resource
    ImpDetailCompanytypeDao impDetailCompanytypeDao;
    @Resource
    ImpDetailTradetypeDao impDetailTradetypeDao;


    @Autowired
    CommonService commonService;

    /**
     * 获得  ImpDetailCount 列表
     * @param impDetailCount
     * @param commonDto
     * @param yearMonthDto
     * @param pageRequest
     * @return
     */
    public Page<ImpDetailCount> findImpWithCriteria(
            ImpDetailCount impDetailCount, CommonDto commonDto,
            YearMonthDto yearMonthDto, PageRequest pageRequest) {

        final List<QueryUtils.PropertyFilter> filterList = getdetailQueryProps(impDetailCount, commonDto);

        filterList.addAll(commonService.getYearMonthQueryProps(yearMonthDto));

        Specification<ImpDetailCount> spec = DynamicSpecifications.<ImpDetailCount>byPropertyFilter(filterList, ImpDetailCount.class);
        Page<ImpDetailCount> detailCountPage = impDetailCountDao.findAll(spec, pageRequest);
        return detailCountPage;

    }

    public List<ImpDetailCount> findImpWithCriteria(
            ImpDetailCount impDetailCount, CommonDto commonDto,
            YearMonthDto yearMonthDto) {

        final List<QueryUtils.PropertyFilter> filterList = getdetailQueryProps(impDetailCount, commonDto);

        filterList.addAll(commonService.getYearMonthQueryProps(yearMonthDto));

        Specification<ImpDetailCount> spec = DynamicSpecifications.<ImpDetailCount>byPropertyFilter(filterList, ImpDetailCount.class);
        List<ImpDetailCount> detailCountList = impDetailCountDao.findAll(spec);
        return detailCountList;

    }

    /**
     * 获得  ExpDetailCount 列表
     * @param expDetailCount
     * @param commonDto
     * @param yearMonthDto
     * @param pageRequest
     * @return
     */
    public Page<ExpDetailCount> findExpWithCriteria(
            ExpDetailCount expDetailCount, CommonDto commonDto,
            YearMonthDto yearMonthDto, PageRequest pageRequest) {

        final List<QueryUtils.PropertyFilter> filterList = getdetailQueryProps(expDetailCount, commonDto);

        filterList.addAll(commonService.getYearMonthQueryProps(yearMonthDto));

        Specification<ExpDetailCount> spec = DynamicSpecifications.<ExpDetailCount>byPropertyFilter(filterList, ExpDetailCount.class);
        Page<ExpDetailCount> detailCountPage = expDetailCountDao.findAll(spec, pageRequest);
        return detailCountPage;
    }

    public List<ExpDetailCount> findExpWithCriteria(
            ExpDetailCount expDetailCount, CommonDto commonDto,
            YearMonthDto yearMonthDto) {

        final List<QueryUtils.PropertyFilter> filterList = getdetailQueryProps(expDetailCount, commonDto);

        filterList.addAll(commonService.getYearMonthQueryProps(yearMonthDto));

        Specification<ExpDetailCount> spec = DynamicSpecifications.<ExpDetailCount>byPropertyFilter(filterList, ExpDetailCount.class);
        List<ExpDetailCount> detailCountList = expDetailCountDao.findAll(spec);
        return detailCountList;
    }

    /**
     * 获得查询条件列表
     * @param detailCount
     * @param commonDto
     * @return
     */
    public List<QueryUtils.PropertyFilter>
    getdetailQueryProps(DetailCount detailCount, CommonDto commonDto){

        List<QueryUtils.PropertyFilter> propList = new ArrayList<QueryUtils.PropertyFilter>();
        if (isNotBlank(detailCount.getProductCode())) {
            propList.add(new QueryUtils.PropertyFilter("productCode", detailCount.getProductCode(), LIKE));
        }
        if (isNotBlank(detailCount.getProductName())) {
            propList.add(new QueryUtils.PropertyFilter("productName", detailCount.getProductName(), LIKE));
        }
        return propList;
    }

    /**
     * 生成月统计数据
     * @param countYear
     * @param countMonth
     * @param countImpExp
     */
    @Transactional
    public void genDetailCount(String countYear, String countMonth, Integer countImpExp) {

        String yearMonth = countYear+ DocBean.Config.yearmonth_split.value()+countMonth;

        if (countImpExp.equals(import_type.ordinal())) {

            impDetailCountDao.deleteByYearMonth(yearMonth);
            impDetailCountDao.insertByYearMonth(yearMonth);

        }
        if(countImpExp.equals(export_type.ordinal())){

            expDetailCountDao.deleteByYearMonth(yearMonth);
            expDetailCountDao.insertByYearMonth(yearMonth);

        }

    }

    /**
     * 生成所有统计数据
     */
    @Transactional
    public void genAllDetailCount() {

        impDetailCountDao.deleteAll();
        impDetailCountDao.insertAll();

        expDetailCountDao.deleteAll();
        expDetailCountDao.insertAll();


    }

    //-----------------------------------------------------------------------

    public Page<ImpDetailTradetype> findImpWithCriteria(
            ImpDetailTradetype impDetailTradetype, CommonDto commonDto,
            YearMonthDto yearMonthDto, PageRequest pageRequest) {

        final List<QueryUtils.PropertyFilter> filterList = getdetailQueryProps(impDetailTradetype, commonDto);

        filterList.addAll(commonService.getYearMonthQueryProps(yearMonthDto));

        Specification<ImpDetailTradetype> spec = DynamicSpecifications.<ImpDetailTradetype>byPropertyFilter(filterList, ImpDetailTradetype.class);
        Page<ImpDetailTradetype> detailCountPage = impDetailTradetypeDao.findAll(spec, pageRequest);
        return detailCountPage;

    }

    public Page<ExpDetailTradetype> findImpWithCriteria(
            ExpDetailTradetype expDetailTradetype, CommonDto commonDto,
            YearMonthDto yearMonthDto, PageRequest pageRequest) {

        final List<QueryUtils.PropertyFilter> filterList = getdetailQueryProps(expDetailTradetype, commonDto);

        filterList.addAll(commonService.getYearMonthQueryProps(yearMonthDto));

        Specification<ExpDetailTradetype> spec = DynamicSpecifications.<ExpDetailTradetype>byPropertyFilter(filterList, ExpDetailTradetype.class);
        Page<ExpDetailTradetype> detailCountPage = expDetailTradetypeDao.findAll(spec, pageRequest);
        return detailCountPage;

    }

    /**
     * 按贸易方式生成月统计数据
     * @param countYear
     * @param countMonth
     * @param countImpExp
     */
    @Transactional
    public void genDetailTradeType(
            String countYear, String countMonth, Integer countImpExp) {

        String yearMonth = countYear+ DocBean.Config.yearmonth_split.value()+countMonth;

        if (countImpExp.equals(import_type.ordinal())) {

            impDetailTradetypeDao.deleteByYearMonth(yearMonth);
            impDetailTradetypeDao.insertByYearMonth(yearMonth);

        }
        if(countImpExp.equals(export_type.ordinal())){

            expDetailTradetypeDao.deleteByYearMonth(yearMonth);
            expDetailTradetypeDao.insertByYearMonth(yearMonth);

        }

    }

    //-----------------------------------------------------------------

    /**
     * 按贸易方式生成所有统计
     */
    @Transactional
    public void genAllDetailTradeType() {
        impDetailTradetypeDao.deleteAll();
        impDetailTradetypeDao.insertAll();

        impDetailTradetypeDao.deleteAll();
        impDetailTradetypeDao.insertAll();
    }


    public Page<ImpDetailCompanytype> findImpWithCriteria(
            ImpDetailCompanytype impDetailCompanytype, CommonDto commonDto,
            YearMonthDto yearMonthDto, PageRequest pageRequest) {

        final List<QueryUtils.PropertyFilter> filterList = getdetailQueryProps(impDetailCompanytype, commonDto);

        filterList.addAll(commonService.getYearMonthQueryProps(yearMonthDto));

        Specification<ImpDetailCompanytype> spec = DynamicSpecifications.<ImpDetailCompanytype>byPropertyFilter(filterList, ImpDetailCompanytype.class);
        Page<ImpDetailCompanytype> detailCountPage = impDetailCompanytypeDao.findAll(spec, pageRequest);
        return detailCountPage;

    }

    public Page<ExpDetailCompanytype> findImpWithCriteria(
            ExpDetailCompanytype expDetailCompanytype, CommonDto commonDto,
            YearMonthDto yearMonthDto, PageRequest pageRequest) {

        final List<QueryUtils.PropertyFilter> filterList = getdetailQueryProps(expDetailCompanytype, commonDto);

        filterList.addAll(commonService.getYearMonthQueryProps(yearMonthDto));

        Specification<ExpDetailCompanytype> spec = DynamicSpecifications.<ExpDetailCompanytype>byPropertyFilter(filterList, ExpDetailCompanytype.class);
        Page<ExpDetailCompanytype> detailCountPage = expDetailCompanytypeDao.findAll(spec, pageRequest);
        return detailCountPage;

    }

    /**
     * 按企业性质成月统计数据
     * @param countYear
     * @param countMonth
     * @param countImpExp
     */
    @Transactional
    public void genDetailCompanyType(String countYear, String countMonth, Integer countImpExp) {

        String yearMonth = countYear+ DocBean.Config.yearmonth_split.value()+countMonth;

        if (countImpExp.equals(import_type.ordinal())) {

            impDetailCompanytypeDao.deleteByYearMonth(yearMonth);
            impDetailCompanytypeDao.insertByYearMonth(yearMonth);

        }
        if(countImpExp.equals(export_type.ordinal())){

            expDetailCompanytypeDao.deleteByYearMonth(yearMonth);
            expDetailCompanytypeDao.insertByYearMonth(yearMonth);

        }

    }

    /**
     * 按企业性质生成所有统计
     */
    @Transactional
    public void genAllDetailCompanyType() {
        impDetailCompanytypeDao.deleteAll();
        impDetailCompanytypeDao.insertAll();

        impDetailCompanytypeDao.deleteAll();
        impDetailCompanytypeDao.insertAll();
    }

}
