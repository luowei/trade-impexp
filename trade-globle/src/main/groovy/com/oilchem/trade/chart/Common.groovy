package com.oilchem.trade.chart

import ofc4j.model.Chart
import com.oilchem.trade.bean.ChartData

import static com.oilchem.trade.bean.DocBean.Config.scale_size
import static com.oilchem.trade.bean.DocBean.Config.axis_steps
import ofc4j.model.axis.XAxis
import ofc4j.model.Text
import ofc4j.model.axis.YAxis
import ofc4j.model.elements.LineChart

import static java.lang.Integer.toHexString
import static java.lang.Integer.toHexString
import static java.lang.Integer.toHexString
import ofc4j.model.elements.BarChart

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-4
 * Time: 上午9:15
 * To change this template use File | Settings | File Templates.
 */
class Common {

     def style = "{color:#736AEF; font-size:12px;}"

    /**
     * 建立一个图表
     * @param chart
     * @param chartData
     * @param key
     * @param minRangMap
     * @param maxRangMap
     * @param stepMap
     * @return
     */
     Chart newChart(Chart chart, ChartData chartData, String key,
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

    /**
     * 设置一条拆线
     * @param lineChart
     * @param it
     * @return
     */
     LineChart newLineElement(LineChart lineChart, def it) {
        return lineChart.setWidth(2)
                .setColour(getRadomColor())
                .setDotSize(5)
    }

    BarChart newBarElement(BarChart barChart){
        return barChart.setColour(getRadomColor())
    }

    /**
     * 随机获得颜色值
     * @return
     */
     def getRadomColor() {
        def rand = new Random()
        def color = "#" + toHexString(rand.nextInt(255)) + toHexString(rand.nextInt(255)) + toHexString(rand.nextInt(255))

        for (def i = 7 - color.length(); i > 0; i--) {
            color = color + "0";
        }
        return color;
    }

}
