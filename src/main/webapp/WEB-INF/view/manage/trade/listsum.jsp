<%--
  Created by IntelliJ IDEA.
  User: luowei
  Date: 12-11-9
  Time: 上午8:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="p" uri="/WEB-INF/tag/page" %>
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
    <c:url var="action" value="/manage/trade/listdetail"/>
    <form:form id="form1" modelAttribute="tradeSumDto" action="${action}" method="post" cssClass="well form-inline">

        <label>起始年月:</label>
        <form:select path="beginYear" items="${year}" itemValue="key" itemLabel="value" cssClass="dropdown-toggle"
                     placeholder="年"/>
        <form:select path="beginMonth" items="${month}" itemValue="key" itemLabel="value" cssClass="dropdown-toggle"
                     placeholder="月"/>

        <label>结束年月:</label>
        <form:select path="endYear" items="${year}" itemValue="key" itemLabel="value" cssClass="dropdown-toggle"
                     placeholder="年"/>
        <form:select path="endMonth" items="${month}" itemValue="key" itemLabel="value" cssClass="dropdown-toggle"
                     placeholder="月"/>

        <label>月同期查询:</label>
        <form:select path="sameMonth" items="${month}" itemValue="complanyType" itemLabel="complanyType"
                     cssClass="dropdown-toggle"/>

        <form:input path="searchKey1" cssClass="input-medium search-query" placeholder="关键字:"/>

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