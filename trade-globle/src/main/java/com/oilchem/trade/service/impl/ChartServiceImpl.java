package com.oilchem.trade.service.impl;

import com.oilchem.trade.bean.ChartData;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.dao.*;
import com.oilchem.trade.domain.*;
import com.oilchem.trade.domain.abstrac.DetailCount;
import com.oilchem.trade.domain.abstrac.TradeDetail;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.service.ChartService;
import ofc4j.model.axis.Label;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

import static com.oilchem.trade.bean.DocBean.Config.axis_steps;
import static com.oilchem.trade.bean.DocBean.Config.scale_size;
import static com.oilchem.trade.bean.DocBean.Config.yearmonth_split;
import static com.oilchem.trade.bean.DocBean.ExcelFiled.*;
import static com.oilchem.trade.bean.DocBean.ExcelFiled.excel_pq;
import static com.oilchem.trade.bean.DocBean.ExcelFiled.excel_py;
import static com.oilchem.trade.bean.DocBean.ImpExpType.export_type;
import static com.oilchem.trade.bean.DocBean.ImpExpType.import_type;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-1
 * Time: 上午10:08
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ChartServiceImpl implements ChartService {


    @Resource
    ExpTradeDetailDao expTradeDetailDao;
    @Resource
    ImpTradeDetailDao impTradeDetailDao;

    @Resource
    ImpDetailCountDao impDetailCountDao;
    @Resource
    ExpDetailCountDao expDetailCountDao;

    @Resource
    ImpTradeSumDao impTradeSumDao;
    @Resource
    ExpTradeSumDao expTradeSumDao;


    BigDecimal defaultMin = BigDecimal.valueOf(0);
    BigDecimal defaultMax = BigDecimal.valueOf(Long.valueOf(axis_steps.value()));


    /**
     * 获得年月的label
     *
     * @param yearMonthDto
     * @return
     */
    public List<Label> getYearMonthLabels(YearMonthDto yearMonthDto) {

        Integer highYear = null;
        Integer highMonth = null;
        Integer lowYear = null;
        Integer lowMonth = null;

        //默认为近一年的统计
        if (yearMonthDto.getLowYear() == null || yearMonthDto.getHighYear() == null) {
            Calendar calendar = Calendar.getInstance();
            highYear = calendar.get((YEAR));
            highMonth = calendar.get(MONTH) + 1 < 10 ? calendar.get(MONTH) + 1 : calendar.get(MONTH) + 1;
            lowYear = highYear - 1;
            lowMonth = highMonth;
        } else {
            lowYear = yearMonthDto.getLowYear();
            lowMonth = yearMonthDto.getLowMonth() == null ? 1 : yearMonthDto.getLowMonth();
            highYear = yearMonthDto.getHighYear();
            highMonth = yearMonthDto.getHighMonth() == null ? 1 : yearMonthDto.getHighMonth();

            if (lowYear > highYear) return null;
        }

        List<Label> labelList = new ArrayList<Label>();
        labelList.add(new Label(lowYear + yearmonth_split.value() + (lowMonth < 10 ? "0" + lowMonth : lowMonth)));
        while (lowYear < highYear || (lowYear.equals(highYear) && lowMonth < highMonth - 1)) {
            if (lowMonth >= 12) {
                lowYear++;
                lowMonth = 1;
            } else {
                ++lowMonth;
            }
            labelList.add(new Label(lowYear + yearmonth_split.value() + (lowMonth < 10 ? "0" + lowMonth : lowMonth)));

        }
        return labelList;
    }


    /**
     * 获得detailChart List
     *
     * @param labels
     * @param codes
     * @param yearMonthDto @return     获得由月份组合而成的 list<TradeDetail>的集合
     */
    public Map<String, ChartData<TradeDetail>> getChartDetailList(
            List<Label> labels,
            List<String> codes, YearMonthDto yearMonthDto) {

        Integer impExpType = yearMonthDto.getImpExpType();
        Map<String, ChartData<TradeDetail>> codeChartDataMap = new HashMap<String, ChartData<TradeDetail>>(codes.size());
        if (labels == null || labels.isEmpty()) return null;

        Map<String, BigDecimal> maxRangMap = new HashMap<String, BigDecimal>();
        Map<String, BigDecimal> minRangMap = new HashMap<String, BigDecimal>();

        //遍历用户选择的每种产品
        for (String code : codes) {

            ChartData<TradeDetail> chartData = new ChartData<TradeDetail>().setLabels(labels);
            Map<String, TradeDetail> labelMap = new TreeMap<String, TradeDetail>();

            //遍历某种产品的每个月
            for (Label label : chartData.getLabels()) {

                String labelText = label.getText();
                TradeDetail tradeDetail = null;

                if (labelMap.get(labelText) == null) {
                    labelMap.put(label.getText(), new TradeDetail(BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0)));
                }

                if (impExpType.equals(import_type.ordinal())) {
                    List<ImpTradeDetail> impTradeDetails = impTradeDetailDao.findByProductCodeAndYearMonth(code, label.getText());

                    if (!impTradeDetails.isEmpty()) {
                        tradeDetail = combinDetail(code, impTradeDetails);
                    }
                }

                if (impExpType.equals(export_type.ordinal())) {
                    List<ExpTradeDetail> expTradeDetails = expTradeDetailDao.findByProductCodeAndYearMonth(code, label.getText());

                    if (!expTradeDetails.isEmpty()) {
                        tradeDetail = combinDetail(code, expTradeDetails);
                    }
                }

                labelMap.put(labelText, tradeDetail);
                if (tradeDetail != null) {
                    putMaxRangMap(maxRangMap, tradeDetail);
                    putMinRangMap(minRangMap, tradeDetail);
                }
            }

            chartData.setElementList(new ArrayList<TradeDetail>(labelMap.values()))
                    .setMaxRangMap(maxRangMap)
                    .setMinRangMap(minRangMap);
            codeChartDataMap.put(code, chartData);
        }

        return codeChartDataMap;
    }

    /**
     * 使用平均值构造tradeDetail供图表使用
     *
     * @param tradeDetails
     * @return
     */
    private <T extends TradeDetail> TradeDetail combinDetail(String code, List<T> tradeDetails) {
        BigDecimal amount = BigDecimal.valueOf(0),
                amountMoney = BigDecimal.valueOf(0),
                unitPrice = BigDecimal.valueOf(0);
        String name = null;

        //求和
        for (TradeDetail tradeDetail : tradeDetails) {
            amount = tradeDetail.getAmount() == null ? amount : amount.add(tradeDetail.getAmount());
            amountMoney = tradeDetail.getAmountMoney() == null ? amountMoney : amountMoney.add(tradeDetail.getAmountMoney());
            unitPrice = tradeDetail.getUnitPrice() == null ? unitPrice : unitPrice.add(tradeDetail.getUnitPrice());
            name = tradeDetail.getProductName().intern();
        }
        int scale = Integer.parseInt(scale_size.value());
        BigDecimal size = BigDecimal.valueOf(tradeDetails.size());

        //求平均值
        amount = amount.divide(size, scale, ROUND_HALF_UP);
        amountMoney = amountMoney.divide(size, scale, ROUND_HALF_UP);
        unitPrice = unitPrice.divide(size, scale, ROUND_HALF_UP);
        return new TradeDetail(code, name, amount, amountMoney, unitPrice);
    }


    //最小值
    BigDecimal minAmount = defaultMin;
    BigDecimal minAmountMoney = defaultMin;
    BigDecimal minUnitPrice = defaultMin;

    /**
     * 存入最小值
     *
     * @param minRangMap
     * @param tradeDetail
     */
    private void putMinRangMap(Map<String, BigDecimal> minRangMap, TradeDetail tradeDetail) {
        minRangMap.put("amount", tradeDetail.getAmount().compareTo(minAmount) > 0 ? minAmount : tradeDetail.getAmount());
        minRangMap.put("amountMoney", tradeDetail.getAmountMoney().compareTo(minAmountMoney) > 0 ? minAmountMoney : tradeDetail.getAmountMoney());
        minRangMap.put("unitPrice", tradeDetail.getUnitPrice().compareTo(minUnitPrice) > 0 ? minUnitPrice : tradeDetail.getUnitPrice());
    }


    //默认最大值
    BigDecimal maxAmount = defaultMax;
    BigDecimal maxAmountMoney = defaultMax;
    BigDecimal maxUnitPrice = defaultMax;

    /**
     * 存入最大值
     *
     * @param maxRangMap
     * @param tradeDetail
     */
    private void putMaxRangMap(Map<String, BigDecimal> maxRangMap, TradeDetail tradeDetail) {
        put2maxRangMap(maxRangMap, "amount", tradeDetail.getAmount(), maxAmount);
        put2maxRangMap(maxRangMap, "amountMoney", tradeDetail.getAmountMoney(), maxAmountMoney);
        put2maxRangMap(maxRangMap, "unitPrice", tradeDetail.getUnitPrice(), maxUnitPrice);
    }


    /**
     * 获得明细统计表
     *
     * @param labels
     * @param codes
     * @param yearMonthDto
     * @return
     */
    public Map<String, ChartData<DetailCount>>
    getChartDetailCountList(List<Label> labels, List<String> codes, YearMonthDto yearMonthDto) {

        Integer impExpType = yearMonthDto.getImpExpType();
        Map<String, ChartData<DetailCount>> codeChartDataMap = new HashMap<String, ChartData<DetailCount>>(codes.size());
        if (labels == null || labels.isEmpty()) return null;

        Map<String, BigDecimal> maxRangMap = new HashMap<String, BigDecimal>();
        Map<String, BigDecimal> minRangMap = new HashMap<String, BigDecimal>();

        //遍历用户选择的每种产品
        for (String code : codes) {

            ChartData<DetailCount> chartData = new ChartData<DetailCount>().setLabels(labels);
            Map<String, DetailCount> labelMap = new TreeMap<String, DetailCount>();

            //遍历某种产品的每个月
            for (Label label : chartData.getLabels()) {

                String labelText = label.getText();
                DetailCount detailCount = null;

                if (labelMap.get(labelText) == null) {
                    labelMap.put(label.getText(), new DetailCount(BigDecimal.valueOf(0), BigDecimal.valueOf(0), BigDecimal.valueOf(0)));
                }

                int scale = Integer.parseInt(scale_size.value());

                if (impExpType.equals(import_type.ordinal())) {
                    List<ImpDetailCount> impDetailCounts = impDetailCountDao.findByProductCodeAndYearMonth(code, label.getText());

                    if (!impDetailCounts.isEmpty()) {
                        detailCount = impDetailCounts.iterator().next();
                    }
                }

                if (impExpType.equals(export_type.ordinal())) {
                    List<ExpDetailCount> expDetailCounts = expDetailCountDao.findByProductCodeAndYearMonth(code, label.getText());

                    if (!expDetailCounts.isEmpty()) {
                        detailCount = expDetailCounts.iterator().next();
                    }
                }

                if(detailCount!=null){
                    detailCount.setMoney(detailCount.getMoney().divide(BigDecimal.valueOf(1000),scale+1,  ROUND_HALF_UP));
                    detailCount.setNum(detailCount.getNum().setScale(scale,ROUND_HALF_UP));
                    detailCount.setUnitPrice(detailCount.getUnitPrice().setScale(scale,ROUND_HALF_UP));
                }

                labelMap.put(labelText, detailCount);
                if (detailCount != null) {
                    putCountMaxRangMap(maxRangMap, detailCount);
                    putCountMinRangMap(minRangMap, detailCount);
                }
            }

            chartData.setElementList(new ArrayList<DetailCount>(labelMap.values()))
                    .setMaxRangMap(maxRangMap)
                    .setMinRangMap(minRangMap);
            codeChartDataMap.put(code, chartData);
        }

        return codeChartDataMap;
    }

    //默认最小值
    BigDecimal minNum = defaultMin,
            minMoney = defaultMin,
            minCountUnitPrice = defaultMin;

    /**
     * 存放最小值
     *
     * @param minRangMap
     * @param detailCoun
     */
    private void putCountMinRangMap(Map<String, BigDecimal> minRangMap, DetailCount detailCoun) {
        minRangMap.put("num", detailCoun.getNum().compareTo(minNum) > 0 ? minNum : detailCoun.getNum());
        minRangMap.put("money", detailCoun.getMoney().compareTo(minMoney) > 0 ? minMoney : detailCoun.getMoney());
        minRangMap.put("unitPrice", detailCoun.getUnitPrice().compareTo(minCountUnitPrice) > 0 ? minCountUnitPrice : detailCoun.getUnitPrice());
    }

    //默认最大值
    BigDecimal maxNum = defaultMax,
            maxMoney = defaultMax,
            maxCountUnitPrice = defaultMax;

    /**
     * 存入最大值
     *
     * @param maxRangMap
     * @param detailCount
     */
    private void putCountMaxRangMap(Map<String, BigDecimal> maxRangMap, DetailCount detailCount) {
        put2maxRangMap(maxRangMap, "num", detailCount.getNum(), maxNum);
        put2maxRangMap(maxRangMap, "money", detailCount.getMoney(), maxMoney);
        put2maxRangMap(maxRangMap, "unitPrice", detailCount.getUnitPrice(), maxCountUnitPrice);
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


    //默认最小值
    BigDecimal minnumMonth = defaultMin,
            minnumSum = defaultMin,
            minmoneyMonth = defaultMin,
            minmoneySum = defaultMin,
            minavgPriceMonth = defaultMin,
            minavgPriceSum = defaultMin,
            minPM = defaultMin,
            minPY = defaultMin,
            minPQ = defaultMin;

    /**
     * 存入最小值
     *
     * @param minRangMap
     * @param tradeSum
     */
    private void putMinRangMap(Map<String, BigDecimal> minRangMap, TradeSum tradeSum) {
        minRangMap.put(excel_num_month.getValue(), tradeSum.getNumMonth().compareTo(minnumMonth) > 0 ? minnumMonth : tradeSum.getNumMonth());
        minRangMap.put(excel_num_sum.getValue(), tradeSum.getMoneySum().compareTo(minnumSum) > 0 ? minnumSum : tradeSum.getMoneySum());
        minRangMap.put(excel_money_month.getValue(), tradeSum.getMoneyMonth().compareTo(minmoneyMonth) > 0 ? minmoneyMonth : tradeSum.getMoneyMonth());
        minRangMap.put(excel_money_sum.getValue(), tradeSum.getMoneySum().compareTo(minmoneySum) > 0 ? minmoneySum : tradeSum.getMoneySum());
        minRangMap.put(excel_avg_price_month.getValue(), tradeSum.getAvgPriceMonth().compareTo(minavgPriceMonth) > 0 ? minavgPriceMonth : tradeSum.getAvgPriceMonth());
        minRangMap.put(excel_avg_price_sum.getValue(), tradeSum.getAvgPriceSum().compareTo(minavgPriceSum) > 0 ? minavgPriceSum : tradeSum.getAvgPriceSum());
        minRangMap.put(excel_pm.getValue(), tradeSum.getPm().compareTo(minPM) > 0 ? minPM : tradeSum.getPm());
        minRangMap.put(excel_py.getValue(), tradeSum.getPy().compareTo(minPY) > 0 ? minPY : tradeSum.getPy());
        minRangMap.put(excel_pq.getValue(), tradeSum.getPq().compareTo(minPQ) > 0 ? minPQ : tradeSum.getPq());
    }


    //默认最大值
    BigDecimal maxnumMonth = defaultMax,
            maxnumSum = defaultMax,
            maxmoneyMonth = defaultMax,
            maxmoneySum = defaultMax,
            maxavgPriceMonth = defaultMax,
            maxavgPriceSum = defaultMax,
            maxPM = defaultMax,
            maxPY = defaultMax,
            maxPQ = defaultMax;

    /**
     * 存入最大值
     *
     * @param maxRangMap
     * @param tradeSum
     */
    private void putMaxRangMap(Map<String, BigDecimal> maxRangMap, TradeSum tradeSum) {
        put2maxRangMap(maxRangMap, excel_num_month.getValue(), tradeSum.getNumMonth(), maxnumMonth);
        put2maxRangMap(maxRangMap, excel_num_sum.getValue(), tradeSum.getNumSum(), maxnumSum);
        put2maxRangMap(maxRangMap, excel_money_month.getValue(), tradeSum.getMoneyMonth(), maxmoneyMonth);
        put2maxRangMap(maxRangMap, excel_money_sum.getValue(), tradeSum.getMoneySum(), maxmoneySum);
        put2maxRangMap(maxRangMap, excel_avg_price_month.getValue(), tradeSum.getAvgPriceMonth(), maxavgPriceMonth);
        put2maxRangMap(maxRangMap, excel_avg_price_sum.getValue(), tradeSum.getAvgPriceSum(), maxavgPriceSum);
        put2maxRangMap(maxRangMap, excel_pm.getValue(), tradeSum.getPm(), maxPM);
        put2maxRangMap(maxRangMap, excel_py.getValue(), tradeSum.getPy(), maxPY);
        put2maxRangMap(maxRangMap, excel_pq.getValue(), tradeSum.getPq(), maxPQ);

    }

    /**
     * 最大值放入map
     *
     * @param maxRangMap
     * @param fieldKey
     * @param fieldValue
     * @param defaultMaxValue
     */
    private void put2maxRangMap(Map<String, BigDecimal> maxRangMap,
                                String fieldKey, BigDecimal fieldValue, BigDecimal defaultMaxValue) {
        if (maxRangMap.get(fieldKey) == null ||
                maxRangMap.get(fieldKey).compareTo(fieldValue) < 0) {
            BigDecimal numMonth = fieldValue.compareTo(defaultMaxValue) < 0 ? defaultMaxValue : fieldValue;
            maxRangMap.put(fieldKey, numMonth);
        }
    }


}
