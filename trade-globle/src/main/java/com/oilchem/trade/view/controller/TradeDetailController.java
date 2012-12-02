package com.oilchem.trade.view.controller;

import com.oilchem.trade.bean.ChartData;
import com.oilchem.trade.bean.DocBean;
import com.oilchem.trade.chart.MyChart;
import com.oilchem.trade.dao.*;
import com.oilchem.trade.domain.*;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.service.ChartService;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TaskService;
import com.oilchem.trade.service.TradeDetailService;
import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.YearMonthDto;
import ofc4j.OFC;
import ofc4j.model.Chart;
import ofc4j.model.axis.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static com.oilchem.trade.bean.DocBean.Config.upload_detailzip_dir;
import static com.oilchem.trade.bean.DocBean.ImpExpType.export_type;
import static com.oilchem.trade.bean.DocBean.ImpExpType.import_type;
import static com.oilchem.trade.util.QueryUtils.*;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-8
 * Time: 下午11:52
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/manage")
public class TradeDetailController extends CommonController {

    @Autowired
    CommonService commonService;

    @Autowired
    TradeDetailService tradeDetailService;

    @Autowired
    ChartService chartService;

    @Autowired
    TaskService taskService;

//    @ModelAttribute
//    public CommonDto  createCommonDto(){
//        return new CommonDto();
//    }

    /**
     * 明细列表
     *
     * @param model
     * @param commonDto
     * @param tradeDetail
     * @return
     */
    @RequestMapping("/listdetail/{pageNumber}")
    public String listexpTradeDetail(Model model, @PathVariable Integer pageNumber,
                                     CommonDto commonDto, YearMonthDto yearMonthDto,
                                     TradeDetail tradeDetail) {
        Integer impExp = yearMonthDto.getImpExpType();
        if (impExp == null)
            yearMonthDto.setImpExpType(impExp = 0);

        if (impExp.equals(import_type.ordinal())) {
            Page<ImpTradeDetail> impTradeDetails = tradeDetailService
                    .findImpWithCriteria(new ImpTradeDetail(tradeDetail), commonDto, yearMonthDto, getPageRequest(commonDto));
            getDetailCriteriaData(addPageInfo(model, impTradeDetails, "/manage/listdetail"))
                    .addAttribute("tradeDetailList", impTradeDetails);
        }
        if (impExp.equals(export_type.ordinal())) {
            Page<ExpTradeDetail> expTradeDetails = tradeDetailService
                    .findExpWithCriteria(new ExpTradeDetail(tradeDetail), commonDto, yearMonthDto, getPageRequest(commonDto));
            getDetailCriteriaData(addPageInfo(model, expTradeDetails, "/manage/listdetail"))
                    .addAttribute("tradeDetailList", expTradeDetails);
        }

        addAtrribute2Model(model, tradeDetail, commonDto, yearMonthDto);

        return "manage/trade/listdetail";
    }

    /**
     * 进入导入数据页面
     *
     * @return
     */
    @RequestMapping("/import")
    public String importpage(Model model) {

        model.addAttribute("productTypeList", tradeDetailService.getProductList());
        return "manage/trade/import";
    }

    /**
     * 导入明细数据
     * @param file         从 DefaultMultipartHttpServletRequest获得的file
     * @param yearMonthDto 年月。。。
     * @return
     */
    @RequestMapping("/importdetail")
    public String importTradeDetail(@RequestParam("file") MultipartFile file,
                                    Model model, YearMonthDto yearMonthDto,
                                    RedirectAttributes redirectAttrs) {

        Boolean validate = (file.getOriginalFilename().endsWith(".rar")
                ||file.getOriginalFilename().endsWith(".zip"))
                && yearMonthDto.validYearMonth(yearMonthDto);
        if (!validate) {
            redirectAttrs.addFlashAttribute("message","输入的年月、或文件格式错误！");
            return "redirect:/manage/import";
        }

        StringBuffer message = new StringBuffer();
        try {
            String uploadUrl = tradeDetailService.uploadFile(file, yearMonthDto);
            message.append("文件已上传到：" + upload_detailzip_dir.value() +
                    uploadUrl.substring(uploadUrl.lastIndexOf("/")));
            taskService.unDetailPackageAndImportTask(yearMonthDto);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message.append("<br/>文件上传或数据导入发生了错误");
        }

        redirectAttrs.addFlashAttribute("message", message.toString());
        return "redirect:/manage/import";
    }

    /**
     * 获得图表
     * @param model
     * @param yearMonthDto
     * @param chartType
     * @return
     */
    @RequestMapping("/detailchart")
    public String getDetailChartData(Model model,YearMonthDto yearMonthDto,
                                     String chartType){

        List<String> codes = new ArrayList<String>();

        List<Label> labels = chartService.getYearMonthLabels(yearMonthDto);

        ChartData<TradeDetail> chartData = new ChartData<TradeDetail>().setLabels(labels);

        List<ChartData<TradeDetail>> chartDataList = tradeDetailService.getChartDetailList(codes, chartData, yearMonthDto);


        Object o = new MyChart().getDetailLineChart(chartDataList);
        List<String> chartList = new ArrayList<String>();

        if(o!=null && o instanceof List){
            for(Object chart_o:(List)o){
                if (Chart.class.isAssignableFrom(chart_o.getClass())) {
                    chartList.add(OFC.instance.render((Chart) chart_o));
                }
            }
        }
        model.addAttribute("chartList",chartList);

        return null;
    }

    /**
     * 获得查询条件数据
     * @param model 模型
     * @return
     */
    private Model getDetailCriteriaData(Model model) {

        List<City> cityList = commonService.findAllIdEntityList(CityDao.class, City.class.getSimpleName());
        List<CompanyType> companyTypeList = commonService.findAllIdEntityList(CompanyTypeDao.class, CompanyType.class.getSimpleName());
        List<Country> countryList = commonService.findAllIdEntityList(CountryDao.class, Country.class.getSimpleName());
        List<Customs> customsList = commonService.findAllIdEntityList(CustomsDao.class, Customs.class.getSimpleName());
        List<TradeType> tradeTypeList = commonService.findAllIdEntityList(TradeTypeDao.class, TradeType.class.getSimpleName());
        List<Transportation> transportationList = commonService.findAllIdEntityList(TransportationDao.class, Transportation.class.getSimpleName());

        model.addAttribute(cityList)
                .addAttribute(companyTypeList)
                .addAttribute(countryList)
                .addAttribute(customsList)
                .addAttribute(tradeTypeList)
                .addAttribute(transportationList);

        return model;
    }

    /**
     * 将属性添加到模型中
     * @param model
     * @param tradeDetail
     * @param commonDto
     * @param yearMonthDto
     * @return
     */
    private Model addAtrribute2Model(Model model, TradeDetail tradeDetail,
                                     CommonDto commonDto, YearMonthDto yearMonthDto) {

        model = yearMonthDto.getMonth() != null ? model.addAttribute("month", yearMonthDto.getMonth()): model;
        model = yearMonthDto.getLowYear() != null ? model.addAttribute("lowYear", yearMonthDto.getLowYear()): model;
        model = yearMonthDto.getHighYear() != null ? model.addAttribute("highYear", yearMonthDto.getHighYear()): model;
        model = yearMonthDto.getHighMonth() != null ? model.addAttribute("highMonth", yearMonthDto.getHighMonth()): model;
        model = yearMonthDto.getImpExpType() != null ? model.addAttribute("impExpType", yearMonthDto.getImpExpType()): model;

        for (PropertyFilter filter : tradeDetailService
                .getdetailQueryProps(tradeDetail, commonDto)) {
            model.addAttribute(filter.getName(), filter.getValue());
        }
        return model;
    }


}
