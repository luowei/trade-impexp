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
     * @param yearMonth   年月
     * @param impExpTradeType
     */
    public void unSumPackageAndImportTask(final Date yearMonth,
                                          final Integer impExpTradeType,final String productType);

    /**
     * 解压明细数据包与导入任务
     * @param yearMonth   年月
     * @param impExpTradeType
     */
    public void unDetailPackageAndImportTask(final Date yearMonth,
                                             final Integer impExpTradeType);

}
