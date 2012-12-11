USE [lzdb]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

--企业性质表
create table t_company_type(
	id bigint primary key identity(1,1) not null,
	company_type nvarchar(50) unique --企业性质
)

--贸易方式
create table t_trade_type(
	id bigint primary key identity(1,1) not null,
	trade_type nvarchar(50) unique --贸易方式
)

--运输方式
create table t_transportation(
	id bigint primary key identity(1,1) not null,
	transportation nvarchar(20) unique, --运输方式
)

--海关
create table t_customs(
	id bigint primary key identity(1,1) not null,
	customs nvarchar(20) unique, --海关
)

--城市
create table t_city(
	id bigint primary key identity(1,1) not null,
	city nvarchar(20) unique, --城市
)

--产销国家
create table t_country(
	id bigint primary key identity(1,1) not null,
	country nvarchar(20) unique, --产销国家
)

--明细表产品类型
create table t_detail_type(
    id bigint primary key identity(1,1) not null,
    code int unique,
	detail_type nvarchar(200) unique,
)
go

--进口明细表
create table t_import_detail(
	id bigint identity(1,1) not null, --主键的关键字primary key
	col_year int , --年
	col_month int , --月
	product_code nvarchar(20), --产品代码
	product_name nvarchar(200), --产品名称
	company_type nvarchar(20), --企业性质--
	trade_type nvarchar(20), --贸易方式--
	transportation nvarchar(20), --运输方式--
	customs nvarchar(20), --海关--
	city nvarchar(20), --城市--
	country nvarchar(20), --产销国家--
	amount numeric(20,2),	--数量
	unit nvarchar(10), --计量单位
	amount_money numeric(20,2), --金额
	unit_price numeric(20,2), --单价
	year_month nvarchar(20), --年月
    product_type nvarchar(200), --产品类型
    type_code int,
	constraint [PK__t_import_detail__id] primary key nonclustered (id)
)
go

--进口明细表统计表
create table t_import_detail_count(
    id bigint identity(1,1) not null, --主键的关键字primary key
    col_year int , --年
	col_month int , --月
    year_month nvarchar(20), --年月
    product_code nvarchar(20), --产品代码
	product_name nvarchar(200), --产品名称
	num numeric(20,2),	--数量
	unit nvarchar(10), --计量单位
	money numeric(20,2), --金额
	avg_price numeric(20,2), --平均价
	constraint [PK__t_import_detail_count__id] primary key nonclustered (id)
)
go

-- --进口明细表统计视图
-- create view v_import_detail_count
-- as
-- select
-- product_code as id ,
-- year_month ,
-- product_code ,
-- product_name ,
-- sum(amount) as num,
-- unit ,
-- sum(amount_money) as money,
-- case sum(amount)
-- 	when 0 then 0
-- 	else sum(amount_money)/sum(amount)
-- end as avg_price
-- from
-- t_import_detail
-- group by
-- year_month,product_code,product_name,unit
-- go

--出口明细表
create table t_export_detail(
	id bigint identity(1,1) not null, --主键的关键字primary key
	col_year int , --年
	col_month int , --月
	product_code nvarchar(20), --产品代码
	product_name nvarchar(200), --产品名称
	company_type nvarchar(20), --企业性质--
	trade_type nvarchar(20), --贸易方式--
	transportation nvarchar(20), --运输方式--
	customs nvarchar(20), --海关--
	city nvarchar(20), --城市--
	country nvarchar(20), --产销国家--
	amount numeric(20,2),	--数量
	unit nvarchar(10), --计量单位
	amount_money numeric(20,2), --金额
	unit_price numeric(20,2), --单价
    year_month nvarchar(20), --年月
    product_type nvarchar(200), --产品类型
    type_code int,
	constraint [PK__t_export_detail__id] primary key nonclustered (id)
)
go

--出口明细表统计表
create table t_export_detail_count(
    id bigint identity(1,1) not null, --主键的关键字primary key
    col_year int , --年
	col_month int , --月
    year_month nvarchar(20), --年月
    product_code nvarchar(20), --产品代码
	product_name nvarchar(200), --产品名称
	num numeric(20,2),	--数量
	unit nvarchar(10), --计量单位
	money numeric(20,2), --金额
	avg_price numeric(20,2), --平均价
	constraint [PK__t_export_detail_count__id] primary key nonclustered (id)
)
go

-- --出口明细表统计视图
-- create view v_export_detail_count
-- as
-- select
-- product_code as id ,
-- year_month ,
-- product_code ,
-- product_name ,
-- sum(amount) as num,
-- unit ,
-- sum(amount_money) as money,
-- case sum(amount)
-- 	when 0 then 0
-- 	else sum(amount_money)/sum(amount)
-- end as avg_price
-- from
-- t_export_detail
-- group by
-- year_month,product_code,product_name,unit
-- go

--产品类型表
create table t_sum_type(
	id bigint primary key identity(1,1) not null,
	product_type nvarchar(20)  unique,
)
go

--进口总表
create table t_import_sum(
	id bigint identity(1,1) not null, --主键的关键字primary key--
	col_year int,      --年
	col_month int,      --月
	product_type nvarchar(20), --产品类型
	product_name nvarchar(200), --产品名称
	num_month numeric(20,3), --当月数量
	num_sum numeric(20,3),	--累计总数量
	money_month numeric(20,2), --当月金额
	money_sum numeric(20,2), --累计金额
	avgprice_month numeric(20,2), --当月平均价格
	avgprice_sum numeric(20,2), --累积平均价格
	num_premonth_incratio numeric(20,2), --与上月数量增长比
	num_preyearsamemonth_incratio numeric(20,2), --与上年同月数量增长比
	num_preyearsamequarter_incratio numeric(20,2), --与上年同期数量增长比
	constraint [PK__t_import_sum__id] primary key nonclustered (id)
)

--出口总表---
create table t_export_sum(
	id bigint identity(1,1) not null, --主键的关键字primary key--
	col_year int,      --年
	col_month int,      --月
	product_type nvarchar(20), --产品类型
	product_name nvarchar(200), --产品名称
	num_month numeric(20,3), --当月数量
	num_sum numeric(20,3),	--累计总数量
	money_month numeric(20,2), --当月金额
	money_sum numeric(20,2), --累计金额
	avgprice_month numeric(20,2), --当月平均价格
	avgprice_sum numeric(20,2), --累积平均价格
	num_premonth_incratio numeric(20,2), --与上月数量增长比
	num_preyearsamemonth_incratio numeric(20,2), --与上年同月数量增长比
	num_preyearsamequarter_incratio numeric(20,2), --与上年同期数量增长比
	constraint [PK__t_export_sum__id] primary key nonclustered (id)
)

--导数据日志表
create table t_log(
	id bigint primary key identity(1,1) not null, --主键的关键字primary key--
	log_type nvarchar(10) check (log_type in('导入','导出')),		--日志类型
	log_time datetime ,--日志时间
	trade_type nvarchar(10) check(trade_type in('进口','出口')), --进出类型
	table_type nvarchar(10) check(table_type in('明细表','总表')),
	upload_path nvarchar(200), --上传包的路径
	extract_path nvarchar(200),--导入文件的路径
	download_url nvarchar(200), --导出文件下载地址
	upload_flag nvarchar(10) check(upload_flag in('上传成功','正在上传','上传失败')), --是否已完成
	extract_flag nvarchar(10) check(extract_flag in('解压成功','正在解压','未解压','解压失败')),--是否解压完成
	import_flag nvarchar(10) check(import_flag in('导入成功','正在导入','未导入','导入失败')),
	error_occur nvarchar(2000), --是否发生错误
	col_year int ,             --年
	col_month int,             --月
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
delete from t_city;
delete from t_company_type;
delete from t_country;
delete from t_customs;
delete from t_trade_type;
delete from t_transportation;

delete from t_detail_type;
delete from t_sum_type;

delete from t_export_detail;
delete from t_import_detail;
delete from t_import_sum;
delete from t_export_sum;

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



