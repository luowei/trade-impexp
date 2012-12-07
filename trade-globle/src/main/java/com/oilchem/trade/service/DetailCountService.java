package com.oilchem.trade.service;

import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.domain.ExpDetailCount;
import com.oilchem.trade.domain.ImpDetailCount;
import com.oilchem.trade.domain.abstrac.DetailCount;
import com.oilchem.trade.util.QueryUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.oilchem.trade.util.QueryUtils.PropertyFilter;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-7
 * Time: 上午10:48
 * To change this template use File | Settings | File Templates.
 */
public interface DetailCountService {

    /**
     * 获得  ImpDetailCount 列表
     * @param impDetailCount
     * @param commonDto
     * @param yearMonthDto
     * @param pageRequest
     * @return
     */
    Page<ImpDetailCount> findImpWithCriteria(
            ImpDetailCount impDetailCount, CommonDto commonDto,
            YearMonthDto yearMonthDto, PageRequest pageRequest);


    /**
     * 获得  ExpDetailCount 列表
     * @param expDetailCount
     * @param commonDto
     * @param yearMonthDto
     * @param pageRequest
     * @return
     */
    Page<ExpDetailCount> findExpWithCriteria(
            ExpDetailCount expDetailCount, CommonDto commonDto,
            YearMonthDto yearMonthDto, PageRequest pageRequest);

    /**
     * 获得查询条件列表
     * @param detailCount
     * @param commonDto
     * @return
     */
    List<QueryUtils.PropertyFilter>
    getdetailQueryProps(DetailCount detailCount, CommonDto commonDto);

    /**
     * 生成月统计数据
     * @param countYear
     * @param countMonth
     * @param countImpExp
     */
    void genDetailCount(String countYear, String countMonth, Integer countImpExp);

    /**
     * 生成所有统计数据
     */
    void genAllDetailCount();

}
