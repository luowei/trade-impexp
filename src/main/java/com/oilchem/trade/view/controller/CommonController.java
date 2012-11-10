package com.oilchem.trade.view.controller;

import com.oilchem.trade.domain.abstrac.IdEntity;
import com.oilchem.trade.util.PageUtil;
import com.oilchem.trade.view.dto.CommonDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.context.ContextLoader;

import java.util.HashMap;
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

    /**
     * 获得page请求参数
     * @param commonDto    commonDto
     * @return
     */
    public PageRequest getPageRequest(CommonDto commonDto) {
        Map<String,Sort.Direction> orderMap = new HashMap<String,Sort.Direction>();
        String orderStr = commonDto.getOrder();
        String[] orderArr = commonDto.getOrders();

        //形如 id:asc,name:desc,age:asc --> orderMap
        if(orderArr!=null ){
            for (String order:orderArr){
                String[] ordstr=order.split(",");
                String field=ordstr[1].toUpperCase().trim();
                if(Sort.Direction.ASC.toString().equals(field)){
                    orderMap.put(ordstr[0].trim(),Sort.Direction.ASC);
                }else if(Sort.Direction.DESC.toString().equals(field)){
                    orderMap.put(ordstr[0].trim(),Sort.Direction.DESC);
                }
            }
        } else{    //形如:id:asc
            if(orderStr==null || orderStr.equals("")){
                orderStr = PageUtil.DEFAULT_ORDER;
            }
            String[] order = orderStr.split(",");
            if(Sort.Direction.ASC.toString().equals(order[1])){
                orderMap.put(order[0].trim(),Sort.Direction.ASC);
            }else if(Sort.Direction.DESC.toString().equals(order[1])){
                orderMap.put(order[0].trim(),Sort.Direction.DESC);
            }
        }

        //构建pagerequest对象
        Sort sort = PageUtil.sortByOrderFiled(orderMap);
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
     * 获得servlet的指定context的rootUrl+path
     *
     * @param path path+
     * @return rootUrl+path
     */
    public String getServletContextPath(String path) {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext().getContext(path).getContextPath();
    }

}
