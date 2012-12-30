package com.oilchem.trade.view.controller;

import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.util.*;
import com.oilchem.trade.domain.abstrac.IdEntity;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.bean.CommonDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.*;

import static com.oilchem.trade.bean.DocBean.ChartProps;
import static com.oilchem.trade.bean.DocBean.ChartType;
import static com.oilchem.trade.bean.DocBean.Config.default_pagesize;

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
     * 重定向时添加消息
     * @param redirectAttrs
     * @param messageText
     */
    public void addRedirectMessage(RedirectAttributes redirectAttrs, String messageText) {
        redirectAttrs.addFlashAttribute("message", messageText);
    }


    /**
     * 重定向时添加错误消息
     * @param redirectAttrs
     * @param messageText
     */
    public void addRedirectError(RedirectAttributes redirectAttrs, String messageText) {
        redirectAttrs.addFlashAttribute("error", messageText);
    }

    /**                                                                             s
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
            commonDto.setPageSize(Integer.parseInt(default_pagesize.value()));
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
    public <T> Model addPageInfo(Model model, Page<T> page, String contextUrl) {

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

    public Model yearMonth2Model(Model model, YearMonthDto yearMonthDto) {
        model = yearMonthDto.getMonth() != null ? model.addAttribute("month", yearMonthDto.getMonth()) : model;
        model = yearMonthDto.getLowYear() != null ? model.addAttribute("lowYear", yearMonthDto.getLowYear()) : model;
        model = yearMonthDto.getLowMonth() != null ? model.addAttribute("lowMonth", yearMonthDto.getLowMonth()) : model;
        model = yearMonthDto.getHighYear() != null ? model.addAttribute("highYear", yearMonthDto.getHighYear()) : model;
        model = yearMonthDto.getHighMonth() != null ? model.addAttribute("highMonth", yearMonthDto.getHighMonth()) : model;
        model = yearMonthDto.getImpExpType() != null ? model.addAttribute("impExpType", yearMonthDto.getImpExpType()) : model;
        return model;
    };

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
     * 查询所有实体
     * @param model
     * @param type
     * @param <E>
     * @return
     */
    public <E extends IdEntity> Model findAllEntity(Model model, String type) {

        List<E> eList = commonService.findAllEntityList(type);
        model.addAttribute(eList);
        return model;
    }

        /**
        * 删除list中重复的元素
        * @param list
        * @param <T>
        * @return
        */
    public <T> List<T>  removeDuplicateWithOrder(List<T> list)   {
        Set<T> set  =   new  HashSet<T>();
        List newList  =   new  ArrayList();
        for  (Iterator<T> iter  =  list.iterator(); iter.hasNext();)   {
            T element  =  iter.next();
            if  (set.add(element))
                newList.add(element);
        }
        return newList;
    }


    /**
     * 获得图表数据
     *
     * @param chartType
     * @return
     */
    public <T extends IdEntity> String getChartData(List<T> list,ChartProps chartProps, ChartType chartType) {

        if (chartType == null) return null;

//        //方法一
//        GroovyShell sh = new GroovyShell();
////        String groovySource = CommonUtil.readStringFromFile(chartType.value());
//        String groovySource = CommonUtil.getStringFromFile(chartType.value());
//        Object o = sh.evaluate(groovySource);


//        //方法二
//        Object o = null;
//        try {
//            ClassLoader parent = getClass().getClassLoader();
//            GroovyClassLoader loader = new GroovyClassLoader(parent);
//            Class groovyClass = loader.parseClass(new File(chartType.value()));
//            GroovyObject groovyObject = (GroovyObject)groovyClass.newInstance();
//            o = groovyObject.invokeMethod("getLineChart",new Object[]{list,new ChartProps()});
//        } catch (Exception e) {
//            logger.error(e.getMessage(),e);
//            throw new RuntimeException(e);
//        }

        //方法三
//        Object o = new DetailChart().getLineChart(list, chartProps,chartType);

//        if (Chart.class.isAssignableFrom(o.getClass())) {
//            return OFC.instance.render((Chart) o);
//        }

        //方法四，直接用java构造Chart,省略。。。

        return null;
    }


}
