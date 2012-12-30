package com.oilchem.trade.domain.abstrac;

import com.oilchem.trade.domain.abstrac.IdEntity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-7
 * Time: 上午10:19
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public class DetailCount extends IdEntity  implements Serializable {
    @Column(name="col_year")
    private Integer year;
    @Column(name="col_month")
    private Integer month;
    @Column(name="year_month")
    private String yearMonth;
    @Column(name="product_code")
    private String productCode;
    @Column(name="product_name")
    private String productName;
    @Column(name="unit")
    private String unit;
    @Column(name="num")
    private BigDecimal num;
    @Column(name="money")
    private BigDecimal money;
    @Column(name="avg_price")
    private BigDecimal unitPrice;


    public DetailCount() {
    }

    public DetailCount(String productName) {
        this.productName = productName;
    }

    public DetailCount(BigDecimal num, BigDecimal money,
                       BigDecimal unitPrice) {
        this.num = num;
        this.money = money;
        this.unitPrice = unitPrice;
    }

    public Integer getYear() {
        return year;
    }

    public DetailCount setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Integer getMonth() {
        return month;
    }

    public DetailCount setMonth(Integer month) {
        this.month = month;
        return this;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public DetailCount setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
        return this;
    }

    public String getProductCode() {
        return productCode;
    }

    public DetailCount setProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public DetailCount setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public DetailCount setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public BigDecimal getNum() {
        return num;
    }

    public DetailCount setNum(BigDecimal num) {
        this.num = num;
        return this;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public DetailCount setMoney(BigDecimal money) {
        this.money = money;
        return this;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public DetailCount setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }
}
