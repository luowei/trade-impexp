package com.oilchem.trade.service.impl;

import com.oilchem.trade.config.ImpExpType;
import com.oilchem.trade.dao.*;
import com.oilchem.trade.dao.map.ExpTradeDetailRowMapper;
import com.oilchem.trade.dao.map.ImpTradeDetailRowMapper;
import com.oilchem.trade.dao.db.AccessDataSource;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TradeDetailService;
import com.oilchem.trade.view.dto.CommonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @Resource
    JdbcTemplate accessJdbcTemplate;

    /**
     * 上传文件包
     *
     * @param file  文件
     * @param year  年
     * @param month 月
     * @return 上传后的路径
     */
    public String uploadFile(MultipartFile file, Integer year, Integer month) {

        //更新日志文件
        //......

        return commonService.uploadFile(file, UPLOAD_DETAILZIP_DIR);
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
     * @param accessFileFullName access文件全名，含绝对路径
     * @param year                 年
     * @param month                月
     * @param impExpTradeType    进出口类型，1进口/2出口
     * @return
     */
    public Boolean importAccess(String accessFileFullName,
                                Integer year,Integer month, Integer impExpTradeType) {

        Boolean isSuccess = true;

        final String sql = "select * from 结果 ";
        accessJdbcTemplate.setDataSource((new AccessDataSource())
                .getAccessDataSource(accessFileFullName));

        //导入查询条件表
        commonService.importCriteriaTab(accessJdbcTemplate, sql);

        //导入进口明细总表
        if (impExpTradeType.equals(ImpExpType.进口.getCode())) {
            isSuccess = isSuccess & commonService.importTradeDetail(
                    impTradeDetailDao, impTradeDetailDao,
                    accessJdbcTemplate, new ImpTradeDetailRowMapper(), year,month, sql);

            //切面更新明细表年月

        }

        //导入出口明细表
        else if (impExpTradeType.equals(ImpExpType.出口.getCode())) {
            isSuccess = isSuccess & commonService.importTradeDetail(
                    expTradeDetailDao, expTradeDetailDao,
                    accessJdbcTemplate, new ExpTradeDetailRowMapper(), year,month, sql);

            //切面更新明细表年月
        }

        //切面更新日志

        return isSuccess;
    }

    /**
     * 根据条件查询
     *
     *
     *
     * @param TradeDetail 页面传来的 ExpTradeDetail ，包含查询条件中里面
     * @param commonDto
     * @param pageRequest
     * @return
     */
    public <T extends TradeDetail> Page<T>
    findWithCriteria(T TradeDetail, CommonDto commonDto, PageRequest pageRequest) {

        return null;
    }


}
