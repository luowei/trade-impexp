package com.oilchem.trade.bean;

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
    private Integer impExpType;
    /**
     * 产品类型
     */
    private String productType;
    /**
     * 表类型
     */
    private String tableType;
    /**
     * 手动导入还是自动导入
     */
    private String importType;

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

    public Integer getImpExpType() {
        return impExpType;
    }

    public void setImpExpType(Integer impExpType) {
        this.impExpType = impExpType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getImportType() {
        return importType;
    }

    public void setImportType(String importType) {
        this.importType = importType;
    }
}
