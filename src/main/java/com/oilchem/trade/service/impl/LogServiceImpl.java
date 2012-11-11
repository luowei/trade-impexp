package com.oilchem.trade.service.impl;

import com.oilchem.trade.config.Config;
import com.oilchem.trade.config.ImpExpType;
import com.oilchem.trade.dao.LogDao;
import com.oilchem.trade.domain.Log;
import com.oilchem.trade.service.LogService;
import com.oilchem.trade.view.dto.YearMonthDto;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

import java.util.Date;

import static com.oilchem.trade.config.Config.*;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-11
 * Time: 下午6:45
 * To change this template use File | Settings | File Templates.
 */
@Aspect
@Component
public class LogServiceImpl implements LogService {

    @Resource
    LogDao logDao;

    Log log;

    /**
     * 上传文件切入点
     * @param file
     * @param realDir
     * @param yearMonthDto
     */
    @Pointcut("call(String com.oilchem.trade.serivce.impl.CommonServiceImpl.uploadFile()) " +
            "&& args(file,realDir,yearMonthDto)")
    void cutUploadFile(MultipartFile file, String realDir, YearMonthDto yearMonthDto){
        log = new Log();
        log.setLogType("导入");
        log.setTableType(yearMonthDto.getTableType());
        if(yearMonthDto.getImpExportType().equals(ImpExpType.进口.getCode())){
             log.setTradeType(ImpExpType.进口.getMessage());
        }else if(yearMonthDto.getImpExportType().equals(ImpExpType.出口.getCode())){
            log.setTradeType(ImpExpType.出口.getMessage());
        }
    }

    /**
     * 上传前更新日志
     * @param file
     * @param readDir
     * @param yearMonthDto
     */
    @Before("cutUploadFile(file,readDir)")
    void logUploadingFile(MultipartFile file,String readDir, YearMonthDto yearMonthDto){
        log.setUploadFlg(UPLOADING_FLAG);
        logDao.save(log);
    }

    /**
     * 上传后更新日志
     * @param file
     * @param readDir
     * @param uploadPath
     */
    @AfterReturning(pointcut="cutUploadFile(file,readDir)",returning = "uploadPath")
    void logUploadedFile(MultipartFile file,String readDir,String uploadPath){
        log.setUploadPath(uploadPath);
        log.setUploadFlg(UPLOADED_FLAG);
        logDao.save(log);
        //更新日志 .. 上传完毕
    }

    /**
     * 发生错误更新日志
     * @param file
     * @param readDir
     */
    @AfterThrowing("cutUploadFile(file,readDir)")
    void logUploadFileThrowing(MultipartFile file,String readDir){
        log.setUploadFlg(UPLOAD_FAILD);
        log.setErrorOccur(UPLOAD_FAILD);
        logDao.save(log);
    }

}
