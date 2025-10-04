# trade-service-impl

trade-impexp 项目的业务层实现模块。

## 模块概述

**trade-service-impl** 提供 [trade-service](../trade-service) 模块中定义的业务逻辑接口的具体实现。

## 当前状态

🚧 **模块待完善** - 当前仅包含基础 POM 配置，具体实现代码尚未迁移。

## 设计目标

### 职责
- 实现业务逻辑接口
- 编排持久层调用
- 处理业务规则和验证
- 管理事务边界
- 处理异常和日志

### 技术选型（计划）
- **依赖注入**: Spring Context 3.1.2
- **事务管理**: Spring Transaction + JPA
- **AOP**: AspectJ（性能监控、日志）
- **缓存**: Ehcache（业务数据缓存）

## 规划的目录结构

```
trade-service-impl/
├── pom.xml
└── src/
    ├── main/java/com/oilchem/trade/service/impl/
    │   ├── TradeDetailServiceImpl.java     # 明细业务实现
    │   ├── TradeSumServiceImpl.java        # 汇总业务实现
    │   ├── TradeImportServiceImpl.java     # 导入业务实现
    │   ├── TradeExportServiceImpl.java     # 导出业务实现
    │   ├── TradeStatisticsServiceImpl.java # 统计业务实现
    │   ├── ChartDataServiceImpl.java       # 图表业务实现
    │   └── ConfigServiceImpl.java          # 配置业务实现
    ├── main/resources/
    │   └── spring/
    │       └── applicationContext-service.xml  # 业务层 Spring 配置
    └── test/java/                          # 业务层单元测试
```

## 临时方案

目前项目的业务层实现集中在 [trade-globle](../trade-globle) 模块中：

- `com.oilchem.trade.service.impl` - 业务实现类
- 事务管理和 AOP 配置在 trade-globle 的 Spring 配置中

## 典型实现示例

```java
@Service
@Transactional
public class TradeDetailServiceImpl implements TradeDetailService {

    @Autowired
    private TradeDetailRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<TradeDetail> findByConditions(SearchCondition condition, Pageable pageable) {
        Specification<TradeDetail> spec = buildSpecification(condition);
        return repository.findAll(spec, pageable);
    }

    @Override
    public TradeDetail save(TradeDetail detail) {
        validate(detail);  // 业务验证
        return repository.save(detail);
    }

    @Override
    public ImportResult batchImport(List<TradeDetail> details) {
        // 分批处理、异常处理、日志记录
        // ...
    }

    @Override
    @Cacheable(value = "detailCount", key = "#condition")
    public long countByConditions(SearchCondition condition) {
        Specification<TradeDetail> spec = buildSpecification(condition);
        return repository.count(spec);
    }
}
```

## 核心业务

### 1. 数据导入业务
- Excel/CSV 文件解析
- 数据验证（必填字段、格式、业务规则）
- 批量保存（分批处理大数据量）
- 异常处理和错误收集
- 导入结果统计

### 2. 统计分析业务
- 多维度数据聚合
- 统计指标计算
- 趋势分析
- 数据缓存优化

### 3. 数据导出业务
- 按条件查询数据
- 数据格式转换
- Excel 文件生成
- 大数据量分页导出

### 4. 图表数据业务
- 查询统计数据
- 数据格式化（JSON）
- 图表配置生成
- Groovy 脚本调用

## 开发计划

1. 从 trade-globle 迁移 Service 实现类
2. 优化业务逻辑（性能、可读性）
3. 添加业务验证逻辑
4. 配置事务管理
5. 添加缓存策略
6. 编写单元测试（使用 Mock）
7. 添加业务日志和监控

## 依赖关系

- 依赖: `trade-service`（业务接口）
- 依赖: `trade-persistent-impl`（持久层实现）
- 依赖: `trade-util`（工具类）
- 被依赖: `trade-globle`（Web 模块）

## 相关模块

- [trade-service](../trade-service) - 业务层接口
- [trade-persistent-impl](../trade-persistent-impl) - 持久层实现
- [trade-globle](../trade-globle) - 当前业务层实现位置

---

更多信息请参考 [项目主 README](../README.md)
