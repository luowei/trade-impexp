package com.oilchem.trade.service;

import com.oilchem.trade.domain.ImpTradeSum;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.view.dto.CommonDto;
import com.oilchem.trade.view.dto.YearMonthDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

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
     *
     * @param packageSourcee 源zip文件绝对路径
     * @return 解包后的文件路径
     */
    String unPackage(String packageSourcee);

    /**
     * 导入Excel
     *
     *
     * @param excelSource     excel文件全名，含绝对路径
     * @param yearMonthDto              年月,产品类型
     * @return
     */
    Boolean importExcel(String excelSource, YearMonthDto yearMonthDto);

    /**
     * 根据条件查询总表记录
     *
     *
     * @param tradeSum  总表实例
     * @param commonDto 条件
     * @param pageRequest
     * @return
     */
    <T extends TradeSum> Page<T> findWithCriteria(T tradeSum, CommonDto commonDto, PageRequest pageRequest);

    /**
     * 上传文件
     *
     *
     *
     *
     * @param file  file
     * @param yearMonthDto
     * @return
     */
    String uploadFile(MultipartFile file, YearMonthDto yearMonthDto);
}
