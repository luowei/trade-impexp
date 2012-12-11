package com.oilchem.trade.service.impl;

import com.oilchem.trade.dao.*;
import com.oilchem.trade.domain.*;
import com.oilchem.trade.service.FilterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import java.lang.reflect.InvocationTargetException;

import static org.springframework.web.context.ContextLoader.getCurrentWebApplicationContext;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-6
 * Time: 上午10:02
 * To change this template use File | Settings | File Templates.
 */
@Service
public class FilterServiceImpl  implements FilterService {


    Logger logger = LoggerFactory.getLogger(FilterServiceImpl.class);

    @Resource
    DetailTypeDao detailTypeDao;

    /**
    * 添加detailType
    * @param code
    * @param name
    */
    public void addDetailType(Integer code, String name) {
        DetailType detailType = new DetailType(code,name);
        detailTypeDao.save(detailType);
    }

}
