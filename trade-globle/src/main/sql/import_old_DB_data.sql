--==================================================================
--导入海关
INSERT INTO
lzdb.dbo.t_customs(
lzdb.dbo.t_customs.customs
)
SELECT
pcdb.dbo.TariffPass.PassName
FROM
pcdb.dbo.TariffPass
GO
DELETE FROM lzdb.dbo.t_customs
WHERE lzdb.dbo.t_customs.customs = ''
OR   lzdb.dbo.t_customs.customs = null;
OR  lzdb.dbo.t_customs.customs = 'null'

--导入贸易方式
INSERT INTO
lzdb.dbo.t_trade_type(
lzdb.dbo.t_trade_type.trade_type
)
SELECT
pcdb.dbo.TariffTrade.name
FROM
pcdb.dbo.TariffTrade
GO

DELETE FROM lzdb.dbo.t_trade_type
WHERE lzdb.dbo.t_trade_type.trade_type = ''
OR   lzdb.dbo.t_trade_type.trade_type = null;
OR  lzdb.dbo.t_trade_type.trade_type = 'null'

--导入城市
INSERT INTO
lzdb.dbo.t_city(
lzdb.dbo.t_city.city
)
SELECT
pcdb.dbo.TariffCity.name
FROM
pcdb.dbo.TariffCity

GO
DELETE FROM lzdb.dbo.t_city
WHERE lzdb.dbo.t_city.city = ''
OR   lzdb.dbo.t_city.city = null;
OR  lzdb.dbo.t_city.city = 'null'


--导入国家
INSERT INTO
lzdb.dbo.t_country(
lzdb.dbo.t_country.country
)
SELECT
pcdb.dbo.TariffCountry.name
FROM
pcdb.dbo.TariffCountry
GO

DELETE FROM lzdb.dbo.t_country
WHERE lzdb.dbo.t_country.country = ''
OR   lzdb.dbo.t_country.country = null;
OR  lzdb.dbo.t_country.country = 'null';

--导入企业性质
INSERT INTO
lzdb.dbo.t_company_type(
lzdb.dbo.t_company_type.company_type
)
SELECT
pcdb.dbo.TariffCompanyType.name
FROM
pcdb.dbo.TariffCompanyType
GO

DELETE FROM lzdb.dbo.t_company_type
WHERE lzdb.dbo.t_company_type.company_type = ''
OR   lzdb.dbo.t_company_type.company_type = null;
OR  lzdb.dbo.t_company_type.company_type = 'null';


--导入运输方式
INSERT INTO
lzdb.dbo.t_transportation(
lzdb.dbo.t_transportation.transportation
)
SELECT
pcdb.dbo.TariffTranName.name
FROM
pcdb.dbo.TariffTranName

GO
DELETE FROM lzdb.dbo.t_transportation
WHERE lzdb.dbo.t_transportation.transportation = ''
OR lzdb.dbo.t_transportation.transportation = null;
OR  lzdb.dbo.t_transportation.transportation = 'null';


--导入产品类型
INSERT INTO
lzdb.dbo.t_detail_type(
lzdb.dbo.t_detail_type.code,
lzdb.dbo.t_detail_type.detail_type
)
SELECT
pcdb.dbo.TariffType.pcode,
pcdb.dbo.TariffType.pCodeName
FROM
pcdb.dbo.TariffType
GO

DELETE FROM lzdb.dbo.t_detail_type
WHERE  lzdb.dbo.t_detail_type.code = ''
OR   lzdb.dbo.t_detail_type.code = null;
OR   lzdb.dbo.t_detail_type.code = 'null'
OR   lzdb.dbo.t_detail_type.detail_type = ''
OR   lzdb.dbo.t_detail_type.detail_type = null
OR   lzdb.dbo.t_detail_type.detail_type = 'null'

--导入总表产品类型
INSERT INTO lzdb.dbo.t_sum_type(product_type) VALUES('石油产品');
INSERT INTO lzdb.dbo.t_sum_type(product_type) VALUES('合成树脂');
INSERT INTO lzdb.dbo.t_sum_type(product_type) VALUES('有机化工');
INSERT INTO lzdb.dbo.t_sum_type(product_type) VALUES('其它化工品');
INSERT INTO lzdb.dbo.t_sum_type(product_type) VALUES('合成橡胶');
INSERT INTO lzdb.dbo.t_sum_type(product_type) VALUES('合成纤维');
INSERT INTO lzdb.dbo.t_sum_type(product_type) VALUES('化肥');
INSERT INTO lzdb.dbo.t_sum_type(product_type) VALUES('化肥');








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

SELECT
pcdb.dbo.Tariff1.ProductName,
pcdb.dbo.Tariff1.TariffNum,
pcdb.dbo.Tariff1.Pass,
pcdb.dbo.Tariff1.Money,
pcdb.dbo.Tariff1.Amount,
pcdb.dbo.Tariff1.OrderRQ,
pcdb.dbo.Tariff1.TradePatterns,
pcdb.dbo.Tariff1.ShippingAddr,
pcdb.dbo.Tariff1.CountryName,
pcdb.dbo.Tariff1.BusinessUnits,
pcdb.dbo.Tariff1.UnitPrice,
pcdb.dbo.Tariff1.Type,
pcdb.dbo.Tariff1.Years,
pcdb.dbo.Tariff1.[Month],
pcdb.dbo.Tariff1.CompanyType,
pcdb.dbo.Tariff1.TranName

FROM
pcdb.dbo.Tariff1

where
pcdb.dbo.Tariff1.IMP_EXP = 0;


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

SELECT
pcdb.dbo.Tariff1.ProductName,
pcdb.dbo.Tariff1.TariffNum,
pcdb.dbo.Tariff1.Pass,
pcdb.dbo.Tariff1.Money,
pcdb.dbo.Tariff1.Amount,
pcdb.dbo.Tariff1.OrderRQ,
pcdb.dbo.Tariff1.TradePatterns,
pcdb.dbo.Tariff1.ShippingAddr,
pcdb.dbo.Tariff1.CountryName,
pcdb.dbo.Tariff1.BusinessUnits,
pcdb.dbo.Tariff1.UnitPrice,
pcdb.dbo.Tariff1.Type,
pcdb.dbo.Tariff1.Years,
pcdb.dbo.Tariff1.[Month],
pcdb.dbo.Tariff1.CompanyType,
pcdb.dbo.Tariff1.TranName

FROM
pcdb.dbo.Tariff1

where
pcdb.dbo.Tariff1.IMP_EXP = 1;

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

SELECT
SUBSTRING(CONVERT(CHAR(19),dbo.jckxx.RQ,20), 1, 4) as col_year,
SUBSTRING(CONVERT(CHAR(19),dbo.jckxx.RQ,20), 6, 2) as col_month,
SUBSTRING(CONVERT(CHAR(19),dbo.jckxx.RQ,20), 1, 7) as col_month,
dbo.jckxx.CPLB,
dbo.jckxx.CPMC,
dbo.jckxx.BYSL,
dbo.jckxx.BSY,
dbo.jckxx.BSNTQ,
dbo.jckxx.LJSL,
dbo.jckxx.BYJE,
dbo.jckxx.LJJE,
dbo.jckxx.dypj,
dbo.jckxx.ljpj,
dbo.jckxx.bsnty

FROM
dbo.jckxx

WHERE
JCK = '进口';


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

SELECT
SUBSTRING(CONVERT(CHAR(19),dbo.jckxx.RQ,20), 1, 4) as col_year,
SUBSTRING(CONVERT(CHAR(19),dbo.jckxx.RQ,20), 6, 2) as col_month,
SUBSTRING(CONVERT(CHAR(19),dbo.jckxx.RQ,20), 1, 7) as col_month,
dbo.jckxx.CPLB,
dbo.jckxx.CPMC,
dbo.jckxx.BYSL,
dbo.jckxx.BSY,
dbo.jckxx.BSNTQ,
dbo.jckxx.LJSL,
dbo.jckxx.BYJE,
dbo.jckxx.LJJE,
dbo.jckxx.dypj,
dbo.jckxx.ljpj,
dbo.jckxx.bsnty

FROM
dbo.jckxx

WHERE
JCK = '出口';