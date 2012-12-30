package com.oilchem.trade.service;

import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.domain.count.*;
import com.oilchem.trade.domain.abstrac.DetailCount;
import com.oilchem.trade.util.QueryUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<ImpDetailCount> findImpWithCriteria(
            ImpDetailCount impDetailCount, CommonDto commonDto,
            YearMonthDto yearMonthDto);


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

    List<ExpDetailCount> findExpWithCriteria(
            ExpDetailCount expDetailCount, CommonDto commonDto,
            YearMonthDto yearMonthDto);

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


    public Page<ImpDetailTradetype> findImpWithCriteria(
            ImpDetailTradetype impDetailTradetype, CommonDto commonDto,
            YearMonthDto yearMonthDto, PageRequest pageRequest);

    public Page<ExpDetailTradetype> findImpWithCriteria(
            ExpDetailTradetype expDetailTradetype, CommonDto commonDto,
            YearMonthDto yearMonthDto, PageRequest pageRequest);

    /**
     * 按贸易方式生成月统计数据
     * @param countYear
     * @param countMonth
     * @param countImpExp
     */
    @Transactional
    public void genDetailTradeType(
            String countYear, String countMonth, Integer countImpExp);

    /**
     * 按贸易方式生成统计
     */
    void genAllDetailTradeType();


    public Page<ImpDetailCompanytype> findImpWithCriteria(
            ImpDetailCompanytype impDetailCompanytype, CommonDto commonDto,
            YearMonthDto yearMonthDto, PageRequest pageRequest);

    public Page<ExpDetailCompanytype> findImpWithCriteria(
            ExpDetailCompanytype expDetailCompanytype, CommonDto commonDto,
            YearMonthDto yearMonthDto, PageRequest pageRequest);

    /**
     * 按企业性质成月统计数据
     * @param countYear
     * @param countMonth
     * @param countImpExp
     */
    @Transactional
    public void genDetailCompanyType(
            String countYear, String countMonth, Integer countImpExp);

    /**
     * 按企业性质生成统计
     */
    void genAllDetailCompanyType();

}
