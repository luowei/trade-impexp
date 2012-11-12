package com.oilchem.trade.service.impl;

import com.oilchem.trade.config.Config;
import com.oilchem.trade.config.ImpExpType;
import com.oilchem.trade.dao.*;
import com.oilchem.trade.dao.map.ExpTradeDetailRowMapper;
import com.oilchem.trade.dao.map.ImpTradeDetailRowMapper;
import com.oilchem.trade.dao.db.AccessDataSource;
import com.oilchem.trade.dao.spec.Spec;
import com.oilchem.trade.domain.ExpTradeDetail;
import com.oilchem.trade.domain.ImpTradeDetail;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TradeDetailService;
import com.oilchem.trade.view.dto.CommonDto;
import com.oilchem.trade.view.dto.YearMonthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static com.oilchem.trade.config.Config.*;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午5:43
 * To change this template use File | Settings | File Templates.
 */
@Service("tradeDetailService")
public class TradeDetailServiceImpl implements TradeDetailService {

    @Autowired
    CommonService commonService;


    @Resource
    ExpTradeDetailDao expTradeDetailDao;
    @Resource
    ImpTradeDetailDao impTradeDetailDao;
    @Resource
    LogDao logDao;

    /**
     * 上传文件包
     *
     * @param file  文件
     * @param yearMonthDto
     * @return 上传后的路径
     */
    public String uploadFile(MultipartFile file, YearMonthDto yearMonthDto) {

        //更新日志文件
        //......
        yearMonthDto.setTableType(Config.DETAIL);
        return commonService.uploadFile(file, UPLOAD_DETAILZIP_DIR, yearMonthDto);
    }

    /**
     * 解包
     *
     * @param packageSource 源zip文件绝对路径
     * @return 解包后的文件路径
     */
    public String unPackage(String packageSource) {
        return commonService.unpackageFile(packageSource, UPLOAD_DETAILZIP_DIR);
    }

    /**
     * 导入Access文件
     *
     *
     * @param accessFileFullName access文件全名，含绝对路径
     * @param yearMonthDto                 年月
     * @return
     */
    public Boolean importAccess(String accessFileFullName,
                                YearMonthDto yearMonthDto) {

        Boolean isSuccess = true;

        final String sql = "select * from 结果 ";
        JdbcTemplate accessJdbcTemplate = new JdbcTemplate((new AccessDataSource())
                .getAccessDataSource(accessFileFullName));

        //导入查询条件表
        commonService.importCriteriaTab(accessJdbcTemplate, sql);

        //导入进口明细总表
        if (yearMonthDto.getImpExpType().equals(ImpExpType.进口.getCode())) {
            isSuccess = isSuccess & commonService.importTradeDetail(
                    impTradeDetailDao,
                    impTradeDetailDao,
                    accessJdbcTemplate,
                    new ImpTradeDetailRowMapper(),
                    yearMonthDto.getYear(),
                    yearMonthDto.getMonth(), sql);

            //切面更新明细表年月

        }

        //导入出口明细表
        else if (yearMonthDto.getImpExpType().equals(ImpExpType.出口.getCode())) {
            isSuccess = isSuccess & commonService.importTradeDetail(
                    expTradeDetailDao,
                    expTradeDetailDao,
                    accessJdbcTemplate,
                    new ExpTradeDetailRowMapper(),
                    yearMonthDto.getYear(),
                    yearMonthDto.getMonth(), sql);

            //切面更新明细表年月
        }

        //切面更新日志

        return isSuccess;
    }

    /**
     * 根据条件查询
     * @param tradeDetail 页面传来的 IxpTradeDetail/ExpTradeDetail ，包含查询条件中里面
     * @param commonDto
     * @param pageRequest
     * @return
     */
    public <T extends TradeDetail> Page<T>
    findWithCriteria(T tradeDetail, CommonDto commonDto, PageRequest pageRequest) {

        if(tradeDetail instanceof ImpTradeDetail){
            Page<ImpTradeDetail> pageImpDetail = impTradeDetailDao
                    .findAll(Specifications
                            .where(Spec.<ImpTradeDetail>hasField("", tradeDetail.getCountry()))
                            .and(Spec.<ImpTradeDetail>hasField("", tradeDetail.getCity()))
                            .and(Spec.<ImpTradeDetail>hasField("", tradeDetail.getCustoms()))
                            .and(Spec.<ImpTradeDetail>hasField("", tradeDetail.getCompanyType()))
                            .and(Spec.<ImpTradeDetail>hasField("", tradeDetail.getTransportation()))
                            .and(Spec.<ImpTradeDetail>hasField("", tradeDetail.getTradeType()))
                            .and(Spec.<ImpTradeDetail>hasField("", tradeDetail.getYear()))
                            .and(Spec.<ImpTradeDetail>hasField("", tradeDetail.getMonth()))
                            , pageRequest);
            return (Page<T>) pageImpDetail;
        }

        if(tradeDetail instanceof ExpTradeDetail){
            Page<ExpTradeDetail> pageExpDetail = expTradeDetailDao
                    .findAll(Specifications
                        .where(Spec.<ExpTradeDetail>hasField("", tradeDetail.getCountry()))
                        .and(Spec.<ExpTradeDetail>hasField("", tradeDetail.getCity()))
                        .and(Spec.<ExpTradeDetail>hasField("", tradeDetail.getCustoms()))
                        .and(Spec.<ExpTradeDetail>hasField("", tradeDetail.getCompanyType()))
                        .and(Spec.<ExpTradeDetail>hasField("", tradeDetail.getTransportation()))
                        .and(Spec.<ExpTradeDetail>hasField("", tradeDetail.getTradeType()))
                        .and(Spec.<ExpTradeDetail>hasField("", tradeDetail.getYear()))
                        .and(Spec.<ExpTradeDetail>hasField("", tradeDetail.getMonth()))
                    ,pageRequest);
            return (Page<T>) pageExpDetail;
        }

        return null;
    }


}
