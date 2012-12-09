package com.oilchem.trade.chart

import ofc4j.model.Chart

import ofc4j.model.elements.LineChart

import ofc4j.model.axis.Label
import com.oilchem.trade.domain.abstrac.TradeSum
import com.oilchem.trade.domain.abstrac.TradeDetail
import ofc4j.model.Text
import com.oilchem.trade.bean.ChartData

import ofc4j.model.elements.BarChart

class DetailChart extends Common {

    /**
     * 获得拆线图
     * @return
     */
    def List<Chart> getDetailLineChart(Map<String, ChartData<TradeDetail>> chartDataMap, String chartType) {

        //-------------tradeDetail  ----------------------
        Chart amountChat = new Chart()
                .setTitle(new Text("平均数量")).setYLegend(new Text("平均数量", style))
        Chart amountMoneyChart = new Chart()
                .setTitle(new Text("平均金额")).setYLegend(new Text("平均金额", style))
        Chart unitpriceChart = new Chart()
                .setTitle(new Text("单价")).setYLegend(new Text("单价", style))

        Map<String, BigDecimal> minRangMap = new TreeMap<String, BigDecimal>();
        Map<String, BigDecimal> maxRangMap = new TreeMap<String, BigDecimal>();
        Map<String, BigDecimal> stepMap = new TreeMap<String, BigDecimal>();
        //遍历每一种产品
        chartDataMap.each {

            String code = it.key;
            ChartData<TradeDetail> chartData = it.value
            List<LineChart> detailElementList = null
            if ("barChart".equals(chartType)) {
                detailElementList = getDetailBarList(code, chartData.elementList)
            } else {
                detailElementList = getDetailLineList(code, chartData.elementList)
            }

            amountChat = newChart(amountChat, chartData, "amount", minRangMap, maxRangMap, stepMap).addElements(detailElementList.get(0))
            amountMoneyChart = newChart(amountMoneyChart, chartData, "amountMoney", minRangMap, maxRangMap, stepMap).addElements(detailElementList.get(1))
            unitpriceChart = newChart(unitpriceChart, chartData, "unitPrice", minRangMap, maxRangMap, stepMap).addElements(detailElementList.get(2))
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
            amountLineChart = newLineElement(amountLineChart, it).addValues(it == null ? 0 : it.amount.doubleValue())
            amountMoneyLineChart = newLineElement(amountMoneyLineChart, it).addValues(it == null ? 0 : it.amountMoney.doubleValue())
            unitPriceLineChart = newLineElement(unitPriceLineChart, it).addValues(it == null ? 0 : it.unitPrice.doubleValue())
        }
        [amountLineChart, amountMoneyLineChart, unitPriceLineChart]

    }

    def getDetailBarList(String code, List<TradeDetail> detailList) {

        //建立三个柱
        BarChart amountBarChart = new BarChart(BarChart.Style.GLASS).setText(code)
        BarChart amountMoneyBarChart = new BarChart(BarChart.Style.GLASS).setText(code)
        BarChart unitPriceBarChart = new BarChart(BarChart.Style.GLASS).setText(code)

        //遍历每个月
        detailList.each {
            amountBarChart = newBarElement(amountBarChart).addValues(it == null ? 0 : it.amount.doubleValue())
            amountMoneyBarChart = newBarElement(amountMoneyBarChart).addValues(it == null ? 0 : it.amountMoney.doubleValue())
            unitPriceBarChart = newBarElement(unitPriceBarChart).addValues(it == null ? 0 : it.unitPrice.doubleValue())
        }
        [amountBarChart, amountMoneyBarChart, unitPriceBarChart]

    }


//    static void main(agrs) {
//        def x_legend = "aaa"
//        def y_legend = "bbb"
//        List<TradeSum> tradeSumList = null
//        List<TradeDetail> tradeDetailList1 = new ArrayList<TradeDetail>()
//        List<TradeDetail> tradeDetailList2 = new ArrayList<TradeDetail>()
//        tradeDetailList1 << new TradeDetail().setAmount(12345).setUnitPrice(11111).setProductName("aaaaaaaa").setAmountMoney(555555).setYearMonth("2010-05")
//        tradeDetailList1 << new TradeDetail().setAmount(23333).setUnitPrice(22222).setProductName("bbbbbbbb").setAmountMoney(666666).setYearMonth("2010-05")
//        tradeDetailList2 << new TradeDetail().setAmount(33563).setUnitPrice(33333).setProductName("cccccccc").setAmountMoney(777777).setYearMonth("2012-11")
//        tradeDetailList2 << new TradeDetail().setAmount(44524).setUnitPrice(44444).setProductName("dddddddd").setAmountMoney(888888).setYearMonth("2012-11")
//
//        ChartData<TradeDetail> chartData1 = new ChartData<TradeDetail>();
//        ChartData<TradeDetail> chartData2 = new ChartData<TradeDetail>();
//
//        List<Label> labels = []
//        labels << new Label("2010-05")
//        labels << new Label("2012-11")
//
//        chartData1.elementList << tradeDetailList1
//        chartData2.elementList << tradeDetailList2
//        chartData1.labels << labels
//        chartData2.labels << labels
//
//        chartData1.minRangMap.put("amount", 3.234)
//        chartData2.minRangMap.put("amount", 1.264)
//        chartData1.maxRangMap.put("amount", 2534.32)
//        chartData2.maxRangMap.put("amount", 5635.32)
//
//        chartData1.minRangMap.put("amountMoney", 3.234)
//        chartData2.minRangMap.put("amountMoney", 1.264)
//        chartData1.maxRangMap.put("amountMoney", 2534.32)
//        chartData2.maxRangMap.put("amountMoney", 5635.32)
//
//        chartData1.minRangMap.put("unitPrice", 3.234)
//        chartData2.minRangMap.put("unitPrice", 1.264)
//        chartData1.maxRangMap.put("unitPrice", 2534.32)
//        chartData2.maxRangMap.put("unitPrice", 5635.32)
//
//        List<ChartData<TradeDetail>> chartDataList = [];
//        chartDataList << chartData1
//        chartDataList << chartData2
//
//        List<Chart> chartList = new DetailChart().getDetailLineChart(chartDataList)
//        println chartList;
//    }

}