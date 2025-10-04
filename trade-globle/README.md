# trade-globle

**进出口贸易数据管理系统 - 核心业务模块**

一个集成 Spring AOP、IoC、Spring MVC、Spring Data JPA、Spring JDBC、Groovy、Highcharts 等技术的企业级 Web 应用。

---

## 📋 目录

- [模块概述](#模块概述)
- [技术架构](#技术架构)
- [项目结构](#项目结构)
- [数据模型](#数据模型)
- [核心功能](#核心功能)
- [代码分层](#代码分层)
- [配置说明](#配置说明)
- [构建与运行](#构建与运行)
- [开发指南](#开发指南)
- [效果截图](#效果截图)

---

## 模块概述

**trade-globle** 是 [trade-impexp](../) 项目的核心业务模块，实现了进出口贸易数据的完整生命周期管理。

### 统计信息

- **代码规模**: 112 个 Java 文件 + 4 个 Groovy 脚本 + 12 个配置文件
- **实体类**: 25 个（含抽象类、明细类、汇总类、统计类）
- **工具类**: 11 个（3186+ 行代码）
- **控制器**: 10 个
- **服务类**: 10 个实现类
- **Repository**: 6 个主接口 + 4 个自定义接口

### 核心价值

✅ **完整的数据流**: 导入 → 存储 → 查询 → 统计 → 可视化 → 导出
✅ **灵活的查询**: 动态条件构建，支持复杂多维度筛选
✅ **高性能**: 二级缓存、连接池优化、批量处理
✅ **可扩展**: 分层架构、接口抽象、依赖注入

---

## 技术架构

### Web 层
- **框架**: Spring MVC 3.1.2（RESTful 风格）
- **视图技术**: JSP + JSTL 1.2
- **页面布局**: Sitemesh 2.4.2（装饰器模式）
- **前端框架**: Bootstrap 2.x + jQuery UI
- **字符编码**: UTF-8（Filter 统一处理）

### 业务层
- **依赖注入**: Spring Context 3.1.2
- **AOP**: AspectJ 1.6.12（性能监控、事务管理）
- **性能监控**: JAMon 2.4（方法执行时间统计）
- **事务管理**: Spring Transaction + JPA

### 持久层

#### 主要方案：Spring Data JPA
- **版本**: Spring Data JPA 1.1.0
- **ORM**: Hibernate 4.1.4
- **特性**:
  - `CrudRepository` - 基础 CRUD 操作
  - `JpaSpecificationExecutor` - 动态查询（Criteria API）
  - 自定义 Repository 实现（接口 + Impl 模式）
  - `@Query` 注解（JPQL / Native SQL）

#### 辅助方案：Spring JDBC
- **用途**: 复杂统计查询、批量操作
- **工具**: `JdbcTemplate`、自定义 `JdbcUtil`

### 数据库
- **支持**: MySQL 5.x / SQL Server 2008+
- **连接池**: BoneCP 0.7.1（性能优于 C3P0）
- **方言**: 通过 Maven Profile 动态配置

### 缓存层
- **技术**: Ehcache 2.5.2
- **封装**: `EHCacheUtil` 工具类
- **策略**:
  - 查询结果缓存
  - 配置数据缓存
  - 字典数据缓存

### 日志
- **框架**: Logback 1.0.7 + SLF4J 1.6.4
- **优势**: 性能优于 Log4j，支持异步日志

### 图表可视化

#### 方案一：Highcharts（主）
- **版本**: Highcharts 2.x
- **类型**: 前端 JavaScript 库
- **特点**: 交互性强、样式美观、浏览器兼容好
- **数据**: JSON 格式（AJAX 异步加载）

#### 方案二：Open Flash Chart（备）
- **版本**: OFC4J 1.0-alpha5
- **类型**: Flash 图表 + Java 封装
- **数据处理**: Groovy 脚本动态生成
- **缺点**: 组件不够成熟，已逐步废弃

---

## 项目结构

```
trade-globle/
├── pom.xml                                    # Maven 配置
├── README.md                                  # 本文档
├── doc/                                       # 项目文档
│   └── img/                                   # 效果截图
├── src/
│   ├── main/
│   │   ├── java/com/oilchem/trade/
│   │   │   ├── domain/                        # 📦 领域模型层（25 个实体类）
│   │   │   │   ├── abstrac/                   # 抽象基类
│   │   │   │   │   ├── IdEntity.java          # 主键基类（继承 Spring Data AbstractPersistable）
│   │   │   │   │   ├── TradeDetail.java       # 明细表抽象类（13 个字段）
│   │   │   │   │   ├── TradeSum.java          # 汇总表抽象类（9 个字段）
│   │   │   │   │   └── DetailCount.java       # 统计抽象类
│   │   │   │   ├── detail/                    # 明细表实体
│   │   │   │   │   ├── ImpTradeDetail.java    # 进口明细（t_import_detail）
│   │   │   │   │   └── ExpTradeDetail.java    # 出口明细（t_export_detail）
│   │   │   │   ├── sum/                       # 汇总表实体
│   │   │   │   │   ├── ImpTradeSum.java       # 进口汇总（t_import_sum）
│   │   │   │   │   └── ExpTradeSum.java       # 出口汇总（t_export_sum）
│   │   │   │   ├── count/                     # 统计表实体（6 个）
│   │   │   │   │   ├── ImpDetailCount.java    # 进口明细统计
│   │   │   │   │   ├── ExpDetailCount.java    # 出口明细统计
│   │   │   │   │   ├── ImpDetailTradetype.java # 按贸易方式统计
│   │   │   │   │   └── ...
│   │   │   │   ├── condition/                 # 🔍 条件/字典表实体（8 个）
│   │   │   │   │   ├── Product.java           # 产品信息
│   │   │   │   │   ├── City.java              # 城市
│   │   │   │   │   ├── Country.java           # 国家
│   │   │   │   │   ├── CompanyType.java       # 企业性质
│   │   │   │   │   ├── TradeType.java         # 贸易方式
│   │   │   │   │   ├── Transportation.java    # 运输方式
│   │   │   │   │   ├── Customs.java           # 海关
│   │   │   │   │   └── SumType.java           # 汇总类型
│   │   │   │   └── Log.java                   # 系统日志
│   │   │   │
│   │   │   ├── dao/                           # 💾 数据访问层
│   │   │   │   ├── BaseDao.java               # JDBC DAO 基类
│   │   │   │   ├── ImpTradeDetailDao.java     # 进口明细 Repository（Spring Data JPA）
│   │   │   │   ├── ExpTradeDetailDao.java     # 出口明细 Repository
│   │   │   │   ├── ImpTradeSumDao.java        # 进口汇总 Repository
│   │   │   │   ├── ExpTradeSumDao.java        # 出口汇总 Repository
│   │   │   │   ├── LogDao.java                # 日志 Repository
│   │   │   │   ├── custom/                    # 自定义 Repository 接口
│   │   │   │   │   ├── ImpTradeDetailDaoCustom.java
│   │   │   │   │   ├── ExpTradeDetailDaoCustom.java
│   │   │   │   │   └── impl/                  # 自定义实现（使用 JDBC）
│   │   │   │   ├── condition/                 # 条件表 DAO
│   │   │   │   ├── count/                     # 统计表 DAO
│   │   │   │   └── others/                    # 其他 JDBC DAO
│   │   │   │       └── map/                   # RowMapper 实现
│   │   │   │
│   │   │   ├── service/                       # 🔧 业务逻辑层
│   │   │   │   ├── TradeDetailService.java    # 明细业务接口
│   │   │   │   ├── TradeSumService.java       # 汇总业务接口
│   │   │   │   ├── CommonService.java         # 通用业务接口
│   │   │   │   ├── ChartService.java          # 图表业务接口
│   │   │   │   ├── HighChartService.java      # Highcharts 业务
│   │   │   │   ├── DetailCountService.java    # 统计业务接口
│   │   │   │   ├── FilterService.java         # 筛选业务接口
│   │   │   │   ├── TaskService.java           # 任务业务接口
│   │   │   │   ├── LogService.java            # 日志业务接口
│   │   │   │   └── impl/                      # 业务实现类（10 个）
│   │   │   │       ├── TradeDetailServiceImpl.java
│   │   │   │       ├── TradeSumServiceImpl.java
│   │   │   │       └── ...
│   │   │   │
│   │   │   ├── view/controller/               # 🌐 控制器层（10 个）
│   │   │   │   ├── CommonController.java      # 公共控制器基类
│   │   │   │   ├── TradeDetailController.java # 明细管理（/manage/*）
│   │   │   │   ├── TradeSumController.java    # 汇总管理（/sum/*）
│   │   │   │   ├── ExportDataController.java  # Excel 导出（/export/*）
│   │   │   │   ├── ChartController.java       # OFC 图表（/chart/*）
│   │   │   │   ├── HighChartController.java   # Highcharts（/highchart/*）
│   │   │   │   ├── DetailCountController.java # 统计查询（/count/*）
│   │   │   │   ├── FilterController.java      # 筛选条件（/filter/*）
│   │   │   │   ├── ConfigController.java      # 系统配置（/config/*）
│   │   │   │   └── LogController.java         # 日志查看（/log/*）
│   │   │   │
│   │   │   ├── util/                          # 🔨 工具类（11 个，3186+ 行）
│   │   │   │   ├── ZipUtil.java               # ZIP/RAR 压缩解压
│   │   │   │   ├── FileUtil.java              # 文件读写、复制、移动、删除
│   │   │   │   ├── EHCacheUtil.java           # Ehcache 缓存管理
│   │   │   │   ├── ConfigUtil.java            # JSON 配置读取（Gson）
│   │   │   │   ├── JdbcUtil.java              # JDBC 工具
│   │   │   │   ├── QueryUtils.java            # 查询工具（PropertyFilter）
│   │   │   │   ├── DynamicSpecifications.java # JPA 动态查询规范构建器
│   │   │   │   ├── DateUtils.java             # 日期工具（Joda-Time）
│   │   │   │   ├── CommonUtil.java            # 通用工具
│   │   │   │   ├── GenericsUtils.java         # 泛型工具（反射）
│   │   │   │   └── CommandUtil.java           # 命令执行
│   │   │   │
│   │   │   └── bean/                          # 📊 DTO 数据传输对象
│   │   │       ├── CommonDto.java             # 通用 DTO（分页、排序）
│   │   │       ├── YearMonthDto.java          # 年月参数
│   │   │       ├── ChartData.java             # 图表数据封装
│   │   │       ├── Series.java                # 图表系列
│   │   │       ├── ProductCount.java          # 产品统计
│   │   │       ├── DetailCriteria.java        # 明细查询条件
│   │   │       └── DocBean.java               # 文档配置 Bean
│   │   │
│   │   ├── groovy/com/oilchem/trade/chart/    # 📈 Groovy 图表脚本（4 个）
│   │   │   ├── Common.groovy                  # 公共方法
│   │   │   ├── DetailChart.groovy             # 明细图表
│   │   │   ├── SumChart.groovy                # 汇总图表
│   │   │   └── DetailCountChart.groovy        # 统计图表
│   │   │
│   │   ├── resources/                         # ⚙️ 配置文件
│   │   │   ├── spring/
│   │   │   │   ├── applicationContext-root.xml       # 根容器配置
│   │   │   │   ├── applicationContext-dataSource.xml # 数据源配置
│   │   │   │   ├── applicationContext-jpa.xml        # JPA 配置
│   │   │   │   ├── applicationContext-jdbc.xml       # JDBC 配置
│   │   │   │   └── appServlet/
│   │   │   │       ├── mvc-servlet.xml               # Spring MVC 核心
│   │   │   │       └── mvc-controllers.xml           # 控制器扫描
│   │   │   ├── persistence.xml                # JPA 持久化单元配置
│   │   │   ├── ehcache.xml                    # Ehcache 缓存配置
│   │   │   ├── logback.xml                    # Logback 日志配置
│   │   │   ├── dataSource.properties          # 数据源属性
│   │   │   └── logback.properties             # 日志属性
│   │   │
│   │   ├── filter/
│   │   │   └── mavenFilters.properties        # Maven 环境变量（dev/test/test2）
│   │   │
│   │   └── webapp/                            # 🌍 Web 资源
│   │       ├── WEB-INF/
│   │       │   ├── web.xml                    # Web 应用配置
│   │       │   ├── decorators.xml             # Sitemesh 装饰器配置
│   │       │   └── views/                     # JSP 视图（未包含，待补充）
│   │       ├── resources/                     # 静态资源
│   │       │   ├── bootstrap/                 # Bootstrap 框架
│   │       │   ├── jquery-ui/                 # jQuery UI
│   │       │   ├── css/                       # 样式表
│   │       │   ├── js/
│   │       │   │   ├── jquery/                # jQuery 库
│   │       │   │   ├── highcharts/            # Highcharts 图表库
│   │       │   │   └── autocomplete/          # 自动完成组件
│   │       │   ├── images/                    # 图片资源
│   │       │   └── openflashchart/            # OFC 资源
│   │       └── trade.jsp                      # 首页
│   │
│   └── test/java/                             # 🧪 单元测试
│       └── com/oilchem/test/
│           ├── AppTest.java
│           ├── ControllerTest.java
│           ├── ImportTest.java
│           └── GenericTest.java
│
└── target/                                    # 构建输出（.gitignore）
```

---

## 数据模型

### 实体类继承体系

```
IdEntity (抽象基类，继承 Spring Data AbstractPersistable<Long>)
├── TradeDetail (明细表抽象类 @MappedSuperclass)
│   ├── ImpTradeDetail (@Entity, t_import_detail)
│   └── ExpTradeDetail (@Entity, t_export_detail)
├── TradeSum (汇总表抽象类 @MappedSuperclass)
│   ├── ImpTradeSum (@Entity, t_import_sum)
│   └── ExpTradeSum (@Entity, t_export_sum)
├── DetailCount (统计抽象类 @MappedSuperclass)
│   ├── ImpDetailCount (@Entity)
│   ├── ExpDetailCount (@Entity)
│   ├── ImpDetailTradetype (@Entity)
│   ├── ExpDetailTradetype (@Entity)
│   ├── ImpDetailCompanytype (@Entity)
│   └── ExpDetailCompanytype (@Entity)
├── Product (@Entity, 产品信息)
├── City (@Entity, 城市)
├── Country (@Entity, 国家)
├── CompanyType (@Entity, 企业性质)
├── TradeType (@Entity, 贸易方式)
├── Transportation (@Entity, 运输方式)
├── Customs (@Entity, 海关)
├── SumType (@Entity, 汇总类型)
└── Log (@Entity, 系统日志)
```

### TradeDetail 明细表字段（13 个核心字段）

| 字段 | 类型 | 说明 | 数据库列名 |
|------|------|------|-----------|
| year | Integer | 年份 | col_year |
| month | Integer | 月份 | col_month |
| yearMonth | String | 年月（组合） | year_month |
| productCode | String | 产品代码 | product_code |
| productName | String | 产品名称 | product_name |
| productType | String | 产品类型 | product_type |
| typeCode | Integer | 类型代码 | type_code |
| companyType | String | 企业性质（国企/民企/外企） | company_type |
| tradeType | String | 贸易方式（一般贸易/加工贸易） | trade_type |
| transportation | String | 运输方式（海运/空运/陆运） | transportation |
| customs | String | 海关 | customs |
| city | String | 城市 | city |
| country | String | 产销国家 | country |
| amount | BigDecimal | 数量 | amount |
| unit | String | 计量单位 | unit |
| amountMoney | BigDecimal | 金额 | amount_money |
| unitPrice | BigDecimal | 单价 | unit_price |

### TradeSum 汇总表字段（9 个核心字段）

汇总表是明细表的聚合，按年月、产品、公司类型、贸易方式等维度统计。字段类似明细表，但数据已汇总。

---

## 核心功能

### 1️⃣ 数据导入

**功能**: 批量导入 Excel/CSV 格式的贸易数据

**流程**:
1. 上传文件（支持 ZIP 压缩包）
2. 解压文件（使用 `ZipUtil`）
3. 解析 Excel（使用 JXL 库）
4. 数据验证（必填字段、格式检查）
5. 删除重复数据（按年月）
6. 批量插入数据库（JDBC 批处理）
7. 生成汇总数据
8. 记录导入日志

**关键代码**:
```java
// TradeDetailController.java
@RequestMapping("/uploadDetailFile")
public String uploadDetailFile(@RequestParam("file") MultipartFile file,
                                YearMonthDto yearMonthDto,
                                RedirectAttributes redirectAttributes) {
    // 1. 保存上传文件
    String zipPath = upload_detailzip_dir.value() + "/" + file.getOriginalFilename();
    FileUtil.saveFile(file, zipPath);

    // 2. 解压文件
    ZipUtil.unzip(zipPath, targetDir);

    // 3. 异步导入（使用 TaskService）
    taskService.importDetailData(targetDir, yearMonthDto);

    return "redirect:/manage/listdetail/1";
}
```

### 2️⃣ 动态查询

**功能**: 支持多条件组合查询，条件可动态添加

**技术**: JPA Criteria API + Spring Data Specification

**实现**:

#### PropertyFilter（属性过滤器）
```java
// QueryUtils.java
public class PropertyFilter {
    private String name;       // 属性名
    private Object value;      // 属性值
    private Type type;         // 比较类型（EQ/LIKE/GT/GE/LT/LE）

    public enum Type {
        EQ,   // 等于
        LIKE, // 模糊查询
        GT,   // 大于
        GE,   // 大于等于
        LT,   // 小于
        LE    // 小于等于
    }
}
```

#### DynamicSpecifications（动态规范构建器）
```java
// DynamicSpecifications.java
public static <T> Specification<T> byPropertyFilter(
        Collection<PropertyFilter> filterList, Class<T> clazz) {

    return (root, query, cb) -> {
        List<Predicate> predicates = new ArrayList<>();

        for (PropertyFilter filter : filterList) {
            Path expression = root.get(filter.getName());

            switch (filter.getType()) {
                case EQ:
                    predicates.add(cb.equal(expression, filter.getValue()));
                    break;
                case LIKE:
                    predicates.add(cb.like(expression, "%" + filter.getValue() + "%"));
                    break;
                case GT:
                    predicates.add(cb.greaterThan(expression, (Comparable) filter.getValue()));
                    break;
                // ... 其他类型
            }
        }

        return cb.and(predicates.toArray(new Predicate[0]));
    };
}
```

#### 使用示例
```java
// TradeDetailServiceImpl.java
public Page<ImpTradeDetail> findImpWithCriteria(ImpTradeDetail tradeDetail,
                                                 CommonDto commonDto,
                                                 YearMonthDto yearMonthDto,
                                                 Pageable pageable) {
    // 构建过滤条件
    Collection<PropertyFilter> filters = buildFilters(tradeDetail, yearMonthDto);

    // 创建 Specification
    Specification<ImpTradeDetail> spec = DynamicSpecifications.byPropertyFilter(
        filters, ImpTradeDetail.class
    );

    // 执行查询
    return repository.findAll(spec, pageable);
}
```

### 3️⃣ 统计分析

**功能**: 多维度数据统计和聚合

**维度**:
- 按时间统计（年/月）
- 按产品统计
- 按企业性质统计
- 按贸易方式统计
- 按地区统计（城市、国家）
- 按海关统计
- 按运输方式统计

**实现方式**:

#### 方式一：JPQL 聚合查询
```java
// ImpTradeDetailDao.java
@Query("SELECT new com.oilchem.trade.bean.ProductCount(" +
       "  t.yearMonth, t.productCode, t.productName, t.companyType, " +
       "  SUM(t.amount), t.unit, SUM(t.amountMoney)) " +
       "FROM ImpTradeDetail t " +
       "WHERE t.productCode = ?1 AND t.yearMonth = ?2 " +
       "GROUP BY t.yearMonth, t.productCode, t.productName, t.unit, t.companyType")
List<ProductCount> getCompanyTypeProductCount(String productCode, String yearMonth);
```

#### 方式二：统计表（定期生成）
```java
// 统计实体类
@Entity
@Table(name = "t_imp_detail_count")
public class ImpDetailCount extends DetailCount {
    // 统计字段继承自 DetailCount
}

// 定时任务生成统计数据
@Scheduled(cron = "0 0 2 * * ?") // 每天凌晨2点
public void generateStatistics() {
    // 清空旧数据
    countRepository.deleteAll();

    // 生成新统计
    List<ImpDetailCount> counts = calculateCounts();
    countRepository.save(counts);
}
```

### 4️⃣ 数据可视化

**功能**: 图表展示统计结果

#### Highcharts 方案（推荐）

**数据流**:
1. 控制器查询数据
2. 转换为 JSON 格式
3. AJAX 返回前端
4. Highcharts 渲染图表

**实现**:
```java
// HighChartController.java
@RequestMapping("/getChartData")
@ResponseBody
public Map<String, Object> getChartData(@RequestParam String productCode,
                                        @RequestParam String yearMonth) {
    // 查询统计数据
    List<ProductCount> counts = service.getProductCount(productCode, yearMonth);

    // 构建 Highcharts 配置
    Map<String, Object> chartConfig = new HashMap<>();
    chartConfig.put("chart", Map.of("type", "column"));
    chartConfig.put("title", Map.of("text", "产品统计"));

    // 构建系列数据
    List<String> categories = new ArrayList<>();
    List<Number> data = new ArrayList<>();
    for (ProductCount count : counts) {
        categories.add(count.getCompanyType());
        data.add(count.getAmount());
    }

    chartConfig.put("xAxis", Map.of("categories", categories));
    chartConfig.put("series", List.of(Map.of("name", "数量", "data", data)));

    return chartConfig;
}
```

**前端**:
```javascript
$.getJSON('/highchart/getChartData', { productCode: '001', yearMonth: '2012-11' },
function(config) {
    $('#chartContainer').highcharts(config);
});
```

#### Open Flash Chart 方案（已废弃）

**数据流**:
1. 控制器调用 Groovy 脚本
2. Groovy 构建 OFC Chart 对象
3. 序列化为 JSON（使用 XStream）
4. 前端 Flash 组件渲染

**Groovy 脚本示例**:
```groovy
// DetailChart.groovy
class DetailChart extends Common {

    List<Chart> getDetailLineChart(Map<String, ChartData<TradeDetail>> chartDataMap) {
        Chart amountChart = new Chart()
            .setTitle(new Text("平均数量"))
            .setYLegend(new Text("平均数量"))

        chartDataMap.each { code, chartData ->
            LineChart lineChart = new LineChart().setText(code)

            chartData.elementList.each { detail ->
                lineChart.addValues(detail.amount.doubleValue())
            }

            amountChart.addElements(lineChart)
        }

        return [amountChart]
    }
}
```

### 5️⃣ Excel 导出

**功能**: 按查询条件导出 Excel 文件

**技术**: Spring MVC View + JXL 库

**实现**:

#### 自定义 Excel 视图
```java
// AbstractJExcelView.java（基类）
public abstract class AbstractJExcelView extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                          HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        // 设置响应头
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition",
            "attachment; filename=" + getFilename() + ".xls");

        // 创建工作簿
        WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());

        // 委托子类构建内容
        buildExcelDocument(model, workbook, request, response);

        // 写入输出流
        workbook.write();
        workbook.close();
    }

    protected abstract void buildExcelDocument(Map<String, Object> model,
                                              WritableWorkbook workbook,
                                              HttpServletRequest request,
                                              HttpServletResponse response) throws Exception;
}
```

#### 实现导出视图
```java
// TradeDetailExcelView.java
public class TradeDetailExcelView extends AbstractJExcelView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                     WritableWorkbook workbook,
                                     HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        WritableSheet sheet = workbook.createSheet("贸易明细", 0);

        // 设置表头
        sheet.addCell(new Label(0, 0, "年月"));
        sheet.addCell(new Label(1, 0, "产品代码"));
        sheet.addCell(new Label(2, 0, "产品名称"));
        sheet.addCell(new Label(3, 0, "企业性质"));
        sheet.addCell(new Label(4, 0, "数量"));
        sheet.addCell(new Label(5, 0, "金额"));

        // 填充数据
        List<TradeDetail> data = (List<TradeDetail>) model.get("data");
        for (int i = 0; i < data.size(); i++) {
            TradeDetail detail = data.get(i);
            sheet.addCell(new Label(0, i + 1, detail.getYearMonth()));
            sheet.addCell(new Label(1, i + 1, detail.getProductCode()));
            sheet.addCell(new Label(2, i + 1, detail.getProductName()));
            sheet.addCell(new Label(3, i + 1, detail.getCompanyType()));
            sheet.addCell(new Number(4, i + 1, detail.getAmount().doubleValue()));
            sheet.addCell(new Number(5, i + 1, detail.getAmountMoney().doubleValue()));
        }
    }

    @Override
    protected String getFilename() {
        return "trade_detail_" + new SimpleDateFormat("yyyyMMdd").format(new Date());
    }
}
```

#### 控制器
```java
// ExportDataController.java
@Controller
@RequestMapping("/export")
public class ExportDataController {

    @Autowired
    private TradeDetailService service;

    @GetMapping("/detail")
    public ModelAndView exportDetail(TradeDetail searchCondition) {
        // 查询数据
        List<TradeDetail> data = service.findByConditions(searchCondition);

        // 返回 Excel 视图
        return new ModelAndView(new TradeDetailExcelView(), "data", data);
    }
}
```

### 6️⃣ 系统配置

**功能**: JSON 配置文件管理

**配置项**:
- 文件上传路径
- 年月分隔符
- 缓存策略
- 业务规则参数

**实现**:
```java
// ConfigUtil.java
public class ConfigUtil {

    public static <T> T loadConfig(String path, Class<T> clazz) {
        try {
            String json = FileUtil.readFile(path);
            return new Gson().fromJson(json, clazz);
        } catch (Exception e) {
            logger.error("加载配置失败: " + path, e);
            return null;
        }
    }

    public static void saveConfig(String path, Object config) {
        String json = new GsonBuilder().setPrettyPrinting().create().toJson(config);
        FileUtil.writeFile(path, json);
    }
}

// DocBean.java
public class DocBean {
    public enum Config {
        upload_detailzip_dir("/data/upload"),
        yearmonth_split("-");

        private String value;

        Config(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }
}
```

---

## 代码分层

### 📦 Domain 领域层（实体类）

**职责**: 定义业务实体和数据模型

**设计模式**:
- **继承**: 抽象基类提取公共字段（`IdEntity` → `TradeDetail` → `ImpTradeDetail`）
- **注解**: JPA 注解（`@Entity`, `@Table`, `@Column`, `@MappedSuperclass`）

**关键类**:
- `IdEntity`: 主键基类，继承 Spring Data `AbstractPersistable<Long>`
- `TradeDetail`: 明细表抽象类，13 个业务字段
- `TradeSum`: 汇总表抽象类，9 个业务字段

### 💾 DAO 数据访问层

**职责**: 封装数据库操作

**技术方案**:

#### 方案一：Spring Data JPA（主）

**优势**:
- 自动实现 CRUD 方法
- 支持方法名查询（`findByProductCode`）
- 支持 `@Query` 注解（JPQL / SQL）
- 支持 Specification 动态查询
- 支持分页排序

**接口设计**:
```java
public interface ImpTradeDetailDao extends
        CrudRepository<ImpTradeDetail, Long>,            // CRUD
        JpaSpecificationExecutor<ImpTradeDetail>,        // 动态查询
        ImpTradeDetailDaoCustom {                        // 自定义方法

    // 方法名查询
    List<ImpTradeDetail> findByProductCodeAndYearMonth(String code, String ym);

    // JPQL 查询
    @Query("DELETE FROM ImpTradeDetail t WHERE t.year = ?1 AND t.month = ?2")
    @Modifying
    @Transactional
    void delRepeatImpTradeDetail(Integer year, Integer month);
}
```

**自定义实现**:
```java
// 接口
public interface ImpTradeDetailDaoCustom {
    List<ProductCount> getCustomStatistics(String productCode, String yearMonth);
}

// 实现类（命名规则：接口名 + Impl）
public class ImpTradeDetailDaoImpl extends BaseDao implements ImpTradeDetailDaoCustom {

    @Override
    public List<ProductCount> getCustomStatistics(String productCode, String yearMonth) {
        String sql = "SELECT product_code, SUM(amount) FROM t_import_detail " +
                     "WHERE product_code = ? AND year_month = ? GROUP BY product_code";

        return jdbcTemplate.query(sql, new ProductCountRowMapper(), productCode, yearMonth);
    }
}
```

#### 方案二：Spring JDBC（辅助）

**使用场景**:
- 复杂统计查询（多表 JOIN）
- 批量操作（性能优化）
- 动态 SQL 构建

**实现**:
```java
// BaseDao.java
public class BaseDao {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    protected <T> List<T> query(String sql, RowMapper<T> mapper, Object... args) {
        return jdbcTemplate.query(sql, mapper, args);
    }

    protected int[] batchUpdate(String sql, List<Object[]> batchArgs) {
        return jdbcTemplate.batchUpdate(sql, batchArgs);
    }
}
```

### 🔧 Service 业务逻辑层

**职责**:
- 实现业务逻辑
- 编排 DAO 调用
- 事务管理
- 异常处理

**实现示例**:
```java
@Service
@Transactional
public class TradeDetailServiceImpl implements TradeDetailService {

    @Autowired
    private ImpTradeDetailDao impDao;

    @Autowired
    private ExpTradeDetailDao expDao;

    @Autowired
    private TradeSumService sumService;

    @Override
    @Transactional(readOnly = true)
    public Page<ImpTradeDetail> findImpWithCriteria(ImpTradeDetail searchCondition,
                                                     CommonDto commonDto,
                                                     YearMonthDto yearMonthDto,
                                                     Pageable pageable) {
        // 构建查询条件
        Collection<PropertyFilter> filters = buildFilters(searchCondition, yearMonthDto);
        Specification<ImpTradeDetail> spec = DynamicSpecifications.byPropertyFilter(
            filters, ImpTradeDetail.class
        );

        // 执行查询
        return impDao.findAll(spec, pageable);
    }

    @Override
    public void importDetailData(String dataDir, YearMonthDto yearMonthDto) {
        // 1. 删除重复数据
        impDao.delRepeatImpTradeDetail(yearMonthDto.getYear(), yearMonthDto.getMonth());

        // 2. 解析 Excel 文件
        List<ImpTradeDetail> details = parseExcelFiles(dataDir);

        // 3. 批量保存
        impDao.save(details);

        // 4. 生成汇总数据
        sumService.generateSum(yearMonthDto);

        // 5. 记录日志
        logger.info("导入完成，共 {} 条数据", details.size());
    }
}
```

### 🌐 Controller 控制器层

**职责**:
- 处理 HTTP 请求
- 参数绑定和验证
- 调用 Service 层
- 返回视图或 JSON

**设计模式**:
- **基类**: `CommonController` 提取公共方法
- **RESTful**: URL 映射遵循 REST 风格
- **分页**: 统一分页参数处理

**实现示例**:
```java
@Controller
@RequestMapping("/manage")
public class TradeDetailController extends CommonController {

    @Autowired
    private TradeDetailService service;

    /**
     * 分页查询明细列表
     */
    @GetMapping("/listdetail/{pageNumber}")
    public String list(Model model,
                      @PathVariable Integer pageNumber,
                      CommonDto commonDto,
                      YearMonthDto yearMonthDto,
                      TradeDetail searchCondition) {
        // 设置默认排序
        if (isBlank(commonDto.getSort())) {
            commonDto.setSort("yearMonth:desc");
        }

        // 执行查询
        Pageable pageable = getPageRequest(commonDto);
        Page<?> page;
        if (yearMonthDto.getImpExpType() == 0) {
            page = service.findImpWithCriteria(
                new ImpTradeDetail(searchCondition), commonDto, yearMonthDto, pageable
            );
        } else {
            page = service.findExpWithCriteria(
                new ExpTradeDetail(searchCondition), commonDto, yearMonthDto, pageable
            );
        }

        // 添加分页信息到模型
        addPageInfo(model, page, "/manage/listdetail");
        model.addAttribute("tradeDetailList", page.getContent());
        model.addAttribute("searchCondition", searchCondition);

        return "manage/trade/listdetail";
    }

    /**
     * Ajax 获取产品列表（自动完成）
     */
    @GetMapping("/getProduct")
    @ResponseBody
    public List<String> getProductList() {
        List<Product> products = service.findAllProduct();
        return products.stream()
            .map(p -> p.getProductCode() + "->" + p.getProductName())
            .collect(Collectors.toList());
    }

    /**
     * 文件上传
     */
    @PostMapping("/uploadDetailFile")
    public String upload(@RequestParam("file") MultipartFile file,
                        YearMonthDto yearMonthDto,
                        RedirectAttributes redirectAttributes) {
        try {
            // 保存文件
            String path = saveUploadFile(file);

            // 异步导入
            service.importDetailData(path, yearMonthDto);

            redirectAttributes.addFlashAttribute("message", "导入成功");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "导入失败: " + e.getMessage());
        }

        return "redirect:/manage/listdetail/1";
    }
}
```

**公共基类**:
```java
public class CommonController {

    /**
     * 构建分页请求
     */
    protected Pageable getPageRequest(CommonDto dto) {
        String[] sortArray = dto.getSort().split(":");
        Sort.Direction direction = "desc".equals(sortArray[1]) ?
            Sort.Direction.DESC : Sort.Direction.ASC;

        return new PageRequest(
            dto.getPageNumber() - 1,  // Spring Data 页码从 0 开始
            dto.getPageSize(),
            new Sort(direction, sortArray[0])
        );
    }

    /**
     * 添加分页信息到模型
     */
    protected Model addPageInfo(Model model, Page<?> page, String baseUrl) {
        model.addAttribute("page", page);
        model.addAttribute("baseUrl", baseUrl);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());
        return model;
    }
}
```

---

## 配置说明

### Spring 配置文件

#### 1. applicationContext-root.xml（根容器）

```xml
<beans>
    <!-- 组件扫描（排除 Controller） -->
    <context:component-scan base-package="com.oilchem">
        <context:exclude-filter type="annotation"
            expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!-- 启用 AspectJ 自动代理 -->
    <aop:aspectj-autoproxy expose-proxy="true"/>

    <!-- 导入其他配置 -->
    <import resource="applicationContext-dataSource.xml"/>
    <import resource="applicationContext-jpa.xml"/>
    <import resource="applicationContext-jdbc.xml"/>
</beans>
```

#### 2. applicationContext-dataSource.xml（数据源）

```xml
<beans>
    <!-- BoneCP 数据源 -->
    <bean id="dataSource" class="com.jolbox.bonecp.BoneCPDataSource" destroy-method="close">
        <property name="driverClass" value="${db.driverClass}"/>
        <property name="jdbcUrl" value="${db.jdbcUrl}"/>
        <property name="username" value="${db.username}"/>
        <property name="password" value="${db.password}"/>

        <!-- 连接池配置 -->
        <property name="partitionCount" value="3"/>
        <property name="maxConnectionsPerPartition" value="10"/>
        <property name="minConnectionsPerPartition" value="2"/>
        <property name="acquireIncrement" value="2"/>

        <!-- 连接测试 -->
        <property name="idleConnectionTestPeriodInMinutes" value="30"/>
        <property name="connectionTestStatement" value="SELECT 1"/>
    </bean>

    <!-- 属性占位符 -->
    <context:property-placeholder location="classpath:dataSource.properties"/>
</beans>
```

#### 3. applicationContext-jpa.xml（JPA）

```xml
<beans>
    <!-- EntityManagerFactory -->
    <bean id="entityManagerFactory"
          class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="persistenceUnitName" value="jpa.trade"/>
        <property name="jpaVendorAdapter">
            <bean class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter">
                <property name="showSql" value="${hibernate.show_sql}"/>
                <property name="generateDdl" value="true"/>
                <property name="databasePlatform" value="${hibernate.dialect}"/>
            </bean>
        </property>
    </bean>

    <!-- Spring Data JPA -->
    <jpa:repositories base-package="com.oilchem.trade.dao"
                     entity-manager-factory-ref="entityManagerFactory"
                     transaction-manager-ref="transactionManager"/>

    <!-- 事务管理器 -->
    <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
        <property name="entityManagerFactory" ref="entityManagerFactory"/>
    </bean>

    <!-- 启用注解事务 -->
    <tx:annotation-driven transaction-manager="transactionManager"/>
</beans>
```

#### 4. mvc-servlet.xml（Spring MVC）

```xml
<beans>
    <!-- 注解驱动 -->
    <mvc:annotation-driven/>

    <!-- 组件扫描（仅扫描 Controller） -->
    <context:component-scan base-package="com.oilchem.trade.view.controller"/>

    <!-- 视图解析器 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/"/>
        <property name="suffix" value=".jsp"/>
    </bean>

    <!-- 静态资源映射 -->
    <mvc:resources mapping="/resources/**" location="/resources/"/>

    <!-- 文件上传 -->
    <bean id="multipartResolver"
          class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
        <property name="maxUploadSize" value="104857600"/> <!-- 100MB -->
        <property name="defaultEncoding" value="UTF-8"/>
    </bean>
</beans>
```

### JPA 配置

#### persistence.xml

```xml
<persistence-unit name="jpa.trade">
    <provider>org.hibernate.ejb.HibernatePersistence</provider>

    <!-- 实体类列表（27 个） -->
    <class>com.oilchem.trade.domain.detail.ImpTradeDetail</class>
    <class>com.oilchem.trade.domain.detail.ExpTradeDetail</class>
    <class>com.oilchem.trade.domain.sum.ImpTradeSum</class>
    <class>com.oilchem.trade.domain.sum.ExpTradeSum</class>
    <!-- ... 其他实体类 ... -->

    <properties>
        <!-- Hibernate 配置 -->
        <property name="hibernate.show_sql" value="${hibernate.show_sql}"/>
        <property name="hibernate.format_sql" value="true"/>
        <property name="hibernate.hbm2ddl.auto" value="update"/>
        <property name="hibernate.dialect" value="${hibernate.dialect}"/>
    </properties>
</persistence-unit>
```

### Ehcache 配置

#### ehcache.xml

```xml
<ehcache>
    <diskStore path="java.io.tmpdir/ehcache"/>

    <!-- 默认缓存策略 -->
    <defaultCache
        maxElementsInMemory="10000"
        eternal="false"
        timeToIdleSeconds="120"
        timeToLiveSeconds="120"
        overflowToDisk="true"
        maxElementsOnDisk="10000000"
        diskPersistent="false"
        diskExpiryThreadIntervalSeconds="120"
        memoryStoreEvictionPolicy="LRU"/>

    <!-- 产品缓存 -->
    <cache name="productCache"
           maxElementsInMemory="1000"
           eternal="true"
           overflowToDisk="false"/>

    <!-- 查询结果缓存 -->
    <cache name="queryCache"
           maxElementsInMemory="500"
           eternal="false"
           timeToLiveSeconds="3600"
           overflowToDisk="true"/>
</ehcache>
```

### Logback 配置

#### logback.xml

```xml
<configuration>
    <!-- 控制台输出 -->
    <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- 文件输出 -->
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/trade-globle.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>logs/trade-globle.%d{yyyy-MM-dd}.log</fileNamePattern>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <!-- 日志级别 -->
    <logger name="com.oilchem" level="DEBUG"/>
    <logger name="org.springframework" level="INFO"/>
    <logger name="org.hibernate" level="WARN"/>

    <root level="INFO">
        <appender-ref ref="STDOUT"/>
        <appender-ref ref="FILE"/>
    </root>
</configuration>
```

### Maven 环境配置

#### mavenFilters.properties

```properties
# 开发环境
dev.db.driverClass=com.mysql.jdbc.Driver
dev.db.jdbcUrl=jdbc:mysql://localhost:3306/trade_db?useUnicode=true&characterEncoding=utf8
dev.db.username=root
dev.db.password=yourpassword
dev.db.dialect=org.hibernate.dialect.MySQL5Dialect

# 测试环境 1
test.db.driverClass=com.microsoft.sqlserver.jdbc.SQLServerDriver
test.db.jdbcUrl=jdbc:sqlserver://192.168.1.21:2433;DatabaseName=testdb
test.db.username=testuser
test.db.password=testpass
test.db.dialect=org.hibernate.dialect.SQLServerDialect

# Hibernate 配置
hibernate.show_sql=true
```

**激活 Profile**:
```bash
mvn clean package -Pdev    # 使用开发环境配置
mvn clean package -Ptest   # 使用测试环境配置
```

---

## 构建与运行

### 环境要求

- **JDK**: 1.6+ （建议 1.8+）
- **Maven**: 3.0+
- **数据库**: MySQL 5.x / SQL Server 2008+
- **应用服务器**: Tomcat 6.x / Jetty 8.x

### 构建项目

```bash
# 进入模块目录
cd trade-globle

# 编译打包（使用开发环境配置）
mvn clean package -Pdev

# 跳过测试
mvn clean package -Pdev -DskipTests

# 生成的 WAR 包
ls -lh target/trade-globle.war
```

### 运行方式

#### 方式一：Jetty 插件（开发推荐）

```bash
mvn jetty:run -Pdev

# 访问地址
http://localhost:9090/trade-globle
```

**Jetty 配置**（pom.xml）:
```xml
<plugin>
    <groupId>org.mortbay.jetty</groupId>
    <artifactId>jetty-maven-plugin</artifactId>
    <configuration>
        <scanIntervalSeconds>10</scanIntervalSeconds> <!-- 热部署 -->
        <webApp>
            <contextPath>/trade-globle</contextPath>
        </webApp>
        <connectors>
            <connector implementation="org.eclipse.jetty.server.nio.SelectChannelConnector">
                <port>9090</port>
                <maxIdleTime>60000</maxIdleTime>
            </connector>
        </connectors>
    </configuration>
</plugin>
```

#### 方式二：Tomcat 部署（生产推荐）

```bash
# 1. 复制 WAR 包到 Tomcat
cp target/trade-globle.war $TOMCAT_HOME/webapps/

# 2. 启动 Tomcat
$TOMCAT_HOME/bin/startup.sh

# 3. 查看日志
tail -f $TOMCAT_HOME/logs/catalina.out

# 访问地址
http://localhost:8080/trade-globle
```

#### 方式三：Cargo 插件（远程部署）

```bash
# 部署到远程 Tomcat
mvn cargo:deploy

# Cargo 配置（pom.xml）
<plugin>
    <groupId>org.codehaus.cargo</groupId>
    <artifactId>cargo-maven2-plugin</artifactId>
    <configuration>
        <container>
            <containerId>tomcat6x</containerId>
            <type>remote</type>
        </container>
        <configuration>
            <type>runtime</type>
            <properties>
                <cargo.hostname>127.0.0.1</cargo.hostname>
                <cargo.servlet.port>8080</cargo.servlet.port>
                <cargo.remote.username>tomcat</cargo.remote.username>
                <cargo.remote.password>tomcat</cargo.remote.password>
            </properties>
        </configuration>
    </configuration>
</plugin>
```

### 数据库初始化

#### MySQL

```bash
# 创建数据库
mysql -u root -p -e "CREATE DATABASE trade_db CHARACTER SET utf8 COLLATE utf8_general_ci;"

# 执行初始化脚本（如果有）
mysql -u root -p trade_db < src/main/sql/init.sql

# Hibernate 会自动创建表（hbm2ddl.auto=update）
```

#### SQL Server

```bash
# 使用 SQL Maven 插件执行脚本
mvn sql:execute -Ptest

# 或手动执行
sqlcmd -S localhost -U sa -P password -d trade_db -i src/main/sql/sql-server.sql
```

---

## 开发指南

### 添加新实体

**步骤**:

1. **创建实体类**（`domain` 包）

```java
@Entity
@Table(name = "t_new_entity")
public class NewEntity extends IdEntity {

    @Column(name = "field1")
    private String field1;

    @Column(name = "field2")
    private Integer field2;

    // Getters and Setters
}
```

2. **注册到 persistence.xml**

```xml
<persistence-unit name="jpa.trade">
    <class>com.oilchem.trade.domain.NewEntity</class>
</persistence-unit>
```

3. **创建 Repository**（`dao` 包）

```java
public interface NewEntityDao extends CrudRepository<NewEntity, Long>,
                                      JpaSpecificationExecutor<NewEntity> {
    List<NewEntity> findByField1(String field1);
}
```

4. **创建 Service**

```java
// 接口
public interface NewEntityService {
    List<NewEntity> findAll();
    NewEntity save(NewEntity entity);
}

// 实现
@Service
@Transactional
public class NewEntityServiceImpl implements NewEntityService {

    @Autowired
    private NewEntityDao dao;

    @Override
    @Transactional(readOnly = true)
    public List<NewEntity> findAll() {
        return Lists.newArrayList(dao.findAll());
    }

    @Override
    public NewEntity save(NewEntity entity) {
        return dao.save(entity);
    }
}
```

5. **创建 Controller**

```java
@Controller
@RequestMapping("/newentity")
public class NewEntityController {

    @Autowired
    private NewEntityService service;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("list", service.findAll());
        return "newentity/list";
    }
}
```

### 使用动态查询

```java
// 1. 构建查询条件
List<PropertyFilter> filters = new ArrayList<>();
filters.add(new PropertyFilter("productCode", "001", PropertyFilter.Type.EQ));
filters.add(new PropertyFilter("yearMonth", "2012-11", PropertyFilter.Type.EQ));
filters.add(new PropertyFilter("companyType", "国企", PropertyFilter.Type.LIKE));

// 2. 创建 Specification
Specification<ImpTradeDetail> spec = DynamicSpecifications.byPropertyFilter(
    filters, ImpTradeDetail.class
);

// 3. 执行查询（带分页）
Pageable pageable = new PageRequest(0, 20, Sort.Direction.DESC, "yearMonth");
Page<ImpTradeDetail> page = repository.findAll(spec, pageable);
```

### 添加缓存

```java
@Service
public class ProductService {

    // 方法级缓存
    @Cacheable(value = "productCache", key = "#code")
    public Product findByCode(String code) {
        return dao.findByCode(code);
    }

    // 缓存清除
    @CacheEvict(value = "productCache", key = "#product.code")
    public Product update(Product product) {
        return dao.save(product);
    }

    // 清空所有缓存
    @CacheEvict(value = "productCache", allEntries = true)
    public void clearCache() {
    }
}
```

**或使用工具类**:
```java
// 存入缓存
EHCacheUtil.put("productCache", "001", product);

// 读取缓存
Product product = (Product) EHCacheUtil.get("productCache", "001");

// 删除缓存
EHCacheUtil.remove("productCache", "001");
```

### 添加 AOP 监控

```java
@Aspect
@Component
public class PerformanceMonitor {

    @Around("execution(* com.oilchem.trade.service..*(..))")
    public Object monitor(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = pjp.proceed();

        long time = System.currentTimeMillis() - start;
        logger.info("方法 {} 执行耗时: {}ms", pjp.getSignature(), time);

        return result;
    }
}
```

### Excel 导出最佳实践

**大数据量导出（分批）**:
```java
@GetMapping("/exportLarge")
public void exportLarge(SearchCondition condition, HttpServletResponse response) throws Exception {
    response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-Disposition", "attachment; filename=export.xls");

    WritableWorkbook workbook = Workbook.createWorkbook(response.getOutputStream());
    WritableSheet sheet = workbook.createSheet("数据", 0);

    // 写表头
    writeHeaders(sheet);

    // 分批查询
    int pageSize = 1000;
    int rowIndex = 1;
    for (int page = 0; ; page++) {
        Pageable pageable = new PageRequest(page, pageSize);
        Page<TradeDetail> pageData = service.findByConditions(condition, pageable);

        if (!pageData.hasContent()) break;

        // 写数据
        for (TradeDetail detail : pageData.getContent()) {
            writeRow(sheet, rowIndex++, detail);
        }

        if (!pageData.hasNext()) break;
    }

    workbook.write();
    workbook.close();
}
```

### 测试

#### 单元测试
```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-root.xml")
@Transactional
public class TradeDetailServiceTest {

    @Autowired
    private TradeDetailService service;

    @Test
    public void testFindByConditions() {
        ImpTradeDetail condition = new ImpTradeDetail();
        condition.setProductCode("001");

        List<ImpTradeDetail> results = service.findByConditions(condition);

        assertNotNull(results);
        assertTrue(results.size() > 0);
    }
}
```

#### 控制器测试
```java
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {
    "classpath:spring/applicationContext-root.xml",
    "classpath:spring/appServlet/mvc-servlet.xml"
})
public class TradeDetailControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testList() throws Exception {
        mockMvc.perform(get("/manage/listdetail/1")
                .param("impExpType", "0"))
            .andExpect(status().isOk())
            .andExpect(view().name("manage/trade/listdetail"))
            .andExpect(model().attributeExists("tradeDetailList"));
    }
}
```

---

## 效果截图

### 首页
![首页](https://raw.github.com/luowei/trade-impexp/master/trade-globle/doc/img/impexp-page.png)

### 明细列表
![明细列表](https://raw.github.com/luowei/trade-impexp/master/trade-globle/doc/img/impexp-detail.png)

### 统计汇总
![统计汇总](https://raw.github.com/luowei/trade-impexp/master/trade-globle/doc/img/impexp-detailsum.png)

### 趋势图表
![趋势图表](https://raw.github.com/luowei/trade-impexp/master/trade-globle/doc/img/impexp-detailsum-curve.png)

### Excel 导出
![Excel 导出](https://raw.github.com/luowei/trade-impexp/master/trade-globle/doc/img/impexp-detail-export.png)

### 系统配置
![系统配置](https://raw.github.com/luowei/trade-impexp/master/trade-globle/doc/img/impexp-config.png)

---

## 常见问题

### 1. 启动报错：No qualifying bean of type...

**原因**: Spring 组件扫描配置问题

**解决**:
- 检查 `@Service`、`@Repository`、`@Controller` 注解是否正确
- 检查 `component-scan` 包路径是否正确
- 确保根容器排除了 `@Controller`，MVC 容器仅扫描 `@Controller`

### 2. 数据库连接失败

**排查**:
```bash
# 测试连接
mysql -h localhost -u root -p trade_db

# 检查配置
cat src/main/filter/mavenFilters.properties

# 验证 Profile
mvn help:active-profiles
```

### 3. 中文乱码

**解决**:
- 确保 `web.xml` 中配置了 `CharacterEncodingFilter`
- 数据库编码设置为 UTF-8
- JSP 页面添加 `<%@ page pageEncoding="UTF-8" %>`

### 4. 缓存未生效

**检查**:
- `ehcache.xml` 中是否定义了缓存
- Service 方法是否添加了 `@Cacheable` 注解
- 是否启用了注解缓存：`<cache:annotation-driven/>`

---

## 性能优化建议

1. **数据库索引**: 为常用查询字段添加索引（`year_month`, `product_code` 等）
2. **批量操作**: 使用 JDBC 批处理代替单条插入
3. **二级缓存**: 缓存字典数据和热点查询
4. **分页查询**: 避免一次性加载大量数据
5. **异步处理**: 数据导入使用异步任务
6. **连接池调优**: 根据并发量调整 BoneCP 参数

---

## 技术亮点

✨ **动态查询**: 基于 JPA Criteria API 的通用动态查询框架
✨ **双持久化**: JPA + JDBC 灵活结合，取长补短
✨ **Groovy 集成**: 动态脚本生成图表配置
✨ **自定义 View**: Spring MVC 视图技术实现 Excel 导出
✨ **工具类封装**: 3000+ 行工具代码，提升开发效率
✨ **分层架构**: 清晰的分层设计，易于维护扩展

---

## 参考资料

- [Spring Framework 3.1.x 文档](https://docs.spring.io/spring-framework/docs/3.1.x/spring-framework-reference/html/)
- [Spring Data JPA 1.1.x 文档](https://docs.spring.io/spring-data/jpa/docs/1.1.x/reference/html/)
- [Hibernate 4.1 文档](https://docs.jboss.org/hibernate/orm/4.1/manual/en-US/html/)
- [Highcharts API](https://api.highcharts.com/highcharts/)
- [JXL 文档](http://jexcelapi.sourceforge.net/)

---

## 作者

**luowei** - [luowei010101@gmail.com](mailto:luowei010101@gmail.com)

---

**返回**: [项目主 README](../README.md)
