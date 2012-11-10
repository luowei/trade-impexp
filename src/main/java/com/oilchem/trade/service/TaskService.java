package com.oilchem.trade.service;

import java.util.Date;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-8
 * Time: 下午3:52
 * To change this template use File | Settings | File Templates.
 */
public interface TaskService {

    /**
     * 解压总表数据包与导入任务
     * @param year                  年
     * @param month                 月
     * @param impExpTradeType     impExpTradeType
     * @param productType          productType
     */
    void unSumPackageAndImportTask(final Integer year,final Integer month,
                                          final Integer impExpTradeType, final String productType);

    /**
     * 解压明细数据包与导入任务
     * @param year                  年
     * @param month                 月
     * @param impExpTradeType
     */
    void unDetailPackageAndImportTask(final Integer year,final Integer month,
                                             final Integer impExpTradeType);

}
