USE [lzdb]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

--企业性质表
CREATE TABLE t_company_type(
	id bigint PRIMARY KEY IDENTITY(1,1) NOT NULL,
	company_type nvarchar(50) UNIQUE --企业性质
)

--贸易方式
CREATE TABLE t_trade_type(
	id bigint PRIMARY KEY IDENTITY(1,1) NOT NULL,
	trade_type nvarchar(50) UNIQUE --贸易方式
)

--运输方式
CREATE TABLE t_transportation(
	id bigint PRIMARY KEY IDENTITY(1,1) NOT NULL,
	transportation nvarchar(20) UNIQUE, --运输方式
)

--海关
CREATE TABLE t_customs(
	id bigint PRIMARY KEY IDENTITY(1,1) NOT NULL,
	customs nvarchar(20) UNIQUE, --海关
)

--城市
CREATE TABLE t_city(
	id bigint PRIMARY KEY IDENTITY(1,1) NOT NULL,
	city nvarchar(20) UNIQUE, --城市
)

--产销国家
CREATE TABLE t_country(
	id bigint PRIMARY KEY IDENTITY(1,1) NOT NULL,
	country nvarchar(20) UNIQUE, --产销国家
)

--明细表产品类型
CREATE TABLE t_detail_type(
    id bigint PRIMARY KEY IDENTITY(1,1) NOT NULL,
    code INT UNIQUE,
	detail_type nvarchar(200) UNIQUE,
)
GO

--明细产品表
CREATE TABLE t_product(
    id bigint PRIMARY KEY IDENTITY(1,1) NOT NULL,
    product_code nvarchar(20), --产品代码
	  product_name nvarchar(200), --产品名称
	  type_code int,              -- 产品类型代码
	  product_type nvarchar(200), --产品类型
)
GO



--进口明细表
CREATE TABLE t_import_detail(
	id bigint IDENTITY(1,1) NOT NULL, --主键的关键字primary key
	col_year INT , --年
	col_month INT , --月
	product_code nvarchar(20), --产品代码
	product_name nvarchar(200), --产品名称
	company_type nvarchar(20), --企业性质--
	trade_type nvarchar(20), --贸易方式--
	transportation nvarchar(20), --运输方式--
	customs nvarchar(20), --海关--
	city nvarchar(20), --城市--
	country nvarchar(20), --产销国家--
	amount NUMERIC(20,2),	--数量
	unit nvarchar(10), --计量单位
	amount_money NUMERIC(20,2), --金额
	unit_price NUMERIC(20,2), --单价
	year_month nvarchar(20), --年月
    product_type nvarchar(200), --产品类型
    type_code INT,
	CONSTRAINT [PK__t_import_detail__id] PRIMARY KEY nonclustered (id)
)
GO

--进口明细表统计表
CREATE TABLE t_import_detail_count(
    id bigint IDENTITY(1,1) NOT NULL, --主键的关键字primary key
    col_year INT , --年
	col_month INT , --月
    year_month nvarchar(20), --年月
    product_code nvarchar(20), --产品代码
	product_name nvarchar(200), --产品名称
	num NUMERIC(20,2),	--数量
	unit nvarchar(10), --计量单位
	money NUMERIC(20,2), --金额
	avg_price NUMERIC(20,2), --平均价
	CONSTRAINT [PK__t_import_detail_count__id] PRIMARY KEY nonclustered (id)
)
GO

--进口明细贸易方式表统计表
CREATE TABLE t_import_detail_tradetype(
    id bigint IDENTITY(1,1) NOT NULL, --主键的关键字primary key
    col_year INT , --年
	col_month INT , --月
    year_month nvarchar(20), --年月
    product_code nvarchar(20), --产品代码
	product_name nvarchar(200), --产品名称
	trade_type nvarchar(20), --贸易方式--
	num NUMERIC(20,2),	--数量
	unit nvarchar(10), --计量单位
	money NUMERIC(20,2), --金额
	avg_price NUMERIC(20,2), --平均价
	CONSTRAINT [PK__t_import_detail_tradetype__id] PRIMARY KEY nonclustered (id)
)
GO

--进口明细企业性质统计表
CREATE TABLE t_import_detail_complanyType(
    id bigint IDENTITY(1,1) NOT NULL, --主键的关键字primary key
    col_year INT , --年
	col_month INT , --月
    year_month nvarchar(20), --年月
    product_code nvarchar(20), --产品代码
	product_name nvarchar(200), --产品名称
	company_type nvarchar(20), --企业性质--
	num NUMERIC(20,2),	--数量
	unit nvarchar(10), --计量单位
	money NUMERIC(20,2), --金额
	avg_price NUMERIC(20,2), --平均价
	CONSTRAINT [PK__t_import_detail_complanyTypee__id] PRIMARY KEY nonclustered (id)
)
GO


--出口明细表
CREATE TABLE t_export_detail(
	id bigint IDENTITY(1,1) NOT NULL, --主键的关键字primary key
	col_year INT , --年
	col_month INT , --月
	product_code nvarchar(20), --产品代码
	product_name nvarchar(200), --产品名称
	company_type nvarchar(20), --企业性质--
	trade_type nvarchar(20), --贸易方式--
	transportation nvarchar(20), --运输方式--
	customs nvarchar(20), --海关--
	city nvarchar(20), --城市--
	country nvarchar(20), --产销国家--
	amount NUMERIC(20,2),	--数量
	unit nvarchar(10), --计量单位
	amount_money NUMERIC(20,2), --金额
	unit_price NUMERIC(20,2), --单价
    year_month nvarchar(20), --年月
    product_type nvarchar(200), --产品类型
    type_code INT,
	CONSTRAINT [PK__t_export_detail__id] PRIMARY KEY nonclustered (id)
)
GO

--出口明细表统计表
CREATE TABLE t_export_detail_count(
    id bigint IDENTITY(1,1) NOT NULL, --主键的关键字primary key
    col_year INT , --年
	col_month INT , --月
    year_month nvarchar(20), --年月
    product_code nvarchar(20), --产品代码
	product_name nvarchar(200), --产品名称
	num NUMERIC(20,2),	--数量
	unit nvarchar(10), --计量单位
	money NUMERIC(20,2), --金额
	avg_price NUMERIC(20,2), --平均价
	CONSTRAINT [PK__t_export_detail_count__id] PRIMARY KEY nonclustered (id)
)
GO

--出口明细贸易方式表统计表
CREATE TABLE t_export_detail_tradetype(
    id bigint IDENTITY(1,1) NOT NULL, --主键的关键字primary key
    col_year INT , --年
	col_month INT , --月
    year_month nvarchar(20), --年月
    product_code nvarchar(20), --产品代码
	product_name nvarchar(200), --产品名称
	trade_type nvarchar(20), --贸易方式--
	num NUMERIC(20,2),	--数量
	unit nvarchar(10), --计量单位
	money NUMERIC(20,2), --金额
	avg_price NUMERIC(20,2), --平均价
	CONSTRAINT [PK__t_export_detail_tradetype__id] PRIMARY KEY nonclustered (id)
)
GO

--出口明细企业性质统计表
CREATE TABLE t_export_detail_complanyType(
    id bigint IDENTITY(1,1) NOT NULL, --主键的关键字primary key
    col_year INT , --年
	col_month INT , --月
    year_month nvarchar(20), --年月
    product_code nvarchar(20), --产品代码
	product_name nvarchar(200), --产品名称
	company_type nvarchar(20), --企业性质--
	num NUMERIC(20,2),	--数量
	unit nvarchar(10), --计量单位
	money NUMERIC(20,2), --金额
	avg_price NUMERIC(20,2), --平均价
	CONSTRAINT [PK__t_export_detail_complanyType__id] PRIMARY KEY nonclustered (id)
)
GO


--产品类型表
CREATE TABLE t_sum_type(
	id bigint PRIMARY KEY IDENTITY(1,1) NOT NULL,
	product_type nvarchar(20)  UNIQUE,
)
GO

--进口总表
CREATE TABLE t_import_sum(
	id bigint IDENTITY(1,1) NOT NULL, --主键的关键字primary key--
	col_year INT,      --年
	col_month INT,      --月
	year_month nvarchar(20), --年月
	product_type nvarchar(20), --产品类型
	product_name nvarchar(200), --产品名称
	num_month NUMERIC(20,3), --当月数量
	num_sum NUMERIC(20,3),	--累计总数量
	money_month NUMERIC(20,2), --当月金额
	money_sum NUMERIC(20,2), --累计金额
	avgprice_month NUMERIC(20,2), --当月平均价格
	avgprice_sum NUMERIC(20,2), --累积平均价格
	num_premonth_incratio NUMERIC(20,2), --与上月数量增长比
	num_preyearsamemonth_incratio NUMERIC(20,2), --与上年同月数量增长比
	num_preyearsamequarter_incratio NUMERIC(20,2), --与上年同期数量增长比
	CONSTRAINT [PK__t_import_sum__id] PRIMARY KEY nonclustered (id)
)

--出口总表---
CREATE TABLE t_export_sum(
	id bigint IDENTITY(1,1) NOT NULL, --主键的关键字primary key--
	col_year INT,      --年
	col_month INT,      --月
	year_month nvarchar(20), --年月
	product_type nvarchar(20), --产品类型
	product_name nvarchar(200), --产品名称
	num_month NUMERIC(20,3), --当月数量
	num_sum NUMERIC(20,3),	--累计总数量
	money_month NUMERIC(20,2), --当月金额
	money_sum NUMERIC(20,2), --累计金额
	avgprice_month NUMERIC(20,2), --当月平均价格
	avgprice_sum NUMERIC(20,2), --累积平均价格
	num_premonth_incratio NUMERIC(20,2), --与上月数量增长比
	num_preyearsamemonth_incratio NUMERIC(20,2), --与上年同月数量增长比
	num_preyearsamequarter_incratio NUMERIC(20,2), --与上年同期数量增长比
	CONSTRAINT [PK__t_export_sum__id] PRIMARY KEY nonclustered (id)
)

--导数据日志表
CREATE TABLE t_log(
	id bigint PRIMARY KEY IDENTITY(1,1) NOT NULL, --主键的关键字primary key--
	log_type nvarchar(10) CHECK (log_type IN('导入','导出')),		--日志类型
	log_time datetime ,--日志时间
	trade_type nvarchar(10) CHECK(trade_type IN('进口','出口')), --进出类型
	table_type nvarchar(10) CHECK(table_type IN('明细表','总表')),
	upload_path nvarchar(200), --上传包的路径
	extract_path nvarchar(200),--导入文件的路径
	download_url nvarchar(200), --导出文件下载地址
	upload_flag nvarchar(10) CHECK(upload_flag IN('上传成功','正在上传','上传失败')), --是否已完成
	extract_flag nvarchar(10) CHECK(extract_flag IN('解压成功','正在解压','未解压','解压失败')),--是否解压完成
	import_flag nvarchar(10) CHECK(import_flag IN('导入成功','正在导入','未导入','导入失败')),
	error_occur nvarchar(2000), --是否发生错误
	col_year INT ,             --年
	col_month INT,             --月
    product_type nvarchar(200), --产品类型
	--constraint chk_Person check (log_time in('导入','导出'))
	--constraint [PK__t_export_sum__id] primary key nonclustered (id)
)


--=============================================================
SET ANSI_NULLS OFF
GO

SET QUOTED_IDENTIFIER OFF
GO

SET ANSI_PADDING OFF
GO

--===============================================================
--删除表数据
-- delete from t_city;
-- delete from t_company_type;
-- delete from t_country;
-- delete from t_customs;
-- delete from t_trade_type;
-- delete from t_transportation;
--
-- delete from t_detail_type;
-- delete from t_sum_type;
--
-- delete from t_export_detail;
-- delete from t_import_detail;
-- delete from t_import_sum;
-- delete from t_export_sum;

-----------------------------------------

--删除表
-- drop table t_export_detail;
-- drop table t_import_detail;
-- drop table t_import_sum;
-- drop table t_export_sum;
--
-- drop table t_city;
-- drop table t_company_type;
-- drop table t_country;
-- drop table t_customs;
-- drop table t_trade_type;
-- drop table t_transportation;
-- drop table t_product_type;


--=====================================================
-- -- 配置sql server的连接数
-- USE lzdb ;
-- GO
-- EXEC sp_configure 'show advanced options', 1;
-- GO
-- RECONFIGURE ;
-- GO
-- EXEC sp_configure 'user connections', 1000 ;
-- GO
-- RECONFIGURE;
-- GO


 --=============================================================================================



