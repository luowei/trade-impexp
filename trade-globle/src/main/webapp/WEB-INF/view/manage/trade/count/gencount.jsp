<%--
  Created by IntelliJ IDEA.
  User: luowei
  Date: 12-12-13
  Time: 下午4:20
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
    <title>统计数据生成</title>
</head>
<body>

<div class="navbar navbar-fixed-top ">
    <div class="navbar-inner">

        <div class="wrapper">
            <a class="brand" href="#">进出口管理</a>

            <div class="nav-collapse collapse navbar-responsive-collapse">
                <ul class="nav nav-tabs">
                    <li><a href="${pageContext.request.contextPath}/manage/toGenCount">统计数据生成</a> </li>
                    <li><a href="javascript:" onclick="history.back();">返回</a></li>
                </ul>
            </div>

        </div>
    </div>
</div>

<h2>生成明细表按年月的统计数据</h2>
<form:form id="genCountForm" modelAttribute="yearMonthDto"
           action="${pageContext.request.contextPath}/manage/genDetailCount"
           method="post" cssClass="well form-inline">
    <label class="label "> 请选择年月与进出口类型:
        <select name="countYear" class=" input-small" onchange="">
            <option value="" selected="selected">--</option>
            <c:forEach var="lyr" begin="2000" end="2050" step="1">
                <option value="${lyr}" <c:if test="${countYear eq lyr}">selected="selected" </c:if>>
                        ${lyr}</option>
            </c:forEach>
        </select> 年&nbsp;
        <select name="countMonth" class=" input-small">
            <option value="" selected="selected">--</option>
            <c:forEach var="lmth" begin="1" end="12" step="1">
                <option value="${lmth}" <c:if test="${countMonth eq lmth}">selected="selected" </c:if>>
                        ${lmth}</option>
            </c:forEach>
        </select>月
            <%--</label>--%>

            <%--<label class="label">进出口类型:--%>
        <select name="countImpExp" class=" input-small">
            <option value="0" <c:if test="${countImpExp eq 0}">selected="selected" </c:if>>进口</option>
            <option value="1" <c:if test="${countImpExp eq 1}">selected="selected" </c:if>>出口</option>
        </select>

        &nbsp;&nbsp;
        <button type="submit" class="btn btn-success">重新生成按年月明细总统计数据</button>
    </label> &nbsp;&nbsp;&nbsp;&nbsp;

    <br/>  <hr/>

    <a href="javascript:if(confirm('您确定要一键重新生成所有按年月明细总统计数据吗?'))
            location.href='${pageContext.request.contextPath}/manage/genAllDetailCount'"
       class="btn btn-warning">一键重新生成 -> 所有按年月明细统计数据</a>

</form:form>



<h2>更新明细表产品类型</h2>
<form:form id="detailTypeForm" modelAttribute="yearMonthDto"
           action="${pageContext.request.contextPath}/manage/updateDetailType"
           method="post" cssClass="well form-inline">
<label class="label "> 请选择年月与进出口类型:
    <select name="year" class=" input-small" onchange="">
        <option value="" selected="selected">--</option>
        <c:forEach var="lyr" begin="2000" end="2050" step="1">
            <option value="${lyr}" <c:if test="${countYear eq lyr}">selected="selected" </c:if>>
                    ${lyr}</option>
        </c:forEach>
    </select> 年&nbsp;
    <select name="month" class=" input-small">
        <option value="" selected="selected">--</option>
        <c:forEach var="lmth" begin="1" end="12" step="1">
            <option value="${lmth}" <c:if test="${countMonth eq lmth}">selected="selected" </c:if>>
                    ${lmth}</option>
        </c:forEach>
    </select>月
        <%--</label>--%>

        <%--<label class="label">进出口类型:--%>
    <select name="impExpType" class=" input-small">
        <option value="0" <c:if test="${impExpType eq 0}">selected="selected" </c:if>>进口</option>
        <option value="1" <c:if test="${impExpType eq 1}">selected="selected" </c:if>>出口</option>
    </select>

    &nbsp;&nbsp;
    <button type="submit" class="btn btn-success">重新更新明细表产品类型</button>
    </form:form>
</label>

<%--<hr/>--%>

<%--<h2>生成明细表按贸易方式的统计数据</h2>--%>
<%--<form:form id="genCountForm" modelAttribute="yearMonthDto"--%>
           <%--action="${pageContext.request.contextPath}/manage/genDetailTradeType"--%>
           <%--method="post" cssClass="well form-inline">--%>
    <%--<label class="label "> 请选择年月与进出口类型:--%>
        <%--<select name="countYear" class=" input-small" onchange="">--%>
            <%--<option value="" selected="selected">--</option>--%>
            <%--<c:forEach var="lyr" begin="2000" end="2050" step="1">--%>
                <%--<option value="${lyr}" <c:if test="${countYear eq lyr}">selected="selected" </c:if>>--%>
                        <%--${lyr}</option>--%>
            <%--</c:forEach>--%>
        <%--</select> 年&nbsp;--%>
        <%--<select name="countMonth" class=" input-small">--%>
            <%--<option value="" selected="selected">--</option>--%>
            <%--<c:forEach var="lmth" begin="1" end="12" step="1">--%>
                <%--<option value="${lmth}" <c:if test="${countMonth eq lmth}">selected="selected" </c:if>>--%>
                        <%--${lmth}</option>--%>
            <%--</c:forEach>--%>
        <%--</select>月--%>
            <%--&lt;%&ndash;</label>&ndash;%&gt;--%>

            <%--&lt;%&ndash;<label class="label">进出口类型:&ndash;%&gt;--%>
        <%--<select name="countImpExp" class=" input-small">--%>
            <%--<option value="0" <c:if test="${countImpExp eq 0}">selected="selected" </c:if>>进口</option>--%>
            <%--<option value="1" <c:if test="${countImpExp eq 1}">selected="selected" </c:if>>出口</option>--%>
        <%--</select>--%>

        <%--&nbsp;&nbsp;--%>
        <%--<button type="submit" class="btn btn-success">生成按贸易方式统计数据</button>--%>
    <%--</label> &nbsp;&nbsp;&nbsp;&nbsp;--%>


    <%--<a href="javascript:if(confirm('您确定要一键重新生成所有按贸易方式明细统计数据吗?'))--%>
            <%--location.href='${pageContext.request.contextPath}/manage/genAllDetailCount'"--%>
       <%--class="btn btn-warning">一键重新生成 -> 所有按贸易方式明细统计数据</a>--%>
<%--</form:form>--%>

<%--<hr/>--%>

<%--<h2>生成按企业性质的明细表统计数据</h2>--%>
<%--<form:form id="genCountForm" modelAttribute="yearMonthDto"--%>
           <%--action="${pageContext.request.contextPath}/manage/genDetailCompanyType"--%>
           <%--method="post" cssClass="well form-inline">--%>
    <%--<label class="label "> 请选择年月与进出口类型:--%>
        <%--<select name="countYear" class=" input-small" onchange="">--%>
            <%--<option value="" selected="selected">--</option>--%>
            <%--<c:forEach var="lyr" begin="2000" end="2050" step="1">--%>
                <%--<option value="${lyr}" <c:if test="${countYear eq lyr}">selected="selected" </c:if>>--%>
                        <%--${lyr}</option>--%>
            <%--</c:forEach>--%>
        <%--</select> 年&nbsp;--%>
        <%--<select name="countMonth" class=" input-small">--%>
            <%--<option value="" selected="selected">--</option>--%>
            <%--<c:forEach var="lmth" begin="1" end="12" step="1">--%>
                <%--<option value="${lmth}" <c:if test="${countMonth eq lmth}">selected="selected" </c:if>>--%>
                        <%--${lmth}</option>--%>
            <%--</c:forEach>--%>
        <%--</select>月--%>
            <%--&lt;%&ndash;</label>&ndash;%&gt;--%>

            <%--&lt;%&ndash;<label class="label">进出口类型:&ndash;%&gt;--%>
        <%--<select name="countImpExp" class=" input-small">--%>
            <%--<option value="0" <c:if test="${countImpExp eq 0}">selected="selected" </c:if>>进口</option>--%>
            <%--<option value="1" <c:if test="${countImpExp eq 1}">selected="selected" </c:if>>出口</option>--%>
        <%--</select>--%>

        <%--&nbsp;&nbsp;--%>
        <%--<button type="submit" class="btn btn-success">生成按企业性质统计数据</button>--%>
    <%--</label> &nbsp;&nbsp;&nbsp;&nbsp;--%>


    <%--<a href="javascript:if(confirm('您确定要一键重新生成所有按企业性质明细统计数据吗?'))--%>
            <%--location.href='${pageContext.request.contextPath}/manage/genAllDetailCount'"--%>
       <%--class="btn btn-warning">一键重新生成 -> 所有按企业性质明细统计数据</a>--%>
<%--</form:form>--%>

</body>
</html>