package com.oilchem.trade.view.controller;

import com.google.gson.Gson;
import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.DocBean;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.domain.abstrac.DetailCount;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.domain.condition.Product;
import com.oilchem.trade.domain.count.ExpDetailCount;
import com.oilchem.trade.domain.count.ImpDetailCount;
import com.oilchem.trade.domain.detail.ExpTradeDetail;
import com.oilchem.trade.domain.detail.ImpTradeDetail;
import com.oilchem.trade.domain.sum.ExpTradeSum;
import com.oilchem.trade.domain.sum.ImpTradeSum;
import com.oilchem.trade.service.DetailCountService;
import com.oilchem.trade.service.FilterService;
import com.oilchem.trade.service.TradeDetailService;
import com.oilchem.trade.service.TradeSumService;
import com.oilchem.trade.util.CommonUtil;
import com.oilchem.trade.util.DynamicSpecifications;
import com.oilchem.trade.util.QueryUtils;
import com.oilchem.trade.view.ExcelView;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.oilchem.trade.bean.DocBean.ImpExpType.export_type;
import static com.oilchem.trade.bean.DocBean.ImpExpType.import_type;
import static com.oilchem.trade.util.CommonUtil.getYYYYMMDDHHMMSS;
import static com.oilchem.trade.view.ExcelView.ExlBean;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-12-21
 * Time: 下午12:27
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/manage")
public class ExportDataController extends CommonController {

    @Autowired
    DetailCountService detailCountService;
    @Autowired
    TradeDetailService tradeDetailService;
    @Autowired
    TradeSumService tradeSumService;

    @RequestMapping("/exportDetailCount")
    public ModelAndView exportDetailCount(String param, Integer expPageSize, Integer expPageNumber) {

        ExportDto exportDto = new Gson().fromJson(param, ExportDto.class);
        CommonDto commonDto = new CommonDto().setPageNumber(expPageNumber).setPageSize(expPageSize);

        Integer impExpType = 0;
        if (exportDto.impExpType.trim().equals(export_type.getValue())) {
            impExpType = 1;
        }

        YearMonthDto yearMonthDto = new YearMonthDto(exportDto.month, impExpType,
                exportDto.lowYear, exportDto.lowMonth, exportDto.highYear, exportDto.highMonth);

        String sheetName = "明细统计_" + getYYYYMMDDHHMMSS(new Date());
        String excelName = sheetName + ".xls";
        List<ExlBean> exlBeanList = getDetailCountExlBeanList();

        if (impExpType.equals(import_type.ordinal())) {
            if ("all".equals(exportDto.getImpType())) {
                List<ImpDetailCount> impDetailCounts = detailCountService.findImpWithCriteria(
                        new ImpDetailCount(exportDto.getProductName()), commonDto, yearMonthDto);
                return new ModelAndView(new ExcelView<ImpDetailCount>(excelName, sheetName, impDetailCounts, exlBeanList, DetailCount.class));
            } else {
                Page<ImpDetailCount> impDetailCounts = detailCountService.findImpWithCriteria(
                        new ImpDetailCount(exportDto.getProductName()), commonDto, yearMonthDto, getPageRequest(commonDto));
                return new ModelAndView(new ExcelView<ImpDetailCount>(excelName, sheetName, impDetailCounts.getContent(), exlBeanList, DetailCount.class));
            }
        }
        if (impExpType.equals(export_type.ordinal())) {
            if ("all".equals(exportDto.getImpType())) {
                List<ExpDetailCount> expDetailCounts = detailCountService.findExpWithCriteria(
                        new ExpDetailCount(exportDto.getProductName()), commonDto, yearMonthDto);
                return new ModelAndView(new ExcelView<ExpDetailCount>(excelName, sheetName, expDetailCounts, exlBeanList, DetailCount.class));
            } else {
                Page<ExpDetailCount> expDetailCounts = detailCountService
                        .findExpWithCriteria(new ExpDetailCount(exportDto.getProductName()),
                                commonDto, yearMonthDto, getPageRequest(commonDto));
                return new ModelAndView(new ExcelView<ExpDetailCount>(excelName, sheetName, expDetailCounts.getContent(), exlBeanList, DetailCount.class));
            }
        }
        return null;
    }

    @RequestMapping("toExportExl")
    public String  toExportExl(Model model){

        return "manage/trade/export";
    }

    @RequestMapping("/exportDetail")
    public ModelAndView exportDetail(String param, Integer expPageSize, Integer expPageNumber) {

        ExportDto exportDto = new Gson().fromJson(param, ExportDto.class);
        CommonDto commonDto = new CommonDto().setPageNumber(expPageNumber).setPageSize(expPageSize);

        Integer impExpType = 0;
        if (exportDto.impExpType.trim().equals(export_type.getValue())) {
            impExpType = 1;
        }

        YearMonthDto yearMonthDto = new YearMonthDto(exportDto.month, impExpType,
                exportDto.lowYear, exportDto.lowMonth, exportDto.highYear, exportDto.highMonth);

        String sheetName = "明细表_" + getYYYYMMDDHHMMSS(new Date());
        String excelName = sheetName + ".xls";
        List<ExlBean> exlBeanList = getDetailExlBeanList();

        if (impExpType.equals(import_type.ordinal())) {
            Page<ImpTradeDetail> impDetails = tradeDetailService.findImpWithCriteria(
                    new ImpTradeDetail(exportDto.getProductName()), commonDto, yearMonthDto, getPageRequest(commonDto));
            return new ModelAndView(new ExcelView<ImpTradeDetail>(excelName, sheetName, impDetails.getContent(), exlBeanList, TradeDetail.class));
        }
        if (impExpType.equals(export_type.ordinal())) {
            Page<ExpTradeDetail> expDetails = tradeDetailService.findExpWithCriteria(
                    new ExpTradeDetail(exportDto.getProductName()), commonDto, yearMonthDto, getPageRequest(commonDto));
            return new ModelAndView(new ExcelView<ExpTradeDetail>(excelName, sheetName, expDetails.getContent(), exlBeanList, TradeDetail.class));
        }

        return null;
    }


    @RequestMapping("/exportSum")
    public ModelAndView exportSum(String param, Integer expPageSize, Integer expPageNumber) {

        ExportDto exportDto = new Gson().fromJson(param, ExportDto.class);
        CommonDto commonDto = new CommonDto().setPageNumber(expPageNumber).setPageSize(expPageSize);

        Integer impExpType = 0;
        if (exportDto.impExpType.trim().equals(export_type.getValue())) {
            impExpType = 1;
        }

        YearMonthDto yearMonthDto = new YearMonthDto(exportDto.month, impExpType,
                exportDto.lowYear, exportDto.lowMonth, exportDto.highYear, exportDto.highMonth);

        String sheetName = "总表_" + getYYYYMMDDHHMMSS(new Date());
        String excelName = sheetName + ".xls";
        List<ExlBean> exlBeanList = getSumExlBeanList();

        //进口
        if (impExpType.equals(import_type.ordinal())) {
            if ("all".equals(exportDto.getImpType())) {
                List<ImpTradeSum> impTradeSums = tradeSumService.findImpWithCriteria(
                        new ImpTradeSum(exportDto.getProductName()), commonDto, yearMonthDto);
                if (impTradeSums.size() > 65535) {
                    impTradeSums = impTradeSums.subList(0, 65534);
                }
                return new ModelAndView(new ExcelView<ImpTradeSum>(excelName, sheetName, impTradeSums, exlBeanList, TradeSum.class));
            } else {
                Page<ImpTradeSum> impTradeSums = tradeSumService.findImpWithCriteria(
                        new ImpTradeSum(exportDto.getProductName()), commonDto, yearMonthDto, getPageRequest(commonDto));
                return new ModelAndView(new ExcelView<ImpTradeSum>(excelName, sheetName, impTradeSums.getContent(), exlBeanList, TradeSum.class));
            }

        }

        //出口
        if (impExpType.equals(export_type.ordinal())) {
            if ("all".equals(exportDto.getImpType())) {
                List<ExpTradeSum> expTradeSums = tradeSumService.findExpWithCriteria(
                        new ExpTradeSum(exportDto.getProductName()), commonDto, yearMonthDto);
                return new ModelAndView(new ExcelView<ExpTradeSum>(excelName, sheetName, expTradeSums, exlBeanList, TradeSum.class));
            } else {
                Page<ExpTradeSum> expTradeSums = tradeSumService.findExpWithCriteria(
                        new ExpTradeSum(exportDto.getProductName()), commonDto, yearMonthDto, getPageRequest(commonDto));
                return new ModelAndView(new ExcelView<ExpTradeSum>(excelName, sheetName, expTradeSums.getContent(), exlBeanList, TradeSum.class));
            }
        }

        return null;
    }


    @Autowired
    FilterService filterService;

    @RequestMapping("/exportProduct")
    public ModelAndView exportProduct(String param, Integer expPageSize, Integer expPageNumber) {
        ExportDto exportDto = new Gson().fromJson(param, ExportDto.class);
        CommonDto commonDto = new CommonDto().setPageNumber(expPageNumber).setPageSize(expPageSize);

        YearMonthDto yearMonthDto = new YearMonthDto(exportDto.month, null,
                exportDto.lowYear, exportDto.lowMonth, exportDto.highYear, exportDto.highMonth);

        String sheetName = "产品表_" + getYYYYMMDDHHMMSS(new Date());
        String excelName = sheetName + ".xls";
        List<ExlBean> exlBeanList = getProductExlBeanList();

        if ("all".equals(exportDto.getImpType())) {
            List<Product> productList = filterService.findProdWithCriteria(
                    new Product(exportDto.getProductName()), commonDto, yearMonthDto);
            if (productList.size() > 65535) {
                productList = productList.subList(0, 65534);
            }
            return new ModelAndView(new ExcelView<Product>(excelName, sheetName, productList, exlBeanList));
        } else {
            Page<Product> productList = filterService.findProdWithCriteria(
                    new Product(exportDto.getProductName()), commonDto, yearMonthDto, getPageRequest(commonDto));
            return new ModelAndView(new ExcelView<Product>(excelName, sheetName, productList.getContent(), exlBeanList));
        }
    }

    private List<ExlBean> getProductExlBeanList() {
        List<ExlBean> exlBeanList = new ArrayList<ExlBean>();
        exlBeanList.add(new ExlBean("产品代码", "productCode"));
        exlBeanList.add(new ExlBean("产品名称", "productName"));
        exlBeanList.add(new ExlBean("类型代码", "typeCode"));
        exlBeanList.add(new ExlBean("产品类型", "productType"));
        return exlBeanList;
    }


    private List<ExlBean> getDetailExlBeanList() {
        List<ExlBean> exlBeanList = new ArrayList<ExlBean>();
        exlBeanList.add(new ExlBean("年月", "yearMonth"));
        exlBeanList.add(new ExlBean("产品代码", "productCode"));
        exlBeanList.add(new ExlBean("产品名称", "productName"));
        exlBeanList.add(new ExlBean("产品类型", "productType"));

        exlBeanList.add(new ExlBean("贸易方式", "tradeType"));
        exlBeanList.add(new ExlBean("企业性质", "companyType"));
        exlBeanList.add(new ExlBean("运输方式", "transportation"));
        exlBeanList.add(new ExlBean("海关", "customs"));
        exlBeanList.add(new ExlBean("城市", "city"));
        exlBeanList.add(new ExlBean("产销国家", "country"));
        exlBeanList.add(new ExlBean("数量", "amount"));
        exlBeanList.add(new ExlBean("单位", "unit"));
        exlBeanList.add(new ExlBean("美元价值", "amountMoney"));
        exlBeanList.add(new ExlBean("单价", "unitPrice"));
        return exlBeanList;
    }


    private List<ExlBean> getDetailCountExlBeanList() {
        List<ExlBean> exlBeanList = new ArrayList<ExlBean>();
        exlBeanList.add(new ExlBean("年月", "yearMonth"));
        exlBeanList.add(new ExlBean("产品代码", "productCode"));
        exlBeanList.add(new ExlBean("产品名称", "productName"));
        exlBeanList.add(new ExlBean("数量", "num"));
        exlBeanList.add(new ExlBean("单位", "unit"));
        exlBeanList.add(new ExlBean("美元价值", "money"));
        exlBeanList.add(new ExlBean("均价", "unitPrice"));
        return exlBeanList;
    }


    private List<ExlBean> getSumExlBeanList() {
        List<ExlBean> exlBeanList = new ArrayList<ExlBean>();
        exlBeanList.add(new ExlBean("年月", "yearMonth"));
        exlBeanList.add(new ExlBean("产品代码", "productCode"));
        exlBeanList.add(new ExlBean("产品名称", "productName"));
        exlBeanList.add(new ExlBean("产品类型", "sumType"));
        exlBeanList.add(new ExlBean("当月数量", "numMonth"));
        exlBeanList.add(new ExlBean("累计总数量", "numSum"));
        exlBeanList.add(new ExlBean("当月金额(万美元)", "moneyMonth"));
        exlBeanList.add(new ExlBean("累计总金额(万美元)", "moneySum"));
        exlBeanList.add(new ExlBean("当月平均价格(美元/T)", "avgPriceMonth"));
        exlBeanList.add(new ExlBean("累计平均价格(美元/T)", "avgPriceSum"));
        exlBeanList.add(new ExlBean("与上月数量增长比(%)", "pm"));
        exlBeanList.add(new ExlBean("与上年同月数量增长比(%)", "py"));
        exlBeanList.add(new ExlBean("与上年同期数量增长比(%)", "pq"));
        return exlBeanList;
    }

    public static class ExportDto {

        Integer lowYear;
        Integer lowMonth;
        Integer highYear;
        Integer highMonth;
        Integer month;
        String impExpType;
        String productName;
        String exlName;
        String impType;

        public Integer getLowYear() {
            return lowYear;
        }

        public ExportDto setLowYear(Integer lowYear) {
            this.lowYear = lowYear;
            return this;
        }

        public Integer getLowMonth() {
            return lowMonth;
        }

        public ExportDto setLowMonth(Integer lowMonth) {
            this.lowMonth = lowMonth;
            return this;
        }

        public Integer getHighYear() {
            return highYear;
        }

        public ExportDto setHighYear(Integer highYear) {
            this.highYear = highYear;
            return this;
        }

        public Integer getHighMonth() {
            return highMonth;
        }

        public ExportDto setHighMonth(Integer highMonth) {
            this.highMonth = highMonth;
            return this;
        }

        public Integer getMonth() {
            return month;
        }

        public ExportDto setMonth(Integer month) {
            this.month = month;
            return this;
        }

        public String getImpExpType() {
            return impExpType;
        }

        public ExportDto setImpExpType(String impExpType) {
            this.impExpType = impExpType;
            return this;
        }

        public String getProductName() {
            return productName;
        }

        public ExportDto setProductName(String productName) {
            this.productName = productName;
            return this;
        }

        public String getExlName() {
            return exlName;
        }

        public void setExlName(String exlName) {
            this.exlName = exlName;
        }

        public String getImpType() {
            return impType;
        }

        public void setImpType(String impType) {
            this.impType = impType;
        }
    }

}
