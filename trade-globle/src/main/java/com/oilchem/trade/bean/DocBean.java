package com.oilchem.trade.bean;

import com.oilchem.trade.util.ConfigUtil;

import static com.oilchem.trade.util.ConfigUtil.getDefault;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 12-11-30
 * Time: 下午1:09
 * To change this template use File | Settings | File Templates.
 */
public class DocBean {

    public static class ChartProps {
        String x_legend;
        String y_legend;
        Integer minRang = 0;
        Integer maxRang = 10000;
        Integer step = 1000;

        public ChartProps() {
        }

        public ChartProps(String x_legend, String y_legend) {
            this.x_legend = x_legend;
            this.y_legend = y_legend;
        }

        public ChartProps(String x_legend, String y_legend, Integer minRang,
                          Integer maxRang, Integer step) {
            this.x_legend = x_legend;
            this.y_legend = y_legend;
            this.minRang = minRang;
            this.maxRang = maxRang;
            this.step = step;
        }

        public String getX_legend() {
            return x_legend;
        }

        public void setX_legend(String x_legend) {
            this.x_legend = x_legend;
        }

        public String getY_legend() {
            return y_legend;
        }

        public void setY_legend(String y_legend) {
            this.y_legend = y_legend;
        }

        public Integer getMinRang() {
            return minRang;
        }

        public void setMinRang(Integer minRang) {
            this.minRang = minRang;
        }

        public Integer getMaxRang() {
            return maxRang;
        }

        public void setMaxRang(Integer maxRang) {
            this.maxRang = maxRang;
        }

        public Integer getStep() {
            return step;
        }

        public void setStep(Integer step) {
            this.step = step;
        }
    }


    /**
     * 表类型
     */
    public static enum TableType {

        detail("明细表"),
        sum("总表");

        private String value;

        TableType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

    }

    /**
     * 导入/导出类型
     */
    public static enum OptType {
        //导入/导出类型
        import_opt("导入"),
        export_opt("导出");

        private String value;

        OptType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }


    }

    public static enum ImpExpType {


        import_type("进口"),
        export_type("出口");

        private String value;

        ImpExpType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    /**
     * 图表类型
     */
    public static enum ChartType {
        lineChart("lineChart"),
        barChart("barChart"),
        pieChart("pieChart"),
//        areaHollowChart("区空心图表"),
//        filledBarChart("填充柱状图"),
//        horiziontalBarChart("横向柱状图"),
//        scatterChart("散点图"),
//        sketchBarChart("速写柱状图"),
//        stackedBarChart("堆叠柱状图")
        ;

        private String value;

        ChartType(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }

    }

    public static enum Flag {

        //上传状态标志
        uploading_flag("正在上传"),
        uploaded_flag("上传成功"),
        upload_faild("上传失败"),

        //解压状态标志
        unextract_flag("未解压"),
        extracting_flag("正在解压"),
        extracted_flag("解压成功"),
        extract_faild("解压失败"),

        //导入状态标志
        unimport_flag("未导入"),
        importing_flag("正在导入"),
        imported_flag("导入成功"),
        import_faild("导入失败"),;

        private String value;

        Flag(String value) {
            this.value = value;
        }

        public String getValue() {
            return this.value;
        }
    }

    public static enum AccessField {

        //Access明细表字段
        access_product_code,
        access_product_name,
        access_company_type,
        access_trade_type,
        access_transportation,
        access_customs,
        access_city,
        access_country,
        access_amount,
        access_unit,
        access_acountmoney,;

        public String getValue() {
            return getDefault(this);
        }

    }

    public static enum ExcelFiled {

        //excel总表字段
        excel_product_name,
        excel_num_month,
        excel_num_sum,
        excel_money_month,
        excel_money_sum,
        excel_avg_price_month,
        excel_avg_price_sum,
        excel_pm,
        excel_py,
        excel_pq,;

        public String getValue() {
            return getDefault(this).trim();
        }
    }

    /**
     * 配置名
     */
    public static enum Config {

        upload_detailzip_dir,
        upload_sumzip_dir,
        unzip_detail_dir,
        unzip_sum_dir,
        root_url,
        default_pagesize,

        yearmonth_split,
        thread_poolsize,
        scale_size,
        need_import_criteria,
        batch_updatesize,
        select_access_sql,

        axis_steps,
        chart_width,
        chart_height,

        series_num_type,
        series_money_type,
        five_star,
        num_axis,
        money_axis,

        exl_headrow_index, ignore_import_fail;

        public String value() {
            return getDefault(this);
        }

    }




    public static enum Symbol {
        left_square_bracket("["),
        right_square_bracket("]"),
        left_round_bracket("("),
        right_round_bracket(")"),
        underline("_"),
        zero("0"),

        ;

        private String value;
        private Symbol(String value) {
            this.value = value;
        }
        public String value(){
            return this.value;
        }
    }


}
