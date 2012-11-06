package com.oilchem.trade.service;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午5:27
 * To change this template use File | Settings | File Templates.
 */
public interface CommonService {

    /**
     * 解压文件
     * @param zipFileFullName   含绝对路径的zip文件全名
     * @param unZipFullName    解压后含绝对路径的文件全名
     * @return
     */
    String unZipFile(String zipFileFullName,String unZipFullName);

}
