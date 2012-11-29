package com.oilchem.trade.view.controller;

import com.oilchem.trade.bean.ChartData;
import com.oilchem.trade.chart.MyChart;
import com.oilchem.trade.dao.ProductTypeDao;
import com.oilchem.trade.domain.ExpTradeSum;
import com.oilchem.trade.domain.ImpTradeSum;
import com.oilchem.trade.domain.ProductType;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.service.ChartService;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TaskService;
import com.oilchem.trade.service.TradeSumService;
import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.util.QueryUtils;
import ofc4j.OFC;
import ofc4j.model.Chart;
import ofc4j.model.axis.Label;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static com.oilchem.trade.bean.DocBean.Config.upload_sumzip_dir;
import static com.oilchem.trade.bean.DocBean.ImpExpType.export_type;
import static com.oilchem.trade.bean.DocBean.ImpExpType.import_type;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-8
 * Time: 下午11:53
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/manage")
public class TradeSumController extends CommonController {

    @Autowired
    CommonService commonService;

    @Autowired
    TradeSumService tradeSumService;

    @Autowired
    ChartService chartService;

    @Autowired
    TaskService taskService;

    /**
     * 获得进口总表的一页数据
     *
     * @param model     model
     * @param tradeSum  tradeSum
     * @param commonDto commonDto
     * @return
     */
    @RequestMapping("/listsum/{pageNumber}")
    public String listTradeImpSum(Model model, TradeSum tradeSum,
                                  YearMonthDto yearMonthDto,
                                  CommonDto commonDto) {

        Integer impExp = yearMonthDto.getImpExpType();
        if ( impExp== null)
            yearMonthDto.setImpExpType(impExp = 0);

        //进口
        if (impExp.equals(import_type.ordinal())) {
            Page<ImpTradeSum> impTradeSums = tradeSumService
                    .findImpWithCriteria(new ImpTradeSum(tradeSum), commonDto,
                            yearMonthDto, getPageRequest(commonDto));

            findAllIdEntity(addPageInfo(model, impTradeSums, "/manage/listsum"),
                    ProductTypeDao.class, ProductType.class.getSimpleName())
                    .addAttribute("tradeSumList", impTradeSums);
        }

        //出口
        if (impExp.equals(export_type.ordinal())) {
            Page<ExpTradeSum> expTradeSums = tradeSumService
                    .findExpWithCriteria(new ExpTradeSum(tradeSum), commonDto,
                            yearMonthDto, getPageRequest(commonDto));

            findAllIdEntity(addPageInfo(model, expTradeSums, "/manage/listsum"),
                    ProductTypeDao.class, ProductType.class.getSimpleName())
                    .addAttribute("tradeSumList", expTradeSums);
        }

        addAtrribute2Model(model, tradeSum, commonDto, yearMonthDto);

        return "manage/trade/listsum";
    }

    /**
     * 导入总表
     *
     * @return
     */
    @RequestMapping("/importsum")
    public String importTradeSum(@RequestParam("file") MultipartFile file,
                                 YearMonthDto yearMonthDto,
                                 RedirectAttributes redirectAttrs) {
        Boolean validate = (file.getOriginalFilename().endsWith(".rar")
                ||file.getOriginalFilename().endsWith(".zip"))
                && yearMonthDto.validYearMonth(yearMonthDto)
                && yearMonthDto.getProductType()!= null;
        if (!validate){
            redirectAttrs.addFlashAttribute("message","输入的年月、或文件、或产品类型格式错误！");
            return "redirect:/manage/import";
        }

        String uploadUrl = null;
        StringBuffer message = new StringBuffer();
        try {
            uploadUrl = tradeSumService.uploadFile(file, yearMonthDto);
            message.append("文件已上传到：" + upload_sumzip_dir.value() +
                    uploadUrl.substring(uploadUrl.lastIndexOf("/")));
            if(yearMonthDto.getProductType().contains(",")){
                String prodType = yearMonthDto.getProductType();
                yearMonthDto.setProductType(StringUtils.substringBefore(prodType,","));
            }
            taskService.unSumPackageAndImportTask(yearMonthDto);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            message.append("<br/>文件上传或数据导入发生了错误");
        }

        redirectAttrs.addFlashAttribute("message",message.toString());
        return "redirect:/manage/import";
    }

    @RequestMapping("/sumchart")
    public String getSumChartData(Model model,YearMonthDto yearMonthDto,
                                  String chartType){

        List<String> names = new ArrayList<String>();
        List<Label> labels = chartService.getYearMonthLabels(yearMonthDto);

        ChartData<TradeSum> chartData = new ChartData<TradeSum>().setLabels(labels);

        List<ChartData<TradeSum>> chartDataList = tradeSumService.getChartSumList(names, chartData, yearMonthDto);


        Object o = new MyChart().getSumLineChart(chartDataList);
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
     * 将属性添加到模型中
     * @param model
     * @param tradeSum
     * @param commonDto
     * @param yearMonthDto
     * @return
     */
    private Model addAtrribute2Model(Model model, TradeSum tradeSum,
                                     CommonDto commonDto, YearMonthDto yearMonthDto) {

        model = yearMonthDto.getMonth() != null ? model.addAttribute("month", yearMonthDto.getMonth()): model;
        model = yearMonthDto.getLowYear() != null ? model.addAttribute("lowYear", yearMonthDto.getLowYear()): model;
        model = yearMonthDto.getLowMonth() != null ? model.addAttribute("lowMonth", yearMonthDto.getLowMonth()): model;
        model = yearMonthDto.getHighYear() != null ? model.addAttribute("highYear", yearMonthDto.getHighYear()): model;
        model = yearMonthDto.getHighMonth() != null ? model.addAttribute("highMonth", yearMonthDto.getHighMonth()): model;
        model = yearMonthDto.getImpExpType() != null ? model.addAttribute("impExpType", yearMonthDto.getImpExpType()): model;

        for (QueryUtils.PropertyFilter filter : tradeSumService
                .getSumQueryProps(tradeSum, commonDto)) {
            model.addAttribute(filter.getName(), filter.getValue());
        }
        return model;
    }


}
