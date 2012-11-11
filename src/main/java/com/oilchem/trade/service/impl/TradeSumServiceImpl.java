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
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TradeSumService;
import com.oilchem.trade.view.dto.CommonDto;
import com.oilchem.trade.view.dto.YearMonthDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import static com.oilchem.trade.config.Config.*;

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
     * 解包
     *
     * @param packageSourcee 源zip文件绝对路径
     * @return 解包后的文件路径
     */
    public String unPackage(String packageSourcee) {
        return commonService.unpackageFile(packageSourcee, UPLOAD_SUMZIP_DIR);
    }


    /**
     * 导入Excel
     * @param excelSource     excel文件全名，含绝对路径
     * @param year             年
     * @param month            月
     * @param productType     产品类型
     * @param impExpTradeType 进出口类型，1进口/2出口
     * @return
     */
    public Boolean importExcel(String excelSource, Integer year,Integer month,
                               String productType, Integer impExpTradeType) {

        Boolean isSuccess = true;

        Boolean validate = StringUtils.isNotBlank(excelSource) && year!=null
                && month!=null && productType!=null && impExpTradeType!=null;
        if(!validate) return false;

        if (impExpTradeType.equals(ImpExpType.进口.getCode())) {
            isSuccess = isSuccess & commonService.importExcel(impTradeSumDao, excelSource,
                    ImpTradeSum.class, ImpTradeSumRowMapper.class, year,month, productType);

            //更新yearMonth
        }


        else if (impExpTradeType.equals(ImpExpType.出口.getCode())) {
            isSuccess = isSuccess & commonService.importExcel(expTradeSumDao, excelSource,
                    ExpTradeSum.class, ExpTradeSumRowMapper.class, year,month, productType);

            //更新yearMonth
        }

        return isSuccess;
    }

    /**
     * 根据条件查找
     *
     * @param tradeSum  总表实例
     * @param commonDto 条件
     * @param pageRequest
     * @return
     */
    public <T extends TradeSum> Page<T> findWithCriteria(T tradeSum, CommonDto commonDto, PageRequest pageRequest) {


        return null;
    }

    @Override
    public String uploadFile(MultipartFile file, YearMonthDto yearMonthDto) {

        //更新年月,产品类型
        //...
        yearMonthDto.setTableType(Config.SUM);
        return commonService.uploadFile(file,UPLOAD_SUMZIP_DIR, yearMonthDto);
    }

}
