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

import static com.oilchem.trade.bean.DocBean.Config.axis_steps;
import static java.lang.Integer.toHexString
import static com.oilchem.trade.bean.DocBean.ExcelFiled.*
import static com.oilchem.trade.bean.DocBean.Config.scale_size
import com.oilchem.trade.bean.DocBean

import static com.oilchem.trade.bean.DocBean.Config.batch_updatesize

class MyChart {

    def style = "{color:#736AEF; font-size:12px;}"

    /**
     * 获得拆线图
     * @return
     */
    def List<Chart> getDetailLineChart(Map<String, ChartData<TradeDetail>> chartDataMap) {

        //-------------tradeDetail  ----------------------
        Chart amountChat = new Chart()
                .setTitle(new Text("数量")).setYLegend(new Text("数量", style))
        Chart amountMoneyChart = new Chart()
                .setTitle(new Text("金额")).setYLegend(new Text("金额", style))
        Chart unitpriceChart = new Chart()
                .setTitle(new Text("单价")).setYLegend(new Text("单价", style))

        Map<String,BigDecimal> minRangMap =  new TreeMap<String, BigDecimal>();
        Map<String,BigDecimal> maxRangMap = new TreeMap<String, BigDecimal>();
        Map<String,BigDecimal> stepMap = new TreeMap<String, BigDecimal>();
        //遍历每一种产品
        chartDataMap.each {

            String code = it.key;
            ChartData<TradeDetail> chartData = it.value
            List<LineChart> detailLineList = getDetailLineList(code, chartData.elementList)

            amountChat = newChart(amountChat, chartData, "amount",minRangMap,maxRangMap,stepMap).addElements(detailLineList.get(0))
            amountMoneyChart = newChart(amountMoneyChart, chartData, "amountMoney",minRangMap,maxRangMap,stepMap).addElements(detailLineList.get(1))
            unitpriceChart = newChart(unitpriceChart, chartData, "unitPrice",minRangMap,maxRangMap,stepMap).addElements(detailLineList.get(2))
        }
        [amountChat, amountMoneyChart, unitpriceChart]
    }

    def getDetailLineList(String code, List<TradeDetail> detailList) {

        //建立三根线
        LineChart amountLineChart = new LineChart().setText(code)
        LineChart amountMoneyLineChart = new LineChart().setText(code)
        LineChart unitPriceLineChart = new LineChart().setText(code)

        //遍历每个月
        detailList.each {
            amountLineChart = newLineElement(amountLineChart, it).addValues(it==null ? 0 : it.amount.doubleValue())
            amountMoneyLineChart = newLineElement(amountMoneyLineChart, it).addValues(it==null ? 0 : it.amountMoney.doubleValue())
            unitPriceLineChart = newLineElement(unitPriceLineChart, it).addValues(it==null ? 0 : it.unitPrice.doubleValue())
        }
        [amountLineChart, amountMoneyLineChart, unitPriceLineChart]

    }



    def List<Chart> getSumLineChart(Map<String, ChartData<TradeSum>> chartDataMap) {

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

         Map<String,BigDecimal> minRangMap =  new TreeMap<String, BigDecimal>();
         Map<String,BigDecimal> maxRangMap = new TreeMap<String, BigDecimal>();
         Map<String,BigDecimal> stepMap = new TreeMap<String, BigDecimal>();

        for(Map.Entry<String,ChartData<TradeSum>> it:chartDataMap) {
            String code = it.key
            ChartData<TradeSum> chartData = it.value
            List<List<LineChart>> sumFiledLineList = getSumFiledLineList(code, chartData.elementList)


            nummonthChat = newChart(nummonthChat, chartData, excel_num_month.value(),minRangMap,maxRangMap,stepMap)
                    .addElements(sumFiledLineList.get(0))

            numSumChat = newChart(numSumChat, chartData, excel_num_sum.value(),minRangMap,maxRangMap,stepMap)
                    .addElements(sumFiledLineList.get(1))

            moneyMonthChat = newChart(moneyMonthChat, chartData, excel_money_month.value(),minRangMap,maxRangMap,stepMap)
                    .addElements(sumFiledLineList.get(2))

            moneySumChat = newChart(moneySumChat, chartData, excel_money_sum.value(),minRangMap,maxRangMap,stepMap)
                    .addElements(sumFiledLineList.get(3))

            avgPriceMonthChat = newChart(avgPriceMonthChat, chartData, excel_avg_price_month.value(),minRangMap,maxRangMap,stepMap)
                    .addElements(sumFiledLineList.get(4))

            avgPriceSumChat = newChart(avgPriceSumChat, chartData, excel_avg_price_sum.value(),minRangMap,maxRangMap,stepMap)
                    .addElements(sumFiledLineList.get(5))

            pmChart = newChart(pmChart, chartData, excel_pm.value(),minRangMap,maxRangMap,stepMap)
                    .addElements(sumFiledLineList.get(6))

            pyChart = newChart(pyChart, chartData, excel_py.value(),minRangMap,maxRangMap,stepMap)
                    .addElements(sumFiledLineList.get(7))

            pqChart = newChart(pqChart, chartData, excel_pq.value(),minRangMap,maxRangMap,stepMap)
                    .addElements(sumFiledLineList.get(8))

        }
        [nummonthChat,numSumChat,moneyMonthChat,moneySumChat,avgPriceMonthChat,avgPriceSumChat,pmChart,pyChart,pqChart]
    }


    def getSumFiledLineList(String code, List<TradeSum> sumsList) {
        List<List<LineChart>> sumFiledLineList = new ArrayList<List<LineChart>>(9)
        LineChart numMonthLineChart = new LineChart().setText(code)
        LineChart numSumLineChart = new LineChart().setText(code)
        LineChart moneyMonthLineChart = new LineChart().setText(code)
        LineChart moneySumhLineChart = new LineChart().setText(code)
        LineChart avgPriceMonthhLineChart = new LineChart().setText(code)
        LineChart avgPriceSumLineChart = new LineChart().setText(code)
        LineChart pmLineChart = new LineChart().setText(code)
        LineChart pyLineChart = new LineChart().setText(code)
        LineChart pqLineChart = new LineChart().setText(code)

        sumsList.each {
            numMonthLineChart = newLineElement(numMonthLineChart, it).addValues(it==null ? 0 : it.numMonth.doubleValue())
            numSumLineChart = newLineElement(numSumLineChart, it).addValues(it==null ? 0 : it.numSum.doubleValue())
            moneyMonthLineChart = newLineElement(moneyMonthLineChart, it).addValues(it==null ? 0 :it.moneyMonth.doubleValue())
            moneySumhLineChart = newLineElement(moneySumhLineChart, it).addValues(it==null ? 0 :it.moneySum.doubleValue())
            avgPriceMonthhLineChart = newLineElement(avgPriceMonthhLineChart, it).addValues(it==null ? 0 :it.avgPriceMonth.doubleValue())
            avgPriceSumLineChart = newLineElement(avgPriceSumLineChart, it).addValues(it==null ? 0 :it.avgPriceSum.doubleValue())
            pmLineChart = newLineElement(pmLineChart, it).addValues(it==null ? 0 :it.pm.doubleValue())
            pyLineChart = newLineElement(pyLineChart, it).addValues(it==null ? 0 :it.py.doubleValue())
            pqLineChart = newLineElement(pqLineChart, it).addValues(it==null ? 0 :it.pq.doubleValue())
        }
        [numMonthLineChart,numSumLineChart,moneyMonthLineChart,moneySumhLineChart,
                avgPriceMonthhLineChart,avgPriceSumLineChart,pmLineChart,pyLineChart,pqLineChart]
    }



    private Chart newChart(Chart chart, ChartData chartData, String key,
                           Map<String,BigDecimal> minRangMap,Map<String,BigDecimal> maxRangMap,Map<String,BigDecimal> stepMap) {

        int scale = Integer.parseInt(scale_size.value())
        def steps = BigDecimal.valueOf(Long.valueOf(axis_steps.value()))

        def minRang = chartData.minRangMap.get(key) == null  ? 0:chartData.minRangMap.get(key)
        def maxRang = chartData.maxRangMap.get(key) == null  ? steps:chartData.maxRangMap.get(key)

        maxRang = maxRang.multiply(BigDecimal.valueOf(1.2))

        //设定最大值最小值
        if(minRangMap.get(key)==null||minRangMap.get(key) < minRang){
            minRangMap.put(key,minRang)
        }
        if( maxRangMap.get(key)==null||maxRangMap.get(key) < maxRang){
            maxRangMap.put(key,maxRang)
        }

        def step = maxRangMap.get(key).divide(steps, scale, BigDecimal.ROUND_HALF_UP).intValue()
        stepMap.put(key,step)

        return chart.setXAxis(new XAxis().addLabels(chartData.labels))
                .setXLegend(new Text("年月"/*chartData.x_legend*/, style))
                .setYAxis(new YAxis()
                .setRange(minRangMap.get(key).intValue(), maxRangMap.get(key).intValue(), stepMap.get(key).intValue()))
    }

    private LineChart newLineElement(LineChart lineChart, def it) {
        return lineChart.setWidth(2)
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