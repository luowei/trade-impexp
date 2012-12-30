<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>明细表配置</title>

</head>
<body >

<%--<jsp:include page="../../common/breadcrumb.jsp" />--%>

<div class="navbar navbar-fixed-top ">
    <div class="navbar-inner">

        <div class="wrapper">
            <a class="brand" href="#">进出口管理</a>

            <div class="nav-collapse collapse navbar-responsive-collapse">
                <ul class="nav nav-tabs">
                    <li><a href="${pageContext.request.contextPath}/manage/acsconf">配置</a></li>
                    <li> <a href="javascript:" onclick="history.back();">返回</a></li>
                </ul>
            </div>

        </div>
    </div>
</div>


<h3>数据库表与Access表字段对应的配置</h3>
    <table class="table  table-hover table-bordered table-striped table-condensed">
        <thead>
        <tr>
            <th><a href="#" id="key">key</a></th>
            <th><a href="#" id="name">name</a></th>
            <th><a href="#" id="value">value(access文件中的列名)</a></th>
            <th><a href="#" id="describe">描述信息</a></th>
            <th><a href="#" id="update">修改</a></th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${configmaps}" var="entry" varStatus="st">
            <c:set var="configBean" value="${entry.value}"/>
            <c:if test="${fn:startsWith(entry.key,'access_')}">
                <tr <c:if test="${st.index%2 eq 1}">class="warning" </c:if>>
                    <td>${configBean.key}</td>
                    <td>${configBean.name}</td>
                    <td>${configBean.value}</td>
                    <td>${configBean.describe}</td>
                    <td>
                        <s:url var="toedit" value="/manage/toeditcfg/${configBean.key}/acs"/>
                        <a href="${toedit}" class="btn btn-info">修改</a>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>

</body>
</html>
