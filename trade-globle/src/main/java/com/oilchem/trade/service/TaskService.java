package com.oilchem.trade.service;

import com.oilchem.trade.bean.YearMonthDto;

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

    /**
     * 解包
     * @param logId
     */
      void extractPackage(final Long logId);

    /**
     * 导入数据
     */
      void importData(final Long logId);

    /**
     * 生成明细统计数据
     */
      void genAllDetailCount();

    /**
     * 生成按贸易方式统计数据
     */
      void genAllDetailTradeType();

    /**
     * 生成按企业性质统计数据
     */
      void genAllDetailCompanyType();

    /**
     * 执行指定任务
      * @param excute
     */
    void excuteService(String excute);

}
