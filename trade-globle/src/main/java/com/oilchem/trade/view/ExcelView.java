package com.oilchem.trade.view;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.oilchem.trade.bean.DocBean.Config.exl_headrow_index;
import static java.net.URLEncoder.encode;

/**
 * Created with IntelliJ IDEA.
 * User: luowei
 * Date: 12-12-21
 * Time: 下午5:30
 * To change this template use File | Settings | File Templates.
 */
public class ExcelView<T> extends AbstractExcelView {

    Logger logger = LoggerFactory.getLogger(ExcelView.class);

    private String excelName;
    private String sheetName;
    /**
     * model中的数据list
     */
    private List<T> list;
    private List<ExlBean> exlBeanList;

    private Class<?> clazz;

    public ExcelView() {
    }

    public ExcelView(String excelName, String sheetName,
                     List<T> list, List<ExlBean> exlBeanList) {
        this(excelName,sheetName,list,exlBeanList,null);
    }

    public ExcelView(String excelName, String sheetName, List<T> list,
                     List<ExlBean> exlBeanList, Class<?> clazz) {
        this.excelName = excelName;
        this.sheetName = sheetName;
        this.list = list;
        this.exlBeanList = exlBeanList;
        this.clazz = clazz;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        if (excelName == null || excelName.equals("")
                || sheetName == null || sheetName.equals("")
                || list == null || exlBeanList == null) return;

        // 设置response方式,使执行此controller时候自动出现下载页面,而非直接使用excel打开
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=" + encode(excelName, "UTF-8"));

        HSSFSheet sheet = workbook.createSheet(sheetName);

        // 产生Excel表头
        int headIdx = Integer.parseInt(exl_headrow_index.value());
        HSSFRow header = sheet.createRow(headIdx);
        for (int idx = 0; idx < exlBeanList.size(); idx++) {
            header.createCell(idx).setCellValue(exlBeanList.get(idx).column);
        }

        //填充数据
        int dataRowIdx = headIdx + 1;
        for (int idx = 0; idx < list.size(); idx++, dataRowIdx++) {
            T element = list.get(idx);
            HSSFRow row = sheet.createRow(dataRowIdx);
            for (int clmIdx = 0; clmIdx < exlBeanList.size(); clmIdx++) {
                String value = getFieldValue(element, clazz, exlBeanList.get(clmIdx).field);
                row.createCell(clmIdx).setCellValue(value);
            }

        }

    }

    private String getFieldValue(T element, Class<?> clazz, String field) {

        if (clazz == null) {
            clazz = element.getClass();
        }
        Field[] fields = clazz.getDeclaredFields();

        for (Field field1 : fields) {
            if (field1.getName().equals(field)) {
                String methodName = "get" + field.substring(0, 1).toUpperCase() + field.substring(1);
                try {
                    Method getMothed = clazz.getMethod(methodName);
                    return String.valueOf(getMothed.invoke(element));
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            }
        }

        return null;
    }


    public List<ExlBean> getExlBeanList() {
        return exlBeanList;
    }

    public ExcelView setExlBeanList(List<ExlBean> exlBeanList) {
        this.exlBeanList = exlBeanList;
        return this;
    }

    public List<T> getList() {
        return list;
    }

    public ExcelView setList(List<T> list) {
        this.list = list;
        return this;
    }

    public String getSheetName() {
        return sheetName;
    }

    public ExcelView setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public String getExcelName() {
        return excelName;
    }

    public ExcelView setExcelName(String excelName) {
        this.excelName = excelName;
        return this;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public ExcelView setClazz(Class<?> clazz) {
        this.clazz = clazz;
        return this;
    }

    public static class ExlBean {

        String column;
        String field;

        public ExlBean() {
        }

        public ExlBean(String column, String field) {
            this.column = column;
            this.field = field;
        }
    }

}
