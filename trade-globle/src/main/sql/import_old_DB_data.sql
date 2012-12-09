--==================================================================
--导入海关
INSERT INTO
lzdb.dbo.t_customs(
lzdb.dbo.t_customs.customs
)
SELECT DISTINCT
ltrim(rtrim(pcdb.dbo.TariffPass.PassName))
FROM
pcdb.dbo.TariffPass
GO

DELETE FROM lzdb.dbo.t_customs
WHERE lzdb.dbo.t_customs.customs = ''
OR   lzdb.dbo.t_customs.customs = null
OR  lzdb.dbo.t_customs.customs = 'null'
Go

--导入贸易方式
INSERT INTO
lzdb.dbo.t_trade_type(
lzdb.dbo.t_trade_type.trade_type
)
SELECT DISTINCT
ltrim(rtrim(pcdb.dbo.TariffTrade.name))
FROM
pcdb.dbo.TariffTrade
GO

DELETE FROM lzdb.dbo.t_trade_type
WHERE lzdb.dbo.t_trade_type.trade_type = ''
OR   lzdb.dbo.t_trade_type.trade_type = null
OR  lzdb.dbo.t_trade_type.trade_type = 'null'
GO

--导入城市
INSERT INTO
lzdb.dbo.t_city(
lzdb.dbo.t_city.city
)
SELECT DISTINCT
ltrim(rtrim(pcdb.dbo.TariffCity.name))
FROM
pcdb.dbo.TariffCity
GO

DELETE FROM lzdb.dbo.t_city
WHERE lzdb.dbo.t_city.city = ''
OR   lzdb.dbo.t_city.city = null
OR  lzdb.dbo.t_city.city = 'null'
GO


--导入国家
INSERT INTO
lzdb.dbo.t_country(
lzdb.dbo.t_country.country
)
SELECT DISTINCT
ltrim(rtrim(pcdb.dbo.TariffCountry.name))
FROM
pcdb.dbo.TariffCountry
GO

DELETE FROM lzdb.dbo.t_country
WHERE lzdb.dbo.t_country.country = ''
OR   lzdb.dbo.t_country.country = null
OR  lzdb.dbo.t_country.country = 'null';
GO

--导入企业性质
INSERT INTO
lzdb.dbo.t_company_type(
lzdb.dbo.t_company_type.company_type
)
SELECT DISTINCT
ltrim(rtrim(pcdb.dbo.TariffCompanyType.name))
FROM
pcdb.dbo.TariffCompanyType
GO

DELETE FROM lzdb.dbo.t_company_type
WHERE lzdb.dbo.t_company_type.company_type = ''
OR   lzdb.dbo.t_company_type.company_type = null
OR  lzdb.dbo.t_company_type.company_type = 'null';
GO


--导入运输方式
INSERT INTO
lzdb.dbo.t_transportation(
lzdb.dbo.t_transportation.transportation
)
SELECT DISTINCT
ltrim(rtrim(pcdb.dbo.TariffTranName.name))
FROM
pcdb.dbo.TariffTranName

GO
DELETE FROM lzdb.dbo.t_transportation
WHERE lzdb.dbo.t_transportation.transportation = ''
OR lzdb.dbo.t_transportation.transportation = null
OR  lzdb.dbo.t_transportation.transportation = 'null';
GO


--导入产品类型
INSERT INTO
lzdb.dbo.t_detail_type(
lzdb.dbo.t_detail_type.code,
lzdb.dbo.t_detail_type.detail_type
)
SELECT DISTINCT
ltrim(rtrim(pcdb.dbo.TariffType.pcode)),
ltrim(rtrim(pcdb.dbo.TariffType.pCodeName))
FROM
pcdb.dbo.TariffType
GO

DELETE FROM lzdb.dbo.t_detail_type
WHERE  lzdb.dbo.t_detail_type.code = ''
OR   lzdb.dbo.t_detail_type.code = null
OR   lzdb.dbo.t_detail_type.code = 'null'
OR   lzdb.dbo.t_detail_type.detail_type = ''
OR   lzdb.dbo.t_detail_type.detail_type = null
OR   lzdb.dbo.t_detail_type.detail_type = 'null'
GO

--导入总表产品类型
INSERT INTO lzdb.dbo.t_sum_type(product_type) VALUES('石油产品');
INSERT INTO lzdb.dbo.t_sum_type(product_type) VALUES('合成树脂');
INSERT INTO lzdb.dbo.t_sum_type(product_type) VALUES('有机化工');
INSERT INTO lzdb.dbo.t_sum_type(product_type) VALUES('其它化工品');
INSERT INTO lzdb.dbo.t_sum_type(product_type) VALUES('合成橡胶');
INSERT INTO lzdb.dbo.t_sum_type(product_type) VALUES('合成纤维');
INSERT INTO lzdb.dbo.t_sum_type(product_type) VALUES('化肥');
INSERT INTO lzdb.dbo.t_sum_type(product_type) VALUES('化肥');
GO








--========================导入明细表================================
-- 导入 t_import_detail
INSERT INTO
lzdb.dbo.t_import_detail(
lzdb.dbo.t_import_detail.product_name,
lzdb.dbo.t_import_detail.product_code,
lzdb.dbo.t_import_detail.customs,
lzdb.dbo.t_import_detail.amount_money,
lzdb.dbo.t_import_detail.amount,
lzdb.dbo.t_import_detail.year_month,
lzdb.dbo.t_import_detail.trade_type,
lzdb.dbo.t_import_detail.city,
lzdb.dbo.t_import_detail.country,
lzdb.dbo.t_import_detail.unit,
lzdb.dbo.t_import_detail.unit_price,
lzdb.dbo.t_import_detail.product_type,
lzdb.dbo.t_import_detail.col_year,
lzdb.dbo.t_import_detail.col_month,
lzdb.dbo.t_import_detail.company_type,
lzdb.dbo.t_import_detail.transportation
)

SELECT DISTINCT
ltrim(rtrim(pcdb.dbo.Tariff1.ProductName)),
ltrim(rtrim(pcdb.dbo.Tariff1.TariffNum)),
ltrim(rtrim(pcdb.dbo.Tariff1.Pass)),
pcdb.dbo.Tariff1.Money,
pcdb.dbo.Tariff1.Amount,
case
len(substring(ltrim(rtrim(pcdb.dbo.Tariff1.OrderRQ)),6,2))
when 1 then  substring(ltrim(rtrim(pcdb.dbo.Tariff1.OrderRQ)),1,5)
		+'0'+substring(ltrim(rtrim(pcdb.dbo.Tariff1.OrderRQ)),6,2)
else ltrim(rtrim(pcdb.dbo.Tariff1.OrderRQ))
end ,
ltrim(rtrim(pcdb.dbo.Tariff1.TradePatterns)),
ltrim(rtrim(pcdb.dbo.Tariff1.ShippingAddr)),
ltrim(rtrim(pcdb.dbo.Tariff1.CountryName)),
ltrim(rtrim(pcdb.dbo.Tariff1.BusinessUnits)),
pcdb.dbo.Tariff1.UnitPrice,
ltrim(rtrim(pcdb.dbo.Tariff1.Type)),
ltrim(rtrim(pcdb.dbo.Tariff1.Years)),
ltrim(rtrim(pcdb.dbo.Tariff1.[Month])),
ltrim(rtrim(pcdb.dbo.Tariff1.CompanyType)),
ltrim(rtrim(pcdb.dbo.Tariff1.TranName))

FROM
pcdb.dbo.Tariff1

where
pcdb.dbo.Tariff1.IMP_EXP = 0;

GO


-- 导入 t_export_detail
INSERT INTO
lzdb.dbo.t_export_detail(
lzdb.dbo.t_export_detail.product_name,
lzdb.dbo.t_export_detail.product_code,
lzdb.dbo.t_export_detail.customs,
lzdb.dbo.t_export_detail.amount_money,
lzdb.dbo.t_export_detail.amount,
lzdb.dbo.t_export_detail.year_month,
lzdb.dbo.t_export_detail.trade_type,
lzdb.dbo.t_export_detail.city,
lzdb.dbo.t_export_detail.country,
lzdb.dbo.t_export_detail.unit,
lzdb.dbo.t_export_detail.unit_price,
lzdb.dbo.t_export_detail.product_type,
lzdb.dbo.t_export_detail.col_year,
lzdb.dbo.t_export_detail.col_month,
lzdb.dbo.t_export_detail.company_type,
lzdb.dbo.t_export_detail.transportation
)

SELECT DISTINCT
ltrim(rtrim(pcdb.dbo.Tariff1.ProductName)),
ltrim(rtrim(pcdb.dbo.Tariff1.TariffNum)),
ltrim(rtrim(pcdb.dbo.Tariff1.Pass)),
pcdb.dbo.Tariff1.Money,
pcdb.dbo.Tariff1.Amount,
case
len(substring(ltrim(rtrim(pcdb.dbo.Tariff1.OrderRQ)),6,2))
when 1 then  substring(ltrim(rtrim(pcdb.dbo.Tariff1.OrderRQ)),1,5)
		+'0'+substring(ltrim(rtrim(pcdb.dbo.Tariff1.OrderRQ)),6,2)
else ltrim(rtrim(pcdb.dbo.Tariff1.OrderRQ))
end ,
ltrim(rtrim(pcdb.dbo.Tariff1.TradePatterns)),
ltrim(rtrim(pcdb.dbo.Tariff1.ShippingAddr)),
ltrim(rtrim(pcdb.dbo.Tariff1.CountryName)),
ltrim(rtrim(pcdb.dbo.Tariff1.BusinessUnits)),
pcdb.dbo.Tariff1.UnitPrice,
ltrim(rtrim(pcdb.dbo.Tariff1.Type)),
ltrim(rtrim(pcdb.dbo.Tariff1.Years)),
ltrim(rtrim(pcdb.dbo.Tariff1.[Month])),
ltrim(rtrim(pcdb.dbo.Tariff1.CompanyType)),
ltrim(rtrim(pcdb.dbo.Tariff1.TranName))

FROM
pcdb.dbo.Tariff1

where
pcdb.dbo.Tariff1.IMP_EXP = 1;

GO

--======================导入总表============================
--导入t_import_sum
INSERT INTO
lzdb.dbo.t_import_sum(
lzdb.dbo.t_import_sum.col_year,
lzdb.dbo.t_import_sum.col_month,
lzdb.dbo.t_import_sum.year_month,
lzdb.dbo.t_import_sum.product_type,
lzdb.dbo.t_import_sum.product_name,
lzdb.dbo.t_import_sum.num_month,
lzdb.dbo.t_import_sum.num_premonth_incratio,
lzdb.dbo.t_import_sum.num_preyearsamequarter_incratio,
lzdb.dbo.t_import_sum.num_sum,
lzdb.dbo.t_import_sum.money_month,
lzdb.dbo.t_import_sum.money_sum,
lzdb.dbo.t_import_sum.avgprice_month,
lzdb.dbo.t_import_sum.avgprice_sum,
lzdb.dbo.t_import_sum.num_preyearsamemonth_incratio
)

SELECT DISTINCT
SUBSTRING(CONVERT(CHAR(19),pcdb.dbo.jckxx.RQ,20), 1, 4) as col_year,
SUBSTRING(CONVERT(CHAR(19),pcdb.dbo.jckxx.RQ,20), 6, 2) as col_month,
SUBSTRING(CONVERT(CHAR(19),pcdb.dbo.jckxx.RQ,20), 1, 7) as col_month,
ltrim(rtrim(pcdb.dbo.jckxx.CPLB)),
ltrim(rtrim(pcdb.dbo.jckxx.CPMC)),
pcdb.dbo.jckxx.BYSL,
pcdb.dbo.jckxx.BSY,
pcdb.dbo.jckxx.BSNTQ,
pcdb.dbo.jckxx.LJSL,
pcdb.dbo.jckxx.BYJE,
pcdb.dbo.jckxx.LJJE,
pcdb.dbo.jckxx.dypj,
pcdb.dbo.jckxx.ljpj,
pcdb.dbo.jckxx.bsnty

FROM
pcdb.dbo.jckxx

WHERE
JCK = '进口'

GO


--导入t_export_sum
INSERT INTO
lzdb.dbo.t_export_sum(
lzdb.dbo.t_export_sum.col_year,
lzdb.dbo.t_export_sum.col_month,
lzdb.dbo.t_export_sum.year_month,
lzdb.dbo.t_export_sum.product_type,
lzdb.dbo.t_export_sum.product_name,
lzdb.dbo.t_export_sum.num_month,
lzdb.dbo.t_export_sum.num_premonth_incratio,
lzdb.dbo.t_export_sum.num_preyearsamequarter_incratio,
lzdb.dbo.t_export_sum.num_sum,
lzdb.dbo.t_export_sum.money_month,
lzdb.dbo.t_export_sum.money_sum,
lzdb.dbo.t_export_sum.avgprice_month,
lzdb.dbo.t_export_sum.avgprice_sum,
lzdb.dbo.t_export_sum.num_preyearsamemonth_incratio
)

SELECT DISTINCT
SUBSTRING(CONVERT(CHAR(19),pcdb.dbo.jckxx.RQ,20), 1, 4) as col_year,
SUBSTRING(CONVERT(CHAR(19),pcdb.dbo.jckxx.RQ,20), 6, 2) as col_month,
SUBSTRING(CONVERT(CHAR(19),pcdb.dbo.jckxx.RQ,20), 1, 7) as year_month,
ltrim(rtrim(pcdb.dbo.jckxx.CPLB)),
ltrim(rtrim(pcdb.dbo.jckxx.CPMC)),
pcdb.dbo.jckxx.BYSL,
pcdb.dbo.jckxx.BSY,
pcdb.dbo.jckxx.BSNTQ,
pcdb.dbo.jckxx.LJSL,
pcdb.dbo.jckxx.BYJE,
pcdb.dbo.jckxx.LJJE,
pcdb.dbo.jckxx.dypj,
pcdb.dbo.jckxx.ljpj,
pcdb.dbo.jckxx.bsnty

FROM
pcdb.dbo.jckxx

WHERE
JCK = '出口';

