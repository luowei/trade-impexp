package com.oilchem.trade.service.impl;

import com.oilchem.trade.dao.LogDao;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TaskService;
import com.oilchem.trade.service.TradeDetailService;
import com.oilchem.trade.service.TradeSumService;
import com.oilchem.trade.view.dto.YearMonthDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import static com.oilchem.trade.config.Config.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-8
 * Time: 下午3:55
 * To change this template use File | Settings | File Templates.
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    CommonService commonService;
    @Autowired
    TradeDetailService tradeDetailService;
    @Autowired
    TradeSumService tradeSumService;

    @Resource
    LogDao logDao;


    long delay = 2L;

    /**
     * 解压明细数据包与导入任务
     * @param yearMonthDto                  年月
     *
     */
    public void unDetailPackageAndImportTask(final YearMonthDto yearMonthDto) {

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                //解包
                Map<Long, String> unExtractMap = commonService.getUnExtractPackage(DETAIL);
                for (Map.Entry<Long, String> entry : unExtractMap.entrySet()) {
                    commonService.unpackageFile(entry, UNZIP_DETAIL_DIR);
                }

                //导入数据
                Map<Long, String> unImportMap = commonService.getUnImportFile(DETAIL);
                for (Map.Entry<Long, String> entry : unImportMap.entrySet()) {
                    tradeDetailService.importAccess(entry, yearMonthDto, createAccessConnect(entry.getValue()));
                }
            }
        };
        new Timer().schedule(task, delay);

    }

    private Connection createAccessConnect(String accessPath) {
        Connection conn;//连接参数
        Properties prop = new Properties();
        prop.put("charSet", "GBK");
        prop.put("user", "");
        prop.put("password", "");
        String url = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ="
                + accessPath;

        //创建连接
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            conn = DriverManager.getConnection(url, prop);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }
        return conn;
    }

    /**
     * 解压总表数据包与导入任务
     * @param yearMonthDto      年月
     *
     */
    public void unSumPackageAndImportTask(final YearMonthDto yearMonthDto) {
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                //解包
                Map<Long, String> unExtractMap = commonService.getUnExtractPackage(SUM);
                for (Map.Entry<Long, String> entry : unExtractMap.entrySet()) {
                    commonService.unpackageFile(entry, UNZIP_SUM_DIR);
                }

                //导入数据
                Map<Long, String> unImportMap = commonService.getUnImportFile(SUM);
                for (Map.Entry<Long, String> entry : unImportMap.entrySet()) {
                    tradeSumService.importExcel(entry.getValue(), yearMonthDto);
                }

            }
        };
        new Timer().schedule(task,delay);
    }


}
