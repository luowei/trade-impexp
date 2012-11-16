package com.oilchem.trade.service;

import com.oilchem.trade.domain.ExpTradeDetail;
import com.oilchem.trade.domain.ImpTradeDetail;
import com.oilchem.trade.domain.Log;
import com.oilchem.trade.domain.ProductType;
import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.YearMonthDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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
     *
     *
     *
     *
     * @param file  文件
     * @param yearMonthDto
     * @return 上传后的文件路径
     */
    String uploadFile(MultipartFile file, YearMonthDto yearMonthDto);

    /**
     * 解包
     *
     *
     * @param logId@return 解包后的文件路径
     */
    String unPackage(Long logId);

    /**
     * 导入Access文件
     *
     *
     * @param logEntry
     * @param yearMonthDto                  年月
     * @return
     */
    Boolean importAccess(Map.Entry<Long, Log> logEntry,
                         YearMonthDto yearMonthDto);


    /**
     * 根据条件查询进口明细
     *
     * @param tradeDetail 页面传来的 IxpTradeDetail/ExpTradeDetail ，包含查询条件中里面
     * @param commonDto
     * @param pageRequest
     * @return
     */
    public  Page<ImpTradeDetail>
    findWithCriteria(ImpTradeDetail tradeDetail, CommonDto commonDto, PageRequest pageRequest);

    /**
     * 根据条件查询出口明细
     *
     * @param tradeDetail 页面传来的 IxpTradeDetail/ExpTradeDetail ，包含查询条件中里面
     * @param commonDto
     * @param pageRequest
     * @return
     */
    public  Page<ExpTradeDetail>
    findWithCriteria(ExpTradeDetail tradeDetail, CommonDto commonDto, PageRequest pageRequest);

    List<ProductType> getProductList();
}
