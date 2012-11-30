package com.oilchem.trade.chart

import ofc4j.model.Chart
import ofc4j.model.axis.YAxis
import ofc4j.model.elements.LineChart
import ofc4j.model.axis.XAxis
import ofc4j.model.axis.Label
import com.oilchem.trade.domain.abstrac.TradeSum
import com.oilchem.trade.domain.abstrac.TradeDetail
import ofc4j.model.Text

import com.oilchem.trade.bean.DocBean

import static java.lang.Integer.toHexString
import ofc4j.model.elements.BarChart
import ofc4j.model.elements.PieChart

import static com.oilchem.trade.bean.DocBean.ExcelFiled.*

class MyChart {

    def getLineChart(List list, DocBean.ChartProps chartProps, DocBean.ChartType chartType) {

        List<Label> labels = new ArrayList<Label>()

        def barElements = new ArrayList<BarChart>()
        def pieElements = new ArrayList<PieChart>()

        List<TradeDetail> tradeDetailList = list.every {
            it.class.simpleName.equals TradeDetail.class.simpleName
        } ? (List<TradeDetail>) list : null

        List<TradeSum> tradeSumList = list.every {
            it.class.simpleName.equals TradeSum.class.simpleName
        } ? (List<TradeDetail>) list : null

        println "aaaaaaaaaaaaaaaaaaaa"

        switch (chartType) {
            case chartType.value().equals("barChart"):
                return getBarChart(tradeSumList, lineElements, tradeDetailList, labels, chartProps);
            case chartType.value().equals("pieChart"):
                return
        }

        if (chartType.value().equals("pieChart")) {

        }

        return getLineChart(tradeSumList, tradeDetailList, labels, chartProps)
    }

    /**
     * 获得拆线图
     * @return
     */
    def List<Chart> getLineChart(List<TradeSum> tradeSumList, List<TradeDetail> tradeDetailList,
                                 List<Label> labels, DocBean.ChartProps chartProps) {

        //-------------tradeDetail  ----------------------
        List<List<LineChart>> detailFiledLineList = new ArrayList<List<LineChart>>(3);
        List<Chart> detailChartList = new ArrayList<Chart>(3);

//        //数量
//        def amountLine = tradeDetailList?.collect(new ArrayList<LineChart>(), {
//            newLineElement().addValues(it.amount)
//                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)
//        })
//        //价格
//        def amountMoneyLine = tradeDetailList?.collect(new ArrayList<LineChart>(), {
//            newLineElement().addValues(it.amountMoney)
//                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)
//        })
//        //单价
//        def unitPriceLine = tradeDetailList?.collect(new ArrayList<LineChart>(), {
//            newLineElement().addValues(it.unitPrice)
//                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)
//        })

        detailFiledLineList = tradeDetailList?.collect {
            detailFiledLineList.get(0) == null ? new ArrayList<LineChart>() : detailFiledLineList.get(0) << newLineElement(it).addValues(it.amount)
            detailFiledLineList.get(1) == null ? new ArrayList<LineChart>() : detailFiledLineList.get(1) << newLineElement(it).addValues(it.amountMoney)
            detailFiledLineList.get(2) == null ? new ArrayList<LineChart>() : detailFiledLineList.get(2) << newLineElement(it).addValues(it.unitPrice)
        }

        //--------------tradeSum----------------------------

        List<List<LineChart>> sumFiledLineList = new ArrayList<List<LineChart>>(9);
        List<Chart> sumChartList = new ArrayList<Chart>(9);
//
//        //当月数量
//        sumFiledLineList << tradeSumList?.collect(new ArrayList<LineChart>(), {
//            newLineElement().addValues(it.numMonth)
//                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)
//        })
//
//        //累计总数量
//        sumFiledLineList << tradeSumList?.collect(new ArrayList<LineChart>(), {
//            newLineElement().addValues(it.numSum)
//                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)
//        })
//
//        ////当月金额
//        sumFiledLineList << tradeSumList?.collect(new ArrayList<LineChart>(), {
//            newLineElement().addValues(it.moneyMonth)
//                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)
//        })
//
//        //累计金额
//        sumFiledLineList << tradeSumList?.collect(new ArrayList<LineChart>(), {
//            newLineElement().addValues(it.moneySum)
//                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)
//        })
//
//        //当月平均价格
//        sumFiledLineList << tradeSumList?.collect(new ArrayList<LineChart>(), {
//            newLineElement().addValues(it.avgPriceMonth)
//                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)
//        })
//
//        //累积平均价格
//        sumFiledLineList << tradeSumList?.collect(new ArrayList<LineChart>(), {
//            newLineElement().addValues(it.avgPriceSum)
//                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)
//        })
//
//        //与上月数量增长比
//        sumFiledLineList << tradeSumList?.collect(new ArrayList<LineChart>(), {
//            newLineElement().addValues(it.numPreMonthIncRatio)
//                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)
//        })
//
//        //与上年同月数量增长比
//        sumFiledLineList << tradeSumList?.collect(new ArrayList<LineChart>(), {
//            newLineElement().addValues(it.numPreYearSameMonthIncRatio)
//                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)
//        })
//
//        //与上年同期数量增长比
//        sumFiledLineList << tradeSumList?.collect(new ArrayList<LineChart>(), {
//            newLineElement().addValues(it.numPreYearSameQuarterInrRatio)
//                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)
//        })


        sumFiledLineList = tradeSumList?.collect(sumFiledLineList, {
            sumFiledLineList.get(0) == null ? new ArrayList<LineChart>() : sumFiledLineList.get(0) << newLineElement(it).addValues(it.numMonth)
            sumFiledLineList.get(1) == null ? new ArrayList<LineChart>() : sumFiledLineList.get(1) << newLineElement(it).addValues(it.numSum)
            sumFiledLineList.get(2) == null ? new ArrayList<LineChart>() : sumFiledLineList.get(2) << newLineElement(it).addValues(it.moneyMonth)
            sumFiledLineList.get(3) == null ? new ArrayList<LineChart>() : sumFiledLineList.get(3) << newLineElement(it).addValues(it.moneySum)
            sumFiledLineList.get(4) == null ? new ArrayList<LineChart>() : sumFiledLineList.get(4) << newLineElement(it).addValues(it.avgPriceMonth)
            sumFiledLineList.get(5) == null ? new ArrayList<LineChart>() : sumFiledLineList.get(5) << newLineElement(it).addValues(it.avgPriceSum)
            sumFiledLineList.get(6) == null ? new ArrayList<LineChart>() : sumFiledLineList.get(6) << newLineElement(it).addValues(it.numPreMonthIncRatio)
            sumFiledLineList.get(7) == null ? new ArrayList<LineChart>() : sumFiledLineList.get(7) << newLineElement(it).addValues(it.numPreYearSameMonthIncRatio)
            sumFiledLineList.get(8) == null ? new ArrayList<LineChart>() : sumFiledLineList.get(8) << newLineElement(it).addValues(it.numPreYearSameQuarterInrRatio)
//             return sumFiledLineList
//                    .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)
        })




        sumFiledLineList.get(0).each {
            print "lineCharts name:" + it.text + " values:" + it.values + "color:" + it.colour + ","

        }

        sumChartList = sumFiledLineList?.collect(sumChartList, {
            newChart(labels, chartProps).setTitle(new Text(excel_num_month.value()))
                    .setYLegend(new Text(excel_num_month.value()))
                    .addElements(numMonthLine)
        })

        //总表
        sumChartList.plus(
                newChart(labels, chartProps).setTitle(new Text(excel_num_month.value()))
                        .setYLegend(new Text(excel_num_month.value()))
                        .addElements(numMonthLine)
        )
                .plus(
                newChart(labels, chartProps).setTitle(new Text(excel_num_month.value()))
                        .setYLegend(new Text(excel_num_month.value()))
                        .addElements(numMonthLine)
        )
                .plus(
                newChart(labels, chartProps).setTitle(new Text(excel_num_month.value()))
                        .setYLegend(new Text(excel_num_month.value()))
                        .addElements(numMonthLine)
        )
                .plus(
                newChart(labels, chartProps).setTitle(new Text(excel_num_month.value()))
                        .setYLegend(new Text(excel_num_month.value()))
                        .addElements(numMonthLine)
        )
                .plus(
                newChart(labels, chartProps).setTitle(new Text(excel_num_month.value()))
                        .setYLegend(new Text(excel_num_month.value()))
                        .addElements(numMonthLine)
        )
                .plus(
                newChart(labels, chartProps).setTitle(new Text(excel_num_month.value()))
                        .setYLegend(new Text(excel_num_month.value()))
                        .addElements(numMonthLine)
        )

        return sumChartList;
    }

    private def Chart newChart(List<Label> labels, DocBean.ChartProps chartProps) {
        new Chart().setXAxis(new XAxis().addLabels(labels))
                .setXLegend(new Text(chartProps.x_legend))
                .setYAxis(new YAxis().setRange(chartProps.minRang, chartProps.maxRang, chartProps.step))
    }

    private def LineChart newLineElement(def it) {
        new LineChart()
                .setWidth(1)
                .setColour(getRadomColor())
                .setDotSize(5)
                .setText(it.productName.length() > 6 ? it.productName.substring(0, 6) : it.productName)
    }

    /**
     * 获得柱状图
     * @return
     */
    def Chart getBarChart(List<TradeSum> tradeSumList, ArrayList<LineChart> lineElements,
                          List<TradeDetail> tradeDetailList, ArrayList<Label> labels,
                          DocBean.ChartProps chartProps) {

    }

    /**
     * 获得饼状图
     * @return
     */
    def Chart getpieChart(List<TradeSum> tradeSumList, ArrayList<LineChart> lineElements,
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
        List<TradeDetail> tradeDetailList = new ArrayList<TradeDetail>()
        tradeDetailList << new TradeDetail().setAmount(12345).setProductName("nameaaaa")
        tradeDetailList << new TradeDetail().setAmount(33333).setProductName("namebbbb")
        new MyChart().getLineChart(tradeDetailList, new DocBean.ChartProps(x_legend, y_legend), DocBean.ChartType.lineChart)
    }

}