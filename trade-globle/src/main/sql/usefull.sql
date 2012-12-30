update t_log set import_flag='导入失败',error_occur='导入失败'
where id = 2

select * from t_export_detail where year_month='2012-11'


delete from t_export_detail where year_month > '2012-11';
delete from t_import_detail where year_month > '2012-11';