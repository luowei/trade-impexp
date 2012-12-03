package com.oilchem.trade.chart

import ofc4j.model.Chart
import ofc4j.model.axis.YAxis
import ofc4j.model.elements.LineChart
import ofc4j.model.axis.XAxis
import ofc4j.model.axis.Label
import com.oilchem.trade.domain.abstrac.TradeSum
import com.oilchem.trade.domain.abstrac.TradeDetail
import ofc4j.model.Text
import com.oilchem.trade.bean.ChartData

import static com.oilchem.trade.bean.DocBean.*;
import static com.oilchem.trade.bean.DocBean.Config.axis_steps;
import static java.lang.Integer.toHexString
import static com.oilchem.trade.bean.DocBean.ExcelFiled.*
import static com.oilchem.trade.bean.DocBean.Config.scale_size
import com.oilchem.trade.bean.DocBean

class MyChart {

    def style = "{color:#736AEF; font-size:12px;}"

    /**
     * 获得拆线图
     * @return
     */
    def List<Chart> getDetailLineChart(ChartData<TradeDetail> chartData) {

        //-------------tradeDetail  ----------------------
        List<Chart> detailChartList = new ArrayList<Chart>();

        Chart amountChat = new Chart()
                .setTitle(new Text("数量")).setYLegend(new Text("数量", style));
        Chart amountMoneyChart = new Chart()
                .setTitle(new Text("金额")).setYLegend(new Text("金额", style));
        Chart unitpriceChart = new Chart()
                .setTitle(new Text("平均价格")).setYLegend(new Text("平均价格", style));

        //遍历每个月
//        chartDataList.each {

        List<List<LineChart>> detailFiledLineList = getDetailFiledListList(chartData.elementList)

        detailChartList << newChart(amountChat, chartData, "amount").addElements(detailFiledLineList.get(0))
        detailChartList << newChart(amountMoneyChart, chartData, "amountMoney").addElements(detailFiledLineList.get(1))
        detailChartList << newChart(unitpriceChart, chartData, "unitPrice").addElements(detailFiledLineList.get(2))
//        }
        detailChartList
    }



    def List<Chart> getSumLineChart(ChartData<TradeSum> chartData) {

        //--------------tradeSum----------------------------
        List<Chart> sumChartList = new ArrayList<Chart>();
        Chart nummonthChat = new Chart()
                .setTitle(new Text(excel_num_month.value())).setYLegend(new Text(excel_num_month.value(), style));
        Chart numSumChat = new Chart()
                .setTitle(new Text(excel_num_sum.value())).setYLegend(new Text(excel_num_sum.value(), style))
        Chart moneyMonthChat = new Chart()
                .setTitle(new Text(excel_money_month.value())).setYLegend(new Text(excel_money_month.value(), style))
        Chart moneySumChat = new Chart()
                .setTitle(new Text(excel_money_sum.value())).setYLegend(new Text(excel_money_sum.value(), style));
        Chart avgPriceMonthChat = new Chart()
                .setTitle(new Text(excel_avg_price_month.value())).setYLegend(new Text(excel_avg_price_month.value(), style));
        Chart avgPriceSumChat = new Chart()
                .setTitle(new Text(excel_avg_price_sum.value())).setYLegend(new Text(excel_avg_price_sum.value(), style));
        Chart pmChart = new Chart()
                .setTitle(new Text(excel_pm.value())).setYLegend(new Text(excel_pm.value(), style));
        Chart pyChart = new Chart()
                .setTitle(new Text(excel_py.value())).setYLegend(new Text(excel_py.value(), style));
        Chart pqChart = new Chart()
                .setTitle(new Text(excel_pq.value())).setYLegend(new Text(excel_pq.value(), style));

            List<List<LineChart>> sumFiledLineList = getSumFiledLineList(chartData.elementList)

            sumChartList << newChart(nummonthChat, chartData, excel_num_month.value()).addElements(sumFiledLineList.get(0))
            sumChartList << newChart(numSumChat, chartData, excel_num_sum.value()).addElements(sumFiledLineList.get(1))
            sumChartList << newChart(moneyMonthChat, chartData, excel_money_month.value()).addElements(sumFiledLineList.get(2))
            sumChartList << newChart(moneySumChat, chartData, excel_money_sum.value()).addElements(sumFiledLineList.get(3))
            sumChartList << newChart(avgPriceMonthChat, chartData, excel_avg_price_month.value()).addElements(sumFiledLineList.get(4))
            sumChartList << newChart(avgPriceSumChat, chartData, excel_avg_price_sum.value()).addElements(sumFiledLineList.get(5))
            sumChartList << newChart(pmChart, chartData, excel_pm.value()).addElements(sumFiledLineList.get(6))
            sumChartList << newChart(pyChart, chartData, excel_py.value()).addElements(sumFiledLineList.get(7))
            sumChartList << newChart(pqChart, chartData, excel_pq.value()).addElements(sumFiledLineList.get(8))

        sumChartList
    }


    def getSumFiledLineList(List<TradeSum> sumsList) {
        List<List<LineChart>> sumFiledLineList = new ArrayList<List<LineChart>>(9);
        LineChart numMonthLineChart = new LineChart();
        LineChart numSumLineChart = new LineChart();
        LineChart moneyMonthLineChart = new LineChart();
        LineChart moneySumhLineChart = new LineChart();
        LineChart avgPriceMonthhLineChart = new LineChart();
        LineChart avgPriceSumLineChart = new LineChart();
        LineChart pmLineChart = new LineChart();
        LineChart pyLineChart = new LineChart();
        LineChart pqLineChart = new LineChart();

        sumsList.each {
            sumFiledLineList.size() < 1 ? sumFiledLineList << new ArrayList<LineChart>() : sumFiledLineList
            sumFiledLineList.get(0) << newLineElement(numMonthLineChart, it).addValues(it.numMonth)
                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)

            sumFiledLineList.size() < 2 ? sumFiledLineList << new ArrayList<LineChart>() : sumFiledLineList
            sumFiledLineList.get(1) << newLineElement(numSumLineChart, it).addValues(it.numSum)
                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)

            sumFiledLineList.size() < 3 ? sumFiledLineList << new ArrayList<LineChart>() : sumFiledLineList
            sumFiledLineList.get(2) << newLineElement(moneyMonthLineChart, it).addValues(it.moneyMonth)
                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)

            sumFiledLineList.size() < 4 ? sumFiledLineList << new ArrayList<LineChart>() : sumFiledLineList
            sumFiledLineList.get(3) << newLineElement(moneySumhLineChart, it).addValues(it.moneySum)
                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)

            sumFiledLineList.size() < 5 ? sumFiledLineList << new ArrayList<LineChart>() : sumFiledLineList
            sumFiledLineList.get(4) << newLineElement(avgPriceMonthhLineChart, it).addValues(it.avgPriceMonth)
                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)

            sumFiledLineList.size() < 6 ? sumFiledLineList << new ArrayList<LineChart>() : sumFiledLineList
            sumFiledLineList.get(5) << newLineElement(avgPriceSumLineChart, it).addValues(it.avgPriceSum)
                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)

            sumFiledLineList.size() < 7 ? sumFiledLineList << new ArrayList<LineChart>() : sumFiledLineList
            sumFiledLineList.get(6) << newLineElement(pmLineChart, it).addValues(it.pm)
                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)

            sumFiledLineList.size() < 8 ? sumFiledLineList << new ArrayList<LineChart>() : sumFiledLineList
            sumFiledLineList.get(7) << newLineElement(pyLineChart, it).addValues(it.py)
                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)

            sumFiledLineList.size() < 9 ? sumFiledLineList << new ArrayList<LineChart>() : sumFiledLineList
            sumFiledLineList.get(8) << newLineElement(pqLineChart, it).addValues(it.pq)
                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)
        }
        sumFiledLineList
    }

    def getDetailFiledListList(List<TradeDetail> detailList) {

        def detailFiledLineList = new ArrayList<List<LineChart>>(3);

        LineChart amountLineChart = new LineChart();
        LineChart amountMoneyLineChart = new LineChart();
        LineChart unitPriceLineChart = new LineChart();

        //添加三个折线图
        detailList.each {
            detailFiledLineList.size() < 1 ? detailFiledLineList << new ArrayList<LineChart>() : detailFiledLineList
            detailFiledLineList.get(0) << newLineElement(amountLineChart, it).addValues(it.amount.doubleValue())
//                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 5) : it.productName)

            detailFiledLineList.size() < 2 ? detailFiledLineList << new ArrayList<LineChart>() : detailFiledLineList
            detailFiledLineList.get(1) << newLineElement(amountMoneyLineChart, it).addValues(it.amountMoney.doubleValue())
//                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 5) : it.productName)

            detailFiledLineList.size() < 3 ? detailFiledLineList << new ArrayList<LineChart>() : detailFiledLineList
            detailFiledLineList.get(2) << newLineElement(unitPriceLineChart, it).addValues(it.unitPrice.doubleValue())
//                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 5) : it.productName)
        }
        detailFiledLineList

    }

    private Chart newChart(Chart chart, ChartData chartData, String key) {

        def minRang = chartData.minRangMap.get(key)==null || chartData.minRangMap.get(key) < 0 ? chartData.minRangMap.get(key) : 0
        def maxRang = chartData.maxRangMap.get(key).multiply(BigDecimal.valueOf(1.2))
        maxRang =  maxRang.compareTo(BigDecimal.valueOf(Long.valueOf(axis_steps.value()))) < 0 ?
            BigDecimal.valueOf(Long.valueOf(axis_steps.value())) : maxRang;


        int scale = Integer.parseInt(scale_size.value())
        def steps = BigDecimal.valueOf(Long.valueOf(axis_steps.value()));
        def step = maxRang.divide(steps, scale, BigDecimal.ROUND_HALF_UP).intValue()

        return chart.setXAxis(new XAxis().addLabels(chartData.labels))
                .setXLegend(new Text("year-month"/*chartData.x_legend*/, style))
                .setYAxis(new YAxis()
                .setRange(minRang, maxRang.intValue(), step))
    }

    private LineChart newLineElement(LineChart lineChart, def it) {
        return lineChart.setWidth(1)
                .setColour(getRadomColor())
                .setDotSize(5)
    }

    /**
     * 获得柱状图
     * @return
     */
    def getBarChart(List<TradeSum> tradeSumList, ArrayList<LineChart> lineElements,
                    List<TradeDetail> tradeDetailList, ArrayList<Label> labels,
                    DocBean.ChartProps chartProps) {

    }

    /**
     * 获得饼状图
     * @return
     */
    def getpieChart(List<TradeSum> tradeSumList, ArrayList<LineChart> lineElements,
                    List<TradeDetail> tradeDetailList, ArrayList<Label> labels,
                    DocBean.ChartProps chartProps) {

    }

    /**
     * 随机获得颜色值
     * @return
     */
    private def getRadomColor() {
        def rand = new Random()
        def color = "#" + toHexString(rand.nextInt(255)) + toHexString(rand.nextInt(255)) + toHexString(rand.nextInt(255))

        for (def i = 7 - color.length(); i > 0; i--) {
            color = color + "0";
        }
        return color;
    }

    static void main(agrs) {
        def x_legend = "aaa"
        def y_legend = "bbb"
        List<TradeSum> tradeSumList = null
        List<TradeDetail> tradeDetailList1 = new ArrayList<TradeDetail>()
        List<TradeDetail> tradeDetailList2 = new ArrayList<TradeDetail>()
        tradeDetailList1 << new TradeDetail().setAmount(12345).setUnitPrice(11111).setProductName("aaaaaaaa").setAmountMoney(555555).setYearMonth("2010-05")
        tradeDetailList1 << new TradeDetail().setAmount(23333).setUnitPrice(22222).setProductName("bbbbbbbb").setAmountMoney(666666).setYearMonth("2010-05")
        tradeDetailList2 << new TradeDetail().setAmount(33563).setUnitPrice(33333).setProductName("cccccccc").setAmountMoney(777777).setYearMonth("2012-11")
        tradeDetailList2 << new TradeDetail().setAmount(44524).setUnitPrice(44444).setProductName("dddddddd").setAmountMoney(888888).setYearMonth("2012-11")

        ChartData<TradeDetail> chartData1 = new ChartData<TradeDetail>();
        ChartData<TradeDetail> chartData2 = new ChartData<TradeDetail>();

        List<Label> labels = []
        labels << new Label("2010-05")
        labels << new Label("2012-11")

        chartData1.elementList << tradeDetailList1
        chartData2.elementList << tradeDetailList2
        chartData1.labels << labels
        chartData2.labels << labels

        chartData1.minRangMap.put("amount", 3.234)
        chartData2.minRangMap.put("amount", 1.264)
        chartData1.maxRangMap.put("amount", 2534.32)
        chartData2.maxRangMap.put("amount", 5635.32)

        chartData1.minRangMap.put("amountMoney", 3.234)
        chartData2.minRangMap.put("amountMoney", 1.264)
        chartData1.maxRangMap.put("amountMoney", 2534.32)
        chartData2.maxRangMap.put("amountMoney", 5635.32)

        chartData1.minRangMap.put("unitPrice", 3.234)
        chartData2.minRangMap.put("unitPrice", 1.264)
        chartData1.maxRangMap.put("unitPrice", 2534.32)
        chartData2.maxRangMap.put("unitPrice", 5635.32)

        List<ChartData<TradeDetail>> chartDataList = [];
        chartDataList << chartData1
        chartDataList << chartData2

        List<Chart> chartList = new MyChart().getDetailLineChart(chartDataList)
        println chartList;
    }

}