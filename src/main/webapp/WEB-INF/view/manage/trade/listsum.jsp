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
    <script type="text/javascript" src="<c:url value="/resources/js/trade.js"/> "></script>
</head>

<body>

<div class="wrapper">
    <h2>进出口总表</h2>
    <c:url var="action" value="${pageContext.request.contextPath}${contextUrl}/1"/>
    <form:form id="form1" modelAttribute="tradeSum" action="${action}" method="post" cssClass="well form-inline">

        <label class="label">起始年月:
           <select name="lowyear" class="input-mini">
               <option value="" selected="selected">--</option>
               <c:forEach var="yr" begin="2000" end="2050" step="1">
                   <option value="${yr}">${yr}</option>
               </c:forEach>
           </select> 年&nbsp;
           <select name="lowmonth" class="input-mini">
               <option value="" selected="selected">--</option>
               <c:forEach var="mth" begin="1" end="12" step="1">
                   <option value="${mth}">${mth}</option>
               </c:forEach>
           </select>月
        </label>


        <label class="label">结束年月:
        <select name="highyear" class="input-mini">
            <option value="" selected="selected">--</option>
            <c:forEach var="yr" begin="2000" end="2050" step="1">
                <option value="${yr}">${yr}</option>
            </c:forEach>
        </select> 年&nbsp;
        <select name="highmonth" class="input-mini">
            <option value="" selected="selected">--</option>
            <c:forEach var="mth" begin="1" end="12" step="1">
                <option value="${mth}">${mth}</option>
            </c:forEach>
        </select>月
        </label>

        <label class="label">月同期查询:
        <select name="month" class="input-mini">
            <option value="" selected="selected">--</option>
            <c:forEach var="mth" begin="1" end="12" step="1">
                <option value="${mth}">${mth}</option>
            </c:forEach>
        </select>月
        </label>

        <br/>

        <label class="label">
            产品名称:<input name="productName" cssClass="input-mini search-query"
                        <c:if test='${productName ne null}'>value="${productName}" </c:if> />
        </label>

        <label class="label">进出口类型:
            <select name="impExp" class=" input-small">
                <option value="0" <c:if test="${impExp eq 0}">selected="selected" </c:if>>进口</option>
                <option value="1" <c:if test="${impExp eq 1}">selected="selected" </c:if>>出口</option>
            </select>
        </label>

        <button type="submit" class="btn btn-success">
            <i class="icon-search icon-white"></i>查询
        </button>
        <input type="button" class="btn btn-primary" value="生成曲线"/>

    </form:form>

    <table class="table table-bordered table-striped table-condensed">
        <thead>
        <tr>
            <th>曲线</th>
            <td>年</td>
            <td>月</td>
            <td>产品名称</td>
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
                <td><form:checkbox path="commonDto.ids" value="${sum.id}"/></td>
                <td>${sum.year}</td>
                <td>${sum.month}</td>
                <td>${sum.productName}</td>
                <td>${sum.numMonth}</td>
                <td>${sum.numSum}</td>
                <td>${sum.moneyMonth}</td>
                <td>${sum.moneySum}</td>
                <td>${sum.avgPriceMonth}</td>
                <td>${sum.avgPriceSum}</td>
                <td>${sum.numPreMonthIncRadio}</td>
                <td>${sum.numPreYearSameMonthIncRatio}</td>
                <td>${sum.numPreYearSameQuarterInrRatio}</td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
    <div class="pagination-centered">
        <jsp:include page="../../common/pagination.jsp"/>
    </div>
</div>
</body>
</html>