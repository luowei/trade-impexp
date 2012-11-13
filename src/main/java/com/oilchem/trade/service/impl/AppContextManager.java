package com.oilchem.trade.service.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-13
 * Time: 下午9:44
 * To change this template use File | Settings | File Templates.
 */
@Component
public class AppContextManager implements ApplicationContextAware {
    private static ApplicationContext _appCtx;

    public void setApplicationContext(ApplicationContext ctx){
        _appCtx = ctx;
    }

    public static ApplicationContext getAppContext(){
        return _appCtx;
    }
}
