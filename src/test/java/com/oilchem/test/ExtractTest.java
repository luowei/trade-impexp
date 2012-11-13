package com.oilchem.test;

import com.oilchem.trade.config.Config;
import com.oilchem.trade.config.ImpExpType;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TaskService;
import com.oilchem.trade.service.TradeDetailService;
import com.oilchem.trade.view.dto.YearMonthDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileInputStream;
import java.sql.Connection;
import java.util.Map;

import static com.oilchem.trade.config.Config.DETAIL;
import static com.oilchem.trade.config.Config.UNZIP_DETAIL_DIR;
import static junit.framework.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-13
 * Time: 上午9:42
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test/applicationContext-root.xml"})
public class ExtractTest {

    @Autowired
    TaskService taskService;

    @Autowired
    CommonService commonService;

    @Autowired
    TradeDetailService tradeDetailService;

    YearMonthDto yearMonth;
    MockMultipartFile file;

    @Before
    public void setUp() throws Exception {
        yearMonth = new YearMonthDto();
//        yearMonth.setImpExpType(ImpExpType.进口.getCode());
        yearMonth.setImpExpType(ImpExpType.出口.getCode());
        yearMonth.setImportType(Config.IMPORT);
        yearMonth.setProductType("有机化工");
        yearMonth.setTableType(Config.DETAIL);
        yearMonth.setYear(2012);
        yearMonth.setMonth(11);

        file = new MockMultipartFile("idb_20121113143200.zip",
                new FileInputStream("D:/aaaa/upload/detailzip/idb_20121113143200.zip"));
    }

    /**
     * 上传测试
     * @throws Exception
     */
    @Test
    public void testUploadFile() throws Exception {

        assertNotNull(tradeDetailService.uploadFile(file, yearMonth));
    }

    /**
     * 解压测试
     * @throws Exception
     */
    @Test
//    @Test(expected = Exception.class)
    public void testUnpackageFile() throws Exception {
        try {
            Map<Long, String> unExtractMap = commonService.getUnExtractPackage(DETAIL);
            for (Map.Entry<Long, String> entry : unExtractMap.entrySet()) {
                commonService.unpackageFile(
                        entry, UNZIP_DETAIL_DIR);
            }
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    /**
     * 导入Access测试
     * @throws Exception
     */
    @Test
    public void testImportAccess() throws Exception {
        Boolean isSuccess = true;
        try {
            Map<Long, String> unImportMap = commonService.getUnImportFile(DETAIL);
            Connection conn = null;
            for (Map.Entry<Long, String> entry : unImportMap.entrySet()) {
                isSuccess = isSuccess && tradeDetailService.importAccess(entry, yearMonth, conn);
            }
            assertTrue(isSuccess);
        } catch (Exception e) {
            e.printStackTrace();
            assertFalse(isSuccess);
        }

    }
}
