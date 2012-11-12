package com.oilchem.trade.service.impl;

import com.oilchem.trade.dao.LogDao;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TaskService;
import com.oilchem.trade.service.TradeDetailService;
import com.oilchem.trade.service.TradeSumService;
import com.oilchem.trade.view.dto.YearMonthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
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

    @Autowired
    CommonService commonService;
    @Autowired
    TradeDetailService tradeDetailService;
    @Autowired
    TradeSumService tradeSumService;

    @Resource
    LogDao logDao;

    //定时器
    Timer timer = new Timer();

    long delay = 0;

    private void unPackageAndImportTask(TimerTask timerTask) {
        timer.schedule(timerTask, delay);
    }

    /**
     * 解压明细数据包与导入任务
     * @param yearMonthDto                  年月
     *
     */
    public void unDetailPackageAndImportTask(final YearMonthDto yearMonthDto) {

        final Boolean isSuccess = true;

        unPackageAndImportTask(new TimerTask() {

            @Override
            public void run() {
                //解包
                Map<Long, String> unExtractMap = commonService.getUnExtractPackage(DETAIL);
                for (Map.Entry<Long, String> entry : unExtractMap.entrySet()) {
                    commonService.unpackageFile(entry.getValue(), UNZIP_DETAIL_DIR);
                }

                //导入数据
                Map<Long, String> unImportMap = commonService.getUnImportFile(DETAIL);
                for (Map.Entry<Long, String> entry : unImportMap.entrySet()) {
                    tradeDetailService.importAccess(entry.getValue(), yearMonthDto);
                }

            }
        });
    }

    /**
     * 解压总表数据包与导入任务
     * @param yearMonthDto      年月
     *
     */
    public void unSumPackageAndImportTask(final YearMonthDto yearMonthDto) {
        unPackageAndImportTask(new TimerTask() {

            @Override
            public void run() {
                //解包
                Map<Long, String> unExtractMap = commonService.getUnExtractPackage(SUM);
                for (Map.Entry<Long, String> entry : unExtractMap.entrySet()) {
                    commonService.unpackageFile(entry.getValue(), UNZIP_SUM_DIR);
                }

                //导入数据
                Map<Long, String> unImportMap = commonService.getUnImportFile(SUM);
                for (Map.Entry<Long, String> entry : unImportMap.entrySet()) {
                    tradeSumService.importExcel(entry.getValue(), yearMonthDto);
                }

            }
        });

    }


}
