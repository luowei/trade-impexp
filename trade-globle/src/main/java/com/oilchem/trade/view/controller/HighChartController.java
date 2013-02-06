package com.oilchem.trade.view.controller;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.ProductCount;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.service.ChartService;
import com.oilchem.trade.service.HighChartService;
import com.oilchem.trade.util.CommonUtil;
import com.oilchem.trade.util.EHCacheUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

import static com.oilchem.trade.bean.DocBean.Config.chart_height;
import static com.oilchem.trade.bean.DocBean.Config.chart_width;
import static com.oilchem.trade.bean.DocBean.Config.max_monthes;
import static com.oilchem.trade.util.CommonUtil.getYYYYMMDDHHMMSS;
import static com.oilchem.trade.util.EHCacheUtil.getValue;
import static com.oilchem.trade.util.EHCacheUtil.setValue;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-12-14
 * Time: 下午4:08
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/manage/hchart")
public class HighChartController extends CommonController {

    @Autowired
    ChartService chartService;
    @Autowired
    HighChartService highChartService;

    //--------------------------------------自然月统计------------------------------------

    @RequestMapping("/toNMDetail")
    public String naturalmonthdetail(){
        return "manage/trade/count/naturalmonthdetail";
    }

    @RequestMapping("/NMDetail")
    public String naturalmonthdetail(Model model, YearMonthDto yearMonthDto,
                                     ProductCount productCount,RedirectAttributes redirectAttrs) {
        StringBuffer message = new StringBuffer();
        if(productCount.getAutoproductCode()==null){
            message.append("请选择要查询的产品");
            addRedirectError(redirectAttrs, message.toString().toString());
            return "redirect:/manage/hchart/toNMDetail";
        }

        List<String> yearMonths = highChartService.getYearMonthLabels(yearMonthDto);
        String productCode = substringBefore(productCount.getAutoproductCode(), "->").trim();
        String productName = substringAfter(productCount.getAutoproductCode(), "->");

        Boolean invalid = yearMonths==null || isBlank(productCode) ||
                isBlank(productCount.getCondition()) || yearMonthDto.getImpExpType() == null;

        if (invalid) {
            message.append("请选择要查询的进出类型,产品或统计的类型 ");
        }
        if (invalid || yearMonths.size() > Integer.valueOf(max_monthes.value()).intValue() || yearMonths.size() < 1) {
            addRedirectError(redirectAttrs, message.append("  月份不能多于"+max_monthes.value()+"小于1  操作不合理").toString());
            return "redirect:/manage/hchart/toNMDetail";
        }

        List<ProductCount> productCountList = new ArrayList<ProductCount>();
        List<String> seriesList = highChartService.getSeriesJson(yearMonthDto, productCount, yearMonths,
                productCode, productCountList);

        String hhmmssRand = getYYYYMMDDHHMMSS(new Date()).substring(8)+new Random().nextInt(1000);

        yearMonth2Model(model, yearMonthDto)
                .addAttribute("seriesList", seriesList)
                .addAttribute("productCountList", productCountList)
                .addAttribute("unit",!productCountList.isEmpty()?productCountList.get(0).getUnit():null)
                .addAttribute("productCode",productCode)
                .addAttribute("productName", StringUtils.substringBefore(productName, "("))
                .addAttribute("yearMonths", new Gson().toJson(yearMonths))
                .addAttribute("sumYear",yearMonthDto.getSumYear())
                .addAttribute("beginSumMonth",yearMonthDto.getBeginSumMonth())
                .addAttribute("endSumMonth",yearMonthDto.getEndSumMonth())
                .addAttribute("contentTitle", "明细产品按自然月统计图表")
                .addAttribute("size", seriesList.size())
                .addAttribute("width", chart_width.value())
                .addAttribute("height", chart_height.value())
                .addAttribute("productCount", productCount)
                .addAttribute("hhmmssRand",hhmmssRand);

        setValue("productChart", "product_" + productCode + "_" + hhmmssRand, model, 1800);

        return "manage/trade/count/naturalmonthdetail";
    }

    //----------------------累计月统计-------------------------------------

    @RequestMapping("/sumMonthdetail")
    public String sumMonthdetail(Model model, YearMonthDto yearMonthDto,
                                     ProductCount productCount,RedirectAttributes redirectAttrs) {
        StringBuffer message = new StringBuffer();
        if(productCount.getAutoproductCode()==null){
            message.append("请选择要查询的产品");
            addRedirectError(redirectAttrs, message.toString().toString());
            return "redirect:/manage/hchart/toNMDetail";
        }

        List<String> yearMonths = highChartService.getSumYearMonthLabels(yearMonthDto);
        String productCode = substringBefore(productCount.getAutoproductCode(), "->").trim();
        String productName = substringAfter(productCount.getAutoproductCode(), "->");

        Boolean invalid = yearMonths==null || isBlank(productCode) ||
                isBlank(productCount.getCondition()) || yearMonthDto.getImpExpType() == null;

        if (invalid) {
            message.append("请选择要查询的进出类型,产品或统计的类型 ");
        }
        if (invalid || yearMonths.size() < 1) {
            addRedirectError(redirectAttrs, message.append("  月份不能小于1  操作不合理").toString());
            return "redirect:/manage/hchart/toNMDetail";
        }

        List<ProductCount> productCountList =  highChartService.getSumCountList(yearMonthDto, productCount, yearMonths,productCode);

        String hhmmssRand = getYYYYMMDDHHMMSS(new Date()).substring(8)+new Random().nextInt(1000);

        yearMonth2Model(model, yearMonthDto)
                .addAttribute("productCountList", productCountList)
                .addAttribute("productCode",productCode)
                .addAttribute("productName", StringUtils.substringBefore(productName, "("))
                .addAttribute("countType", "sumCount")
                .addAttribute("sumYear",yearMonthDto.getSumYear())
                .addAttribute("beginSumMonth",yearMonthDto.getBeginSumMonth())
                .addAttribute("endSumMonth",yearMonthDto.getEndSumMonth())
                .addAttribute("unit",!productCountList.isEmpty()?productCountList.get(0).getUnit():null)
                .addAttribute("productCount", productCount);

        setValue("productChart", "product_" + productCode + "_" + hhmmssRand, model, 1800);

        return "manage/trade/count/naturalmonthdetail";
    }

    @RequestMapping("/showNMChart/{productCode}/{hhmmssRand}")
    public String showProductChart(Model model, @PathVariable String productCode,
                                   @PathVariable String hhmmssRand,RedirectAttributes redirectAttrs) {

        Object modelObj = getValue("productChart", "product_" + productCode+"_"+hhmmssRand);
        if (modelObj != null) {
            model.addAllAttributes(((Model) modelObj).asMap());
        }
        if(isBlank(hhmmssRand)){
            redirectAttrs.addAttribute("error","未找到曲线数据");
            return "redirect::/manage/hchart/toNMDetail";
        }
        return "manage/trade/chart/detailchart";
    }

    //-----------------------------同期月统计------------------------------------

    @RequestMapping("/toSMDetail")
    public String sameMonthDetail(){
        return "manage/trade/count/samemonthdetail";
    }

    @RequestMapping("/SMDetail")
    public String  samemonthdetail(Model model, YearMonthDto yearMonthDto,
                                   ProductCount productCount, RedirectAttributes redirectAttrs) {
        StringBuffer message = new StringBuffer();
        if(productCount.getAutoproductCode()==null){
            message.append("请选择要查询的产品");
            addRedirectError(redirectAttrs, message.toString().toString());
            return "redirect:/manage/hchart/toSMDetail";
        }

        List<String> yearMonths = highChartService.getSameMonthLabels(yearMonthDto);
        String productCode = substringBefore(productCount.getAutoproductCode(), "->").trim();
        String productName = substringAfter(productCount.getAutoproductCode(), "->");

        Boolean invalid = yearMonths==null || isBlank(productCode) ||
                isBlank(productCount.getCondition()) || yearMonthDto.getImpExpType() == null;

        if (invalid) {
            message.append("操作不合理 请选择要查询的进出类型,产品或统计条件 或月份不能多于"+max_monthes.value());
            addRedirectError(redirectAttrs, message.toString().toString());
            return "redirect:/manage/hchart/toSMDetail";
        }

        List<ProductCount> productCountList = new ArrayList<ProductCount>();
        List<String> seriesList = highChartService.getSMSeriesJson(yearMonthDto, productCount, yearMonths,
                productCode, productCountList);

        String hhmmssRand = getYYYYMMDDHHMMSS(new Date()).substring(8)+new Random().nextInt(1000);

        yearMonth2Model(model, yearMonthDto)
                .addAttribute("seriesList", seriesList)
                .addAttribute("productCountList", productCountList)
                .addAttribute("unit",!productCountList.isEmpty()?productCountList.get(0).getUnit():null)
                .addAttribute("productName", StringUtils.substringBefore(productName, "("))
                .addAttribute("yearMonths", new Gson().toJson(yearMonths))
                .addAttribute("contentTitle", "明细产品按同期月统计图表")
                .addAttribute("size", seriesList.size())
                .addAttribute("width", chart_width.value())
                .addAttribute("height", chart_height.value())
                .addAttribute("productCount", productCount)
                .addAttribute("hhmmssRand",hhmmssRand);

        setValue("productChart", "product_" + productCode+"_"+hhmmssRand, model, 1800);


        return "manage/trade/count/samemonthdetail";
    }


    @RequestMapping("/showSMChart/{productCode}/{hhmmssRand}")
    public String showDetailCompareChart(Model model, @PathVariable String productCode,
                                         @PathVariable String hhmmssRand,RedirectAttributes redirectAttrs) {

        Object modelObj = getValue("productChart", "product_" + productCode+"_"+hhmmssRand);
        if (modelObj != null) {
            model.addAllAttributes(((Model) modelObj).asMap());
        }if(isBlank(hhmmssRand)){
            redirectAttrs.addAttribute("error","未找到曲线数据");
            return "redirect::/manage/hchart/toSMDetail";
        }

        return "manage/trade/chart/smdetailchart";
    }

    //-----------------------------------------明细总统计----------------------------------------------

    @RequestMapping("/detailCountChart")
    public String getDetailCountChartData(Model model, YearMonthDto yearMonthDto,
                                          CommonDto commonDto,RedirectAttributes redirectAttrs) {

        //-----------------参数验证------------------------
        StringBuilder errorMsg = new StringBuilder();
        Boolean invalidate = validateChartDto(yearMonthDto, commonDto, errorMsg);
        if(invalidate){
            addRedirectError(redirectAttrs,errorMsg.toString());
            return "redirect:/manage/list/detailCount/" + (commonDto.getPageNumber() == null ? 1 : commonDto.getPageNumber());
        }

        //-----------------获取图表数据---------------------------
        List<String> names = removeDuplicateWithOrder(
                Lists.asList(commonDto.getCodes()[0], commonDto.getCodes()));

        List<String> yearMonths = highChartService.getYearMonthLabels(yearMonthDto);
        List<String> seriesList = highChartService.getChartDetailCountList(yearMonths, names, yearMonthDto.getImpExpType());

        //----------------构造返回页面参数----------------------------------
        yearMonth2Model(model, yearMonthDto)
                .addAttribute("seriesList", seriesList)
                .addAttribute("yearMonths", new Gson().toJson(yearMonths))
                .addAttribute("contentTitle", "明细总统计图表")
                .addAttribute("width", chart_width.value())
                .addAttribute("height", chart_height.value())
                .addAttribute("size", seriesList.size());

        return "manage/trade/chart/detailchart";
    }

    //------------------------------------------总表统计-----------------------------------------------

    @RequestMapping("/sumChart")
    public String sumChart(Model model, YearMonthDto yearMonthDto,
                                          CommonDto commonDto, TradeSum tradeSum,
                                          RedirectAttributes redirectAttrs) {

        //-----------------参数验证------------------------
        StringBuilder errorMsg = new StringBuilder();
        Boolean invalidate = validateChartDto(yearMonthDto, commonDto, errorMsg);
        if(invalidate){
            addRedirectError(redirectAttrs,errorMsg.toString());
            return "redirect:/manage/listsum/" + (commonDto.getPageNumber() == null ? 1 : commonDto.getPageNumber());
        }

        //-----------------获取图表数据---------------------------
        List<String> codes = removeDuplicateWithOrder(
                Lists.asList(commonDto.getCodes()[0], commonDto.getCodes()));

        List<String> yearMonths = highChartService.getYearMonthLabels(yearMonthDto);
        //月统计
        List<String> monthSeries = highChartService
                .getTradeSumSeries(yearMonths, codes, yearMonthDto.getImpExpType()).get("month");
        //累计统计
        List<String> sumSeries = highChartService
                .getTradeSumSeries(yearMonths, codes, yearMonthDto.getImpExpType()).get("num");

        //比率统计

        //----------------构造返回页面参数----------------------------------
        yearMonth2Model(model, yearMonthDto)
                .addAttribute("monthSeries", monthSeries)
                .addAttribute("sumSeries", sumSeries)
                .addAttribute("yearMonths", new Gson().toJson(yearMonths))
                .addAttribute("contentTitle", "明细总统计图表")
                .addAttribute("width", chart_width.value())
                .addAttribute("height", chart_height.value());

        return "manage/trade/chart/sumchart";
    }

    private Boolean validateChartDto(YearMonthDto yearMonthDto, CommonDto commonDto, StringBuilder errorMsg) {
        Boolean invalidate = false;

        Boolean inValidateTemp = commonDto.getCodes() == null || commonDto.getCodes().length < 1;
        if (inValidateTemp) {
            errorMsg.append("至少选择一种产品");
            invalidate = invalidate || inValidateTemp;
        }
        if(!yearMonthDto.validYearMonthRang(yearMonthDto)){
            errorMsg.append("起始年月与结束年月选择不合理,不能小于1个月大于"+max_monthes.value()+"个");
            invalidate = invalidate || true;
        }
        return invalidate;
    }

}
