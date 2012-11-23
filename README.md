com.oilchem.trade-impexp
=============================================

进出口数据的导入导出查询数据...


0	col001  	数字	月份
1	col002		文本	产品代码
2	PName		备注	产品名称
3	E2		备注	企业性质
4	TradeName	备注	贸易方式
5	cityName	备注	城市
6	CountryName	备注	国家
7	PGCountryName	备注	国家
8	CustomsName	备注	出口海关
9	TransName	备注	运输方式
10	UnitName	备注	单位
11	Col012		数字	数量
12	Col013		数字	金额

==============================================

1.注意:下次做项目要从顶层开始，先写view层，保证项目能跑通，再补齐service层,最后实现底层。

问题:
1.jpa配置问题，致项目起不来;
2.解压日志记录，导入日志记录
3.导入数据，更新年月
4.detail,sum表的list条件(criteria)查询

5.添加权限验证

--------------------------------------------------
1. zip/rar/一般文件兼容 ；
2.多线程多链接并发导入；
3.各层分割；
4.加入权限验证(单独做个模块)；
5.spring jpa 自定义更新、删除方法在代理类上执行时的事务问题;


----------------------------------------------------
自定义某个方法，了类必须override

---------------------------------------------------
经验之点:
1.先写测试再写实现，速度更快，成效更好；
2.参数尽可能采用对象传递，避免用一般类型传递；
3.

---------------------------------
springside:
https://github.com/springside/springside4

==========================================================================

 public static class ConfigName {

        //json配置文件的名字
        public static String CONFIG_COMMON_JSON = "config_common.json";
        public static String CONFIG_FLAG_JSON = "config_flag.json";
        public static String CONFIG_MAPPER_JSON = "config_mapper.json";


        //---------------- common config---------------------

        public static String UPLOAD_DETAILZIP_DIR = "upload_detailzip_dir";
        public static String UPLOAD_SUMZIP_DIR = "upload_sumzip_dir";
        public static String UNZIP_DETAIL_DIR = "unzip_detail_dir";
        public static String UNZIP_SUM_DIR = "unzip_sum_dir";

        public static String ROOT_URL = "root_url";

        //年月分融符
        public static String YEARMONTH_SPLIT = "yearmonth_split";

        //线程大小
        public static String THREAD_POOLSIZE = "thread_poolsize";

        //过滤条件是否导入开关
        public static String NEED_IMPORT_CRITERIA = "need_import_criteria";

        //批处理块大小
        public static String BATCH_UPDATESIZE = "batch_updatesize";

        //读取access源数据表的sql语句
        public static String ACCESS_SELECT_SQL = "access_select_sql";


        //----------------- flag config ----------------------


        //表类型
        public static String DETAIL = "detail";
        public static String SUM = "sum";

        //导入/导出类型
        public static String IMPORT_TYPE = "import_type";
        public static String EXPORT_TYPE = "export_type";

        //上传状态标志
        public static String UPLOADING_FLAG = "uploading_flag";
        public static String UPLOADED_FLAG = "uploaded_flag";
        public static String UPLOAD_FAILD = "upload_faild";

        //解压状态标志
        public static String UNEXTRACT_FLAG = "unextract_flag";
        public static String EXTRACTING_FLAG = "extracting_flag";
        public static String EXTRACTED_FLAG = "extracted_flag";
        public static String EXTRACT_FAILD = "extract_faild";

        //导入状态标志
        public static String UNIMPORT_FLAG = "unimport_flag";
        public static String IMPORTING_FLAG = "importing_flag";
        public static String IMPORTED_FLAG = "imported_flag";
        public static String IMPORT_FAILD = "import_faild";


        //---------------------mapper config -------------------------

        /**
         * Access明细表字段
         */
        public static String ACCESS_PRODUCT_CODE = "access_product_code";
        public static String ACCESS_PRODUCT_NAME = "access_product_name";
        public static String ACCESS_COMPANY_TYPE = "access_company_type";
        public static String ACCESS_TRADE_TYPE = "access_trade_type";
        public static String ACCESS_TRANSPORTATION = "access_transportation";
        public static String ACCESS_CUSTOMS = "access_customs";
        public static String ACCESS_CITY = "access_city";
        public static String ACCESS_COUNTRY = "access_country";
        public static String ACCESS_AMOUNT = "access_amount";
        public static String ACCESS_UNIT = "access_unit";
        public static String ACCESS_ACOUNTMONEY = "access_acountmoney";


        /**
         * excel总表字段
         */
        public static String EXCEL_PRODUCT_NAME = "product_name";
        public static String NUM_MONTH = "num_month";
        public static String NUM_SUM = "num_sum";
        public static String MONEY_MONTH = "money_month";
        public static String MONEY_SUM = "money_sum";
        public static String AVG_PRICE_MONTH = "avg_price_month";
        public static String AVG_PRICE_SUM = "avg_price_sum";
        public static String NUM_PREMONTH_INCRAtIO = "num_premonth_incratio";
        public static String NUM_PREYEARSAMEMONTH_INCRAtIO = "num_preyearsamemonth_incratio";
        public static String NUM_PREYEARSAMEQUARTER_INCRATIO = "num_preyearsamequarter_imcratio";

    }


    -------------------------------------------------------------------------------
    maven 在使用 -pl 选项指定的值过滤模块的时候，通过两种方式，一是把 -pl 选项的值当做 groupId:artifactId 来查找，其次把 -pl 选项的值作为相对路径来查找，相对于用户运行 maven 时的工作目录。

    例如有以下项目结构：

    all [org.apache.maven:test]
    |-- m-1 [org.apache.maven:m1]
    |-- m-2 [org.apache.maven:m2]

    如果想通过 -pl 选项来指定顶级模块 all 和 m-1 模块，可以使用一下这么命令：

    mvn -pl org.apache.maven:test,m-1 clean install
