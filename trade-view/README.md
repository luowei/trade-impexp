# trade-view

trade-impexp 项目的视图层模块。

## 模块概述

**trade-view** 负责项目的表现层（视图层），包含控制器、视图模板和前端资源。

## 当前状态

🚧 **模块待完善** - 当前仅包含基础 POM 配置，视图层代码尚未从 trade-globle 迁移。

## 设计目标

### 职责
- 处理 HTTP 请求和响应
- 渲染视图模板
- 数据展示和交互
- RESTful API 提供

### 技术选型（计划）
- **Web 框架**: Spring MVC 3.1.2
- **视图技术**: JSP + JSTL
- **页面布局**: Sitemesh 2.4.2
- **前端框架**: jQuery + Bootstrap（可选）
- **图表库**: Highcharts
- **数据格式**: JSON（AJAX）

## 规划的目录结构

```
trade-view/
├── pom.xml
└── src/main/
    ├── java/com/oilchem/trade/view/
    │   ├── controller/                 # 控制器
    │   │   ├── TradeDetailController.java     # 明细管理
    │   │   ├── TradeSumController.java        # 汇总管理
    │   │   ├── ExportDataController.java      # 数据导出
    │   │   ├── ChartController.java           # 图表展示
    │   │   ├── HighChartController.java       # Highcharts
    │   │   ├── DetailCountController.java     # 统计查询
    │   │   ├── FilterController.java          # 数据筛选
    │   │   ├── ConfigController.java          # 系统配置
    │   │   ├── LogController.java             # 日志查看
    │   │   └── CommonController.java          # 通用功能
    │   ├── excel/                      # Excel 视图
    │   │   ├── AbstractJExcelView.java        # Excel 视图基类
    │   │   └── TradeExcelView.java            # 贸易数据导出视图
    │   └── dto/                        # 视图 DTO
    │       ├── ChartData.java                 # 图表数据
    │       └── CommonDto.java                 # 通用 DTO
    ├── resources/
    │   └── spring/
    │       └── mvc-servlet.xml         # Spring MVC 配置
    └── webapp/
        ├── WEB-INF/
        │   ├── views/                  # JSP 视图
        │   │   ├── detail/             # 明细页面
        │   │   ├── sum/                # 汇总页面
        │   │   ├── chart/              # 图表页面
        │   │   └── common/             # 公共页面
        │   ├── decorators/             # Sitemesh 装饰器
        │   └── web.xml                 # Web 配置
        └── resources/                  # 静态资源
            ├── css/                    # 样式表
            ├── js/                     # JavaScript
            └── images/                 # 图片
```

## 临时方案

目前项目的视图层实现集中在 [trade-globle](../trade-globle) 模块中：

- `com.oilchem.trade.view.controller` - 控制器（10 个）
- `src/main/webapp/` - JSP 视图和静态资源
- Spring MVC 配置在 `mvc-servlet.xml` 中

## 主要控制器

### 数据管理

| 控制器 | URL 映射 | 功能 |
|--------|----------|------|
| TradeDetailController | /detail/* | 贸易明细 CRUD |
| TradeSumController | /sum/* | 汇总数据 CRUD |
| DetailCountController | /count/* | 明细统计查询 |

### 数据导出

| 控制器 | URL 映射 | 功能 |
|--------|----------|------|
| ExportDataController | /export/* | Excel 数据导出 |

### 数据可视化

| 控制器 | URL 映射 | 功能 |
|--------|----------|------|
| ChartController | /chart/* | OFC 图表数据 |
| HighChartController | /highchart/* | Highcharts 图表 |

### 系统功能

| 控制器 | URL 映射 | 功能 |
|--------|----------|------|
| FilterController | /filter/* | 动态筛选条件 |
| ConfigController | /config/* | 系统配置管理 |
| LogController | /log/* | 日志查看 |
| CommonController | / | 首页和通用功能 |

## 控制器设计规范

### RESTful 风格
```java
@Controller
@RequestMapping("/detail")
public class TradeDetailController {

    @Autowired
    private TradeDetailService service;

    // 列表页
    @GetMapping
    public String list(Model model, Pageable pageable) {
        Page<TradeDetail> page = service.findAll(pageable);
        model.addAttribute("page", page);
        return "detail/list";
    }

    // 详情页
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        TradeDetail detail = service.findById(id);
        model.addAttribute("detail", detail);
        return "detail/view";
    }

    // 创建
    @PostMapping
    @ResponseBody
    public Result create(@RequestBody TradeDetail detail) {
        service.save(detail);
        return Result.success();
    }

    // 更新
    @PutMapping("/{id}")
    @ResponseBody
    public Result update(@PathVariable Long id, @RequestBody TradeDetail detail) {
        detail.setId(id);
        service.save(detail);
        return Result.success();
    }

    // 删除
    @DeleteMapping("/{id}")
    @ResponseBody
    public Result delete(@PathVariable Long id) {
        service.delete(id);
        return Result.success();
    }
}
```

### Excel 导出
```java
@Controller
@RequestMapping("/export")
public class ExportDataController {

    @GetMapping("/detail")
    public ModelAndView exportDetail(SearchCondition condition) {
        List<TradeDetail> data = service.findByConditions(condition);
        return new ModelAndView(new TradeExcelView(), "data", data);
    }
}
```

## Excel 视图实现

```java
public class TradeExcelView extends AbstractJExcelView {

    @Override
    protected void buildExcelDocument(
            Map<String, Object> model,
            WritableWorkbook workbook,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        WritableSheet sheet = workbook.createSheet("贸易明细", 0);

        // 设置表头
        sheet.addCell(new Label(0, 0, "日期"));
        sheet.addCell(new Label(1, 0, "公司名称"));
        // ...

        // 填充数据
        List<TradeDetail> data = (List<TradeDetail>) model.get("data");
        for (int i = 0; i < data.size(); i++) {
            TradeDetail detail = data.get(i);
            sheet.addCell(new Label(0, i + 1, detail.getDate()));
            sheet.addCell(new Label(1, i + 1, detail.getCompany()));
            // ...
        }
    }
}
```

## 开发计划

1. 从 trade-globle 提取控制器到本模块
2. 从 trade-globle 提取 JSP 视图
3. 从 trade-globle 提取 Excel 视图类
4. 优化前端代码（使用前端框架）
5. 实现前后端分离（可选）
6. 添加 API 文档（Swagger）
7. 添加控制器单元测试（MockMvc）

## 依赖关系

- 依赖: `trade-service`（业务接口）
- 依赖: `trade-util`（工具类）
- 被依赖: `trade-globle`（Web 应用打包）

## 前端技术栈

### 当前使用
- jQuery（AJAX 请求）
- Highcharts（图表）
- JSP + JSTL（视图渲染）
- Sitemesh（页面布局）

### 可选升级
- Vue.js / React（前端框架）
- Element UI / Ant Design（UI 组件库）
- ECharts（图表库）
- RESTful API（前后端分离）

## 相关模块

- [trade-service](../trade-service) - 业务层接口
- [trade-globle](../trade-globle) - 当前视图层临时实现位置

---

更多信息请参考 [项目主 README](../README.md)
