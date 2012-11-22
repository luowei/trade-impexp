package com.oilchem.trade.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-23
 * Time: 上午12:08
 * To change this template use File | Settings | File Templates.
 */
public abstract class CommonUtil {


    static Logger logger = LoggerFactory.getLogger(CommonUtil.class);


    /**
     * read string from file
     *
     * @return 文件中的字符串
     */
    public static synchronized String readStringFromFile(String filePath) {
        InputStream in = null;
        InputStreamReader read = null;
        BufferedReader reader = null;
        try {
            File file = new File(filePath);
            StringBuffer strbuf = new StringBuffer();
            read = new InputStreamReader(new FileInputStream(file), "UTF-8");
            reader = new BufferedReader(read);
            String line;
            while ((line = reader.readLine()) != null) {
                strbuf.append(line);
            }
            return strbuf.toString();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            close(read);
            close(reader);
        }
    }

    /**
     * write string to file
     *
     * @return 成功或失败
     */
    public static synchronized Boolean writeString2File(String filePath, String string) {
        OutputStream out = null;
        try {
            System.out.println(filePath);
            File file = new File(filePath);
            out = new FileOutputStream(file);
            out.write(string.getBytes());
            out.flush();
            return true;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            close(out);
        }
    }

    /**
     * 把输入流导到输出流
     *
     * @param inputStream   输入流
     * @param outputStream  输出流
     * @param readBlockSize 每次读写的块大小(以byte为单位)
     * @return 成功或失败
     */
    public static Boolean inputStream2OutPutStream(
            InputStream inputStream, OutputStream outputStream, int readBlockSize) {
        Boolean flag = false;
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        BufferedOutputStream bos = new BufferedOutputStream(outputStream);
        byte[] bytes = new byte[readBlockSize];
        int count = 0;
        try {
            while ((count = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, count);
            }
            bos.flush();
            flag = true;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            close(bis);
            close(bos);
        }
        return flag;
    }

    /**
     * 获年月日时分秒字符串
     *
     * @param date date
     * @return
     * @author wei.luo
     * @createTime 2012-4-18
     */
    public static String getYYYYMMDDHHMMSS(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        StringBuffer strbuf = new StringBuffer();
        strbuf.append(calendar.get(Calendar.YEAR));
        strbuf.append(calendar.get(Calendar.MONTH) + 1 < 10 ? "0" + (calendar.get(Calendar.MONTH) + 1) : (calendar.get(Calendar.MONTH) + 1));
        strbuf.append(calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + calendar.get(Calendar.DAY_OF_MONTH) : calendar.get(Calendar.DAY_OF_MONTH));
        strbuf.append(calendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + calendar.get(Calendar.HOUR_OF_DAY) : calendar.get(Calendar.HOUR_OF_DAY));
        strbuf.append(calendar.get(Calendar.MINUTE) < 10 ? "0" + calendar.get(Calendar.MINUTE) : calendar.get(Calendar.MINUTE));
        strbuf.append(calendar.get(Calendar.SECOND) < 10 ? "0" + calendar.get(Calendar.SECOND) : calendar.get(Calendar.SECOND));
        return strbuf.toString();
    }


    /**
     * close resource
     *
     * @param closeable 是否可关闭
     */
    public static Boolean close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
            return true;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }


}
