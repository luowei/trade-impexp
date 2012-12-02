package com.oilchem.trade.service.impl;

import com.oilchem.trade.bean.ChartData;
import com.oilchem.trade.dao.ExpTradeSumDao;
import com.oilchem.trade.dao.ImpTradeSumDao;
import com.oilchem.trade.dao.LogDao;
import com.oilchem.trade.dao.ProductTypeDao;
import com.oilchem.trade.dao.map.ExpTradeSumRowMapper;
import com.oilchem.trade.dao.map.ImpTradeSumRowMapper;
import com.oilchem.trade.domain.ExpTradeSum;
import com.oilchem.trade.domain.ImpTradeSum;
import com.oilchem.trade.domain.Log;
import com.oilchem.trade.domain.ProductType;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TradeSumService;
import com.oilchem.trade.bean.CommonDto;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.util.DynamicSpecifications;
import com.oilchem.trade.util.QueryUtils;
import ofc4j.model.axis.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.*;

import static com.oilchem.trade.bean.DocBean.Config.*;
import static com.oilchem.trade.bean.DocBean.ExcelFiled.*;
import static com.oilchem.trade.bean.DocBean.ImpExpType.export_type;
import static com.oilchem.trade.bean.DocBean.ImpExpType.import_type;
import static com.oilchem.trade.bean.DocBean.TableType.sum;
import static java.math.BigDecimal.ROUND_HALF_UP;
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
        if (productTypeDao.findByProductType(
                yearMonthDto.getProductType()) == null)
            isSuccess = isSuccess && productTypeDao.save(
                    new ProductType(yearMonthDto.getProductType())) != null;

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

        yearMonthDto.setTableType(sum.value());
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
            propList.add(new QueryUtils.PropertyFilter("productName", tradeSum.getProductName(), Type.LIKE));
        }
        if (isNotBlank(tradeSum.getProductType())) {
            propList.add(new PropertyFilter("productType", tradeSum.getProductType()));
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


    /**
     * 获得图表数据
     *
     * @param labels
     * @param names
     * @param yearMonthDto
     * @return
     */
    public Map<String, ChartData<TradeSum>> getChartSumList(
            List<Label> labels, List<String> names,
            YearMonthDto yearMonthDto) {

        Integer impExpType = yearMonthDto.getImpExpType();
        Map<String, ChartData<TradeSum>> chartDataMap = new HashMap<String, ChartData<TradeSum>>(names.size());
        if (labels == null || labels.isEmpty()) return null;

        Map<String, BigDecimal> maxRangMap = new HashMap<String, BigDecimal>();
        Map<String, BigDecimal> minRangMap = new HashMap<String, BigDecimal>();

        //遍历用户选择的名字
        for (String name : names) {

            ChartData<TradeSum> chartData = new ChartData<TradeSum>().setLabels(labels);
            Map<String, TradeSum> labelMap = new TreeMap<String, TradeSum>();

            //遍历每个月
            for (Label label : chartData.getLabels()) {

                TradeSum tradeSum = null;
                String labelText = label.getText();

                if (labelMap.get(labelText) == null) {
                    labelMap.put(label.getText(), new TradeSum(
                            BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0),
                            BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0),
                            BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0)));
                }

                if (impExpType.equals(import_type.ordinal())) {
                    List<ImpTradeSum> impTradeSums = impTradeSumDao.findByProductNameAndYearMonth(name, label.getText());
                    if (!impTradeSums.isEmpty()) {
                        tradeSum = combinTradSum(name, impTradeSums);
                    }

                }
                if (impExpType.equals(export_type.ordinal())) {
                    List<ExpTradeSum> expTradeDetails = expTradeSumDao.findByProductNameAndYearMonth(name, label.getText());
                    if (!expTradeDetails.isEmpty()) {
                        tradeSum = combinTradSum(name, expTradeDetails);
                    }
                }

                labelMap.put(labelText, tradeSum);
                if (tradeSum != null) {
                    putMaxRangMap(maxRangMap, tradeSum);
                    putMinRangMap(minRangMap, tradeSum);
                }

            }

            chartData.setElementList(new ArrayList<TradeSum>(labelMap.values()))
                    .setMaxRangMap(maxRangMap).setMinRangMap(minRangMap);
            chartDataMap.put(name, chartData);
        }

        return chartDataMap;
    }


    /**
     * 使用平均值构造tradeSum供图表使用
     *
     * @param name
     * @param tradeSums
     * @return
     */
    private <T extends TradeSum> TradeSum combinTradSum(String name, List<T> tradeSums) {
        BigDecimal numMonth = BigDecimal.valueOf(0),
                numSum = BigDecimal.valueOf(0),
                moneyMonth = BigDecimal.valueOf(0),
                moneySum = BigDecimal.valueOf(0),
                avgPriceMonth = BigDecimal.valueOf(0),
                avgPriceSum = BigDecimal.valueOf(0),
                pm = BigDecimal.valueOf(0),
                py = BigDecimal.valueOf(0),
                pq = BigDecimal.valueOf(0);

        //累加
        for (TradeSum tradeSum : tradeSums) {
            numMonth = tradeSum.getNumMonth() == null ? numMonth : numMonth.add(tradeSum.getNumMonth());
            numSum = tradeSum.getNumSum() == null ? numSum : numSum.add(tradeSum.getNumSum());
            moneyMonth = tradeSum.getMoneyMonth() == null ? moneyMonth : moneyMonth.add(tradeSum.getMoneyMonth());
            moneySum = tradeSum.getMoneySum() == null ? moneySum : moneySum.add(tradeSum.getMoneySum());
            avgPriceMonth = tradeSum.getAvgPriceMonth() == null ? avgPriceMonth : avgPriceMonth.add(tradeSum.getAvgPriceMonth());
            avgPriceSum = tradeSum.getAvgPriceSum() == null ? avgPriceSum : avgPriceSum.add(tradeSum.getAvgPriceSum());
            pm = tradeSum.getPm() == null ? pm : pm.add(tradeSum.getPm());
            py = tradeSum.getPy() == null ? py : py.add(tradeSum.getPy());
            pq = tradeSum.getPq() == null ? pq : pq.add(tradeSum.getPq());
        }
        BigDecimal size = BigDecimal.valueOf(tradeSums.size());
        int scale = Integer.parseInt(scale_size.value());

        //取平均值
        numMonth = numMonth.divide(size, scale, ROUND_HALF_UP);
        numSum = numSum.divide(size, scale, ROUND_HALF_UP);
        moneyMonth = moneyMonth.divide(size, scale, ROUND_HALF_UP);
        moneySum = moneySum.divide(size, scale, ROUND_HALF_UP);
        avgPriceMonth = avgPriceMonth.divide(size, scale, ROUND_HALF_UP);
        avgPriceSum = avgPriceSum.divide(size, scale, ROUND_HALF_UP);
        pm = pm.divide(size, scale, ROUND_HALF_UP);
        py = py.divide(size, scale, ROUND_HALF_UP);
        pq = pq.divide(size, scale, ROUND_HALF_UP);

        return new TradeSum(name, numMonth, numSum,
                moneyMonth, moneySum, avgPriceMonth, avgPriceSum, pm, py, pq);
    }


    BigDecimal minnumMonth = BigDecimal.valueOf(0),
            minnumSum = BigDecimal.valueOf(0),
            minmoneyMonth = BigDecimal.valueOf(0),
            minmoneySum = BigDecimal.valueOf(0),
            minavgPriceMonth = BigDecimal.valueOf(0),
            minavgPriceSum = BigDecimal.valueOf(0),
            minPM = BigDecimal.valueOf(0),
            minPY = BigDecimal.valueOf(0),
            minPQ = BigDecimal.valueOf(0);

    //存入最小值
    private void putMinRangMap(Map<String, BigDecimal> minRangMap, TradeSum tradeSum) {
        minRangMap.put(excel_num_month.value(), tradeSum.getNumMonth().compareTo(minnumMonth) > 0 ? minnumMonth : tradeSum.getNumMonth());
        minRangMap.put(excel_money_sum.value(), tradeSum.getMoneySum().compareTo(minnumSum) > 0 ? minnumSum : tradeSum.getMoneySum());
        minRangMap.put(excel_money_month.value(), tradeSum.getMoneyMonth().compareTo(minmoneyMonth) > 0 ? minmoneyMonth : tradeSum.getMoneyMonth());
        minRangMap.put(excel_money_sum.value(), tradeSum.getMoneySum().compareTo(minmoneySum) > 0 ? minmoneySum : tradeSum.getMoneySum());
        minRangMap.put(excel_avg_price_month.value(), tradeSum.getAvgPriceMonth().compareTo(minavgPriceMonth) > 0 ? minavgPriceMonth : tradeSum.getAvgPriceMonth());
        minRangMap.put(excel_avg_price_sum.value(), tradeSum.getAvgPriceSum().compareTo(minavgPriceSum) > 0 ? minavgPriceSum : tradeSum.getAvgPriceSum());
        minRangMap.put(excel_pm.value(), tradeSum.getPm().compareTo(minPM) > 0 ? minPM : tradeSum.getPm());
        minRangMap.put(excel_py.value(), tradeSum.getPy().compareTo(minPY) > 0 ? minPY : tradeSum.getPy());
        minRangMap.put(excel_pq.value(), tradeSum.getPq().compareTo(minPQ) > 0 ? minPQ : tradeSum.getPq());
    }

    //存入最大值
    BigDecimal maxnumMonth = BigDecimal.valueOf(Long.valueOf(axis_steps.value())),
            maxnumSum = BigDecimal.valueOf(Long.valueOf(axis_steps.value())),
            maxmoneyMonth = BigDecimal.valueOf(Long.valueOf(axis_steps.value())),
            maxmoneySum = BigDecimal.valueOf(Long.valueOf(axis_steps.value())),
            maxavgPriceMonth = BigDecimal.valueOf(Long.valueOf(axis_steps.value())),
            maxavgPriceSum = BigDecimal.valueOf(Long.valueOf(axis_steps.value())),
            maxPM = BigDecimal.valueOf(Long.valueOf(axis_steps.value())),
            maxPY = BigDecimal.valueOf(Long.valueOf(axis_steps.value())),
            maxPQ = BigDecimal.valueOf(Long.valueOf(axis_steps.value()));

    private void putMaxRangMap(Map<String, BigDecimal> maxRangMap, TradeSum tradeSum) {
        maxRangMap.put(excel_num_month.value(), tradeSum.getNumMonth().compareTo(maxnumMonth) < 0 ? maxnumMonth : tradeSum.getNumMonth());
        maxRangMap.put(excel_money_sum.value(), tradeSum.getMoneySum().compareTo(maxnumSum) < 0 ? maxnumSum : tradeSum.getMoneySum());
        maxRangMap.put(excel_money_month.value(), tradeSum.getMoneyMonth().compareTo(maxmoneyMonth) < 0 ? maxmoneyMonth : tradeSum.getMoneyMonth());
        maxRangMap.put(excel_money_sum.value(), tradeSum.getMoneySum().compareTo(maxmoneySum) < 0 ? maxmoneySum : tradeSum.getMoneySum());
        maxRangMap.put(excel_avg_price_month.value(), tradeSum.getAvgPriceMonth().compareTo(maxavgPriceMonth) < 0 ? maxavgPriceMonth : tradeSum.getAvgPriceMonth());
        maxRangMap.put(excel_avg_price_sum.value(), tradeSum.getAvgPriceSum().compareTo(maxavgPriceSum) < 0 ? maxavgPriceSum : tradeSum.getAvgPriceSum());
        maxRangMap.put(excel_pm.value(), tradeSum.getPm().compareTo(maxPM) < 0 ? maxPM : tradeSum.getPm());
        maxRangMap.put(excel_py.value(), tradeSum.getPy().compareTo(maxPY) > 0 ? maxPY : tradeSum.getPy());
        maxRangMap.put(excel_pq.value(), tradeSum.getPq().compareTo(maxPQ) < 0 ? maxPQ : tradeSum.getPq());
    }


}
