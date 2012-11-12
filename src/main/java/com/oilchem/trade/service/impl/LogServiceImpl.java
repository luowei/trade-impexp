package com.oilchem.trade.service.impl;

import com.oilchem.trade.config.ImpExpType;
import com.oilchem.trade.dao.LogDao;
import com.oilchem.trade.domain.Log;
import com.oilchem.trade.service.LogService;
import com.oilchem.trade.view.dto.YearMonthDto;
import org.aspectj.lang.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

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

    @Resource
    LogDao logDao;

    Log log;

    /**
     * 上传文件切入点
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
    void cutUploadFile(MultipartFile file, String realDir,YearMonthDto yearMonthDto){
        log = new Log();
        log.setLogType("导入");
//        YearMonthDto yearMonthDto = null;
        log.setTableType(yearMonthDto.getTableType());
        if(yearMonthDto.getImpExpType().equals(ImpExpType.进口.getCode())){
             log.setTradeType(ImpExpType.进口.getMessage());
        }else if(yearMonthDto.getImpExpType().equals(ImpExpType.出口.getCode())){
            log.setTradeType(ImpExpType.出口.getMessage());
        }
    }

    /**
     * 上传前更新日志
     * @param file
     * @param readDir
     */
    @Before("cutUploadFile(file,readDir,yearMonthDto)")
    void logUploadingFile(MultipartFile file,String readDir,YearMonthDto yearMonthDto){
        log = new Log();
        log.setLogType(yearMonthDto.getTableType());
        log.setTableType(yearMonthDto.getTableType());
        if(yearMonthDto.getImpExpType().equals(ImpExpType.进口.getCode())){
            log.setTradeType(ImpExpType.进口.getMessage());
        }else if(yearMonthDto.getImpExpType().equals(ImpExpType.出口.getCode())){
            log.setTradeType(ImpExpType.出口.getMessage());
        }
        log.setYear(yearMonthDto.getYear());
        log.setMonth(yearMonthDto.getMonth());
        log.setUploadFlg(UPLOADING_FLAG);
        logDao.save(log);
    }

    /**
     * 上传后更新日志
     * @param file
     * @param readDir
     * @param uploadUrl
     */
    @AfterReturning(pointcut="cutUploadFile(file,readDir,yearMonthDto)",returning = "uploadUrl")
    void logUploadedFile(MultipartFile file,String readDir,YearMonthDto yearMonthDto,String uploadUrl){
        log.setUploadPath(uploadUrl);
        log.setUploadPath(readDir+uploadUrl.substring(uploadUrl.lastIndexOf("/")));
        log.setUploadFlg(UPLOADED_FLAG);
        log.setExtractFlag(UNEXTRACT_FLAG);
        logDao.save(log);
        //更新日志 .. 上传完毕
    }

    /**
     * 发生错误更新日志
     * @param file
     * @param readDir
     */
    @AfterThrowing("cutUploadFile(file,readDir,yearMonthDto)")
    void logUploadFileThrowing(MultipartFile file,String readDir,YearMonthDto yearMonthDto){
        log.setUploadFlg(UPLOAD_FAILD);
        log.setErrorOccur(UPLOAD_FAILD);
        logDao.save(log);
    }

    /**
     * 解压文件切入点
     * @param packageSource
     * @param unPackageDir
     */
    @Pointcut(value = "execution(String com.oilchem.trade.service.impl.CommonServiceImpl.unpackageFile(" +
            "java.lang.String," +
            "java.lang.String))"+
            "&& args(packageSource,unPackageDir)",
            argNames = "packageSource,unPackageDir")
    void cutUnpackageFile(String packageSource, String unPackageDir){

    }

    /**
     * 解压前更新日志
     * @param packageSource
     * @param upPackageDir
     */
    @Before("cutUnpackageFile(packageSource,upPackageDir)")
    void logUnpackagingFile(String packageSource,String upPackageDir){

    }

    /**
     * 解压后更新日志
     * @param packageSource
     * @param upPackageDir
     * @param unPackagePath
     */
    @AfterReturning(pointcut = "cutUnpackageFile(packageSource,upPackageDir)",
            returning = "unPackagePath")
    void logUppackagedFile(String packageSource,String upPackageDir,String unPackagePath){

    }


    /**
     * 导入详细表日志记录切入点
     * @param crudRepository
     */
    @Pointcut(value = "execution(Boolean com.oilchem.trade.service.impl.CommonServiceImpl.importTradeDetail(" +
            "org.springframework.data.repository.CrudRepository,..))"+
            "&& args(crudRepository,..)",
            argNames = "crudRepository")
    void cutImportTradeDetail(CrudRepository crudRepository){

    }

    /**
     * 更新日志与更新年月
     * @param crudRepository
     * @param isSuccess
     */
    @AfterReturning(pointcut = "cutImportTradeDetail(crudRepository)",returning = "isSuccess")
    void logImportTradeDetail(CrudRepository crudRepository,Boolean isSuccess){
        crudRepository = null;
        isSuccess = null;

    }


    /**
     * 列出日志
     * @param pageable
     * @return
     */
    public Page<Log> findAll(Pageable pageable) {
        if(pageable!=null){
            return logDao.findAll(pageable);
        }
        return null;
    }
}
