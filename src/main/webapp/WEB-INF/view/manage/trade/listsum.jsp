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
</head>

<body>

<div class="wrapper">
    <c:url var="action" value="/manage/listsum"/>
    <form:form id="form1" modelAttribute="tradeSum" action="${action}" method="post" cssClass="well form-inline">

        <label>起始年月:
           <select name="lowyear">
               <option value="" selected="selected">--</option>
               <c:forEach var="yr" begin="2000" end="2050" step="1">
                   <option value="${yr}">${yr}</option>
               </c:forEach>
           </select> 年&nbsp;
           <select name="lowmonth">
               <option value="" selected="selected">--</option>
               <c:forEach var="mth" begin="1" end="12" step="1">
                   <option value="${mth}">${mth}</option>
               </c:forEach>
           </select>月
        </label>


        <label>结束年月:</label>
        <select name="highyear">
            <option value="" selected="selected">--</option>
            <c:forEach var="yr" begin="2000" end="2050" step="1">
                <option value="${yr}">${yr}</option>
            </c:forEach>
        </select> 年&nbsp;
        <select name="highmonth">
            <option value="" selected="selected">--</option>
            <c:forEach var="mth" begin="1" end="12" step="1">
                <option value="${mth}">${mth}</option>
            </c:forEach>
        </select>月

        <label>月同期查询:</label>
        <select name="month">
            <option value="" selected="selected">--</option>
            <c:forEach var="mth" begin="1" end="12" step="1">
                <option value="${mth}">${mth}</option>
            </c:forEach>
        </select>月

        <br/>
        <form:input path="productName" cssClass="input-medium search-query" placeholder="产品名称:"/>
        <form:input path="productCode" cssClass="input-medium search-query" placeholder="产品代码:"/>

        <button type="submit" class="btn btn-success">
            <i class="icon-search icon-white"></i>查询
        </button>

    </form:form>

    <table class="table table-bordered table-striped table-condensed">
        <thead>
        <tr>
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
        <c:forEach items="${sumlist}" var="sum" varStatus="st">
            <tr>
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
    ${pageTag}
</div>
</body>
</html>