<%--
  Created by IntelliJ IDEA.
  User: luowei
  Date: 12-11-9
  Time: 上午8:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>进出口总表</title>

    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap/css/bootstrap.min.css' />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap/css/bootstrap-responsive.min.css' />"/>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/1.7.2/jquery.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/trade.js" />"></script>
    <script type="text/javascript">
        $(function () {
            checkAll("all", "codes");
        });

        function genSumChart() {
            $("#form1").attr("action", "${pageContext.request.contextPath}/manage/sumchart");
            $("#form1").submit();
        }
    </script>


</head>

<body style="padding-top: 60px">

<jsp:include page="../../common/breadcrumb.jsp"/>

<div class="container">
    <h2>进出口总表</h2>
    <c:url var="action" value="${contextUrl}/1"/>
    <form:form id="form1" modelAttribute="tradeSum" action="${action}" method="post" cssClass="well form-inline">

        <label class="label">起始年月:
            <select name="lowYear" class="input-mini">
                <option value="" selected="selected">--</option>
                <c:forEach var="lyr" begin="2000" end="2050" step="1">
                    <option value="${lyr}" <c:if test="${lowYear eq lyr}">selected="selected" </c:if>>
                            ${lyr}</option>
                </c:forEach>
            </select> 年&nbsp;
            <select name="lowMonth" class="input-mini">
                <option value="" selected="selected">--</option>
                <c:forEach var="lmth" begin="1" end="12" step="1">
                    <option value="${lmth}" <c:if test="${lowMonth eq lmth}">selected="selected" </c:if>>
                            ${lmth}</option>
                </c:forEach>
            </select>月
        </label>


        <label class="label">结束年月:
            <select name="highYear" class="input-mini">
                <option value="" selected="selected">--</option>
                <c:forEach var="hyr" begin="2000" end="2050" step="1">
                    <option value="${yr}" <c:if test="${highYear eq hyr}">selected="selected" </c:if>>
                            ${hyr}</option>
                </c:forEach>
            </select> 年&nbsp;
            <select name="highMonth" class="input-mini">
                <option value="" selected="selected">--</option>
                <c:forEach var="hmth" begin="1" end="12" step="1">
                    <option value="${hmth}" <c:if test="${highMonth eq hmth}">selected="selected" </c:if>>
                            ${hmth}</option>
                </c:forEach>
            </select>月
        </label>

        <label class="label">月同期查询:
            <select name="month" class="input-mini">
                <option value="" selected="selected">--</option>
                <c:forEach var="mth" begin="1" end="12" step="1">
                    <option value="${mth}" <c:if test="${month eq mth}">selected="selected" </c:if>>
                            ${mth}</option>
                </c:forEach>
            </select>月
        </label>

        <br/>

        <label class="label">
            产品名称:<input name="productName" cssClass="input-mini search-query"
                        <c:if test='${productName ne null}'>value="${productName}" </c:if> />
        </label>

        <label class="label">产品类型:
            <select name="sumType" class="input-mini">
                <option value="" selected="selected">--</option>
                <c:forEach var="prodType" items="${sumTypeList}">
                    <option value="${prodType.sumType}"
                            <c:if test="${sumType eq prodType.sumType}">selected="selected" </c:if>>
                            ${prodType.sumType}</option>
                </c:forEach>
            </select>
        </label>

        <label class="label">进出口类型:
            <select name="impExpType" class=" input-small">
                <option value="0" <c:if test="${impExpType eq 0}">selected="selected" </c:if>>进口</option>
                <option value="1" <c:if test="${impExpType eq 1}">selected="selected" </c:if>>出口</option>
            </select>
        </label>

        <button type="submit" class="btn btn-success">
            <i class="icon-search icon-white"></i>查询
        </button>
        <input id="chart" type="button" class="btn-small btn-primary" value="生成曲线" onclick="genSumChart()"/>
        <%--<a  href="" target="_blank" class="btn btn-small btn-primary" onclick="genSumChart()">生成曲线</a>--%>

        <div class="pagination-centered">
            <jsp:include page="../../common/pagination.jsp"/>
        </div>

        <h4> <span> 过滤条件:</span>
            <label class="label"> 进出口类型:
                <c:if test="${impExpType eq 0}"><span style="color: yellow;">进口</span></c:if>
                <c:if test="${impExpType eq 1}"><span style="color: yellow;">出口</span></c:if> </label>
            <c:if test="${lowYear ne null and lowMonth ne null}">
                <label class="label"> 起始年月:<span style="color: yellow;">${lowYear}-${lowMonth} </span> </label>
            </c:if>
            <c:if test="${highYear ne null}">
                <label class="label">结束年月: <span style="color: yellow;">${highYear}-${highMonth} </span> </label>
            </c:if>
            <c:if test="${month ne null}">
                <label class="label"> 月同期: <span style="color: yellow;">${month} </span> </label>
            </c:if>
            <c:if test="${sumType ne null}">
                <label class="label"> 企业性质: <span style="color: yellow;">${sumType} </span> </label>
            </c:if>
            <c:if test="${productName ne null}">
                <label class="label">产品名称: <span style="color: yellow;">${productName}</span> </label>
            </c:if>

        </h4>

        <table class="table table-bordered table-striped table-condensed">
            <thead>
            <tr>
                <th>曲线</th>
                    <%--<th><label><input type="checkbox" id="all"/>曲线</label></th>--%>
                <td>年月</td>
                <td>产品名称</td>
                <td>产品类型</td>
                <td>当月数量(T)</td>
                <td>累计总数量(T)</td>
                <td>当月金额(万美元)</td>
                <td>累计总金额(万美元)</td>
                <td>当月平均价格(美元/T)</td>
                <td>累计平均价格(美元/T)</td>
                <td>与上月数量增长比(%)</td>
                <td>与上年同月数量增长比(%)</td>
                <td>与上年同期数量增长比(%)</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${tradeSumList.content}" var="sum" varStatus="st">
                <tr>
                    <td><input type="checkbox" name="codes" value="${sum.productName}"/></td>
                    <td>${sum.yearMonth}</td>
                    <td>${sum.productName}</td>
                    <td>${sum.sumType}</td>
                    <td>${sum.numMonth}</td>
                    <td>${sum.numSum}</td>
                    <td>${sum.moneyMonth}</td>
                    <td>${sum.moneySum}</td>
                    <td>${sum.avgPriceMonth}</td>
                    <td>${sum.avgPriceSum}</td>
                    <td>${sum.pm}</td>
                    <td>${sum.py}</td>
                    <td>${sum.pq}</td>
                </tr>
            </c:forEach>

            </tbody>
        </table>
    </form:form>

    <div class="pagination-centered">
        <jsp:include page="../../common/pagination.jsp"/>
    </div>
</div>
</body>
</html>