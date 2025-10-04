# trade-util

trade-impexp 项目的工具类模块。

## 模块概述

**trade-util** 提供项目中通用的工具类和辅助方法，供其他模块复用。

## 当前状态

🚧 **模块待完善** - 当前仅包含基础 POM 配置，工具类尚未从 trade-globle 迁移。

## 设计目标

### 职责
- 提供通用工具方法
- 封装第三方库调用
- 简化常用操作
- 避免代码重复

### 设计原则
- **无状态**: 工具类方法应为静态方法或线程安全
- **单一职责**: 每个工具类专注于一类功能
- **高复用性**: 方法应通用，避免业务耦合
- **文档完善**: 每个方法提供清晰的 JavaDoc

## 规划的目录结构

```
trade-util/
├── pom.xml
└── src/main/java/com/oilchem/trade/util/
    ├── file/                           # 文件操作
    │   ├── FileUtil.java               # 文件读写、复制、移动
    │   ├── ZipUtil.java                # ZIP/RAR 压缩解压
    │   └── ExcelUtil.java              # Excel 操作工具
    ├── cache/                          # 缓存工具
    │   └── EHCacheUtil.java            # Ehcache 封装
    ├── database/                       # 数据库工具
    │   ├── JdbcUtil.java               # JDBC 工具
    │   ├── QueryUtils.java             # 查询工具
    │   └── DynamicSpecifications.java  # JPA 动态查询
    ├── date/                           # 日期时间
    │   └── DateUtils.java              # 日期格式化、计算
    ├── config/                         # 配置管理
    │   └── ConfigUtil.java             # JSON 配置读取
    ├── common/                         # 通用工具
    │   ├── CommonUtil.java             # 通用方法
    │   ├── GenericsUtils.java          # 泛型工具
    │   └── CommandUtil.java            # 命令执行
    └── validation/                     # 数据验证
        └── ValidationUtil.java         # 参数验证
```

## 临时方案

目前项目的工具类集中在 [trade-globle](../trade-globle) 模块的 `util` 包中：

```
com.oilchem.trade.util/
├── ZipUtil.java                # ZIP/RAR 解压
├── FileUtil.java               # 文件操作
├── EHCacheUtil.java            # 缓存管理
├── JdbcUtil.java               # JDBC 工具
├── QueryUtils.java             # 查询工具
├── DynamicSpecifications.java  # 动态查询规范
├── ConfigUtil.java             # 配置读取
├── DateUtils.java              # 日期工具
├── CommonUtil.java             # 通用工具
├── GenericsUtils.java          # 泛型工具
└── CommandUtil.java            # 命令工具
```

## 主要工具类说明

### 文件处理

#### FileUtil
- 文件读写
- 文件复制、移动、删除
- 目录遍历
- 文件类型判断

#### ZipUtil
- ZIP 压缩包解压
- RAR 文件解压（使用 java-unrar）
- 压缩文件创建
- 支持密码保护

### 缓存管理

#### EHCacheUtil
- Ehcache 初始化
- 对象缓存存取
- 缓存过期管理
- 缓存统计信息
- 缓存清理

### 数据库工具

#### JdbcUtil
- JDBC 连接管理
- 参数化查询
- 批量操作
- 结果集映射

#### QueryUtils
- 查询条件构建
- 参数绑定
- 分页查询
- 排序处理

#### DynamicSpecifications
- JPA Specification 动态构建
- 多条件组合查询
- 支持等于、大于、小于、模糊查询等操作
- 类型安全

### 配置管理

#### ConfigUtil
- JSON 配置文件读取
- 配置对象映射（使用 Gson）
- 配置热更新
- 默认值处理

### 日期时间

#### DateUtils
- 日期格式化
- 日期解析
- 日期计算（加减天数、月份等）
- 时间区间判断
- 基于 Joda-Time

### 通用工具

#### CommonUtil
- 字符串处理
- 集合操作
- 类型转换
- 空值判断
- 随机数生成

#### GenericsUtils
- 泛型类型获取
- 反射操作简化
- 泛型数组创建

#### CommandUtil
- 外部命令执行
- 进程管理
- 输出流读取

## 依赖的第三方库

```xml
<!-- 日期时间 -->
<dependency>
    <groupId>joda-time</groupId>
    <artifactId>joda-time</artifactId>
</dependency>

<!-- JSON 处理 -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
</dependency>

<!-- Apache Commons -->
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
</dependency>
<dependency>
    <groupId>commons-io</groupId>
    <artifactId>commons-io</artifactId>
</dependency>

<!-- 压缩文件 -->
<dependency>
    <groupId>de.innosystec</groupId>
    <artifactId>java-unrar</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.ant</groupId>
    <artifactId>ant</artifactId>
</dependency>

<!-- 缓存 -->
<dependency>
    <groupId>net.sf.ehcache</groupId>
    <artifactId>ehcache</artifactId>
</dependency>
```

## 开发计划

1. 从 trade-globle 提取工具类到本模块
2. 按功能分类组织目录结构
3. 补充单元测试
4. 完善 JavaDoc 文档
5. 添加使用示例
6. 性能优化（缓存、懒加载等）

## 使用示例

### 文件解压
```java
// 解压 ZIP 文件
ZipUtil.unzip("/path/to/file.zip", "/path/to/output/");

// 解压 RAR 文件
ZipUtil.unrar("/path/to/file.rar", "/path/to/output/");
```

### 缓存操作
```java
// 存入缓存
EHCacheUtil.put("cacheName", "key", value);

// 从缓存读取
Object value = EHCacheUtil.get("cacheName", "key");

// 清除缓存
EHCacheUtil.remove("cacheName", "key");
```

### 配置读取
```java
// 读取 JSON 配置文件
Config config = ConfigUtil.loadConfig("/path/to/config.json", Config.class);
```

### 动态查询
```java
// 构建动态查询条件
Specification<Entity> spec = DynamicSpecifications
    .where("field1", value1)
    .and("field2", value2)
    .like("field3", value3);
```

### 日期处理
```java
// 格式化日期
String dateStr = DateUtils.format(new Date(), "yyyy-MM-dd");

// 计算日期
Date tomorrow = DateUtils.addDays(new Date(), 1);
```

## 依赖关系

- 被依赖: 所有业务模块（`trade-service-impl`, `trade-globle` 等）
- 无外部模块依赖（仅依赖第三方库）

## 相关模块

- [trade-globle](../trade-globle) - 当前工具类临时实现位置

---

更多信息请参考 [项目主 README](../README.md)
