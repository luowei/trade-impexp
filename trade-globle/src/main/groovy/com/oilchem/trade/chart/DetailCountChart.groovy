package com.oilchem.trade.chart

import ofc4j.model.Chart
import com.oilchem.trade.bean.ChartData
import ofc4j.model.Text
import ofc4j.model.elements.LineChart
import ofc4j.model.elements.BarChart
import com.oilchem.trade.domain.abstrac.DetailCount

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-12-9
 * Time: 下午1:44
 * To change this template use File | Settings | File Templates.
 */
class DetailCountChart  extends Common {

    /**
     * 获得拆线图
     * @return
     */
    def List<Chart> getDetailCountLineChart(Map<String, ChartData<DetailCount>> chartDataMap, String chartType) {

        //-------------tradeDetail  ----------------------
        Chart numChat = new Chart()
                .setTitle(new Text("总数量")).setYLegend(new Text("总数量", style))
        Chart moneyChart = new Chart()
                .setTitle(new Text("总金额")).setYLegend(new Text("总金额", style))
        Chart unitpriceChart = new Chart()
                .setTitle(new Text("平均价")).setYLegend(new Text("平均价", style))

        Map<String, BigDecimal> minRangMap = new TreeMap<String, BigDecimal>();
        Map<String, BigDecimal> maxRangMap = new TreeMap<String, BigDecimal>();
        Map<String, BigDecimal> stepMap = new TreeMap<String, BigDecimal>();
        //遍历每一种产品
        chartDataMap.each {

            String code = it.key;
            ChartData<DetailCount> chartData = it.value
            List<LineChart> detailElementList = null
            if ("barChart".equals(chartType)) {
                detailElementList = getDetailCountBarList(code, chartData.elementList)
            } else {
                detailElementList = getDetailCountLineList(code, chartData.elementList)
            }

            numChat = newChart(numChat, chartData, "num", minRangMap, maxRangMap, stepMap).addElements(detailElementList.get(0))
            moneyChart = newChart(moneyChart, chartData, "money", minRangMap, maxRangMap, stepMap).addElements(detailElementList.get(1))
            unitpriceChart = newChart(unitpriceChart, chartData, "unitPrice", minRangMap, maxRangMap, stepMap).addElements(detailElementList.get(2))
        }
        [numChat, moneyChart, unitpriceChart]
    }

    def getDetailCountLineList(String code, List<DetailCount> detailList) {

        //建立三根线
        LineChart numLineChart = new LineChart().setText(code)
        LineChart moneyLineChart = new LineChart().setText(code)
        LineChart unitPriceLineChart = new LineChart().setText(code)

        //遍历每个月
        detailList.each {
            numLineChart = newLineElement(numLineChart, it).addValues(it == null ? 0 : it.num.doubleValue())
            moneyLineChart = newLineElement(moneyLineChart, it).addValues(it == null ? 0 : it.money.doubleValue())
            unitPriceLineChart = newLineElement(unitPriceLineChart, it).addValues(it == null ? 0 : it.unitPrice.doubleValue())
        }
        [numLineChart, moneyLineChart, unitPriceLineChart]

    }

    def getDetailCountBarList(String code, List<DetailCount> detailList) {

        //建立三个柱
        BarChart numBarChart = new BarChart(BarChart.Style.GLASS).setText(code)
        BarChart moneyBarChart = new BarChart(BarChart.Style.GLASS).setText(code)
        BarChart unitPriceBarChart = new BarChart(BarChart.Style.GLASS).setText(code)

        //遍历每个月
        detailList.each {
            numBarChart = newBarElement(numBarChart).addValues(it == null ? 0 : it.num.doubleValue())
            moneyBarChart = newBarElement(moneyBarChart).addValues(it == null ? 0 : it.money.doubleValue())
            unitPriceBarChart = newBarElement(unitPriceBarChart).addValues(it == null ? 0 : it.unitPrice.doubleValue())
        }
        [numBarChart, moneyBarChart, unitPriceBarChart]

    }
    
}
