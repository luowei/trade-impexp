package com.oilchem.trade.service;

import com.oilchem.trade.config.IMessageCode;
import com.oilchem.trade.config.ImpExpType;

import java.util.Date;

/**
 * 进出口明细service接口
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午3:16
 * To change this template use File | Settings | File Templates.
 */
public interface TradeDetailService {

    /**
     * 解包
     * @param packageSourcee    源zip文件绝对路径
     * @return   上传后的url
     */
    public String unPackage(String packageSourcee) ;

    /**
     * 导入Access文件
     * @param accessFileFullName     access文件全名，含绝对路径
     * @param yearMonth        年月
     * @param impExpType   进出口类型，1进口/2出口
     * @return
     */
    Boolean importAccess(String accessFileFullName,
         Date yearMonth,Integer impExpType);


}
