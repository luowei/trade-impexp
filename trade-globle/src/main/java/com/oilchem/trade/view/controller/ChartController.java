package com.oilchem.trade.view.controller;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.oilchem.trade.bean.ChartData;
import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.chart.DetailChart;
import com.oilchem.trade.chart.SumChart;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.service.ChartService;
import com.oilchem.trade.util.EHCacheUtil;
import ofc4j.model.Chart;
import ofc4j.model.axis.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.oilchem.trade.bean.DocBean.Config.chart_height;
import static com.oilchem.trade.bean.DocBean.Config.chart_width;
import static com.oilchem.trade.util.EHCacheUtil.setValue;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-3
 * Time: 下午6:01
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/manage")
public class ChartController extends CommonController{

    @Autowired
    ChartService chartService;


    /**
     * 获得图表
     *
     * @param model
     * @param yearMonthDto
     * @param chartType
     * @return
     */
    @RequestMapping("/detailchart")
    public String getDetailChartData(Model model, YearMonthDto yearMonthDto,
                                     CommonDto commonDto, String chartType,
                                     TradeDetail tradeDetail, HttpSession session,
                                     RedirectAttributes redirectAttributes) {

        if (commonDto.getCodes() == null || commonDto.getCodes().length < 1) {
            return "redirect:/manage/listdetail/" + (commonDto.getPageNumber() == null ? 1 : commonDto.getPageNumber());
        }

        List<String> codes = removeDuplicateWithOrder(
                Lists.asList(commonDto.getCodes()[0], commonDto.getCodes()));

        List<Label> labels = chartService.getYearMonthLabels(yearMonthDto);

        Map<String, ChartData<TradeDetail>> chartDataMap = chartService.getChartDetailList(labels,codes, yearMonthDto);


        Object o = new DetailChart().getDetailLineChart(chartDataMap);

        //缓存
        int idx = 1;
        if (o != null && o instanceof List) {
            for (Object chart_o : (List) o) {
                if (Chart.class.isAssignableFrom(chart_o.getClass())) {
                    Gson gson = new Gson();
                    String chart = gson.toJson(chart_o,Chart.class);
//                    String chart = OFC.instance.render((Chart) chart_o);
                    setValue("chart", "chartList_"
//                            +session.getId()
                            + idx, chart);

                    System.out.println("=================================================");
                    System.out.println(chart);
                    System.out.println("=================================================");

                    idx++;
                }
            }
        }


        model.addAttribute("idx",idx-1).addAttribute("width", chart_width.value())
                .addAttribute("height", chart_height.value());

        return "manage/trade/chart";
    }


    @RequestMapping("/sumchart")
    public String getSumChartData(Model model,YearMonthDto yearMonthDto,
                                  CommonDto commonDto,String chartType,
                                  TradeSum tradeSum,RedirectAttributes redirectAttr){

        if (commonDto.getCodes()==null || commonDto.getCodes().length < 1) {
            return "redirect:/manage/sumchart/" + (commonDto.getPageNumber()==null?1:commonDto.getPageNumber());
        }

        List<String> names = removeDuplicateWithOrder(
                Lists.asList(commonDto.getCodes()[0], commonDto.getCodes()));

        List<Label> labels = chartService.getYearMonthLabels(yearMonthDto);



        Map<String, ChartData<TradeSum>> chartDataMap = chartService.getChartSumList(labels,names,yearMonthDto );


        Object o = new SumChart().getSumLineChart(chartDataMap);
        List<String> chartList = new ArrayList<String>();

        //缓存
        int idx = 1;
        if(o!=null && o instanceof List){
            for(Object chart_o:(List)o){
                if (Chart.class.isAssignableFrom(chart_o.getClass())) {
                    Gson gson = new Gson();
                    String chart = gson.toJson(chart_o,Chart.class);
//                    String chart = OFC.instance.render((Chart) chart_o);
                    setValue("chart", "chartList_"
                            + idx, chart);
                    idx++;
                }
            }
        }
        model.addAttribute("idx", idx - 1)
                .addAttribute("width", chart_width.value())
                .addAttribute("height", chart_height.value());

        return  "manage/trade/chart";
    }


    @RequestMapping("/gdchart/{chartIdx}")
    @ResponseBody
    public String getChart( HttpSession session,@PathVariable Integer chartIdx) {
        String chart = (String) EHCacheUtil.getValue("chart", "chartList_"
//                +session.getId()
                + chartIdx);
        System.out.println("***************************************************************");
        System.out.println(chart);
        System.out.println("***************************************************************");
//        return new ArrayList<String>(){{add(chart);}};
        return chart;
    }
}
