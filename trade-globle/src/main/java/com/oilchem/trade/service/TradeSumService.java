package com.oilchem.trade.service;

import com.oilchem.trade.domain.*;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.domain.condition.SumType;
import com.oilchem.trade.domain.sum.ExpTradeSum;
import com.oilchem.trade.domain.sum.ImpTradeSum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.oilchem.trade.util.QueryUtils.PropertyFilter;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午3:17
 * To change this template use File | Settings | File Templates.
 */
public interface TradeSumService {

    /**
     * 获得productType列表
     * @return
     */
    public List<SumType> getSumTypeList();

    /**
     * 解包
     *
     * @param logId@return 解包后的文件路径
     */
    String unPackage(Long logId);

    /**
     * 导入Excel
     *
     * @param logEntry
     * @param yearMonthDto 年月,产品类型
     * @return
     */
    Boolean importExcel(Map.Entry<Long, Log> logEntry,
                        YearMonthDto yearMonthDto);

    /**
     * 上传文件
     *
     * @param file         file
     * @param yearMonthDto
     * @return
     */
    String uploadFile(MultipartFile file,
                      YearMonthDto yearMonthDto);

    /**
     * 进口列表
     *
     * @param tradeSum
     * @param commonDto
     * @param yearMonthDto
     * @param pageRequest  @return
     */
    Page<ImpTradeSum> findImpWithCriteria(
            ImpTradeSum tradeSum,
            CommonDto commonDto,
            YearMonthDto yearMonthDto,
            PageRequest pageRequest);

    List<ImpTradeSum> findImpWithCriteria(
            ImpTradeSum tradeSum,
            CommonDto commonDto,
            YearMonthDto yearMonthDto);

    /**
     * 出口列表
     *
     * @param tradeSum
     * @param commonDto
     * @param yearMonthDto
     * @param pageRequest
     * @return
     */
    Page<ExpTradeSum> findExpWithCriteria(
            ExpTradeSum tradeSum,
            CommonDto commonDto,
            YearMonthDto yearMonthDto,
            PageRequest pageRequest);

    List<ExpTradeSum> findExpWithCriteria(
            ExpTradeSum tradeSum,
            CommonDto commonDto,
            YearMonthDto yearMonthDto);

    /**
     * 获得总量
     *
     * @param tradeSum
     * @param commonDto
     * @return
     */
    List<PropertyFilter> getSumQueryProps(TradeSum tradeSum,
                                          CommonDto commonDto);

    /**
     * 获得进口列表数据
     *
     * @param ids
     * @return
     */
    List<ImpTradeSum> getImpTradeSum(List<Long> ids);

    /**
     * 获得出口列表数据
     *
     * @param ids
     * @return
     */
    List<ExpTradeSum> getExpSumList(List<Long> ids);

}
