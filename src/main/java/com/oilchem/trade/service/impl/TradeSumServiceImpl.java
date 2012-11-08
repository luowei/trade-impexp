package com.oilchem.trade.service.impl;

import com.oilchem.trade.config.Config;
import com.oilchem.trade.config.ImpExpType;
import com.oilchem.trade.dao.ExpTradeSumDao;
import com.oilchem.trade.dao.ImpTradeSumDao;
import com.oilchem.trade.dao.LogDao;
import com.oilchem.trade.dao.map.ExpTradeSumRowMapper;
import com.oilchem.trade.dao.map.ImpTradeSumRowMapper;
import com.oilchem.trade.domain.ExpTradeSum;
import com.oilchem.trade.domain.ImpTradeSum;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TradeSumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 下午5:44
 * To change this template use File | Settings | File Templates.
 */
@Service("tradeSumService")
public class TradeSumServiceImpl implements TradeSumService {

    @Autowired
    CommonService commonService;

    @Resource
    ImpTradeSumDao impTradeSumDao;
    @Resource
    ExpTradeSumDao expTradeSumDao;
    @Resource
    LogDao logDao;

    /**
     * 上传文件包
     * @param file  文件
     * @return  上传后的文件路径
     */
    public String uploadPackage(MultipartFile file) {
        return commonService.uploadFile(file,Config.UPLOAD_EXCELZIP_DIR);
    }

    /**
     * 解包
     * @param packageSourcee    源zip文件绝对路径
     * @return  解包后的文件路径
     */
    public String unPackage(String packageSourcee) {
        return commonService.unpackageFile(packageSourcee, Config.UPLOAD_EXCELZIP_DIR);
    }


    /**
     * 导入Excel
     * @param excelSource     excel文件全名，含绝对路径
     * @param yearMonth        年月
     * @param productType   产品类型
     * @param impExpType   进出口类型，1进口/2出口
     * @return
     */
    public Boolean importExcel(String excelSource, Date yearMonth,
            String productType,Integer impExpType) {

        Boolean isSuccess = true;

        if(impExpType.equals(ImpExpType.进口.getCode())){
            isSuccess = isSuccess & commonService.importExcel(impTradeSumDao,excelSource,
                    ImpTradeSum.class,ImpTradeSumRowMapper.class,yearMonth,productType);
            //更新yearMonth
        }

        else if(impExpType.equals(ImpExpType.出口.getCode())){
            isSuccess = isSuccess & commonService.importExcel(expTradeSumDao,excelSource,
                    ExpTradeSum.class,ExpTradeSumRowMapper.class,yearMonth,productType);
            //更新yearMonth
        }

        //更新日志记录

        return isSuccess;
    }

}
