--创建索引
-----------------------
--进口明细
create clustered index clustered_index_year
	on t_import_detail(col_year)
go
create clustered index clustered_index_month
	on t_import_detail(col_month)
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

------------------------
--出口明细
--创建索引
create clustered index clustered_index_year
	on t_export_detail(col_year)
go
create clustered index clustered_index_month
	on t_export_detail(col_month)
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

-----------------------
--进口总表
-- create clustered index clustered_index_year_month
-- 	on t_import_sum(year_month)
-- go
--DROP INDEX t_import_sum.clustered_index_year_month

create nonclustered index nonclustered_index_year
    on t_import_sum(col_year)
go
create nonclustered index nonclustered_index_month
    on t_import_sum(col_month)
go
create nonclustered index nonclustered_index_product_type
    on t_import_sum(product_type)
go
create nonclustered index nonclustered_index_product_name
    on t_import_sum(product_name)
go

-----------------------------
--出口总表
-- create clustered index clustered_index_year_month
-- 	on t_export_sum(year_month)
-- go
--DROP INDEX t_export_sum.clustered_index_year_month

create nonclustered index nonclustered_index_year
    on t_export_sum(col_year)
go

create nonclustered index nonclustered_index_month
    on t_export_sum(col_month)
go

create nonclustered index nonclustered_index_product_type
    on t_export_sum(product_type)
go
create nonclustered index nonclustered_index_product_name
    on t_export_sum(product_name)
go

