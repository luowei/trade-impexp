package com.oilchem.trade.service.impl;

import com.oilchem.trade.bean.DocBean;
import com.oilchem.trade.dao.LogDao;
import com.oilchem.trade.domain.Log;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TaskService;
import com.oilchem.trade.service.TradeDetailService;
import com.oilchem.trade.service.TradeSumService;
import com.oilchem.trade.bean.YearMonthDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.oilchem.trade.bean.DocBean.Config.unzip_detail_dir;
import static com.oilchem.trade.bean.DocBean.Config.unzip_sum_dir;
import static com.oilchem.trade.bean.DocBean.ImpExpType.export_type;
import static com.oilchem.trade.bean.DocBean.ImpExpType.import_type;
import static com.oilchem.trade.bean.DocBean.TableType.detail;
import static com.oilchem.trade.bean.DocBean.TableType.sum;

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
     *
     * @param yearMonthDto 年月
     */
    public void unDetailPackageAndImportTask(final YearMonthDto yearMonthDto) {

        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                //解包
                Map<Long, Log> unExtractMap = commonService.getUnExtractPackage(detail.value());
                for (Map.Entry<Long, Log> entry : unExtractMap.entrySet()) {
                    commonService.unpackageFile(entry, unzip_detail_dir.value());
                }

                //导入数据
                Map<Long, Log> unImportMap = commonService.getUnImportFile(detail.value());
                for (Map.Entry<Long, Log> entry : unImportMap.entrySet()) {
                    tradeDetailService.importAccess(entry, yearMonthDto);
                }
            }
        };
        new Timer().schedule(task, delay);

    }

    /**
     * 解压总表数据包与导入任务
     *
     * @param yearMonthDto 年月
     */
    public void unSumPackageAndImportTask(final YearMonthDto yearMonthDto) {
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                //解包
                Map<Long, Log> unExtractMap = commonService.getUnExtractPackage(sum.value());
                for (Map.Entry<Long, Log> entry : unExtractMap.entrySet()) {
                    commonService.unpackageFile(entry, unzip_sum_dir.value());
                }

                //导入数据
                Map<Long, Log> unImportMap = commonService.getUnImportFile(sum.value());
                for (Map.Entry<Long, Log> entry : unImportMap.entrySet()) {
                    tradeSumService.importExcel(entry, yearMonthDto);
                }

            }
        };
        new Timer().schedule(task, delay);
    }

    /**
     * 解包
     * @param logId
     */
    public void extractPackage(final Long logId) {
        TimerTask task = new TimerTask() {

            public void run() {
                Log log = logDao.findOne(logId);
                Map<Long, Log> map = new HashMap<Long, Log>();
                map.put(logId, log);
                Map.Entry<Long, Log> entry = map.entrySet().iterator().next();
                String tableType = log.getTableType().trim();

                if (detail.value().equals(tableType) && entry != null) {
                    commonService.unpackageFile(entry, unzip_detail_dir.value());
                }
                if (sum.value().equals(tableType) && entry != null) {
                    commonService.unpackageFile(entry, unzip_sum_dir.value());
                }
            }
        };
        new Timer().schedule(task, delay);
    }

    /**
     * 导入数据
     */
    public void importData(final Long logId) {
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                Log log = logDao.findOne(logId);
                Map<Long, Log> map = new HashMap<Long, Log>();
                map.put(logId, log);
                Map.Entry<Long, Log> entry = map.entrySet().iterator().next();
                String tableType = log.getTableType().trim();

                Integer impExpType = null;
                YearMonthDto yearMonthDto = null;
                if (import_type.equals(log.getTradeType())) {
                    impExpType = import_type.ordinal();
                    yearMonthDto = new YearMonthDto(log.getYear(), log.getMonth(), impExpType,
                            log.getProductType(), tableType);
                    tradeDetailService.importAccess(entry, yearMonthDto);
                }

                if (export_type.equals(log.getTradeType())) {
                    impExpType = export_type.ordinal();
                    yearMonthDto = new YearMonthDto(log.getYear(), log.getMonth(), impExpType,
                            log.getProductType(), tableType);
                    tradeSumService.importExcel(entry, yearMonthDto);
                }

            }
        };
    }

}
