package com.oilchem.trade.service;

import com.oilchem.trade.config.IMessageCode;
import com.oilchem.trade.config.ImpExpType;
import org.springframework.web.multipart.MultipartFile;

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
     * 上传文件包
     * @param file  文件
     * @return   上传后的文件路径
     */
    public String uploadPackage(MultipartFile file);

    /**
     * 解包
     * @param packageSource    源zip文件绝对路径
     * @return  解包后的文件路径
     */
    public String unPackage(String packageSource) ;

    /**
     * 导入Access文件
     * @param accessFileFullName     access文件全名，含绝对路径
     * @param yearMonth        年月
     * @param impExpType   进出口类型，1进口/2出口
     * @return
     */
    Boolean importExcel(String accessFileFullName,
                        Date yearMonth, Integer impExpType);


}
