package com.oilchem.trade.service.impl;

import com.oilchem.trade.dao.ExpTradeSumDao;
import com.oilchem.trade.dao.ImpTradeSumDao;
import com.oilchem.trade.dao.LogDao;
import com.oilchem.trade.dao.condition.SumTypeDao;
import com.oilchem.trade.dao.others.map.ExpTradeSumRowMapper;
import com.oilchem.trade.dao.others.map.ImpTradeSumRowMapper;
import com.oilchem.trade.domain.sum.ExpTradeSum;
import com.oilchem.trade.domain.sum.ImpTradeSum;
import com.oilchem.trade.domain.Log;
import com.oilchem.trade.domain.condition.SumType;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TradeSumService;
import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.util.DynamicSpecifications;
import com.oilchem.trade.util.QueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.util.*;

import static com.oilchem.trade.bean.DocBean.Config.*;
import static com.oilchem.trade.bean.DocBean.ImpExpType.export_type;
import static com.oilchem.trade.bean.DocBean.ImpExpType.import_type;
import static com.oilchem.trade.bean.DocBean.TableType.sum;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static com.oilchem.trade.util.QueryUtils.*;

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
    SumTypeDao sumTypeDao;

    @Resource
    LogDao logDao;

    /**
     * 获得productType列表
     *
     * @return
     */
    public List<SumType> getSumTypeList() {
        return (List<SumType>) sumTypeDao.findAll();
    }

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
                    , upload_detailzip_dir.value());
        }
        return null;
    }


    /**
     * 导入Excel
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


        Boolean isImp = yearMonthDto.getImpExpType().equals(import_type.ordinal());
        Boolean isExp = yearMonthDto.getImpExpType().equals(export_type.ordinal());
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
                        impTradeSumDao, impTradeSumDao,
                        logEntry, ImpTradeSum.class,
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
                        expTradeSumDao, expTradeSumDao,
                        logEntry, ExpTradeSum.class,
                        ExpTradeSumRowMapper.class, yearMonthDto);
            }
        }

        //导入产品类型
        if (sumTypeDao.findBySumType(
                yearMonthDto.getProductType()) == null)
            isSuccess = isSuccess && sumTypeDao.save(
                    new SumType(yearMonthDto.getProductType())) != null;

        return isSuccess;
    }

    /**
     * 上传文件
     *
     * @param file         file
     * @param yearMonthDto
     * @return
     */
    @Override
    public String uploadFile(MultipartFile file,
                             YearMonthDto yearMonthDto) {

        yearMonthDto.setTableType(sum.getValue());
        return commonService.uploadFile(file,
                upload_sumzip_dir.value(), yearMonthDto);
    }

    /**
     * 根据条件查找出口记录
     *
     * @param tradeSum
     * @param commonDto
     * @param yearMonthDto
     * @param pageRequest
     * @return
     */
    public Page<ExpTradeSum> findExpWithCriteria(
            ExpTradeSum tradeSum, CommonDto commonDto, YearMonthDto yearMonthDto, PageRequest pageRequest) {

        List<QueryUtils.PropertyFilter> filterList = getSumQueryProps(tradeSum, commonDto);
        filterList.addAll(commonService.getYearMonthQueryProps(yearMonthDto));

        Specification<ExpTradeSum> spec = DynamicSpecifications
                .byPropertyFilter(filterList, ExpTradeSum.class);

        return expTradeSumDao.findAll(spec, pageRequest);
    }

    public List<ExpTradeSum> findExpWithCriteria(
            ExpTradeSum tradeSum, CommonDto commonDto, YearMonthDto yearMonthDto) {

        List<QueryUtils.PropertyFilter> filterList = getSumQueryProps(tradeSum, commonDto);
        filterList.addAll(commonService.getYearMonthQueryProps(yearMonthDto));

        Specification<ExpTradeSum> spec = DynamicSpecifications
                .byPropertyFilter(filterList, ExpTradeSum.class);

        return expTradeSumDao.findAll(spec);
    }

    /**
     * 根据条件查找进口记录
     *
     * @param tradeSum
     * @param commonDto
     * @param yearMonthDto
     * @param pageRequest  @return
     * @return
     */
    public Page<ImpTradeSum> findImpWithCriteria(
            ImpTradeSum tradeSum, CommonDto commonDto, YearMonthDto yearMonthDto, PageRequest pageRequest) {

        List<QueryUtils.PropertyFilter> filterList = getSumQueryProps(tradeSum, commonDto);
        filterList.addAll(commonService.getYearMonthQueryProps(yearMonthDto));

        Specification<ImpTradeSum> spec = DynamicSpecifications
                .byPropertyFilter(filterList, ImpTradeSum.class);

        return impTradeSumDao.findAll(spec, pageRequest);
    }

    public List<ImpTradeSum> findImpWithCriteria(
            ImpTradeSum tradeSum, CommonDto commonDto, YearMonthDto yearMonthDto) {

        List<QueryUtils.PropertyFilter> filterList = getSumQueryProps(tradeSum, commonDto);
        filterList.addAll(commonService.getYearMonthQueryProps(yearMonthDto));

        Specification<ImpTradeSum> spec = DynamicSpecifications
                .byPropertyFilter(filterList, ImpTradeSum.class);

        return impTradeSumDao.findAll(spec);
    }

    /**
     * 获得总表的条件列表
     *
     * @param tradeSum
     * @param commonDto
     * @return
     */
    public List<QueryUtils.PropertyFilter>
    getSumQueryProps(TradeSum tradeSum, CommonDto commonDto) {
        List<QueryUtils.PropertyFilter> propList = new ArrayList<QueryUtils.PropertyFilter>();
        if (isNotBlank(tradeSum.getProductName())) {
            if (Type.LIKE.name().equals(commonDto.getNameSelType())) {
                propList.add(new QueryUtils.PropertyFilter("productName", tradeSum.getProductName(), Type.LIKE));
            } else {
                propList.add(new QueryUtils.PropertyFilter("productName", tradeSum.getProductName(), Type.EQ));
            }
        }
        if (isNotBlank(tradeSum.getSumType())) {
            propList.add(new PropertyFilter("sumType", tradeSum.getSumType()));
        }
        return propList;
    }

    /**
     * 获得出口列表数据
     *
     * @param ids
     * @return
     */
    public List<ExpTradeSum> getExpSumList(List<Long> ids) {
        return ids != null ? (List<ExpTradeSum>) expTradeSumDao.findAll(ids) : null;
    }

    /**
     * 获得进口列表数据
     *
     * @param ids
     * @return
     */
    public List<ImpTradeSum> getImpTradeSum(List<Long> ids) {
        return ids != null ? (List<ImpTradeSum>) impTradeSumDao.findAll(ids) : null;
    }

}
