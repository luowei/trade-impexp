# trade-impexp

基于 Maven 多模块架构的进出口贸易数据管理系统，提供数据导入、统计分析、图表展示和数据导出等功能。

## 项目简介

**trade-impexp** 是一个企业级 Java Web 应用项目，采用 Maven 多模块设计，用于管理和分析进出口贸易数据。项目基于 Spring 技术栈，整合了现代 Web 开发的最佳实践。

## 技术栈

### 核心框架
- **Web 框架**: Spring MVC 3.1.2（RESTful 风格）
- **持久化**: Spring Data JPA 1.1.0 + Hibernate 4.1.4
- **数据库**: 支持 MySQL / SQL Server
- **连接池**: BoneCP 0.7.1（性能优于 C3P0）

### 主要技术
- **日志**: Logback 1.0.7 + SLF4J 1.6.4
- **缓存**: Ehcache 2.5.2（带自定义封装）
- **AOP**: AspectJ 1.6.12
- **图表**: Highcharts + Open Flash Chart (ofc4j)
- **脚本**: Groovy（用于图表数据处理）
- **工具库**: Apache Commons、Joda-Time、Google Gson

### 构建工具
- Maven 3.x
- 支持 Jetty、Cargo 等插件

## 项目结构

```
trade-impexp/
├── pom.xml                    # 父 POM，统一依赖管理
├── lib/                       # 第三方 JAR 包
│   ├── java-unrar-0.3.jar
│   ├── ofc4j-1.0-alpha5.jar
│   └── 测试数据.zip
├── trade-globle/              # ⭐ 核心业务模块（WAR）
├── trade-persistent/          # 持久层接口（待完善）
├── trade-persistent-impl/     # 持久层实现（待完善）
├── trade-service/             # 业务层接口（待完善）
├── trade-service-impl/        # 业务层实现（待完善）
├── trade-util/                # 工具类模块（待完善）
└── trade-view/                # 视图模块（待完善）
```

## 核心模块：trade-globle

当前主要功能集中在 `trade-globle` 模块（112 个 Java 文件 + 4 个 Groovy 文件）。

### 功能特性

#### 1. 数据管理
- **数据导入**: 支持 Excel/CSV 批量导入贸易数据
- **数据查询**: 动态条件查询，支持多维度筛选
- **数据导出**: 基于 Spring MVC 视图技术的 Excel 导出

#### 2. 统计分析
- 进出口数据统计
- 按公司类型、贸易类型、时间等维度汇总
- 自定义报表生成

#### 3. 可视化
- **图表库**: Highcharts（主）、Open Flash Chart（备选）
- **图表类型**: 柱状图、折线图、饼图等
- **数据处理**: Groovy 脚本动态生成图表数据

#### 4. 系统功能
- 配置管理（JSON 格式存储）
- 日志记录与监控
- 缓存优化（二级缓存）

### 代码结构

```
com.oilchem.trade/
├── domain/              # 实体类（25 个）
│   ├── abstrac/         # 抽象基类
│   ├── detail/          # 明细表实体
│   ├── sum/             # 汇总表实体
│   └── count/           # 统计实体
├── dao/                 # 数据访问层
│   ├── custom/          # 自定义 Repository
│   └── condition/       # 条件构建器
├── service/             # 业务逻辑层
├── view/                # 视图控制器
│   └── controller/      # 10 个控制器
├── util/                # 工具类
└── bean/                # DTO 对象
```

### 主要控制器

| 控制器 | 功能 |
|--------|------|
| TradeDetailController | 贸易明细管理 |
| TradeSumController | 汇总数据管理 |
| ExportDataController | 数据导出 |
| ChartController | 图表展示 |
| HighChartController | Highcharts 图表 |
| DetailCountController | 明细统计 |
| FilterController | 数据筛选 |
| ConfigController | 系统配置 |

### 工具类封装

- **ZipUtil**: ZIP/RAR 文件解压
- **FileUtil**: 文件操作
- **EHCacheUtil**: 缓存管理
- **ConfigUtil**: 配置文件读取
- **JdbcUtil**: JDBC 工具
- **DynamicSpecifications**: 动态查询规范
- **QueryUtils**: 查询工具
- **CommonUtil**: 通用工具

## 快速开始

### 环境要求
- JDK 1.6+
- Maven 3.x
- MySQL 5.x / SQL Server 2008+
- Tomcat 6.x / Jetty 8.x

### 构建项目

```bash
# 克隆项目
git clone https://github.com/luowei/trade-impexp.git
cd trade-impexp

# 编译打包
mvn clean install

# 仅构建 trade-globle 模块
mvn -pl trade-globle clean package
```

### 运行方式

#### 使用 Jetty（开发环境）
```bash
cd trade-globle
mvn jetty:run
# 访问 http://localhost:9090/trade-globle
```

#### 使用 Tomcat（生产环境）
```bash
# 部署 trade-globle/target/trade-globle.war 到 Tomcat
# 或使用 Cargo 插件远程部署
mvn cargo:deploy
```

### 数据库配置

编辑 `trade-globle/src/main/filter/mavenFilters.properties`：

```properties
# 开发环境
dev.db.driverClass=com.mysql.jdbc.Driver
dev.db.jdbcUrl=jdbc:mysql://localhost:3306/trade_db
dev.db.username=root
dev.db.password=yourpassword
dev.db.dialect=org.hibernate.dialect.MySQL5Dialect
```

激活配置文件：
```bash
mvn clean install -Pdev  # 开发环境
mvn clean install -Ptest # 测试环境
```

## 开发指南

### Maven Profile
- **dev**: 开发环境
- **test**: 测试环境 1
- **test2**: 测试环境 2

### 部署到 Nexus
```bash
mvn deploy
# 自动发布到 http://localhost:8081/nexus/
```

### Maven 常用命令

```bash
# 指定模块构建
mvn -pl trade-globle,trade-util clean install

# 跳过测试
mvn clean install -DskipTests

# 运行 SQL 脚本
mvn sql:execute

# Groovy 编译
mvn gmaven:compile
```

## 视频教程

1. [项目配置及概览](http://www.tudou.com/programs/view/6HqDeYy7guE/)
2. [代码分析及反射、AOP 应用](http://www.tudou.com/programs/view/AHZeS687Jrk/)
3. [项目成品与效果预览](http://www.tudou.com/programs/view/JPTEEJSELBI/)

## 子模块说明

| 模块 | 说明 | 状态 |
|------|------|------|
| [trade-globle](./trade-globle) | 核心业务模块（WAR） | ✅ 已完成 |
| [trade-persistent](./trade-persistent) | 持久层接口 | 🚧 待完善 |
| [trade-persistent-impl](./trade-persistent-impl) | 持久层实现 | 🚧 待完善 |
| [trade-service](./trade-service) | 业务层接口 | 🚧 待完善 |
| [trade-service-impl](./trade-service-impl) | 业务层实现 | 🚧 待完善 |
| [trade-util](./trade-util) | 工具类模块 | 🚧 待完善 |
| [trade-view](./trade-view) | 视图模块 | 🚧 待完善 |

## 开发建议

1. **测试驱动**: 先写测试再写实现，提升代码质量
2. **对象传参**: 优先使用对象传递参数，避免基本类型
3. **代码规范**: 遵循阿里巴巴 Java 开发手册
4. **日志记录**: 合理使用日志级别，避免过度打印

## 参考项目

- [SpringSide 4](https://github.com/springside/springside4)

## 许可证

本项目采用开源协议，详情请查看 LICENSE 文件。

---

**注意**: 本项目部分模块仍在开发中，欢迎提交 Issue 和 Pull Request！
