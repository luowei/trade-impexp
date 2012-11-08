package com.oilchem.trade.service.impl;

import com.oilchem.trade.config.Config;
import com.oilchem.trade.service.CommonService;
import com.oilchem.trade.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-8
 * Time: 下午3:55
 * To change this template use File | Settings | File Templates.
 */
@Service("taskService")
public class TaskServiceImpl implements TaskService {

    @Autowired
    CommonService commonService;

    //定时器
    Timer timer = new Timer();

    long delay = 0;

    public void unPackageAndImportTask(TimerTask timerTask) {
        timer.schedule(timerTask,delay);

    }

    /**
     * 解Acces包与导入任务
     * @param timerTask 定时任务
     */
    public void unAccessPackageAndImportTask(TimerTask timerTask){
        unPackageAndImportTask(new TimerTask (){

            @Override
            public void run() {
                //解包
                Map<Long,String> unExtractMap = commonService.getUnExtractPackage(Config.ACCESS_PACKAGE);
                for(Map.Entry<Long,String> entry:unExtractMap.entrySet()){
                    commonService.unpackageFile(entry.getValue(), Config.UNZIP_ACCESS_DIR);

                }
                //执行解包并导入数据

            }
        });
    }

    /**
     * 解Excel包与导入任务
     * @param timerTask   定时任务
     */
    public void unExcelPackageAndImportTask(TimerTask timerTask) {

    }


}
