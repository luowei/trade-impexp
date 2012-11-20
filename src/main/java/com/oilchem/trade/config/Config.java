package com.oilchem.trade.config;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-11-7
 * Time: 下午7:13
 * To change this template use File | Settings | File Templates.
 */
public class Config {

    public static String UPLOAD_DETAILZIP_DIR = "d:/aaaa/upload/detailzip";
    public static String UPLOAD_SUMZIP_DIR = "d:/aaaa/upload/sumzip";
    public static String UNZIP_DETAIL_DIR = "d:/aaaa/unzip/detail";
    public static String UNZIP_SUM_DIR = "d:/aaaa/unzip/sum";

    public static String ROOT_URL = "http://trade.vvvv.com";

    public static String DETAIL = "明细表";
    public static String SUM = "总表";

    public static String UPLOADING_FLAG = "正在上传";
    public static String UPLOADED_FLAG = "上传成功";

    public static String UNEXTRACT_FLAG = "未解压";
    public static String EXTRACTING_FLAG = "正在解压";
    public static String EXTRACTED_FLAG = "解压成功";

    public static String UNIMPORT_FLAG = "未导入";
    public static String IMPORTING_FLAG = "正在导入";
    public static String IMPORTED_FLAG = "导入成功";

    public static String UPLOAD_FAILD = "上传失败";
    public static String EXTRACT_FAILD = "解压失败";
    public static String IMPORT_FAILD ="导入失败";

    public static String IMPORT = "导入";
    public static String EXPORT = "导出";


    public static String YEARMONTH_SPLIT = "-";

    public static int THREAD_POOLSIZE = 100;
}
