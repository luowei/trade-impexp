package com.oilchem.trade.domain.abstrac;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 进出口明细表
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-5
 * Time: 上午10:15
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public class TradeDetail
        extends IdEntity implements Serializable {
    //年
    @Column(name = "col_year")
    private Integer year;
    //月
    @Column(name = "col_month")
    private Integer month;
    @Column(name = "year_month")
    private String yearMonth;
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

    public TradeDetail() {
    }

    public TradeDetail(String productName, BigDecimal amount,
                       BigDecimal amountMoney, BigDecimal unitPrice) {
        this.productName = productName;
        this.amount = amount;
        this.amountMoney = amountMoney;
        this.unitPrice = unitPrice;
    }

    public Integer getYear() {
        return year;
    }

    public TradeDetail setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Integer getMonth() {
        return month;
    }

    public TradeDetail setMonth(Integer month) {
        this.month = month;
        return this;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public TradeDetail setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
        return this;
    }

    public String getProductCode() {
        return productCode;
    }

    public TradeDetail setProductCode(String productCode) {
        this.productCode = productCode;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public TradeDetail setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getCompanyType() {
        return companyType;
    }

    public TradeDetail setCompanyType(String companyType) {
        this.companyType = companyType;
        return this;
    }

    public String getTradeType() {
        return tradeType;
    }

    public TradeDetail setTradeType(String tradeType) {
        this.tradeType = tradeType;
        return this;
    }

    public String getTransportation() {
        return transportation;
    }

    public TradeDetail setTransportation(String transportation) {
        this.transportation = transportation;
        return this;
    }

    public String getCustoms() {
        return customs;
    }

    public TradeDetail setCustoms(String customs) {
        this.customs = customs;
        return this;
    }

    public String getCity() {
        return city;
    }

    public TradeDetail setCity(String city) {
        this.city = city;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public TradeDetail setCountry(String country) {
        this.country = country;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TradeDetail setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public TradeDetail setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    public BigDecimal getAmountMoney() {
        return amountMoney;
    }

    public TradeDetail setAmountMoney(BigDecimal amountMoney) {
        this.amountMoney = amountMoney;
        return this;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public TradeDetail setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public Integer getCompanyTypeId() {
        return companyTypeId;
    }

    public TradeDetail setCompanyTypeId(Integer companyTypeId) {
        this.companyTypeId = companyTypeId;
        return this;
    }

    public Integer getTradeTypeId() {
        return tradeTypeId;
    }

    public TradeDetail setTradeTypeId(Integer tradeTypeId) {
        this.tradeTypeId = tradeTypeId;
        return this;
    }

    public Integer getTransportaionId() {
        return transportaionId;
    }

    public TradeDetail setTransportaionId(Integer transportaionId) {
        this.transportaionId = transportaionId;
        return this;
    }

    public Integer getCustomsId() {
        return customsId;
    }

    public TradeDetail setCustomsId(Integer customsId) {
        this.customsId = customsId;
        return this;
    }

    public Integer getCityId() {
        return cityId;
    }

    public TradeDetail setCityId(Integer cityId) {
        this.cityId = cityId;
        return this;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public TradeDetail setCountryId(Integer countryId) {
        this.countryId = countryId;
        return this;
    }
}
