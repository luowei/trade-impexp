package com.oilchem.trade.view.controller;

import com.oilchem.trade.util.QueryUtils;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param commonDto    commonDto
     * @return
     */
    public PageRequest getPageRequest(CommonDto commonDto) {

        if(commonDto.getPageNumber()==null || commonDto.getPageSize()==null){
            commonDto.setPageNumber(1).setPageSize(QueryUtils.DEFAULT_PAGESIZE);
        }

        Map<String,Sort.Direction> orderMap = new HashMap<String,Sort.Direction>();
        String orderStr = commonDto.getOrder();
        String[] orderArr = commonDto.getOrders();

        //形如 id:asc,name:desc,age:asc --> orderMap
        if(orderArr!=null ){
            for (String order:orderArr){
                String[] ordstr=order.split(":");
                String field=ordstr[1].toUpperCase().trim();
                if(Sort.Direction.ASC.toString().equals(field)){
                    orderMap.put(ordstr[0].trim(),Sort.Direction.ASC);
                }else if(Sort.Direction.DESC.toString().equals(field)){
                    orderMap.put(ordstr[0].trim(),Sort.Direction.DESC);
                }
            }
        } else{    //形如:id:asc
            if(orderStr==null || orderStr.equals("")){
                orderStr = QueryUtils.DEFAULT_ORDER;
            }

            String[] order = orderStr.split(":");
            String str1 = order[1].toLowerCase().trim();
            String asc_str = Sort.Direction.ASC.name().toLowerCase();
            String desc_str = Sort.Direction.DESC.name().toLowerCase();

            if(str1.equals(asc_str)){
                orderMap.put(order[0].trim(),Sort.Direction.ASC);
            }else if(str1.equals(desc_str)){
                orderMap.put(order[0].trim(),Sort.Direction.DESC);
            }
        }

        //构建pagerequest对象
        Sort sort = QueryUtils.sortByOrderFiled(orderMap);
        return new PageRequest(commonDto.getPageNumber(),commonDto.getPageSize(),sort);
    }

    /**
     * 添加页面信息
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

        return model == null ? model : model.addAttribute("currentIndex", current)
                .addAttribute("beginIndex", begin)
                .addAttribute("endIndex", end)
                .addAttribute("totalPages", totalPages)
                .addAttribute("totalElements", totalElements)
                .addAttribute("contextUrl", contextUrl);
    }

    /**
     * 获得servlet的指定context的rootUrl
     * @return rootUrl+path
     */
    public String getServletContextPath() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext().getContextPath();
    }

    /**
     * 获得查询条件数据
     *
     *
     *
     * @param model
     * @param daoClass
     *@param idEntityName  @return
     */
    public <E extends IdEntity> Model
    findAllIdEntity(Model model, Class daoClass, String idEntityName) {
        List<E> productTypeList = commonService.findAllIdEntityList(daoClass, idEntityName);
        model.addAttribute(productTypeList);
        return model;
    }




}
