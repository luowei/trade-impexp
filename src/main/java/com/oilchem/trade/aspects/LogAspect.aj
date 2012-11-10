package com.oilchem.trade.aspects;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Before;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-10
 * Time: 上午11:27
 * To change this template use Fi le | Settings | File Templates.
 */
@Aspect @Component
public class LogAspect {

     @Pointcut("call(String com.oilchem.trade.serivce.uploadFile(MultipartFile file, String realDir))")
     void cutUploadFile(){

     }

//     @Before("excution(String com.oilchem.trade.serivce.uploadFile(..))")
    @Before("cutUploadFile()")
     void logUploadingFile(){

         //更新日志 ..正在上传
     }

//    @After("excution(String com.oilchem.trade.service.uploadFile(..))")
    @After("cutUploadFile()")
    void logUpoadedFile(){

         //更新日志 .. 上传完毕
    }


}
