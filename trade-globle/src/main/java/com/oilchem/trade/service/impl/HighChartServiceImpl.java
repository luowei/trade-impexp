package com.oilchem.trade.service.impl;

import com.google.gson.Gson;
import com.oilchem.trade.bean.ProductCount;
import com.oilchem.trade.bean.Series;
import com.oilchem.trade.bean.YearMonthDto;
import com.oilchem.trade.dao.ExpTradeDetailDao;
import com.oilchem.trade.dao.ExpTradeSumDao;
import com.oilchem.trade.dao.ImpTradeDetailDao;
import com.oilchem.trade.dao.ImpTradeSumDao;
import com.oilchem.trade.dao.count.ExpDetailCountDao;
import com.oilchem.trade.dao.count.ImpDetailCountDao;
import com.oilchem.trade.domain.abstrac.DetailCount;
import com.oilchem.trade.domain.abstrac.TradeSum;
import com.oilchem.trade.domain.count.ExpDetailCount;
import com.oilchem.trade.domain.count.ImpDetailCount;
import com.oilchem.trade.domain.sum.ExpTradeSum;
import com.oilchem.trade.domain.sum.ImpTradeSum;
import com.oilchem.trade.service.HighChartService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

import static com.oilchem.trade.bean.DocBean.Config.*;
import static com.oilchem.trade.bean.DocBean.ImpExpType.export_type;
import static com.oilchem.trade.bean.DocBean.ImpExpType.import_type;
import static com.oilchem.trade.bean.DocBean.Symbol.*;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import static org.apache.commons.lang3.StringUtils.*;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-12-14
 * Time: 下午4:12
 * To change this template use File | Settings | File Templates.
 */
@Service
public class HighChartServiceImpl implements HighChartService {

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


    @Override
    public List<ProductCount> getProductCount(String productCode, String condition,
                                              String yearMonth, Integer impExp) {
        List<ProductCount> productCountList = null;
        if (impExp.equals(import_type.ordinal()) || impExp.equals(import_type.getValue())) {
            productCountList = impTradeDetailDao.getProductCount(condition, productCode, yearMonth);
        }

        if (impExp.equals(export_type.ordinal()) || impExp.equals(export_type.getValue())) {
            productCountList = expTradeDetailDao.getProductCount(condition, productCode, yearMonth);
        }

        return productCountList;

    }

    @Override
    public List<String> getYearMonthLabels(YearMonthDto yearMonthDto) {
        Integer highYear = null;
        Integer highMonth = null;
        Integer lowYear = null;
        Integer lowMonth = null;

        //默认为近一年的统计
        if (yearMonthDto.getLowYear() == null || yearMonthDto.getHighYear() == null) {
            Calendar calendar = Calendar.getInstance();
            highYear = calendar.get((YEAR));
            highMonth = calendar.get(MONTH) + 1;
            lowYear = highYear - 1;
            lowMonth = highMonth;
        } else {
            lowYear = yearMonthDto.getLowYear();
            lowMonth = yearMonthDto.getLowMonth() == null ? 1 : yearMonthDto.getLowMonth();
            highYear = yearMonthDto.getHighYear();
            highMonth = yearMonthDto.getHighMonth() == null ? 1 : yearMonthDto.getHighMonth();

            if (lowYear > highYear) return null;
        }

        List<String> labelList = new ArrayList<String>();
        labelList.add(lowYear + yearmonth_split.value() + (lowMonth < 10 ? zero.value() + lowMonth : lowMonth));
        while (lowYear < highYear || (lowYear.equals(highYear) && lowMonth < highMonth)) {
            if (lowMonth >= 12) {
                lowYear++;
                lowMonth = 1;
            } else {
                ++lowMonth;
            }
            labelList.add(lowYear + yearmonth_split.value() + (lowMonth < 10 ? zero.value() + lowMonth : lowMonth));

        }

        return labelList;
    }

    @Override
    public List<String> getSameMonthLabels(YearMonthDto yearMonthDto) {
        Integer highYear = null;
        Integer highMonth = null;
        Integer lowYear = null;
        Integer lowMonth = null;
        Calendar calendar = Calendar.getInstance();

        //默认为近一年的统计
        if (yearMonthDto.getLowYear() == null || yearMonthDto.getHighYear() == null) {
            highYear = calendar.get((YEAR));
            lowYear = highYear - 2;
            highMonth = calendar.get(MONTH) + 1;
            lowMonth = highMonth;
        } else {
            lowYear = yearMonthDto.getLowYear();
            highYear = yearMonthDto.getHighYear();
            lowMonth = yearMonthDto.getLowMonth() == null ? 1 : yearMonthDto.getLowMonth();
            highMonth = yearMonthDto.getHighMonth() == null ? 12 : yearMonthDto.getHighMonth();

            Integer maxMonthes = Integer.valueOf(max_monthes.value());
            if ((highYear - lowYear) * (highMonth + 1 - lowMonth) > maxMonthes.intValue() || lowYear > highYear) {
                return null;
            }

        }

        List<String> labelList = new ArrayList<String>();
        while (lowMonth <= highMonth) {
            int lyr = lowYear, hyr = highYear;
            while (lyr <= hyr) {
                labelList.add(lyr + yearmonth_split.value() + (lowMonth < 10 ? zero.value() + lowMonth : lowMonth));
                lyr++;
            }
            if ((lyr - 1) != hyr) {
                labelList.add((lyr - 1) + yearmonth_split.value() + (highMonth < 10 ? zero.value() + highMonth : highMonth));
            }
            lowMonth++;
        }
//        labelList.add(lowYear + yearmonth_split.value() + (highMonth < 10 ? zero.value() + highMonth : highMonth));
//        labelList.add(highYear + yearmonth_split.value() + (highMonth < 10 ? zero.value() + highMonth : highMonth));
        return labelList;
    }

    /**
     * 获得月累计的label
     *
     * @param yearMonthDto
     * @return
     */
    public List<String> getSumYearMonthLabels(YearMonthDto yearMonthDto) {
        Integer year = yearMonthDto.getSumYear();
        if (year == null) {
            return null;
        }
        Integer beginSumMonth = yearMonthDto.getBeginSumMonth();
        Integer endSumMonth = yearMonthDto.getEndSumMonth();
        List<String> labelList = new ArrayList<String>();
        while (beginSumMonth <= endSumMonth) {
            labelList.add(year + "-" + (endSumMonth<10?"0"+endSumMonth:endSumMonth));
            endSumMonth--;
        }
        return labelList;
    }

    @Override
    public List<ProductCount> getSumCountList(
            YearMonthDto yearMonthDto, ProductCount productCount, List<String> yearMonths, String productCode) {

        List<ProductCount> productCountList = new ArrayList<ProductCount>();
        Integer impExp = yearMonthDto.getImpExpType();
        String condition = productCount.getCondition();
        for (String yearMonth : yearMonths) {
            if (impExp.equals(import_type.ordinal()) || impExp.equals(import_type.getValue())) {
                List<ProductCount> tempProductCounts=impTradeDetailDao.getSumProductCount(condition, productCode, yearMonth,"t_import_detail", yearMonthDto);
                if(tempProductCounts!=null){
                    productCountList.addAll(tempProductCounts);
                }
            }

            if (impExp.equals(export_type.ordinal()) || impExp.equals(export_type.getValue())) {
                List<ProductCount> tempProductCounts=expTradeDetailDao.getSumProductCount(condition, productCode, yearMonth,"t_export_detail", yearMonthDto);
                if(tempProductCounts!=null){
                    productCountList.addAll(tempProductCounts);
                }
            }
        }
        return productCountList;
    }


    public List<String> getSeriesJson(
            YearMonthDto yearMonthDto, ProductCount productCount,
            List<String> yearMonths, String productCode, List<ProductCount> productCountList) {

        String unit = null;
        Map<String, Series> numSeriesMap = new TreeMap<String, Series>();
        Map<String, Series> moneySeriesMap = new TreeMap<String, Series>();

        Collections.reverse(yearMonths);
        for (String yearMonth : yearMonths) {
            List<ProductCount> productCounts = getProductCount(productCode, productCount.getCondition()
                    , yearMonth, yearMonthDto.getImpExpType());

            //这个月的这个条件的所有产品记录
            for (ProductCount productCount1 : productCounts) {
                String condition = productCount1.getCondition();

                //这个产品月是否有此条件的记录
                if (isBlank(condition)) {
                    condition = five_star.value();
                }

                //数量统计
                if (Boolean.valueOf(num_axis.value())) {
                    if (numSeriesMap.get(condition) == null) {
                        List<Double> doubleList = new ArrayList<Double>();
                        doubleList.add(productCount1.getNum().doubleValue());
                        numSeriesMap.put(yearMonth + underline.value() + condition,
                                new Series(yearMonth + underline.value() + condition, series_num_type.value(), 0, doubleList));
                    } else {
                        numSeriesMap.get(yearMonth + underline.value() + condition).getData().add(productCount1.getNum().doubleValue());
                    }
                }

                //金额统计
                if (Boolean.valueOf(money_axis.value())) {
                    if (moneySeriesMap.get(condition) == null) {
                        List<Double> doubleList = new ArrayList<Double>();
                        doubleList.add(productCount1.getMoney().doubleValue());
                        moneySeriesMap.put(yearMonth + underline.value() + condition,
                                new Series(yearMonth + underline.value() + condition, series_money_type.value(), 1, doubleList));
                    } else {
                        moneySeriesMap.get(yearMonth + underline.value() + condition).getData().add(productCount1.getMoney().doubleValue());
                    }
                }
                //单位
                unit = productCount1.getUnit();

            }
            productCountList.addAll(productCounts);

        }

        //验证每个月是否都有数据
        Map<String, Series> okNumSeriesMap = new TreeMap<String, Series>();
        Map<String, Series> okMoneySeriesMap = new TreeMap<String, Series>();
        Set<String> typeSet = new HashSet<String>();
        for (String type : numSeriesMap.keySet()) {
            typeSet.add(type.substring(8));
        }
        fillEveryMonth2Series(yearMonths, numSeriesMap, moneySeriesMap,
                typeSet, okNumSeriesMap, okMoneySeriesMap);

        productCount.setUnit(unit);

        List<String> seriesList = getSeriesJson(typeSet, okNumSeriesMap, okMoneySeriesMap);
//        List<String> seriesList = getSMSeriesJson(typeSet,nameSet,okNumSeriesMap, okMoneySeriesMap);
        return seriesList;
    }

    public List<String> getSMSeriesJson(
            YearMonthDto yearMonthDto, ProductCount productCount,
            List<String> yearMonths, String productCode, List<ProductCount> productCountList) {

        String unit = null;
        Map<String, Series> numSeriesMap = new TreeMap<String, Series>();
        Map<String, Series> moneySeriesMap = new TreeMap<String, Series>();

        Collections.reverse(yearMonths);
        for (String yearMonth : yearMonths) {
            List<ProductCount> productCounts = getProductCount(productCode, productCount.getCondition()
                    , yearMonth, yearMonthDto.getImpExpType());

            //这个月的这个条件的所有产品记录
            for (ProductCount productCount1 : productCounts) {
                String condition = productCount1.getCondition();

                //这个产品月是否有此条件的记录
                if (isBlank(condition)) {
                    condition = five_star.value();
                }

                //数量统计
                if (Boolean.valueOf(num_axis.value())) {
                    if (numSeriesMap.get(condition) == null) {
                        List<Double> doubleList = new ArrayList<Double>();
                        doubleList.add(productCount1.getNum().doubleValue());
                        numSeriesMap.put(yearMonth + underline.value() + condition,
                                new Series(yearMonth + underline.value() + condition, series_num_type.value(), 0, doubleList));
                    } else {
                        numSeriesMap.get(yearMonth + underline.value() + condition).getData().add(productCount1.getNum().doubleValue());
                    }
                }

                //金额统计
                if (Boolean.valueOf(money_axis.value())) {
                    if (moneySeriesMap.get(condition) == null) {
                        List<Double> doubleList = new ArrayList<Double>();
                        doubleList.add(productCount1.getMoney().doubleValue());
                        moneySeriesMap.put(yearMonth + underline.value() + condition,
                                new Series(yearMonth + underline.value() + condition, series_money_type.value(), 1, doubleList));
                    } else {
                        moneySeriesMap.get(yearMonth + underline.value() + condition).getData().add(productCount1.getMoney().doubleValue());
                    }
                }
                //单位
                unit = productCount1.getUnit();

            }
            productCountList.addAll(productCounts);

        }

        //验证每个月是否都有数据
        Map<String, Series> okNumSeriesMap = new TreeMap<String, Series>();
        Map<String, Series> okMoneySeriesMap = new TreeMap<String, Series>();
        Set<String> typeSet = new HashSet<String>();
        Set<String> nameSet = new HashSet<String>();
        for (String type : numSeriesMap.keySet()) {
            typeSet.add(type.substring(8));
            nameSet.add(new String(numSeriesMap.get(type).getName()));
        }
        fillEveryMonth2Series(yearMonths, numSeriesMap, moneySeriesMap,
                typeSet, okNumSeriesMap, okMoneySeriesMap);

        productCount.setUnit(unit);

//        List<String> seriesList = getSeriesJson(typeSet,okNumSeriesMap, okMoneySeriesMap);
        List<String> seriesList = getSMSeriesJson(typeSet, nameSet, yearMonths, okNumSeriesMap, okMoneySeriesMap);
        return seriesList;
    }

    private void fillEveryMonth2Series(
            List<String> yearMonths, final Map<String, Series> numSeriesMap,
            final Map<String, Series> moneySeriesMap, final Set<String> typeSet,
            final Map<String, Series> okNumSeriesMap, final Map<String, Series> okMoneySeriesMap) {

//        ExecutorService pool = Executors.newFixedThreadPool(12);
        for (String yearMonth : yearMonths) {
//            final String yearMonth = ym;
//            pool.submit(new Runnable() {
//                @Override
//                public void run() {
            //数量轴每条线中是否包含每个月
            if (!numSeriesMap.isEmpty()) {
                fillSeriesMap(numSeriesMap, typeSet, okNumSeriesMap, yearMonth, series_num_type.value(), 0);
            }
            //金额轴每条线中是否包含每个月
            if (!moneySeriesMap.isEmpty()) {
                fillSeriesMap(moneySeriesMap, typeSet, okMoneySeriesMap, yearMonth, series_money_type.value(), 1);
            }
//                }
//            });

        }
    }

    private void fillSeriesMap(Map<String, Series> seriesMap,
                               Set<String> typeSet, Map<String, Series> okSeriesMap,
                               String yearMonth, String seriesType, Integer yAxis) {
        Boolean exsitYearMonth = false;
        Set<String> okTypeSet = new HashSet<String>();
        for (Map.Entry<String, Series> entry : seriesMap.entrySet()) {
            Boolean isMatch = entry.getKey().startsWith(yearMonth);
            if (isMatch) {
                okSeriesMap.put(entry.getKey(), entry.getValue());
                okTypeSet.add(entry.getKey().substring(8));
                exsitYearMonth = true;
            }
            if (!exsitYearMonth) {
                //给某条线的某个月的每个类型都赋0值
                List<Double> zero = new ArrayList<Double>();
                zero.add(Double.valueOf(0));
                for (String type : typeSet) {
                    okSeriesMap.put(yearMonth + underline.value() + type, new Series(yearMonth
                            + underline.value() + type, seriesType, yAxis, zero));
                }
            }
        }

        String[] typeArr = typeSet.toArray(new String[typeSet.size()]);
        List<String> typelist = new ArrayList<String>();
        typelist.addAll(Arrays.asList(typeArr));
        if (okTypeSet.size() < typeSet.size()) {
            for (String type : okTypeSet) {
                if (typelist.contains(type)) {
                    typelist.remove(type);
                }
            }
            for (String type : typelist) {
                List<Double> zero = new ArrayList<Double>();
                zero.add(Double.valueOf(0));
                okSeriesMap.put(yearMonth + underline.value() + type,
                        new Series(yearMonth + underline.value() + type, seriesType, yAxis, zero));
            }
        }
    }

    /**
     * 把Series map转换成 seriesJson
     *
     * @param typeSet
     * @param seriesMaps
     * @return
     */
    public List<String> getSeriesJson(Set<String> typeSet, Map<String, Series>... seriesMaps) {
        Gson gson = new Gson();
        List<String> seriesJsons = new ArrayList<String>();

        //遍历每根轴
        for (Map<String, Series> seriesMap : seriesMaps) {
            List<Series> seriesList = new ArrayList<Series>(typeSet.size());
            List<Series> seriesList1 = new ArrayList<Series>(seriesMap.values());

            //遍历每根线
            for (String type : typeSet) {
                Series series = new Series();
                List<Double> data = new ArrayList<Double>();

                //遍历某一根线中的所有元素 -> 构造成一个seriers
                for (Series series1 : seriesList1) {

                    //处理曲线名称
                    String name = series1.getName();
                    String suffix = name.substring(name.length() - 3, name.length());
                    if (suffix.startsWith(left_square_bracket.value()) && suffix.endsWith(right_square_bracket.value())) {
                        name = name.substring(1, name.length() - 3);
                    }

                    //是否匹配
                    Boolean isMatch = name.endsWith(type);
                    if (isMatch) {
                        series.setName(name.substring(8))
                                .setType(series1.getType())
                                .setyAxis(series1.getyAxis());
                        data.addAll(series1.getData());
                        series.setData(data);
                    }

                }

                //如果这根轴的数据不为空
                if (!seriesMap.isEmpty()) {
                    seriesList.add(series);
                }
            }

            //添加到series json list中
            for (Series series : seriesList) {
                String seriesJson = gson.toJson(series);
                seriesJsons.add(seriesJson);
            }
        }
        return seriesJsons;
    }

    public List<String> getSMSeriesJson(Set<String> typeSet, Set<String> nameSet, List<String> yearMonths, Map<String, Series>... seriesMaps) {
        Gson gson = new Gson();
        List<String> seriesJsons = new ArrayList<String>();

        //遍历每根轴
        for (Map<String, Series> seriesMap : seriesMaps) {
            List<Series> seriesList = new ArrayList<Series>(typeSet.size());
            List<Series> seriesList1 = new ArrayList<Series>(seriesMap.values());


            for (String type : typeSet) {
                Series series = new Series();
                List<Double> data = new ArrayList<Double>();

                for (String yearMonth : yearMonths) {
                    String key = yearMonth + underline.value() + type;
                    Series series1 = seriesMap.get(key);
                    series.setName(key.substring(8))
                            .setType(series1.getType())
                            .setyAxis(series1.getyAxis());
                    data.addAll(series1.getData());
                    series.setData(data);
                }

                //如果这根轴的数据不为空
                if (!seriesMap.isEmpty()) {
                    seriesList.add(series);
                }

            }

            //添加到series json list中
            for (Series series : seriesList) {
                String seriesJson = gson.toJson(series);
                seriesJsons.add(seriesJson);
            }
        }
        return seriesJsons;
    }

    @Override
    public List<String> getChartDetailCountList(List<String> yearMonths, List<String> names, Integer impExpType) {

        Map<String, Series> numSeriesMap = new TreeMap<String, Series>();
        Map<String, Series> moneySeriesMap = new TreeMap<String, Series>();
        //遍历用户选择的每种产品
        for (String name : names) {

            //遍历某种产品的每个月
            for (String yearMonth : yearMonths) {

                //进口
                if (impExpType.equals(import_type.ordinal())) {
                    List<ImpDetailCount> detailCounts = impDetailCountDao.findByProductNameAndYearMonth(name, yearMonth);
                    detailCounts2SeriesMap(numSeriesMap, moneySeriesMap, yearMonth, name, detailCounts);
                }

                //出口
                if (impExpType.equals(export_type.ordinal())) {
                    List<ExpDetailCount> detailCounts = expDetailCountDao.findByProductNameAndYearMonth(name, yearMonth);
                    detailCounts2SeriesMap(numSeriesMap, moneySeriesMap, yearMonth, name, detailCounts);
                }
            }
        }


        //验证每个月是否都有数据
        Map<String, Series> okNumSeriesMap = new TreeMap<String, Series>();
        Map<String, Series> okMoneySeriesMap = new TreeMap<String, Series>();

        Set<String> typeSet = new HashSet<String>();
        for (String code : numSeriesMap.keySet()) {
            typeSet.add(code.substring(8));
        }
        fillEveryMonth2Series(yearMonths, numSeriesMap, moneySeriesMap,
                typeSet, okNumSeriesMap, okMoneySeriesMap);

        List<String> seriesList = getSeriesJson(typeSet, okNumSeriesMap, okMoneySeriesMap);
        return seriesList;
    }

    @Override
    public Map<String, List<String>> getTradeSumSeries(
            List<String> yearMonths, List<String> names, Integer impExpType) {

        Map<String, List<String>> seriesJsonMap = new HashMap<String, List<String>>();

        Map<String, Series> numSeriesMap_Month = new TreeMap<String, Series>();
        Map<String, Series> moneySeriesMap_Month = new TreeMap<String, Series>();
        Map<String, Series> numSeriesMap_Sum = new TreeMap<String, Series>();
        Map<String, Series> moneySeriesMap_Sum = new TreeMap<String, Series>();
        //遍历用户选择的每种产品
        for (String name : names) {

            //遍历某种产品的每个月
            for (String yearMonth : yearMonths) {
                if (impExpType.equals(import_type.ordinal())) {
                    List<ImpTradeSum> tradeSums = impTradeSumDao.findByProductNameAndYearMonth(name, yearMonth);
                    tradeSums2SeriesMap_Month(numSeriesMap_Month, moneySeriesMap_Month, yearMonth, name, tradeSums);
                    tradeSums2SeriesMap_Sum(numSeriesMap_Sum, moneySeriesMap_Sum, yearMonth, name, tradeSums);
                }

                if (impExpType.equals(export_type.ordinal())) {
                    List<ExpTradeSum> tradeSums = expTradeSumDao.findByProductNameAndYearMonth(name, yearMonth);
                    tradeSums2SeriesMap_Month(numSeriesMap_Month, moneySeriesMap_Month, yearMonth, name, tradeSums);
                    tradeSums2SeriesMap_Sum(numSeriesMap_Sum, moneySeriesMap_Sum, yearMonth, name, tradeSums);
                }
            }
        }

        //验证每个月是否都有数据
        Map<String, Series> okNumSeriesMap_Month = new TreeMap<String, Series>();
        Map<String, Series> okMoneySeriesMap_Month = new TreeMap<String, Series>();

        Set<String> typeSet_Month = new HashSet<String>();
        for (String code : numSeriesMap_Month.keySet()) {
            typeSet_Month.add(code.substring(8));
        }
        fillEveryMonth2Series(yearMonths, numSeriesMap_Month, moneySeriesMap_Month,
                typeSet_Month, okNumSeriesMap_Month, okMoneySeriesMap_Month);

        List<String> monthSeries = getSeriesJson(typeSet_Month, okNumSeriesMap_Month, okMoneySeriesMap_Month);
        seriesJsonMap.put("month", monthSeries);

        //-----------------------------------------------------------------

        Set<String> typeSet_Sum = new HashSet<String>();
        for (String code : numSeriesMap_Month.keySet()) {
            typeSet_Sum.add(code.substring(8));
        }

        Map<String, Series> okNumSeriesMap_Sum = new TreeMap<String, Series>();
        Map<String, Series> okMoneySeriesMap_Sum = new TreeMap<String, Series>();
        fillEveryMonth2Series(yearMonths, numSeriesMap_Sum, moneySeriesMap_Sum,
                typeSet_Sum, okNumSeriesMap_Sum, okMoneySeriesMap_Sum);

        List<String> sumSeries = getSeriesJson(typeSet_Sum, okNumSeriesMap_Sum, okMoneySeriesMap_Sum);
        seriesJsonMap.put("num", sumSeries);

        return seriesJsonMap;
    }


    private void tradeSums2SeriesMap_Month(
            Map<String, Series> numSeriesMap, Map<String, Series> moneySeriesMap,
            String yearMonth, String name, List<? extends TradeSum> tradeSums) {

        if (tradeSums.isEmpty()) return;

        //若统计表中无脏数据，则某一年某一月的某一种产品在统计表中只有一条记录,取第一条
        TradeSum tradeSum = null;
        tradeSum = tradeSums.iterator().next();

        //产品名:洗涤剂(包括助洗剂) -> 洗涤剂
        name = name.contains(left_round_bracket.value()) ?
                substringBefore(name, left_round_bracket.value()) : name;

        //数量统计
        if (Boolean.valueOf(num_axis.value())) {
            if (numSeriesMap.get(yearMonth + underline.value() + name) == null) {
                List<Double> doubleList = new ArrayList<Double>();
                doubleList.add(tradeSum.getNumMonth().doubleValue());
                numSeriesMap.put(yearMonth + underline.value() + name,
                        new Series(yearMonth + underline.value() + name, series_num_type.value(), 0, doubleList));
            } else {
                numSeriesMap.get(yearMonth + underline.value() + name).getData().add(tradeSum.getNumMonth().doubleValue());
            }
        }

        //金额统计
        if (Boolean.valueOf(money_axis.value())) {
            if (moneySeriesMap.get(yearMonth + underline.value() + name) == null) {
                List<Double> doubleList = new ArrayList<Double>();
                doubleList.add(tradeSum.getMoneyMonth().doubleValue());
                moneySeriesMap.put(yearMonth + underline.value() + name,
                        new Series(yearMonth + underline.value() + name, series_money_type.value(), 1, doubleList));
            } else {
                moneySeriesMap.get(yearMonth + underline.value() + name).getData().add(tradeSum.getMoneyMonth().doubleValue());
            }
        }


    }

    private void tradeSums2SeriesMap_Sum(
            Map<String, Series> numSeriesMap, Map<String, Series> moneySeriesMap,
            String yearMonth, String name, List<? extends TradeSum> tradeSums) {

        if (tradeSums.isEmpty()) return;

        //若统计表中无脏数据，则某一年某一月的某一种产品在统计表中只有一条记录,取第一条
        TradeSum tradeSum = null;
        tradeSum = tradeSums.iterator().next();

        //产品名:洗涤剂(包括助洗剂) -> 洗涤剂
        name = name.contains(left_round_bracket.value()) ?
                substringBefore(name, left_round_bracket.value()) : name;

        //数量统计
        if (Boolean.valueOf(num_axis.value())) {
            if (numSeriesMap.get(yearMonth + underline.value() + name) == null) {
                List<Double> doubleList = new ArrayList<Double>();
                doubleList.add(tradeSum.getNumSum().doubleValue());
                numSeriesMap.put(yearMonth + underline.value() + name,
                        new Series(yearMonth + underline.value() + name, series_num_type.value(), 0, doubleList));
            } else {
                numSeriesMap.get(yearMonth + underline.value() + name).getData().add(tradeSum.getNumSum().doubleValue());
            }
        }

        //金额统计
        if (Boolean.valueOf(money_axis.value())) {
            if (moneySeriesMap.get(yearMonth + underline.value() + name) == null) {
                List<Double> doubleList = new ArrayList<Double>();
                doubleList.add(tradeSum.getMoneySum().doubleValue());
                moneySeriesMap.put(yearMonth + underline.value() + name,
                        new Series(yearMonth + underline.value() + name, series_money_type.value(), 1, doubleList));
            } else {
                moneySeriesMap.get(yearMonth + underline.value() + name).getData().add(tradeSum.getMoneySum().doubleValue());
            }
        }

    }

    /**
     * 构造SeriesMap
     *
     * @param numSeriesMap
     * @param moneySeriesMap
     * @param name
     * @param detailCounts
     */
    private void detailCounts2SeriesMap
    (Map<String, Series> numSeriesMap, Map<String, Series> moneySeriesMap,
     String yearMonth, String name, List<? extends DetailCount> detailCounts) {

        if (detailCounts.isEmpty()) return;

        //若统计表中无脏数据，则某一年某一月的某一种产品在统计表中只有一条记录,取第一条
        DetailCount detailCount = null;
        detailCount = detailCounts.iterator().next();

        //产品名:洗涤剂(包括助洗剂) -> 洗涤剂
        String productName = detailCount.getProductName().contains(left_round_bracket.value()) ?
                substringBefore(detailCount.getProductName(), left_round_bracket.value()) : detailCount.getProductName();

        //数量统计
        if (Boolean.valueOf(num_axis.value())) {
            if (numSeriesMap.get(yearMonth + underline.value() + name) == null) {
                List<Double> doubleList = new ArrayList<Double>();
                doubleList.add(detailCount.getNum().doubleValue());
                numSeriesMap.put(yearMonth + underline.value() + name,
                        new Series(yearMonth + underline.value() + name
                                + left_square_bracket.value() + detailCount.getUnit() + right_square_bracket.value(),
                                series_num_type.value(), 0, doubleList));
            } else {
                numSeriesMap.get(yearMonth + underline.value() + name).getData().add(detailCount.getNum().doubleValue());
            }
        }


        //金额统计
        if (Boolean.valueOf(money_axis.value())) {
            if (moneySeriesMap.get(yearMonth + underline.value() + name) == null) {
                List<Double> doubleList = new ArrayList<Double>();
                doubleList.add(detailCount.getMoney().doubleValue());
                moneySeriesMap.put(yearMonth + underline.value() + name,
                        new Series(yearMonth + underline.value() + name, series_money_type.value(), 1, doubleList));
            } else {
                moneySeriesMap.get(yearMonth + underline.value() + name).getData().add(detailCount.getMoney().doubleValue());
            }
        }

    }


}
