# trade-persistent

trade-impexp 项目的持久层接口模块。

## 模块概述

**trade-persistent** 定义了项目的数据访问层接口（DAO/Repository），遵循接口与实现分离的设计原则。

## 当前状态

🚧 **模块待完善** - 当前仅包含基础 POM 配置，具体接口定义尚未实现。

## 设计目标

### 职责
- 定义数据访问接口
- 声明 CRUD 操作方法
- 定义自定义查询接口
- 提供数据访问层的契约

### 技术选型（计划）
- **接口规范**: Spring Data JPA Repository
- **查询语言**: JPQL / Native SQL
- **分页排序**: Spring Data Pageable

## 规划的目录结构

```
trade-persistent/
├── pom.xml
└── src/main/java/com/oilchem/trade/persistent/
    ├── TradeDetailRepository.java      # 明细数据接口
    ├── TradeSumRepository.java         # 汇总数据接口
    ├── CountRepository.java            # 统计数据接口
    └── custom/                         # 自定义查询接口
        ├── TradeDetailRepositoryCustom.java
        └── ...
```

## 临时方案

目前项目的持久层实现集中在 [trade-globle](../trade-globle) 模块的 `dao` 包中：

- `com.oilchem.trade.dao.[Entity]Repository` - Spring Data JPA 接口
- `com.oilchem.trade.dao.custom` - 自定义 Repository 实现
- `com.oilchem.trade.dao.condition` - 查询条件构建器
- `com.oilchem.trade.dao.others` - JDBC DAO

## 开发计划

1. 提取 trade-globle 中的 Repository 接口到本模块
2. 定义统一的数据访问规范
3. 抽象公共查询接口
4. 编写接口文档和使用示例

## 依赖关系

- 被依赖: `trade-persistent-impl`（实现层）
- 被依赖: `trade-service`（业务层）
- 依赖: 实体类模块（待规划）

## 相关模块

- [trade-persistent-impl](../trade-persistent-impl) - 持久层实现
- [trade-globle](../trade-globle) - 当前持久层临时实现位置

---

更多信息请参考 [项目主 README](../README.md)
