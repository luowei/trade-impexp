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
    private Integer lowYear;
    private Integer lowMonth;
    private Integer highYear;
    private Integer highMonth;

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

    public YearMonthDto() {
    }

    public YearMonthDto(Integer month,Integer impExpType,
                        Integer lowYear,Integer lowMonth,
                        Integer highYear,Integer highMonth) {
        this.month = month;
        this.impExpType = impExpType;
        this.lowYear = lowYear;
        this.lowMonth = lowMonth;
        this.highYear = highYear;
        this.highMonth = highMonth;
    }

    public YearMonthDto(Integer year, Integer month, Integer impExpType,
                        String productType, String tableType) {
        this.year = year;
        this.month = month;
        this.impExpType = impExpType;
        this.productType = productType;
        this.tableType = tableType;
    }

    public Integer getYear() {
        return year;
    }

    public YearMonthDto setYear(Integer year) {
        this.year = year;
        return this;
    }

    public Integer getMonth() {
        return month;
    }

    public YearMonthDto setMonth(Integer month) {
        this.month = month;
        return this;
    }

    public Integer getImpExpType() {
        return impExpType;
    }

    public YearMonthDto setImpExpType(Integer impExpType) {
        this.impExpType = impExpType;
        return this;
    }

    public String getProductType() {
        return productType;
    }

    public YearMonthDto setProductType(String productType) {
        this.productType = productType;
        return this;
    }

    public String getTableType() {
        return tableType;
    }

    public YearMonthDto setTableType(String tableType) {
        this.tableType = tableType;
        return this;
    }

    public String getImportType() {
        return importType;
    }

    public YearMonthDto setImportType(String importType) {
        this.importType = importType;
        return this;
    }

    public Integer getLowYear() {
        return lowYear;
    }

    public YearMonthDto setLowYear(Integer lowYear) {
        this.lowYear = lowYear;
        return this;
    }

    public Integer getLowMonth() {
        return lowMonth;
    }

    public YearMonthDto setLowMonth(Integer lowMonth) {
        this.lowMonth = lowMonth;
        return this;
    }

    public Integer getHighYear() {
        return highYear;
    }

    public YearMonthDto setHighYear(Integer highYear) {
        this.highYear = highYear;
        return this;
    }

    public Integer getHighMonth() {
        return highMonth;
    }

    public YearMonthDto setHighMonth(Integer highMonth) {
        this.highMonth = highMonth;
        return this;
    }

    public boolean validYearMonth(YearMonthDto yearMonthDto){
         return yearMonthDto.getYear()!=null
                 && yearMonthDto.getMonth()!=null
                 && yearMonthDto.getImpExpType()!=null;
    }

    /**
     * 起始年月，结束年月跨度不超过12
     * @param yearMonthDto
     * @return
     */
    public boolean validYearMonthRang(YearMonthDto yearMonthDto){

        Integer lowMonth = yearMonthDto.getLowMonth();
        Integer lowYear = yearMonthDto.getLowYear();
        Integer highMonth = yearMonthDto.getHighMonth();
        Integer highYear = yearMonthDto.getHighYear();
        Boolean inValidate = lowMonth==null || lowYear==null || highMonth==null || highYear==null;
        if(inValidate) return false;
        if(lowYear > highYear) return false;
        if(lowYear.equals(highYear) && lowMonth > highMonth) return false;
        if(highMonth-lowMonth+(highYear-lowYear)*12 > 12) return false;
        return true;
    }
}
