package com.oilchem.trade.service;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午3:17
 * To change this template use File | Settings | File Templates.
 */
public interface TradeSumService {

    /**
     * 解包
     * @param packageSourcee    源zip文件绝对路径
     * @return   解包后的文件路径
     */
    public String unPackage(String packageSourcee);

    /**
     * 导入Excel
     * @param excelSource     excel文件全名，含绝对路径
     * @param yearMonth        年月
     * @param productType   产品类型
     * @param impExpTradeType   进出口类型，1进口/2出口
     * @return
     */
    public Boolean importExcel(String excelSource, Date yearMonth,
                               String productType,Integer impExpTradeType);

}
