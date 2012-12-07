package com.oilchem.trade.service.impl;

import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.DocBean;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.dao.ExpDetailCountDao;
import com.oilchem.trade.dao.ImpDetailCountDao;
import com.oilchem.trade.domain.ExpDetailCount;
import com.oilchem.trade.domain.ImpDetailCount;
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
}
