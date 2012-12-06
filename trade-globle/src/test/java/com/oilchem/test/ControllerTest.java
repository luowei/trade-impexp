package com.oilchem.test;

import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.DocBean;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.domain.abstrac.TradeSum;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

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

    @Test
    public void testGroovyShell() throws Exception {

        String x_legend = "aaa";
        String y_legend = "bbb";
        List<TradeSum> tradeSumList = null;
        List<TradeDetail> tradeDetailList = new ArrayList<TradeDetail>();
        tradeDetailList.add(new TradeDetail ().setAmount (new BigDecimal(12345)));
        tradeDetailList.add(new TradeDetail().setAmount(new BigDecimal(33333)));

        String chartData = new CommonController().getChartData(tradeDetailList,
                new DocBean.ChartProps(x_legend, y_legend), DocBean.ChartType.lineChart);
        assertNotNull(chartData);

    }
}
