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
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap/css/bootstrap-responsive.min.css' />"/>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/1.7.2/jquery.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/trade.js"/> "></script>

</head>
<body>
<h2>进出口明细</h2>

<div class="container">
    <form:form id="form1" modelAttribute="tradeDetail"
               action="${pageContext.request.contextPath}${contextUrl}/1"
               method="post" cssClass="well form-inline">
        <input type="hidden" name="pageSize" value="${pageSize}"/>
        <input type="hidden" name="sort" value="${sort}"/>
        <input type="hidden" name="highValue" value=""/>
        <input type="hidden" name="lowValue" value=""/>

        <label class="label ">起始年月:
            <select name="lowYear" class=" input-mini" onchange="">
                <option value="" selected="selected">--</option>
                <c:forEach var="yr" begin="2000" end="2050" step="1">
                    <option value="${yr}" <c:if test="${lowYear eq yr}">selected="selected" </c:if>>
                    ${yr}</option>
                </c:forEach>
            </select> 年&nbsp;
            <select name="lowMonth" class=" input-mini">
                    <option value="" selected="selected">--</option>
                <c:forEach var="mth" begin="1" end="12" step="1">
                    <option value="${mth}" <c:if test="${lowMonth eq mth}">selected="selected" </c:if>>
                    ${mth}</option>
                </c:forEach>
            </select>月
        </label>

        <label class="label">结束年月:
            <select name="highYear" class=" input-mini" onchange="">
                <option value="" selected="selected">--</option>
                <c:forEach var="yr" begin="2000" end="2050" step="1">
                    <option value="${yr}" <c:if test="${highYear eq yr}">selected="selected" </c:if>>
                    ${yr}</option>
                </c:forEach>
            </select> 年&nbsp;
            <select name="highMonth" class=" input-mini">
                    <option value="" selected="selected">--</option>
                <c:forEach var="mth" begin="1" end="12" step="1">
                    <option value="${mth}"  <c:if test="${highMonth eq mth}">selected="selected" </c:if>>
                    ${mth}</option>
                </c:forEach>
            </select>月
        </label>


        <label class="label">月同期查询:
            <select name="month" class=" input-mini">
                <option value="" selected="selected">--</option>
                <c:forEach var="mth" begin="1" end="12" step="1">
                    <option value="${mth}"  <c:if test="${month eq mth}">selected="selected" </c:if>>
                    ${mth}</option>
                </c:forEach>
            </select>月
        </label>

        <br/>

        <label class="label">企业性质:
            <select name="companyType" class=" input-mini">
                <option value="">--</option>
                <c:forEach items="${companyTypeList}" var="companyTypeItem">
                    <option value="${companyTypeItem.companyType}"
                            <c:if test="${companyTypeItem.companyType eq companyType}">selected="selected" </c:if>>
                            ${companyTypeItem.companyType}
                    </option>
                </c:forEach>
            </select>
        </label>

        <label class="label">贸易方式:
            <select name="tradeType" class=" input-mini">
                <option value="">--</option>
                <c:forEach items="${tradeTypeList}" var="tradeTypeItem">
                    <option value="${tradeTypeItem.tradeType}"
                            <c:if test="${tradeTypeItem.tradeType eq tradeType}">selected="selected" </c:if>>
                            ${tradeTypeItem.tradeType}
                    </option>
                </c:forEach>
            </select>
        </label>

        <label class="label">运输方式:
            <select name="transportation" class=" input-mini">
                <option value="">--</option>
                <c:forEach items="${transportationList}" var="transportationItem">
                    <option value="${transportationItem.transportation}"
                            <c:if test="${transportationItem.transportation eq transportation}">selected="selected" </c:if>>
                            ${transportationItem.transportation}
                    </option>
                </c:forEach>
            </select>
        </label>

        <label class="label">海关:
            <select name="customs" class=" input-mini">
                <option value="">--</option>
                <c:forEach items="${customsList}" var="customsItem">
                    <option value="${customsItem.customs}"
                            <c:if test="${customsItem.customs eq customs}">selected="selected" </c:if>>
                            ${customsItem.customs}
                    </option>
                </c:forEach>
            </select>
        </label>

        <label class="label">产销国家:
            <select name="country" class=" input-mini">
                <option value="">--</option>
                <c:forEach items="${countryList}" var="countryItem">
                    <option value="${countryItem.country}"
                            <c:if test="${countryItem.country eq country}">selected="selected" </c:if>>
                            ${countryItem.country}
                    </option>
                </c:forEach>
            </select>
        </label>

        <label class="label">城市:
            <select name="city" class=" input-mini">
                <option value="">--</option>
                <c:forEach items="${cityList}" var="cityItem">
                    <option value="${cityItem.city}"
                            <c:if test="${cityItem.city eq city}">selected="selected" </c:if>>
                            ${cityItem.city}
                    </option>
                </c:forEach>
            </select>
        </label>

        <br/>

        <label class="label">
            产品代码:<input name="productCode" cssClass="input-mini search-query"
                        <c:if test='${productCode ne null}'>value="${productCode}" </c:if> />
        </label>
        <label class="label">
            产品名称:<input name="productName" cssClass="input-mini search-query"
                        <c:if test='${productName ne null}'>value="${productName}" </c:if> />
        </label>

        <br/>

        <label class="label">进出口类型:
            <select name="impExpType" class=" input-small">
                <option value="0" <c:if test="${impExpType eq 0}">selected="selected" </c:if>>进口</option>
                <option value="1" <c:if test="${impExpType eq 1}">selected="selected" </c:if>>出口</option>
            </select>
        </label>


            <button type="submit" class="btn btn-success">
                <i class="icon-search icon-white"></i>查询
            </button>
        <%--<div class="btn-group">--%>
            <input type="button" class="btn btn-primary" value="生成曲线"/>
        <%--</div>--%>
    </form:form>

    <table class="table table-bordered table-striped table-condensed">
        <thead>
        <tr>
            <th>曲线</th>
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
                <td><form:checkbox path="commonDto.ids" value="${detail.id}"/></td>
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