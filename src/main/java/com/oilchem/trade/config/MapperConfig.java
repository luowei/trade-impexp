package com.oilchem.trade.config;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-8
 * Time: 下午2:55
 * To change this template use File | Settings | File Templates.
 */
public class MapperConfig {

    /**
     * Access明细表字段
     */
    public static String PRODUCT_CODE = "col002";
    public static String PRODUCT_NAME = "PName";
    public static String COMPANY_TYPE = "E2";
    public static String TRADE_TYPE = "TradeName";
    public static String TRANSPORTATION = "TransName";
    public static String CUSTOMS = "CustomsName";
    public static String CITY = "cityName";
    public static String COUNTRY = "PGCountryName";
    public static String AMOUNT = "Col012";
    public static String UNIT = "UnitName";
    public static String ACOUNTMONEY = "Col013";


    /**
     * excel总表字段
     */
    public static String EXCEL_PRODUCT_NAME = "产品品种";
    public static String NUM_MONTH = "当月数量";
    public static String NUM_SUM = "累计总数量";
    public static String MONEY_MONTH = "当月金额";
    public static String MONEY_SUM = "累计总金额";
    public static String AVG_PRICE_MONTH = "当月平均价格";
    public static String AVG_PRICE_SUM = "累计平均价格";
    public static String NUM_PREMONTH_INCRATIO = "与上月数量增长比";
    public static String NUM_PREYEARSAMEMONTH_INCRATIO = "与上年同月数量增长比";
    public static String NUM_PREYEARSAMEQUARTER_INCRATIO = "与上年同期数量增长比";


}
