package com.oilchem.trade.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import static com.oilchem.trade.bean.DocBean.Config.scale_size;
import static java.math.BigDecimal.ROUND_HALF_UP;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-12-17
 * Time: 上午8:49
 * To change this template use File | Settings | File Templates.
 */
public class ProductCount implements Serializable {

    private String yearMonth;
    private String productCode;
    private String productName;

    private BigDecimal num;
    private String unit;
    private BigDecimal money;

    private BigDecimal unitPrice;
    private String condition;
    private String autoproductCode;


    private BigDecimal monthSumNum;
    //  num / monthSumNum -> monthNumRatio
    private BigDecimal monthNumRatio;

    private BigDecimal currentSumNum;
    private BigDecimal yearSumNum;
    //  currentSumNum / yearSumNum -> currentSumNumRato
    private BigDecimal currentSumNumRato;

    public ProductCount() {
    }

    public ProductCount(String productCode,String productName, String yearMonth, BigDecimal num,
                        String unit, BigDecimal money, String condition,
                        BigDecimal monthNumRatio, BigDecimal currentSumNum,BigDecimal currentSumNumRato) {
        this.yearMonth = yearMonth;
        this.productCode = productCode;
        this.productName = productName;
        this.num = num;
        this.unit = unit;
        this.money = money;
        this.condition = condition;
        this.monthNumRatio = monthNumRatio;
        this.currentSumNumRato = currentSumNumRato;
        this.currentSumNum = currentSumNum;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public ProductCount setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
        return this;
    }

    public String getAutoproductCode() {
        return autoproductCode;
    }

    public ProductCount setAutoproductCode(String autoproductCode) {
        this.autoproductCode = autoproductCode;
        return this;
    }

    public String getCondition() {
        return condition;
    }

    public ProductCount setCondition(String condition) {
        this.condition = condition;
        return this;
    }

    public String getProductCode() {
        return productCode;
    }

    public ProductCount setProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public ProductCount setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public BigDecimal getNum() {
        return num;
    }

    public void setNum(BigDecimal num) {
        this.num = num;
    }

    public String getUnit() {
        return unit;
    }

    public ProductCount setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public ProductCount setMoney(BigDecimal money) {
        this.money = money;
        return this;
    }

    public BigDecimal getUnitPrice() {
        if (num==null || num.doubleValue()==0D) {
            return BigDecimal.valueOf(0);
        }
        int scale = Integer.parseInt(scale_size.value());
        unitPrice = money.divide(num, scale, ROUND_HALF_UP);
        return unitPrice;
    }

    public BigDecimal getMonthSumNum() {
        return monthSumNum;
    }

    public void setMonthSumNum(BigDecimal monthSumNum) {
        this.monthSumNum = monthSumNum;
    }

    public BigDecimal getMonthNumRatio() {
        return monthNumRatio;
    }

    public void setMonthNumRatio(BigDecimal monthNumRatio) {
        this.monthNumRatio = monthNumRatio;
    }

    public BigDecimal getCurrentSumNum() {
        return currentSumNum;
    }

    public void setCurrentSumNum(BigDecimal currentSumNum) {
        this.currentSumNum = currentSumNum;
    }

    public BigDecimal getYearSumNum() {
        return yearSumNum;
    }

    public void setYearSumNum(BigDecimal yearSumNum) {
        this.yearSumNum = yearSumNum;
    }

    public BigDecimal getCurrentSumNumRato() {
        return currentSumNumRato;
    }

    public void setCurrentSumNumRato(BigDecimal currentSumNumRato) {
        this.currentSumNumRato = currentSumNumRato;
    }
}
