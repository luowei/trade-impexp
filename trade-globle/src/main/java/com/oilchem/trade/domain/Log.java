package com.oilchem.trade.domain;

import com.oilchem.trade.domain.abstrac.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 数据导入导出日志记录表
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-6
 * Time: 下午12:30
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "t_log")
public class Log extends IdEntity implements Serializable {

    //日志类型
    @Column(name = "log_type")
    private String logType;
    //日志时间
    @Column(name = "log_time")
    private Date logTime;
    //进/出口类型
    @Column(name = "trade_type")
    private String tradeType;
    //表类型
    @Column(name = "table_type")
    private String tableType;
    //导入文件上传后的路径
    @Column(name = "upload_path")
    private String uploadPath;
    //解压文件路径
    @Column(name = "extract_path")
    private String extractPath;
    //导出文件的下载地址
    @Column(name = "download_url")
    private String downLoadUrl;
    //上传标志
    @Column(name = "upload_flag")
    private String uploadFlg;
    //解压标志
    @Column(name = "extract_flag")
    private String extractFlag;
    //导入标志
    @Column(name = "import_flag")
    private String importFlag;
    //是否发生了错误
    @Column(name = "error_occur")
    private String errorOccur;
    //年
    @Column(name = "col_year")
    private Integer year;
    //月
    @Column(name = "col_month" )
    private Integer month;
    //产品类型
    @Column(name = "product_type")
    private String productType;

    //完成进度
    @Transient
    private Float progress;

    public void setId(Long id) {
        super.setId(id);
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getExtractPath() {
        return extractPath;
    }

    public void setExtractPath(String extractPath) {
        this.extractPath = extractPath;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public String getUploadFlg() {
        return uploadFlg;
    }

    public void setUploadFlg(String uploadFlg) {
        this.uploadFlg = uploadFlg;
    }

    public String getExtractFlag() {
        return extractFlag;
    }

    public void setExtractFlag(String extractFlag) {
        this.extractFlag = extractFlag;
    }

    public String getImportFlag() {
        return importFlag;
    }

    public void setImportFlag(String importFlag) {
        this.importFlag = importFlag;
    }

    public String getErrorOccur() {
        return errorOccur;
    }

    public void setErrorOccur(String errorOccur) {
        this.errorOccur = errorOccur;
    }

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

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Float getProgress() {
        return progress;
    }

    public void setProgress(Float progress) {
        this.progress = progress;
    }
}
