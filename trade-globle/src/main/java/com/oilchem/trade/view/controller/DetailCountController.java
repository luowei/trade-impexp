package com.oilchem.trade.view.controller;

import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.domain.count.*;
import com.oilchem.trade.domain.abstrac.DetailCount;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.DetailCountService;
import com.oilchem.trade.service.TaskService;
import com.oilchem.trade.util.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static com.oilchem.trade.bean.DocBean.ImpExpType.export_type;
import static com.oilchem.trade.bean.DocBean.ImpExpType.import_type;
import static org.codehaus.plexus.util.StringUtils.isNotBlank;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-7
 * Time: 上午10:52
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/manage")
public class DetailCountController extends CommonController{

    @Autowired
    CommonService commonService;
    @Autowired
    DetailCountService detailCountService;
    @Autowired
    TaskService taskService;


    @RequestMapping("/list/detailCount/{pageNumber}")
    public String listDetailCount(Model model,CommonDto commonDto,
                                  YearMonthDto yearMonthDto,
                                  DetailCount detailCount) {

        Integer impExp = yearMonthDto.getImpExpType();
        if (impExp == null)
            yearMonthDto.setImpExpType(impExp = 0);

        if (impExp.equals(import_type.ordinal())) {
            Page<ImpDetailCount> impDetailCounts = detailCountService
                    .findImpWithCriteria(new ImpDetailCount(detailCount), commonDto, yearMonthDto, getPageRequest(commonDto));
            addPageInfo(model, impDetailCounts, "/manage/list/detailCount") .addAttribute("detailCountList", impDetailCounts);
        }
        if (impExp.equals(export_type.ordinal())) {
            Page<ExpDetailCount> expDetailCounts = detailCountService
                    .findExpWithCriteria(new ExpDetailCount(detailCount), commonDto, yearMonthDto, getPageRequest(commonDto));
            addPageInfo(model, expDetailCounts, "/manage/list/detailCount") .addAttribute("detailCountList", expDetailCounts);
        }

        for (QueryUtils.PropertyFilter filter : detailCountService
                .getdetailQueryProps(detailCount, commonDto)) {
            model.addAttribute(filter.getName(), filter.getValue());
        }
        yearMonth2Model(model,yearMonthDto);

        return "manage/trade/count/listdetailcount";
    }


    @RequestMapping("/list/detailTradeType/{pageNumber}")
    public String listDetailTradeType(Model model,CommonDto commonDto,
                                  YearMonthDto yearMonthDto,
                                  DetailCount detailCount) {

        Integer impExp = yearMonthDto.getImpExpType();
        if (impExp == null)
            yearMonthDto.setImpExpType(impExp = 0);

        if (impExp.equals(import_type.ordinal())) {
            Page<ImpDetailTradetype> impTradeTypelCounts = detailCountService
                    .findImpWithCriteria(new ImpDetailTradetype(detailCount), commonDto, yearMonthDto, getPageRequest(commonDto));
            addPageInfo(model, impTradeTypelCounts, "/manage/list/detailTradeType") .addAttribute("tradeTypeList", impTradeTypelCounts);
        }
        if (impExp.equals(export_type.ordinal())) {
            Page<ExpDetailTradetype> expTradeTypeCounts = detailCountService
                    .findImpWithCriteria( new ExpDetailTradetype(detailCount),  commonDto, yearMonthDto,  getPageRequest(commonDto));
            addPageInfo(model, expTradeTypeCounts, "/manage/list/detailTradeType") .addAttribute("tradeTypeList", expTradeTypeCounts);
        }

        yearMonth2Model(model,yearMonthDto);


        return "manage/trade/count/listdetailtradetype";
    }

    @RequestMapping("/list/detailCompanyType/{pageNumber}")
    public String listDetailCompanyType(Model model,CommonDto commonDto,
                                      YearMonthDto yearMonthDto,
                                      DetailCount detailCount) {

        Integer impExp = yearMonthDto.getImpExpType();
        if (impExp == null)
            yearMonthDto.setImpExpType(impExp = 0);

        if (impExp.equals(import_type.ordinal())) {
            Page<ImpDetailCompanytype> impDetailCounts = detailCountService
                    .findImpWithCriteria(new ImpDetailCompanytype(detailCount), commonDto, yearMonthDto, getPageRequest(commonDto));
            addPageInfo(model, impDetailCounts, "/manage/list/detailCompanyType") .addAttribute("companyTypeList", impDetailCounts);
        }
        if (impExp.equals(export_type.ordinal())) {
            Page<ExpDetailCompanytype> expDetailCounts = detailCountService
                    . findImpWithCriteria(  new ExpDetailCompanytype(detailCount),  commonDto, yearMonthDto,  getPageRequest(commonDto));
            addPageInfo(model, expDetailCounts, "/manage/list/detailCompanyType") .addAttribute("companyTypeList", expDetailCounts);
        }

        yearMonth2Model(model,yearMonthDto);


        return "manage/trade/count/listdetailcompanytype";
    }


    /**
     * 前往往统计数据生成页面
     * @return
     */
    @RequestMapping("/toGenCount")
    public String toGenCount(){
        return "manage/trade/count/gencount";
    }

    @RequestMapping("/genDetailCount")
    public String genDetailCount(String countYear,String countMonth,Integer countImpExp,
                                 RedirectAttributes redirectAttrs){

        Boolean validate = isNotBlank(countYear) && isNotBlank(countMonth) && countImpExp!=null;
        if (!validate) {
            addRedirectError(redirectAttrs,"请选择正确的年月");
            return "redirect:/manage/toGenCount";
        }
        try {
            detailCountService.genDetailCount(countYear,countMonth,countImpExp);
            addRedirectMessage(redirectAttrs, "生成"+countYear+"年"+countMonth+"月的统计数据**成功**");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            addRedirectError(redirectAttrs,"生成"+countYear+"年"+countMonth+"月的统计数据**失败**");
        }

        return "redirect:/manage/toGenCount";
    }

    @RequestMapping("/genDetailTradeType")
    public String genDetailTradeType(String countYear,String countMonth,Integer countImpExp,
                                 RedirectAttributes redirectAttrs){

        Boolean validate = isNotBlank(countYear) && isNotBlank(countMonth) && countImpExp!=null;
        if (!validate) {
            addRedirectError(redirectAttrs,"请选择正确的年月");
            return "redirect:/manage/list/detailCount/1";
        }
        try {
            detailCountService.genDetailCount(countYear,countMonth,countImpExp);
            addRedirectMessage(redirectAttrs, "生成"+countYear+"年"+countMonth+"月的统计数据**成功**");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            addRedirectError(redirectAttrs,"生成"+countYear+"年"+countMonth+"月的统计数据**失败**");
        }

        return "redirect:/manage/list/detailCount/1";
    }


    @RequestMapping("/genAllDetailCount")
    public String genAllDetailCount(RedirectAttributes redirectAttrs){

        try {
            taskService.genAllDetailCount();
            addRedirectMessage(redirectAttrs,"生成统计数据任务**正在后台执行**");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            addRedirectError(redirectAttrs,"生成统计数据**失败**");
        }

        return "redirect:/manage/list/detailCount/1";
    }

    @RequestMapping("/genAllDetailTradeType")
    public String genAllDetailTradeType(RedirectAttributes redirectAttrs){

        try {
            taskService.genAllDetailCount();
            addRedirectMessage(redirectAttrs,"生成按贸易方式统计数据任务**正在后台执行**");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            addRedirectError(redirectAttrs,"生成按贸易方式统计数据**失败**");
        }

        return "redirect:/manage/list/detailtradetype/1";
    }

    @RequestMapping("/genAllDetailCompanyType")
    public String genAllDetailCompanyType(RedirectAttributes redirectAttrs){

        try {
            taskService.genAllDetailCount();
            addRedirectMessage(redirectAttrs,"生成按企业性质统计数据任务**正在后台执行**");
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            addRedirectError(redirectAttrs,"生成按企业性质统计数据**失败**");
        }

        return "redirect:/manage/list/detailcompanytype/1";
    }


    /**
     * 把属性放到model中
     * @param model
     * @param detailCount
     * @param commonDto
     * @param yearMonthDto
     * @return
     */
    private Model addAtrribute2Model(Model model, DetailCount detailCount,
                                     CommonDto commonDto, YearMonthDto yearMonthDto) {

        model = yearMonth2Model(model, yearMonthDto);

        for (QueryUtils.PropertyFilter filter : detailCountService
                .getdetailQueryProps(detailCount, commonDto)) {
            model.addAttribute(filter.getName(), filter.getValue());
        }
        return model;
    }

}
