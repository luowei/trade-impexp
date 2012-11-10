package com.oilchem.trade.util;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-11
 * Time: 上午12:20
 * To change this template use File | Settings | File Templates.
 */
public abstract class PageUtil {


    public static String DEFAULT_ORDER = "id: asc";

    /**
     * 获得排序条件
     * @param orderMap  排序字段map
     * @return
     */
    public static Sort sortByOrderFiled(Map<String,Sort.Direction>  orderMap){
        if(orderMap==null) return null;
        List<Sort.Order> orderList = new ArrayList<Sort.Order>();
        for(Map.Entry<String,Sort.Direction>  entry:orderMap.entrySet()){
            orderList.add(new Sort.Order(entry.getValue(),entry.getKey()));
        }
        return new Sort(orderList);
    }



}
