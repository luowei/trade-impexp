--表设计

USE [lzdb]
GO

SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO
--================================================
--企业性质表
create table t_company_type(
	id bigint primary key identity(1,1) not null,
	company_type nvarchar(50) not null --企业性质
)

--贸易方式
create table t_trade_type(
	id bigint primary key identity(1,1) not null,
	trade_type nvarchar(50) not null --贸易方式
)

--运输方式
create table t_transportation(
	id bigint primary key identity(1,1) not null,
	transportation nvarchar(20) not null, --运输方式
)

--海关
create table t_customs(
	id bigint primary key identity(1,1) not null,
	customs nvarchar(20) not null, --海关
)

--城市
create table t_city(
	id bigint primary key identity(1,1) not null,
	city nvarchar(20) not null, --城市
)

--产销国家
create table t_country(
	id bigint primary key identity(1,1) not null,
	country nvarchar(20) not null, --产销国家
)


--进口明细表
create table t_import_detail(
	id bigint identity(1,1) not null, --主键的关键字primary key
	year_month nvarchar(20) not null, --进口年月--
	single_year int check(single_year>2000 and single_year<2500), --年
	single_month int check(single_month>0 and single_month<13), --月
	product_code nvarchar(20) not null, --产品代码
	product_name nvarchar(50) not null, --产品名称
	company_type nvarchar(20) not null, --企业性质--
	trade_type nvarchar(20) not null, --贸易方式--
	transportation nvarchar(20) not null, --运输方式--
	customs nvarchar(20) not null, --海关--
	city nvarchar(20) not null, --城市--
	country nvarchar(20) not null, --产销国家--
	amount numeric(20,2) not null,	--数量
	unit nvarchar(10) not null, --计量单位
	amount_money numeric(20,2) not null, --金额
	unit_price numeric(20,2), --单价
	company_type_id bigint foreign key references t_company_type(id),
	trade_type_id bigint foreign key references t_trade_type(id),
	transportation_id bigint foreign key references t_transportation(id),
	customs_id bigint foreign key references t_customs(id),
	city_id bigint foreign key references t_city(id),
	country_id bigint foreign key references t_country(id),
	constraint [PK__t_import_detail__id] primary key nonclustered (id)
)
go

/**
--创建索引
create clustered index clustered_index_year_month
	on t_import_detail(year_month)
go
create nonclustered index nonclustered_index_product_code
    on t_import_detail(product_code)
go
create nonclustered index nonclustered_index_product_name
    on t_import_detail(product_name)
go
create nonclustered index nonclustered_index__company_type_id
    on t_import_detail(company_type_id)
go
create nonclustered index nonclustered_index__company_type_id
    on t_import_detail(trade_type_id)
go
create nonclustered index nonclustered_index__transportation_id
    on t_import_detail(transportation_id)
go
create nonclustered index nonclustered_index__customs_id
    on t_import_detail(customs_id)
go
create nonclustered index nonclustered_index__city_id
    on t_import_detail(city_id)
go
create nonclustered index nonclustered_index__country_id
    on t_import_detail(country_id)
go

*/
--DROP INDEX t_export_detail.clustered_index_year_month


---------------------------------------------
--出口明细表
create table t_export_detail(
	id bigint identity(1,1) not null, --主键的关键字primary key
	year_month nvarchar(20) not null, --进口年月--
	single_year int check(single_year>2000 and single_year<2500), --年
	single_moth int check(single_moth>0 and single_moth<13), --月
	product_code nvarchar(20) not null, --产品代码
	product_name nvarchar(50) not null, --产品名称
	company_type nvarchar(20) not null, --企业性质--
	trade_type nvarchar(20) not null, --贸易方式--
	transportation nvarchar(20) not null, --运输方式--
	customs nvarchar(20) not null, --海关--
	city nvarchar(20) not null, --城市--
	country nvarchar(20) not null, --产销国家--
	amount numeric(20,2) not null,	--数量
	unit nvarchar(10) not null, --计量单位
	amount_money numeric(20,2) not null, --金额
	unit_price numeric(20,2), --单价
	company_type_id bigint foreign key references t_company_type(id),
	trade_type_id bigint foreign key references t_trade_type(id),
	transportation_id bigint foreign key references t_transportation(id),
	customs_id bigint foreign key references t_customs(id),
	city_id bigint foreign key references t_city(id),
	country_id bigint foreign key references t_country(id),
	constraint [PK__t_export_detail__id] primary key nonclustered (id)
)
go
/**
--创建索引
create clustered index clustered_index_year_month
	on t_export_detail(year_month)
go
create nonclustered index nonclustered_index_product_code
    on t_export_detail(product_code)
go
create nonclustered index nonclustered_index_product_name
    on t_export_detail(product_name)
go
create nonclustered index nonclustered_index__company_type_id
    on t_export_detail(company_type_id)
go
create nonclustered index nonclustered_index__company_type_id
    on t_export_detail(trade_type_id)
go
create nonclustered index nonclustered_index__transportation_id
    on t_export_detail(transportation_id)
go
create nonclustered index nonclustered_index__customs_id
    on t_export_detail(customs_id)
go
create nonclustered index nonclustered_index__city_id
    on t_export_detail(city_id)
go
create nonclustered index nonclustered_index__country_id
    on t_export_detail(country_id)
go
*/

------------------------------------
--产品类型表
create table t_product_type(
	id bigint primary key identity(1,1) not null,
	product_type nvarchar(20),
)
go

--进口总表
create table t_import_sum(
	id bigint identity(1,1) not null, --主键的关键字primary key--
	year_month nvarchar(20) not null, --进口年月--
	product_type nvarchar(20) not null, --产品类型
	product_name nvarchar(50) not null, --产品名称
	num_month numeric(20,3) not null, --当月数量
	num_sum numeric(20,3) not null,	--累计总数量
	money_month numeric(20,2) not null, --当月金额
	money_sum numeric(20,2) not null, --累计金额
	avgprice_month numeric(20,2) not null, --当月平均价格
	avgprice_sum numeric(20,2) not null, --累积平均价格
	num_premonth_incratio numeric(20,2) not null, --与上月数量增长比
	num_preyearsamemonth_incratio numeric(20,2) not null, --与上年同月数量增长比
	num_preyearsamequarter_incratio numeric(20,2) not null, --与上年同期数量增长比
	product_type_id bigint foreign key references t_product_type(id),
	constraint [PK__t_import_sum__id] primary key nonclustered (id)
)
create clustered index clustered_index_year_month
	on t_import_sum(year_month)
go
--DROP INDEX t_import_sum.clustered_index_year_month

create nonclustered index nonclustered_index_product_type
    on t_import_sum(product_type)
go
create nonclustered index nonclustered_index_product_name
    on t_import_sum(product_name)
go

-------------------------------
--出口总表---
create table t_export_sum(
	id bigint identity(1,1) not null, --主键的关键字primary key--
	year_month nvarchar(20) not null, --进口年月--
	product_type nvarchar(20) not null, --产品类型
	product_name nvarchar(50) not null, --产品名称
	num_month numeric(20,3) not null, --当月数量
	num_sum numeric(20,3) not null,	--累计总数量
	money_month numeric(20,2) not null, --当月金额
	money_sum numeric(20,2) not null, --累计金额
	avgprice_month numeric(20,2) not null, --当月平均价格
	avgprice_sum numeric(20,2) not null, --累积平均价格
	num_premonth_incratio numeric(20,2) not null, --与上月数量增长比
	num_preyearsamemonth_incratio numeric(20,2) not null, --与上年同月数量增长比
	num_preyearsamequarter_incratio numeric(20,2) not null, --与上年同期数量增长比
	product_type_id bigint foreign key references t_product_type(id),
	constraint [PK__t_export_sum__id] primary key nonclustered (id)
)
create clustered index clustered_index_year_month
	on t_export_sum(year_month)
go
--DROP INDEX t_export_sum.clustered_index_year_month

create nonclustered index nonclustered_index_product_type
    on t_export_sum(product_type)
go
create nonclustered index nonclustered_index_product_name
    on t_export_sum(product_name)
go

--------------------------------
--导数据日志表
create table t_log(
	id bigint primary key identity(1,1) not null, --主键的关键字primary key--
	log_type nvarchar(10) check (log_type in('导入','导出')),		--日志类型
	log_time datetime ,--日志时间
	trade_type nvarchar(10) check(trade_type in('进口','出口')), --进出类型
	table_type nvarchar(10) check(table_type in('明细表','总表')),
	upload_path nvarchar(200), --上传包的路径
	impfile_path nvarchar(200),--导入文件的路径
	download_url nvarchar(200), --导出文件下载地址
	upload_flag nvarchar(10) check(upload_flag in('上传成功','正在上传')), --是否已完成
	extract_flag nvarchar(10) check(extract_flag in('解压成功','正在解压','未解压')),--是否解压完成
	import_flag nvarchar(10) check(import_flag in('导入成功','正在导入','未导入'))
	lock_flag nvarchar(10) check(lock_flag in('未锁定','锁定中'))
	error_occur nvarchar(2000), --是否发生错误
	--constraint chk_Person check (log_time in('导入','导出'))
	--constraint [PK__t_export_sum__id] primary key nonclustered (id)
)

--==============================================
SET ANSI_NULLS OFF
GO

SET QUOTED_IDENTIFIER OFF
GO

SET ANSI_PADDING OFF
GO

--===========================================================
/**
drop table t_export_detail
drop table t_export_sum
drop table t_import_detail
drop table t_import_sum
drop table t_city
drop table t_company_type
drop table t_country
drop table t_customs
drop table t_product_type
drop table t_trade_type
drop table t_transportation
drop table t_log
*/
