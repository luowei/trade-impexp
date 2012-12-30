<%--
  Created by IntelliJ IDEA.
  User: luowei
  Date: 12-12-14
  Time: 下午12:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
    <title>明细数据图表</title>

    <%--<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.2/jquery.min.js"></script>--%>
    <script type="text/javascript" src="<c:url value='/resources/js/highcharts/highcharts.js' />"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/highcharts/exporting.js' />"></script>
    <%--<script type="text/javascript" src="<c:url value='/resources/js/mychart.js' />"></script>--%>
    <script type="text/javascript">

        <%--<c:forEach items="${yearMonthMap}" var="entry" varStatus="vs">--%>

        $(function () {
            var chart;
            $(document).ready(function () {
                chart = new Highcharts.Chart({
                    chart: {
                        renderTo: 'productChart',
                        type: 'column',
                        marginRight: 300,
                        marginBottom: 50
                    },
                    title: {
                        text: '<b>${productName}</b><c:if test="${yearMonthDto.impExpType eq '0'}">进口</c:if><c:if test="${yearMonthDto.impExpType eq '1'}">出口 </c:if> 统计'
                    },
                    subtitle: {
                        text: '来源:<a target="_blank" href="http://www.oilchem.net">隆众石化网</a>'
                    },
                    xAxis: {
                        categories: ${yearMonths}
                    },
                    <%--yAxis: {--%>
                    <%--min: 0,--%>
                    <%--title: {--%>
                    <%--text: '数量(${productCount.unit})'--%>
                    <%--}--%>
                    <%--},--%>
                    yAxis: [
                        { // Primary yAxis
                            labels: {
                                formatter: function () {
                                    return this.value + '${productCount.unit}';
                                },
                                style: {
                                    color: '#89A54E'
                                }
                            },
                            title: {
                                text: '数量<c:if test="${productCount.unit ne null or productCount.unit eq ''}">(${productCount.unit})</c:if>',
                                style: {
                                    color: '#89A54E'
                                }
                            }
                        },
                        { // Secondary yAxis
                            title: {
                                text: '金额(美元)',
                                style: {
                                    color: '#4572A7'
                                }
                            },
                            labels: {
                                formatter: function () {
                                    return this.value + ' 美元';
                                },
                                style: {
                                    color: '#4572A7'
                                }
                            },
                            opposite: true
                        }
                    ],
                    legend: {
                        layout: 'vertical',
                        backgroundColor: '#FFFFFF',
                        align: 'right',
                        verticalAlign: 'top',
                        x: 30,
                        y: 30,
                        width: 200,
                        floating: true,
                        shadow: true
                    },
                    tooltip: {
                        formatter: function () {
                            return '' +
                                    this.x + ': ' + this.y +
                                    (this.series.type == 'column' ? ' ${productCount.unit}' : '美元');
                        }
                    },
                    plotOptions: {
                        column: {
                            pointPadding: 0.2,
                            borderWidth: 0
                        }
                    },
                    series:${seriesList}
                });
            });

        });

        <%--</c:forEach>--%>
    </script>


</head>
<body>

<div class="navbar navbar-fixed-top ">
    <div class="navbar-inner">

        <div class="wrapper">
            <a class="brand" href="#">进出口管理</a>

            <div class="nav-collapse collapse navbar-responsive-collapse">
                <ul class="nav nav-tabs">
                    <li><a href="${pageContext.request.contextPath}/manage/list/detailCount/1">明细图表</a></li>
                    <li><a href="javascript:" onclick="history.back();">返回</a></li>
                </ul>
            </div>

        </div>
    </div>
</div>

<h4>
    <span>
        <c:if test="${contentTitle ne null}">${contentTitle}</c:if>
        <c:if test="${contentTitle eq null or contentTitle eq ''}">统计图表</c:if>
    </span><br/>
    <label class="label"> 进出口类型为:
        <c:if test="${impExpType eq 0}"><span style="color: yellow;">进口</span></c:if>
        <c:if test="${impExpType eq 1}"><span style="color: yellow;">出口</span></c:if>
    </label>
    <c:if test="${lowYear ne null and lowMonth ne null}">
        <label class="label"> 年份为:<span style="color: yellow;">${lowYear}-${highYear} </span> </label>
    </c:if>
    <c:if test="${highYear ne null}">
        <label class="label">月份为: <span style="color: yellow;">${lowMonth}-${highMonth} </span> </label>
    </c:if>
    <c:if test="${productCount.condition eq 'tradeType'}">
        <label class="label">统计条件为: <span style="color: yellow;">贸易方式</span> </label>
    </c:if>
    <c:if test="${productCount.condition eq 'companyType'}">
        <label class="label">统计条件为: <span style="color: yellow;">企业性质</span> </label>
    </c:if>
    <c:if test="${productCount.condition eq 'transportation'}">
        <label class="label">统计条件为: <span style="color: yellow;">运输方式</span> </label>
    </c:if>
    <c:if test="${productCount.condition eq 'customs'}">
        <label class="label">统计条件为: <span style="color: yellow;">海关</span> </label>
    </c:if>
    <c:if test="${productCount.condition eq 'country'}">
        <label class="label">统计条件为: <span style="color: yellow;">产销国家</span> </label>
    </c:if>
    <c:if test="${productCount.condition eq 'city'}">
        <label class="label">统计条件为: <span style="color: yellow;">城市</span></label>
    </c:if>
    <c:if test="${productCode ne null}">
        <label class="label">产品代码为: <span style="color: yellow;">${productCode}</span> </label>
    </c:if>
    <c:if test="${productName ne null}">
        <label class="label">产品名称为: <span style="color: yellow;">${productName}</span> </label>
    </c:if>

</h4>
<hr/>
<div id="productChart" style="min-width: 400px;width: ${width}px; min-height: 400px; height:${height};margin: 0 auto"></div>

</body>
</html>