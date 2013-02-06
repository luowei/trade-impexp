update t_log set import_flag='导入失败',error_occur='导入失败'
where id = 2

select * from t_export_detail where year_month='2012-11'


delete from t_export_detail where year_month > '2012-11';
delete from t_import_detail where year_month > '2012-11';


update t_import_detail set customs=customs+'海关'
where substring(customs,len(rtrim(customs))-1,len(rtrim(customs))) <> '海关';

update t_export_detail set customs=customs+'海关'
where substring(customs,len(rtrim(customs))-1,len(rtrim(customs))) <> '海关';

delete from t_trade_type
where trade_type not in (
  '保税仓库货物','保税仓库进出境货物','保税区仓储转口货物','保税区转储',
  '边境小额贸易','出口加工区进口设备','出料加工贸易','对外承包工程出口货物',
  '对外承包货物','国际援助赠送','国家间、国际组织无偿援助和赠送的物资','加工贸易进口设备',
  '进口设备','进料加工贸易','境外设备进区','来料加工装配','来料加工装配贸易',
  '免税外汇商品','其他','其他境外捐赠物资','外商投资企业作为投资进口的设备、物品',
  '外商投资设备','一般贸易','易货贸易','租赁贸易')

delete from t_city
where city  in (
  '保税仓库货物','保税仓库进出境货物','保税区仓储转口货物','保税区转储',
  '边境小额贸易','出口加工区进口设备','出料加工贸易','对外承包工程出口货物',
  '对外承包货物','国际援助赠送','国家间、国际组织无偿援助和赠送的物资','加工贸易进口设备',
  '进口设备','进料加工贸易','境外设备进区','来料加工装配','来料加工装配贸易',
  '免税外汇商品','其他','其他境外捐赠物资','外商投资企业作为投资进口的设备、物品',
  '外商投资设备','一般贸易','易货贸易','租赁贸易')



