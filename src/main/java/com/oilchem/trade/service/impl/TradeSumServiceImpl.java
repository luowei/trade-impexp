package com.oilchem.trade.service.impl;

import com.oilchem.trade.config.Config;
import com.oilchem.trade.config.Message;
import com.oilchem.trade.dao.ExpTradeSumDao;
import com.oilchem.trade.dao.ImpTradeSumDao;
import com.oilchem.trade.dao.LogDao;
import com.oilchem.trade.dao.ProductTypeDao;
import com.oilchem.trade.dao.map.ExpTradeSumRowMapper;
import com.oilchem.trade.dao.map.ImpTradeSumRowMapper;
import com.oilchem.trade.dao.spec.Spec;
import com.oilchem.trade.domain.ExpTradeSum;
import com.oilchem.trade.domain.ImpTradeSum;
import com.oilchem.trade.domain.Log;
import com.oilchem.trade.domain.ProductType;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TradeSumService;
import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.YearMonthDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static com.oilchem.trade.config.Config.*;
import static org.springframework.data.jpa.domain.Specifications.*;

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
    ProductTypeDao productTypeDao;

    @Resource
    LogDao logDao;

    /**
     * 解包
     *
     * @param logId@return 解包后的文件路径
     */
    public String unPackage(Long logId) {
        Log log = logDao.findOne(logId);
        if (log != null) {
            Map<Long, Log> map = new HashMap<Long, Log>();
            map.put(log.getId(), log);
            return commonService.unpackageFile(map.entrySet().iterator().next()
                    , UPLOAD_DETAILZIP_DIR);
        }
        return null;
    }


    /**
     * 导入Excel
     *
     *
     * @param logEntry
     * @param yearMonthDto 年月，产品类型
     * @return
     */
    public Boolean importExcel(Map.Entry<Long, Log> logEntry,
                               YearMonthDto yearMonthDto) {
        if (logEntry == null && yearMonthDto == null)
            return false;
        Boolean isSuccess = true;


        Boolean isImp = yearMonthDto.getImpExpType().equals(Message.ImpExpType.进口.getCode());
        Boolean isExp = yearMonthDto.getImpExpType().equals(Message.ImpExpType.出口.getCode());
        //进口
        if (isImp) {

            //判断是否已存在当年当月的数量，执行保存
            synchronized ("synchronized_sumimp_lock".intern()) {

                //处理重复数据
                Long count = impTradeSumDao.countWithYearMonth(
                        yearMonthDto.getYear(), yearMonthDto.getMonth(), ImpTradeSum.class);
                if (count != null && count > 0)
                    impTradeSumDao.delRepeatImpTradeSum(
                            yearMonthDto.getYear(), yearMonthDto.getMonth());

                //导入
                isSuccess = isSuccess & commonService.importExcel(
                        impTradeSumDao,  impTradeSumDao,
                        logEntry,  ImpTradeSum.class,
                        ImpTradeSumRowMapper.class, yearMonthDto);
            }
        }

        //出口
        else if (isExp) {

            //判断是否已存在当年当月的数量，执行保存
            synchronized ("synchronized_sumexp_lock".intern()) {

                //处理重复数据
                Long count = expTradeSumDao.countWithYearMonth(
                        yearMonthDto.getYear(), yearMonthDto.getMonth(), ExpTradeSum.class);
                if (count != null && count > 0)
                    expTradeSumDao.delRepeatExpTradeSum(
                            yearMonthDto.getYear(), yearMonthDto.getMonth());

                //导入数据
                isSuccess = isSuccess & commonService.importExcel(
                        expTradeSumDao,  expTradeSumDao,
                        logEntry,  ExpTradeSum.class,
                        ExpTradeSumRowMapper.class, yearMonthDto);
            }
        }

        //导入产品类型
        if (productTypeDao.findByProductType(
                yearMonthDto.getProductType()) == null)
            isSuccess = isSuccess && productTypeDao.save(
                    new ProductType(yearMonthDto.getProductType())) != null;

        return isSuccess;
    }

    /**
     * 根据条件查找
     *
     * @param tradeSum    总表实例
     * @param commonDto   条件
     * @param pageRequest
     * @return
     */
    public <T extends TradeSum> Page<T> findWithCriteria(
            T tradeSum, CommonDto commonDto,
            PageRequest pageRequest) {

        if (tradeSum instanceof ImpTradeSum) {
            Page<ImpTradeSum> pageImpDetail = impTradeSumDao
                    .findAll(where(new Spec< ImpTradeSum >().hasField("", tradeSum.getYear()))
                            .and(new Spec<ImpTradeSum>().hasField("", tradeSum.getMonth()))
                            , pageRequest);
            return (Page<T>) pageImpDetail;
        }


        if (tradeSum instanceof ExpTradeSum) {
            Page<ExpTradeSum> pageExpDetail = expTradeSumDao
                    .findAll(where(new Spec<ExpTradeSum>().hasField("", tradeSum.getYear()))
                            .and(new Spec<ExpTradeSum>().hasField("", tradeSum.getMonth()))
                            , pageRequest);
            return (Page<T>) pageExpDetail;
        }

        return null;
    }

    @Override
    public String uploadFile(MultipartFile file,
                             YearMonthDto yearMonthDto) {

        yearMonthDto.setTableType(Config.SUM);
        return commonService.uploadFile(file,
                UPLOAD_SUMZIP_DIR, yearMonthDto);
    }

}
