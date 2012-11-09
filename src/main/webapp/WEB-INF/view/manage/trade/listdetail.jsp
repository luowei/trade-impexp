<%--
  Created by IntelliJ IDEA.
  User: luowei
  Date: 12-11-9
  Time: 上午8:57
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
    <title>进出口明细</title>
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


        <label>企业性质:</label>
        <form:select path="complanyType" items="${complanyType}" itemValue="complanyType" itemLabel="complanyType"
                     cssClass="dropdown-toggle"/>

        <label>贸易方式:</label>
        <form:select path="complanyType" items="${complanyType}" itemValue="complanyType" itemLabel="complanyType"
                     cssClass="dropdown-toggle"/>

        <label>运输方式:</label>
        <form:select path="complanyType" items="${complanyType}" itemValue="complanyType" itemLabel="complanyType"
                     cssClass="dropdown-toggle"/>

        <label>海关:</label>
        <form:select path="complanyType" items="${complanyType}" itemValue="complanyType" itemLabel="complanyType"
                     cssClass="dropdown-toggle"/>

        <label>产销国家:</label>
        <form:select path="complanyType" items="${complanyType}" itemValue="complanyType" itemLabel="complanyType"
                     cssClass="dropdown-toggle"/>

        <label>城市:</label>
        <form:select path="complanyType" items="${complanyType}" itemValue="complanyType" itemLabel="complanyType"
                     cssClass="dropdown-toggle"/>

        <form:select path="impExpType" cssClass="dropdown-toggle" placeholder="进出口类型">
            <form:option value="1" label="进口"/>
            <form:option value="2" label="出口"/>
        </form:select>

        <form:input path="searchKey1" cssClass="input-medium search-query" placeholder="关键字:"/>

        <button type="submit" class="btn btn-success">
            <i class="icon-search icon-white"></i>搜索
        </button>
    </form:form>

    <table class="table table-bordered table-striped table-condensed">
        <thead>
        <tr>
            <th>年</th>
            <th>月</th>
            <th>产品代码</th>
            <th>产品名称</th>
            <th>贸易方式</th>
            <th>企业性质</th>
            <th>运输方式</th>
            <th>城市</th>
            <th>产销国家</th>
            <th>数量</th>
            <th>单位</th>
            <th>美元价值</th>
            <th>均价</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${detaillist}" var="detail" varStatus="st">
            <tr>
                <td>${detail.year}</td>
                <td>${detail.month}</td>
                <td>${detail.productCode}</td>
                <td>${detail.productName}</td>
                <td>${detail.tradeType}</td>
                <td>${detail.companyType}</td>
                <td>${detail.transportation}</td>
                <td>${detail.city}</td>
                <td>${detail.country}</td>
                <td>${detail.amount}</td>
                <td>${detail.unit}</td>
                <td>${detail.amountMoney}</td>
                <td></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    ${pageTag}

</div>

</body>
</html>