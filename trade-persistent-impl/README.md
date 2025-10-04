# trade-persistent-impl

trade-impexp 项目的持久层实现模块。

## 模块概述

**trade-persistent-impl** 提供 [trade-persistent](../trade-persistent) 模块中定义的数据访问接口的具体实现。

## 当前状态

🚧 **模块待完善** - 当前仅包含基础 POM 配置，具体实现代码尚未迁移。

## 设计目标

### 职责
- 实现持久层接口
- 提供自定义查询实现
- 封装复杂的数据库操作
- 优化查询性能

### 技术选型（计划）
- **ORM 框架**: Hibernate 4.1.4 + JPA 2.0
- **辅助方案**: Spring JdbcTemplate（复杂查询）
- **数据库**: MySQL / SQL Server
- **连接池**: BoneCP 0.7.1

## 规划的目录结构

```
trade-persistent-impl/
├── pom.xml
└── src/main/java/com/oilchem/trade/persistent/impl/
    ├── TradeDetailRepositoryImpl.java   # Repository 实现类
    ├── TradeSumRepositoryImpl.java
    ├── CountRepositoryImpl.java
    ├── jdbc/                            # JDBC 实现
    │   ├── TradeDetailJdbcDao.java
    │   └── ...
    └── specification/                   # JPA Specification
        ├── TradeDetailSpecifications.java
        └── ...
```

## 临时方案

目前项目的持久层实现集中在 [trade-globle](../trade-globle) 模块中：

- `com.oilchem.trade.dao.custom.impl` - 自定义 Repository 实现
- `com.oilchem.trade.dao.others` - JDBC DAO 实现
- `com.oilchem.trade.util.DynamicSpecifications` - 动态查询规范

## 开发计划

1. 从 trade-globle 迁移 Repository 实现类
2. 从 trade-globle 迁移 JDBC DAO 实现
3. 优化查询性能（索引、批处理）
4. 添加数据库事务管理
5. 编写持久层单元测试

## 依赖关系

- 依赖: `trade-persistent`（接口定义）
- 被依赖: `trade-service-impl`（业务实现）
- 被依赖: `trade-globle`（Web 模块）

## 相关模块

- [trade-persistent](../trade-persistent) - 持久层接口
- [trade-globle](../trade-globle) - 当前持久层实现位置

---

更多信息请参考 [项目主 README](../README.md)
