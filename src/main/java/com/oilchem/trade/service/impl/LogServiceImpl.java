package com.oilchem.trade.service.impl;

import com.oilchem.trade.config.Config;
import com.oilchem.trade.config.ImpExpType;
import com.oilchem.trade.dao.LogDao;
import com.oilchem.trade.domain.Log;
import com.oilchem.trade.service.LogService;
import com.oilchem.trade.view.dto.YearMonthDto;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import static com.oilchem.trade.config.Config.*;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-11
 * Time: 下午6:45
 * To change this template use File | Settings | File Templates.
 */
@Aspect
@Service("logService")
public class LogServiceImpl implements LogService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    LogDao logDao;

    Log log;

    /**
     * 上传文件切入点
     *
     * @param file
     * @param realDir
     * @param yearMonthDto
     */
    @Pointcut(value = "execution(String com.oilchem.trade.service.impl.CommonServiceImpl.uploadFile(" +
            "org.springframework.web.multipart.MultipartFile," +
            "java.lang.String," +
            "com.oilchem.trade.view.dto.YearMonthDto)) " +
            "&& args(file,realDir,yearMonthDto)",
            argNames = "file,realDir,yearMonthDto")
    void cutUploadFile(MultipartFile file, String realDir, YearMonthDto yearMonthDto) {
    }

    /**
     * 上传前更新日志
     *
     * @param file
     * @param readDir
     */
    @Before("cutUploadFile(file,readDir,yearMonthDto)")
    void logUploadingFile(MultipartFile file, String readDir, YearMonthDto yearMonthDto) {
        log = new Log();
        log.setLogType(Config.IMPORT);
        log.setTableType(yearMonthDto.getTableType());
        if (yearMonthDto.getImpExpType().equals(ImpExpType.进口.getCode())) {
            log.setTradeType(ImpExpType.进口.getMessage());
        } else if (yearMonthDto.getImpExpType().equals(ImpExpType.出口.getCode())) {
            log.setTradeType(ImpExpType.出口.getMessage());
        }
        log.setLogTime(new Date());
        log.setYear(yearMonthDto.getYear());
        log.setMonth(yearMonthDto.getMonth());
        log.setUploadFlg(UPLOADING_FLAG);
        logDao.save(log);
    }

    /**
     * 上传后更新日志
     *
     * @param file
     * @param readDir
     * @param uploadUrl
     */
    @AfterReturning(pointcut = "cutUploadFile(file,readDir,yearMonthDto)", returning = "uploadUrl")
    void logUploadedFile(MultipartFile file, String readDir, YearMonthDto yearMonthDto, String uploadUrl) {
        log.setLogTime(new Date());
        log.setUploadPath(uploadUrl);
        log.setUploadPath(readDir + uploadUrl.substring(uploadUrl.lastIndexOf("/")));
        log.setUploadFlg(UPLOADED_FLAG);
        log.setExtractFlag(UNEXTRACT_FLAG);
        logDao.save(log);
        //更新日志 .. 上传完毕
    }

    /**
     * 发生错误更新日志
     *
     * @param file
     * @param readDir
     */
    @AfterThrowing("cutUploadFile(file,readDir,yearMonthDto)")
    void logUploadFileThrowing(MultipartFile file, String readDir, YearMonthDto yearMonthDto) {
        log.setLogTime(new Date());
        log.setUploadFlg(UPLOAD_FAILD);
        log.setErrorOccur(UPLOAD_FAILD);
        logDao.save(log);
    }

    /**
     * 解压文件切入点
     *
     * @param logEntry
     * @param unPackageDir
     */
    @Pointcut(value = "execution(" +
            "String com.oilchem.trade.service.impl.CommonServiceImpl.unpackageFile(" +
            "java.util.Map.Entry<Long, String>," +
            "java.lang.String))" +
            "&& args(logEntry,unPackageDir)",
            argNames = "logEntry,unPackageDir")
    void cutUnpackageFile(Map.Entry<Long, String> logEntry, String unPackageDir) {
    }

    /**
     * 解压前更新日志
     *
     * @param logEntry
     * @param unPackageDir
     */
    @Before("cutUnpackageFile(logEntry,unPackageDir)")
    void logUnpackagingFile(Map.Entry<Long, String> logEntry, String unPackageDir) {
        log = logDao.findOne(logEntry.getKey());
        log.setLogTime(new Date());
        log.setExtractFlag(EXTRACTING_FLAG);
        logDao.save(log);
    }

    /**
     * 解压后更新日志
     *
     * @param logEntry
     * @param unPackagePath
     */
    @AfterReturning(pointcut = "cutUnpackageFile(logEntry,unPackageDir)",
            returning = "unPackagePath")
    void logUnpackagedFile(Map.Entry<Long, String> logEntry, String unPackageDir, String unPackagePath) {
        log = logDao.findOne(logEntry.getKey());
        log.setLogTime(new Date());
        log.setExtractFlag(EXTRACTED_FLAG);
        log.setExtractPath(unPackagePath);
        log.setImportFlag(UNIMPORT_FLAG);
        logDao.save(log);
    }

    /**
     * 解压发生异常
     *
     * @param logEntry
     * @param unPackageDir
     */
    @AfterThrowing("cutUnpackageFile(logEntry,unPackageDir)")
    void logUnpackageFileThrowing(Map.Entry<Long, String> logEntry, String unPackageDir) {
        log = logDao.findOne(logEntry.getKey());
        log.setLogTime(new Date());
        log.setExtractFlag(EXTRACT_FAILD);
        log.setImportFlag(UNIMPORT_FLAG);
        logDao.save(log);
    }


    /**
     * 导入详细表日志记录切入点
     *
     * @param logEntry     logEntry
     * @param yearMonthDto yearMonthDto
     */
    @Pointcut(value = "execution(" +
            "Boolean com.oilchem.trade.service.impl.TradeDetailServiceImpl.importAccess(" +
            "java.util.Map.Entry<Long, String>," +
            "com.oilchem.trade.view.dto.YearMonthDto," +
            "java.sql.Connection))" +
            "&& args(logEntry,yearMonthDto,conn)",
            argNames = "logEntry,yearMonthDto,conn")
    void cutImportTradeDetail(Map.Entry<Long, String> logEntry,
                              YearMonthDto yearMonthDto,
                              Connection conn) {
    }

    @Before("cutImportTradeDetail(logEntry,yearMonthDto,conn)")
    void logImportingTradeDetail(Map.Entry<Long, String> logEntry,
                                 YearMonthDto yearMonthDto,
                                 Connection conn) {

    }

    /**
     * 更新日志与更新年月
     *
     * @param logEntry
     * @param yearMonthDto
     * @param isSuccess
     */
    @AfterReturning(pointcut = "cutImportTradeDetail(logEntry,yearMonthDto,conn)",
            returning = "isSuccess")
    void logImportedTradeDetail(Map.Entry<Long, String> logEntry,
                                YearMonthDto yearMonthDto,
                                Connection conn,
                                Boolean isSuccess) {

        //关闭access连接
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @AfterThrowing("cutImportTradeDetail(logEntry,yearMonthDto,conn)")
    void logImportTradeDetailThrowing(Map.Entry<Long, String> logEntry,
                                      YearMonthDto yearMonthDto,
                                      Connection conn) {
        //关闭access连接
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }


    /**
     * 列出日志
     *
     * @param pageable
     * @return
     */
    public Page<Log> findAll(Pageable pageable) {
        if (pageable != null) {
            return logDao.findAll(pageable);
        }
        return null;
    }


}
