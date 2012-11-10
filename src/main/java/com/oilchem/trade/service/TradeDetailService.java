package com.oilchem.trade.service;

import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.view.dto.CommonDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

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
     * @param file  文件
     * @param year  年
     * @param month 月
     * @return 上传后的文件路径
     */
    String uploadFile(MultipartFile file, Integer year, Integer month);

    /**
     * 解包
     *
     * @param packageSource 源zip文件绝对路径
     * @return 解包后的文件路径
     */
    String unPackage(String packageSource);

    /**
     * 导入Access文件
     *
     * @param accessFileFullName access文件全名，含绝对路径
     * @param year                  年
     * @param month                 月
     * @param impExpTradeType    进出口类型，1进口/2出口
     * @return
     */
    Boolean importAccess(String accessFileFullName,
                         Integer year,Integer month, Integer impExpTradeType);


    /**
     * 根据条件查询
     *
     *
     * @param TradeDetail 页面传来的 ExpTradeDetail ，包含查询条件中里面
     * @param commonDto
     * @param pageRequest
     * @return
     */
    public <T extends TradeDetail> Page<T>
    findWithCriteria(T TradeDetail, CommonDto commonDto, PageRequest pageRequest);
}
