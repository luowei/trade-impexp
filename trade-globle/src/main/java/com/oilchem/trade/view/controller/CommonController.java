package com.oilchem.trade.view.controller;

import com.oilchem.trade.util.CommonUtil;
import com.oilchem.trade.util.ConfigUtil;
import com.oilchem.trade.util.QueryUtils;
import com.oilchem.trade.domain.abstrac.IdEntity;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.bean.CommonDto;
import groovy.lang.GroovyShell;
import ofc4j.OFC;
import ofc4j.model.Chart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.context.ContextLoader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.oilchem.trade.util.ConfigUtil.ChartType;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-9
 * Time: 下午5:21
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class CommonController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    CommonService commonService;


//    @InitBinder
//    protected void ininBinder(WebDataBinder binder){
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf,true));
//    }

    /**
     * 获得page请求参数
     *
     * @param commonDto commonDto
     * @return
     */
    public PageRequest getPageRequest(CommonDto commonDto) {

        if (commonDto.getPageNumber() == null) {
            commonDto.setPageNumber(1);
        }
        if (commonDto.getPageSize() == null) {
            commonDto.setPageSize(QueryUtils.DEFAULT_PAGESIZE);
        }

        Map<String, Sort.Direction> sortMap = new HashMap<String, Sort.Direction>();
        String sortStr = commonDto.getSort();
        String[] sortArr = commonDto.getSorts();

        //形如 id:asc,name:desc,age:asc --> orderMap
        if (sortArr != null) {
            for (String order : sortArr) {
                String[] ordstr = order.split(":");
                String field = ordstr[1].toUpperCase().trim();
                if (Sort.Direction.ASC.toString().equals(field)) {
                    sortMap.put(ordstr[0].trim(), Sort.Direction.ASC);
                } else if (Sort.Direction.DESC.toString().equals(field)) {
                    sortMap.put(ordstr[0].trim(), Sort.Direction.DESC);
                }
            }
        } else {    //形如:id:asc
            if (sortStr == null || sortStr.equals("")) {
                sortStr = QueryUtils.DEFAULT_ORDER;
            }

            String[] order = sortStr.split(":");
            String str1 = order[1].toLowerCase().trim();
            String asc_str = Sort.Direction.ASC.name().toLowerCase();
            String desc_str = Sort.Direction.DESC.name().toLowerCase();

            if (str1.equals(asc_str)) {
                sortMap.put(order[0].trim(), Sort.Direction.ASC);
            } else if (str1.equals(desc_str)) {
                sortMap.put(order[0].trim(), Sort.Direction.DESC);
            }
        }

        //构建pagerequest对象
        Sort sort = QueryUtils.sortByOrderFiled(sortMap);
        return new PageRequest(commonDto.getPageNumber() - 1, commonDto.getPageSize(), sort);
    }

    /**
     * 添加页面信息
     *
     * @param model
     * @param page
     * @param contextUrl
     * @param <T>
     * @return
     */
    public <T extends IdEntity> Model addPageInfo(Model model, Page<T> page, String contextUrl) {

        int current = page.getNumber() + 1;
        int begin = Math.max(1, current - 5);
        int end = Math.min(begin + 10, page.getTotalPages());
        Integer totalPages = page.getTotalPages();
        Long totalElements = page.getTotalElements();
        Integer pageSize = page.getSize();
        String sort = page.getSort().toString();

        return model == null ? model : model.addAttribute("currentIndex", current)
                .addAttribute("beginIndex", begin)
                .addAttribute("endIndex", end)
                .addAttribute("totalPages", totalPages)
                .addAttribute("totalElements", totalElements)
                .addAttribute("contextUrl", contextUrl)
                .addAttribute("pageSize", pageSize)
                .addAttribute("sort", sort);
    }

    /**
     * 获得servlet的指定context的rootUrl
     *
     * @return rootUrl+path
     */
    public String getServletContextPath() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext().getContextPath();
    }

    /**
     * 获得查询条件数据
     *
     * @param model
     * @param daoClass
     * @param idEntityName @return
     */
    public <E extends IdEntity> Model
    findAllIdEntity(Model model, Class daoClass, String idEntityName) {
        List<E> eList = commonService.findAllIdEntityList(daoClass, idEntityName);
        model.addAttribute(eList);
        return model;
    }

    /**
     * 获得图表数据
     * @param chartType
     * @return
     */
    public String getChartData(ChartType chartType) {

        if(chartType==null) return null;

        GroovyShell sh = new GroovyShell();
//        String groovySource = CommonUtil.readStringFromFile(chartType.value());
        String groovySource = CommonUtil.getStringFromFile(chartType.value());

        Object o = sh.evaluate(groovySource);
        if (Chart.class.isAssignableFrom(o.getClass())) {
            return OFC.instance.render((Chart) o);
        }

        return null;
    }

    public static void main(String[] args){
        String chartData = new CommonController().getChartData(ConfigUtil.ChartType.lineChart);
        System.out.println("chartData:"+chartData);
    }

}
