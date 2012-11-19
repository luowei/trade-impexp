<%--
  Created by IntelliJ IDEA.
  User: luowei
  Date: 12-11-9
  Time: 上午8:57
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
    <title>进出口明细</title>

    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap/css/bootstrap.min.css' />"/>
    <link rel="stylesheet" type="text/css"
          href="<c:url value='/resources/bootstrap/css/bootstrap-responsive.min.css' />"/>
</head>
<body>

<div class="wrapper">
    <c:url var="action" value="/manage/listdetail"/>
    <form:form id="form1" modelAttribute="tradeDetail" action="${action}"
               method="post" cssClass="well form-inline">
        <label class="label ">起始年月:
            <select name="lowyear" class=" input-mini">
                <option value="" selected="selected">--</option>
                <c:forEach var="yr" begin="2000" end="2050" step="1">
                    <option value="${yr}">${yr}</option>
                </c:forEach>
            </select> 年&nbsp;
            <select name="lowmonth" class=" input-mini">
                <option value="" selected="selected">--</option>
                <c:forEach var="mth" begin="1" end="12" step="1">
                    <option value="${mth}">${mth}</option>
                </c:forEach>
            </select>月
        </label>

        <label class="label">结束年月:
            <select name="highyear" class=" input-mini">
                <option value="" selected="selected">--</option>
                <c:forEach var="yr" begin="2000" end="2050" step="1">
                    <option value="${yr}">${yr}</option>
                </c:forEach>
            </select> 年&nbsp;
            <select name="highmonth" class=" input-mini">
                <option value="" selected="selected">--</option>
                <c:forEach var="mth" begin="1" end="12" step="1">
                    <option value="${mth}">${mth}</option>
                </c:forEach>
            </select>月
        </label>


        <label class="label">月同期查询:
            <select name="month" class=" input-mini">
                <option value="" selected="selected">--</option>
                <c:forEach var="mth" begin="1" end="12" step="1">
                    <option value="${mth}">${mth}</option>
                </c:forEach>
            </select>月
        </label>

        <br/>

        <label class="label">企业性质:
            <select name="companyType" class=" input-mini">
                <option value="">--</option>
                <c:forEach items="${companyTypeList}" var="companyType">
                    <option value="${companyType.companyType}">${companyType.companyType}</option>
                </c:forEach>
            </select>
        </label>

        <label class="label">贸易方式:
            <select name="tradeType" class=" input-mini">
                <option value="">--</option>
                <c:forEach items="${tradeTypeList}" var="tradeType">
                    <option value="${tradeType.tradeType}">${tradeType.tradeType}</option>
                </c:forEach>
            </select>
        </label>

        <label class="label">运输方式:
            <select name="transportation" class=" input-mini">
                <option value="">--</option>
                <c:forEach items="${transportationList}" var="transportation">
                    <option value="${transportation.transportation}">${transportation.transportation}</option>
                </c:forEach>
            </select>
        </label>

        <label class="label">海关:
            <select name="customs" class=" input-mini">
                <option value="">--</option>
                <c:forEach items="${customsList}" var="customs">
                    <option value="${customs.customs}">${customs.customs}</option>
                </c:forEach>
            </select>
        </label>

        <label class="label">产销国家:
            <select name="country" class=" input-mini">
                <option value="">--</option>
                <c:forEach items="${countryList}" var="country">
                    <option value="${country.country}">${country.country}</option>
                </c:forEach>
            </select>
        </label>

        <label class="label">城市:
            <select name="city" class=" input-mini">
                <option value="">--</option>
                <c:forEach items="${cityList}" var="city">
                    <option value="${city.city}">${city.city}</option>
                </c:forEach>
            </select>
        </label>

        <br/>

        <label class="label">
            产品代码:<input name="productCode" cssClass="input-mini search-query"/>
        </label>
        <label class="label">
            产品名称:<input name="productName" cssClass="input-mini search-query"/>
        </label>

        <br/>

        <label class="label">进出口类型:
            <select name="impExp" class=" input-small">
                <option value="0">进口</option>
                <option value="1">出口</option>
            </select>
        </label>

        <button type="submit" class="btn btn-success">
            <i class="icon-search icon-white"></i>查询
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
            <th>海关</th>
            <th>产销国家</th>
            <th>数量</th>
            <th>单位</th>
            <th>美元价值</th>
            <th>均价</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${tradeDetailList.content}" var="detail" varStatus="st">
            <tr>
                <td>${detail.year}</td>
                <td>${detail.month}</td>
                <td>${detail.productCode}</td>
                <td>${detail.productName}</td>
                <td>${detail.tradeType}</td>
                <td>${detail.companyType}</td>
                <td>${detail.transportation}</td>
                <td>${detail.city}</td>
                <td>${detail.customs}</td>
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

<div class="pagination-centered">
    <jsp:include page="../../common/pagination.jsp"/>
</div>
</body>
</html>