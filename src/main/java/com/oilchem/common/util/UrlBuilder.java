package com.oilchem.common.util;

import com.oilchem.common.bean.PropertyFilter;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * User: luowei
 * Date: 12-10-15
 * Time: 下午1:23
 */
public abstract class UrlBuilder {

    /**
     * 从给定的路径构建多条件参数的url
     * @param path
     * @param pfList
     * @return
     */
    public static String getUrl(String path, List<PropertyFilter> pfList){
        UriComponentsBuilder ucb = UriComponentsBuilder.fromPath(path);

        for(PropertyFilter pf : pfList){
            String queryName = pf.getName();
            if(queryName != null && pf.isNotBlankOfThisPropertyValue()){
                ucb.queryParam(queryName, pf.getValue());
            }
        }

        UriComponents uriComponents = ucb.build();

        return uriComponents.toUriString();

    }


    /**
     * 从给定的路径和条件参数构建排序url
     * @param path
     * @param pfList
     * @return
     */
    public static String getOrdersUrl(String path, List<PropertyFilter> pfList){

        return getUrl(getUrl(path, pfList), "orders", null);

    }


    /**
     * 从给定的url构建排序url
     * @param uriString
     * @return
     */
    public static String getOrdersUrl(String uriString, String orders){
        String url = getUrl(uriString, "orders", orders);
        if(orders == null || orders.trim().isEmpty()){
            url += "=";
        }

        return url;

    }


    public static String getOrdersUrl(String uriString){

        return getUrl(uriString, "orders", null) + "=";

    }

    public static String getUrl(HttpServletRequest request, List<PropertyFilter> pfList){
        UriComponentsBuilder ucb = UriComponentsBuilder.fromUriString(request.getRequestURI());

        for(PropertyFilter pf : pfList){
            String queryName = pf.getName();
            if(queryName != null && pf.isNotBlankOfThisPropertyValue()){
                ucb.queryParam(queryName, pf.getValue());
            }
        }


        UriComponents uriComponents = ucb.build();

        return uriComponents.toUriString();

    }

    /**
     * 附近新的条件参数到已有的url
     * @param uriString
     * @param paramName
     * @param paramValue
     * @return
     */
    public static String getUrl(String uriString, String paramName, Object paramValue){
        UriComponentsBuilder ucb = UriComponentsBuilder.fromUriString(uriString);
        ucb.queryParam(paramName, paramValue);

        UriComponents uriComponents = ucb.build();

        return uriComponents.toUriString();
    }
    
    
    
    
}
