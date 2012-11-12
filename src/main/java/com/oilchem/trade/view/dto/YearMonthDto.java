package com.oilchem.trade.view.dto;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-11
 * Time: 下午7:28
 * To change this template use File | Settings | File Templates.
 */
public class YearMonthDto {

    private Integer year;
    private Integer month;

    /**
     * 进口/出口
     */
    private Integer impExportType;
    /**
     * 产品类型
     */
    private Integer productType;
    /**
     * 表类型
     */
    private String tableType;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getImpExportType() {
        return impExportType;
    }

    public void setImpExportType(Integer impExportType) {
        this.impExportType = impExportType;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }



}
