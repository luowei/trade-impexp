package com.oilchem.trade.domain.abstrac;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 进出口总表
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-5
 * Time: 上午10:27
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//@DiscriminatorColumn(name="tradeSumType",discriminatorType=DiscriminatorType.STRING)
public class TradeSum extends IdEntity implements Serializable {
    //进出口年
    @Column(name = "col_year")
    private Integer year;
    //进出口月
    @Column(name = "col_month")
    private Integer month;
    @Column(name = "year_month")
    private String yearMonth;
    //产品类型
    @Column(name = "product_type")
    private String productType;
    // 产品名称
    @Column(name = "product_name")
    private String productName;
    //当月数量
    @Column(name = "num_month")
    private BigDecimal numMonth;
    //累计总数量
    @Column(name = "num_sum")
    private BigDecimal numSum;
    //当月金额
    @Column(name = "money_month")
    private BigDecimal moneyMonth;
    //累计金额
    @Column(name = "money_sum")
    private BigDecimal moneySum;
    //当月平均价格
    @Column(name = "avgprice_month")
    private BigDecimal avgPriceMonth;
    //累积平均价格
    @Column(name = "avgprice_sum")
    private BigDecimal avgPriceSum;
    //与上月数量增长比
    @Column(name = "num_premonth_incratio")
    private BigDecimal pm;
    //与上年同月数量增长比
    @Column(name = "num_preyearsamemonth_incratio")
    private BigDecimal py;
    //与上年同期数量增长比
    @Column(name = "num_preyearsamequarter_incratio")
    private BigDecimal pq;

    public TradeSum() {
    }

    public TradeSum(Integer year, Integer month,
                    String yearMonth, String productType) {
        this.year = year;
        this.month = month;
        this.yearMonth = yearMonth;
        this.productType = productType;
    }

    public TradeSum(String productName, BigDecimal numMonth, BigDecimal numSum,
                    BigDecimal moneyMonth, BigDecimal moneySum,
                    BigDecimal avgPriceMonth, BigDecimal avgPriceSum,
                    BigDecimal pm, BigDecimal py, BigDecimal pq) {
        this.productName = productName;
        this.numMonth = numMonth;
        this.numSum = numSum;
        this.moneyMonth = moneyMonth;
        this.moneySum = moneySum;
        this.avgPriceMonth = avgPriceMonth;
        this.avgPriceSum = avgPriceSum;
        this.pm = pm;
        this.py = py;
        this.pq = pq;
    }

    public Integer getYear() {
        return year;
    }

    public TradeSum setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Integer getMonth() {
        return month;
    }

    public TradeSum setMonth(Integer month) {
        this.month = month;
        return this;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public TradeSum setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
        return this;
    }

    public String getProductType() {
        return productType;
    }

    public TradeSum setProductType(String productType) {
        this.productType = productType;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public TradeSum setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public BigDecimal getNumMonth() {
        return numMonth;
    }

    public TradeSum setNumMonth(BigDecimal numMonth) {
        this.numMonth = numMonth;
        return this;
    }

    public BigDecimal getNumSum() {
        return numSum;
    }

    public TradeSum setNumSum(BigDecimal numSum) {
        this.numSum = numSum;
        return this;
    }

    public BigDecimal getMoneyMonth() {
        return moneyMonth;
    }

    public TradeSum setMoneyMonth(BigDecimal moneyMonth) {
        this.moneyMonth = moneyMonth;
        return this;
    }

    public BigDecimal getMoneySum() {
        return moneySum;
    }

    public TradeSum setMoneySum(BigDecimal moneySum) {
        this.moneySum = moneySum;
        return this;
    }

    public BigDecimal getAvgPriceMonth() {
        return avgPriceMonth;
    }

    public TradeSum setAvgPriceMonth(BigDecimal avgPriceMonth) {
        this.avgPriceMonth = avgPriceMonth;
        return this;
    }

    public BigDecimal getAvgPriceSum() {
        return avgPriceSum;
    }

    public TradeSum setAvgPriceSum(BigDecimal avgPriceSum) {
        this.avgPriceSum = avgPriceSum;
        return this;
    }

    public BigDecimal getPm() {
        return pm;
    }

    public TradeSum setPm(BigDecimal numPreMonthIncRadio) {
        this.pm = numPreMonthIncRadio;
        return this;
    }

    public BigDecimal getPy() {
        return py;
    }

    public TradeSum setPy(BigDecimal py) {
        this.py = py;
        return this;
    }

    public BigDecimal getPq() {
        return pq;
    }

    public TradeSum setPq(BigDecimal pq) {
        this.pq = pq;
        return this;
    }
}
