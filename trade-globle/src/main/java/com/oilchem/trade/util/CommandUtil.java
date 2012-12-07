package com.oilchem.trade.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-12-7
 * Time: 上午9:48
 * To change this template use File | Settings | File Templates.
 */
public abstract class CommandUtil {

    static Logger logger = LoggerFactory.getLogger(CommandUtil.class);

    /**
     * 调用系统程序执行解压
     * @param targetPath
     * @param absolutePath
     * @param cmd
     */
    public static void unrar(String targetPath, String absolutePath,String cmd) {

        try {
//            String cmd = "C:\\Program Files\\WinRAR\\winrar.exe";
            String unrarCmd = cmd + " x -r -p- -o+ " + targetPath + " " + absolutePath;
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(unrarCmd);

            InputStreamReader isr = new InputStreamReader(process.getInputStream());
            BufferedReader bf = new BufferedReader(isr);
            String line = null;
            while ((line = bf.readLine()) != null) {
                line = line.trim();
                if ("".equals(line)) {
                    continue;
                }
            }
            bf.close();
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new RuntimeException(e);
        }

    }

}
