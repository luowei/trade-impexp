package com.oilchem.test;

import com.oilchem.trade.service.TradeDetailService;
import com.oilchem.trade.service.TradeSumService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static junit.framework.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-8
 * Time: 下午5:01
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test/applicationContext-root.xml"})
public class AppTest {

    @Autowired
    private TradeDetailService tradeDetailService;

    @Autowired
    private TradeSumService tradeSumService;

    @Test
    public void testComplieAndLunch(){
         assertTrue(true);
    }

}
