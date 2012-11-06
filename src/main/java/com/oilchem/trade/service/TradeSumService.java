package com.oilchem.trade.service;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午3:17
 * To change this template use File | Settings | File Templates.
 */
public interface TradeSumService {

    /**
     * 解压excel的zip文件
     * @param zipPath
     * @param unZipPath
     * @return
     */
    String unZipExcel(String zipPath,String unZipPath);

    /**
     * 读取excel文件
     * @param ExcelFilePath
     * @return
     */
    Boolean readExcel(String ExcelFilePath);

}
