# trade-service

trade-impexp 项目的业务层接口模块。

## 模块概述

**trade-service** 定义了项目的业务逻辑层接口，遵循接口与实现分离的设计原则。

## 当前状态

🚧 **模块待完善** - 当前仅包含基础 POM 配置，具体接口定义尚未实现。

## 设计目标

### 职责
- 定义业务逻辑接口
- 声明业务操作方法
- 封装业务规则
- 提供业务层的契约

### 设计原则
- **单一职责**: 每个服务接口专注于一个业务领域
- **高内聚低耦合**: 减少服务间依赖
- **面向接口编程**: 便于测试和替换实现
- **事务边界**: 明确事务管理范围

## 规划的目录结构

```
trade-service/
├── pom.xml
└── src/main/java/com/oilchem/trade/service/
    ├── TradeDetailService.java         # 贸易明细业务接口
    ├── TradeSumService.java            # 汇总数据业务接口
    ├── TradeImportService.java         # 数据导入业务接口
    ├── TradeExportService.java         # 数据导出业务接口
    ├── TradeStatisticsService.java     # 统计分析业务接口
    ├── ChartDataService.java           # 图表数据业务接口
    └── ConfigService.java              # 配置管理业务接口
```

## 临时方案

目前项目的业务层实现集中在 [trade-globle](../trade-globle) 模块的 `service` 包中：

- `com.oilchem.trade.service.[Business]Service` - 业务接口
- `com.oilchem.trade.service.impl.[Business]ServiceImpl` - 业务实现类

## 典型业务接口示例

```java
public interface TradeDetailService {

    /**
     * 分页查询贸易明细
     */
    Page<TradeDetail> findByConditions(SearchCondition condition, Pageable pageable);

    /**
     * 保存或更新明细数据
     */
    TradeDetail save(TradeDetail detail);

    /**
     * 批量导入明细数据
     */
    ImportResult batchImport(List<TradeDetail> details);

    /**
     * 删除明细数据
     */
    void delete(Long id);

    /**
     * 按条件统计数量
     */
    long countByConditions(SearchCondition condition);
}
```

## 开发计划

1. 提取 trade-globle 中的 Service 接口到本模块
2. 定义统一的业务操作规范
3. 抽象公共业务接口
4. 定义 DTO 对象（数据传输对象）
5. 编写接口文档和使用示例

## 依赖关系

- 依赖: `trade-persistent`（数据访问层）
- 被依赖: `trade-service-impl`（业务实现）
- 被依赖: `trade-globle`（Web 控制器）

## 相关模块

- [trade-service-impl](../trade-service-impl) - 业务层实现
- [trade-globle](../trade-globle) - 当前业务层临时实现位置

---

更多信息请参考 [项目主 README](../README.md)
