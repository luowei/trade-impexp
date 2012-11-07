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
     * 解包
     * @param packageSource    源zip文件绝对路径
     * @param packageType   包类型，rar包为true,zip包为false
     * @return   上传后的url
     */
    public String unPackage(String packageSource,Boolean packageType);

    /**
     * 读取excel文件
     * @param ExcelFilePath
     * @return
     */
    Boolean readExcel(String ExcelFilePath);

}
