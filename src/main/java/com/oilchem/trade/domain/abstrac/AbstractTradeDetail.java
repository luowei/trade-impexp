package com.oilchem.trade.domain.abstrac;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 进出口明细表
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class AbstractTradeDetail extends IdEntity implements Serializable {
    //进出口年月
    @Column(name = "year_month")
    private Date yearMonth;
    //年
    @Column(name = "col_year")
    private Integer colYear;
    //月
    @Column(name = "col_month")
    private Integer colMonth;
    //产品代码
    @Column(name = "product_code")
    private String productCode;
    //产品名称
    @Column(name = "product_name")
    private String productName;
    //企业性质
    @Column(name = "company_type")
    private String companyType;
    //贸易方式
    @Column(name = "trade_type")
    private String tradeType;
    //运输方式
    @Column(name = "transportation")
    private String transportation;
    //海关
    @Column(name = "customs")
    private String customs;
    //城市
    @Column(name = "city")
    private String city;
    //产销国家
    @Column(name = "country")
    private String country;
    //数量
    @Column(name = "amount")
    private BigDecimal amount;
    //计量单位
    @Column(name = "unit")
    private String unit;
    //金额
    @Column(name = "amount_money")
    private BigDecimal amountMoney;
    //单价
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "company_type_id")
    private Integer companyTypeId;
    @Column(name = "trade_type_id")
    private Integer tradeTypeId;
    @Column(name = "transportation_id")
    private Integer transportaionId;
    @Column(name = "customs_id")
    private Integer customsId;
    @Column(name = "city_id")
    private Integer cityId;
    @Column(name = "country_id")
    private Integer countryId;

    public Date getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Date yearMonth) {
        this.yearMonth = yearMonth;
    }

    public Integer getColYear() {
        return colYear;
    }

    public void setColYear(Integer colYear) {
        this.colYear = colYear;
    }

    public Integer getColMonth() {
        return colMonth;
    }

    public void setColMonth(Integer colMonth) {
        this.colMonth = colMonth;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public String getCustoms() {
        return customs;
    }

    public void setCustoms(String customs) {
        this.customs = customs;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getAmountMoney() {
        return amountMoney;
    }

    public void setAmountMoney(BigDecimal amountMoney) {
        this.amountMoney = amountMoney;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getCompanyTypeId() {
        return companyTypeId;
    }

    public void setCompanyTypeId(Integer companyTypeId) {
        this.companyTypeId = companyTypeId;
    }

    public Integer getTradeTypeId() {
        return tradeTypeId;
    }

    public void setTradeTypeId(Integer tradeTypeId) {
        this.tradeTypeId = tradeTypeId;
    }

    public Integer getTransportaionId() {
        return transportaionId;
    }

    public void setTransportaionId(Integer transportaionId) {
        this.transportaionId = transportaionId;
    }

    public Integer getCustomsId() {
        return customsId;
    }

    public void setCustomsId(Integer customsId) {
        this.customsId = customsId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }
}
