package com.oilchem.test;

import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TaskService;
import com.oilchem.trade.service.TradeDetailService;
import com.oilchem.trade.service.TradeSumService;
import com.oilchem.trade.view.controller.CommonController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-19
 * Time: 上午11:21
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test/applicationContext-root.xml"})
public class ControllerTest {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    TaskService taskService;

    @Autowired
    CommonService commonService;

    @Autowired
    TradeDetailService tradeDetailService;

    @Autowired
    TradeSumService tradeSumService;

    @Autowired
    CommonController commonController;

    @Test
    public void testGetPageRequest() throws Exception {
        CommonDto commonDto = new CommonDto();
        PageRequest pageRequest = commonController.getPageRequest(commonDto);

    }
}
