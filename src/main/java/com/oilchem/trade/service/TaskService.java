package com.oilchem.trade.service;

import com.oilchem.trade.view.dto.YearMonthDto;

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
     * @param yearMonthDto       年月,产品类型
     *
     */
    void unSumPackageAndImportTask(final YearMonthDto yearMonthDto);

    /**
     * 解压明细数据包与导入任务
     * @param yearMonthDto     年月
     */
    void unDetailPackageAndImportTask(final YearMonthDto yearMonthDto);

}
