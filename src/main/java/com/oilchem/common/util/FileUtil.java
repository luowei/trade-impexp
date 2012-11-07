package com.oilchem.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * User: luowei
 * Date: 12-5-28
 * Time: 下午12:51
 */
public class FileUtil {

    private static int YY;//年份的文件夹
    private static int  MM;//月份的文件夹

    /**
     * 将文件copy到新的文件夹下
     * @author wei.luo
     * @createTime 2012-3-24
     * @param srcFile
     *          源文件
     * @param desFile
     *          被拷贝的文件
     * @return  ture  拷贝成功，false 拷贝失败
     */
    public  static boolean copyFile(String srcFile,String desFile){
        if(null==srcFile||"".equals(srcFile)){
            return false;
        }
        if(null==desFile||"".equals(desFile)){
            return false;
        }
        try {
            FileCopyUtils.copy(new File(srcFile),new File(desFile));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 拷贝文件
     * @author wei.luo
     * @createTime 2012-4-6
     * @param inDir	源文件夹
     * @param outDir 目标文件夹
     * @return 是否成功
     * @throws java.io.IOException	抛出IO异常
     */
    public static boolean copy(String inDir, String outDir) throws IOException {
        File fInDir = new File(inDir);
        if (fInDir.isDirectory()) {
            File fOutDir = new File(outDir);
            if (!fOutDir.exists()) {
                fOutDir.mkdirs();
            }
            for (File file : fInDir.listFiles()) {
                copy(file.getPath(), outDir+File.separator+ file.getName());
            }
        }else{
            FileCopyUtils.copy(fInDir, new File(outDir));
        }

        return true;
    }

    /**
     * 删除文件或文件夹
     * @author wei.luo
     * @createTime 2012-4-7
     * @param path	文件或文件夹路径
     * @return	是否成功
     * @throws Exception 抛出Exception异常
     */
    public static boolean delete(String path) throws Exception{
        File file=new File(path);
        if(file.isDirectory()){
            for(File file2:file.listFiles()){
                delete(file2.getPath());
            }
        }else{
            file.delete();
        }
        file.delete();
        return true;
    }

    /**
     * 根据指定的url保存文件到path
     * @param url
     * @param path
     * @param filename
     * @return	保存成功返回假，保存失败返回真
     */
    public static boolean saveFile(String url,String path,String filename){
        URLConnection urlConnect=null;
        try{
            urlConnect=new URL(url).openConnection();
            urlConnect.connect();
            InputStream is=urlConnect.getInputStream();
            File pathDir=new File(path);
            if(!pathDir.exists()){
                pathDir.mkdirs();
            }
            FileOutputStream fos=new FileOutputStream(new File(path,filename));
            int idx=is.read();
            while(idx!=-1){
                fos.write(idx);
                idx=is.read();
            }
            is.close();
            fos.close();
            return true;
        }catch(IOException e){
            return false;
        }finally{
            if(urlConnect!=null){
                urlConnect=null;
            }
        }
    }

    /**
     * 下载文件
     * @author wei.luo
     * @createTime 2012-4-17
     * @param fileUrl 文件
     * @param path 下载存放的绝对目录
     * @return 成功返回文件名
     */
    public static String downFile(String fileUrl,String path) {
        String fileName=fileUrl;
        if(!StringUtils.isBlank(fileUrl) && !StringUtils.isBlank(path)){
            String temp[]=fileUrl.split("\\.");
            fileName=FileUtil.getNewId()+"."+temp[temp.length-1];
            if(FileUtil.saveFile(fileUrl,path,fileName)){
                return fileName;
            }
        }
        return null;
    }

    /**
     *取唯一串
     * @author wei.luo
     * @createTime 2012-4-17
     * @return 唯一串
     */
    public static String getNewId(){
        Calendar calendar = Calendar.getInstance();
        StringBuffer strbuf = new StringBuffer();
        strbuf.append(calendar.get(Calendar.YEAR));
        strbuf.append(calendar.get(Calendar.MONTH));
        strbuf.append(calendar.get(Calendar.DAY_OF_MONTH));
        strbuf.append(calendar.get(Calendar.HOUR_OF_DAY));
        strbuf.append(calendar.get(Calendar.MINUTE));
        strbuf.append(calendar.get(Calendar.MILLISECOND));

        Random rand = new Random();
        for (int i=0;i<4;i++) {
            strbuf.append(rand.nextInt(10));
        }
        return strbuf.toString();
    }

    /**
     * 获年月日时分秒字符串
     * @return
     */
    public static String getYYYYMMDDHHMMSS(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        StringBuffer strbuf = new StringBuffer();
        strbuf.append(calendar.get(Calendar.YEAR));
        strbuf.append(calendar.get(Calendar.MONTH)+1<10?"0"+(calendar.get(Calendar.MONTH)+1):(calendar.get(Calendar.MONTH)+1));
        strbuf.append(calendar.get(Calendar.DAY_OF_MONTH)<10?"0"+calendar.get(Calendar.DAY_OF_MONTH):calendar.get(Calendar.DAY_OF_MONTH));
        strbuf.append(calendar.get(Calendar.HOUR_OF_DAY)<10?"0"+calendar.get(Calendar.HOUR_OF_DAY):calendar.get(Calendar.HOUR_OF_DAY));
        strbuf.append(calendar.get(Calendar.MINUTE)<10?"0"+calendar.get(Calendar.MINUTE):calendar.get(Calendar.MINUTE));
        strbuf.append(calendar.get(Calendar.SECOND)<10?"0"+calendar.get(Calendar.SECOND):calendar.get(Calendar.SECOND));
        return strbuf.toString();
    }


    /**
     * 上传文件
     * @author wei.luo
     * @createTime 2012-3-24
     * @param file	MultipartFile的文件
     * @param realDir	目标目录的物理路径
     * @param netDir	目标目录
     * @return		返回上传之后的目标路径
     */
    public static synchronized String upload(MultipartFile file,String realDir,String netDir) {
        if (file.isEmpty() || StringUtils.isBlank(realDir) || netDir==null) {
            return null;
        }
        // 获取路径，生成完整的文件路径
        String fileName = getFileName(realDir)
                + file.getOriginalFilename().substring(
                file.getOriginalFilename().lastIndexOf("."));
        File uploadFile = new File(fileName);
        try {
            // 上传
            FileCopyUtils.copy(file.getBytes(), uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 网页端显示的路径的分隔符为/。
        return netDir + "/" + YY + "/" + MM + "/" + uploadFile.getName();
    }


    /**
     * 将不存在的文件夹创建，并且将完整路径+文件名（无后缀）返回。
     * @author wandonghai
     * @createTime 2011-6-14
     * @param RealPath
     * @return
     */
    private static String getFileName(final String RealPath) {
        //获取系统分隔符
        String fs = File.separator;
        Calendar calendar = Calendar.getInstance();
        YY = calendar.get(Calendar.YEAR);
        MM = calendar.get(Calendar.MONTH) + 1;
        int DD = calendar.get(Calendar.DATE);
        int HH = calendar.get(Calendar.HOUR);
        int NN = calendar.get(Calendar.MINUTE);
        int SS = calendar.get(Calendar.SECOND);
//		int MILL =calendar.get(Calendar.MILLISECOND);
        File pathFile = new File(RealPath + fs + YY + fs + MM);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }
        return pathFile.getPath()+fs + YY + MM + DD + HH + NN + SS ;
    }
}
