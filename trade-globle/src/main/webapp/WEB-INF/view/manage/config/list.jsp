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
    <title>配置管理</title>

    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap/css/bootstrap.min.css' />"/>
    <link rel="stylesheet" type="text/css"
          href="<c:url value='/resources/bootstrap/css/bootstrap-responsive.min.css' />"/>

    <script type="text/javascript">

        function init() {
            if (confirm("确实要手动执行生成最新最热数据及快照吗？")) {
                return true;
            }
            return false;
        }

    </script>
</head>
<body >

<%--<jsp:include page="../../common/breadcrumb.jsp" />--%>


    <h3>数据库表与Access表字段对应的配置</h3>
    <form:form modelAttribute="userCommand" method="post" id="form1">
        <table class="table table-bordered table-striped table-condensed">
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
                    <tr>
                        <td>${configBean.key}</td>
                        <td>${configBean.name}</td>
                        <td>${configBean.value}</td>
                        <td>${configBean.describe}</td>
                        <td>
                            <s:url var="toedit" value="/manage/toeditcfg/${configBean.key}"/>
                            <a href="${toedit}" class="btn btn-info">修改</a>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </form:form>
</div>

<hr/>

<div class="container">
    <h3>数据库表与Excel表字段对应的配置</h3>
    <form:form modelAttribute="userCommand" method="post" id="form1">
        <table class="table table-bordered table-striped table-condensed">
            <thead>
            <tr>
                <th><a href="#">key</a></th>
                <th><a href="#">name</a></th>
                <th><a href="#">value(Excel文件中的列名)</a></th>
                <th><a href="#">描述信息</a></th>
                <th><a href="#">修改</a></th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${configmaps}" var="entry" varStatus="st">
                <c:set var="configBean" value="${entry.value}"/>
                <c:if test="${fn:startsWith(entry.key,'excel_')}">
                    <tr>
                        <td>${configBean.key}</td>
                        <td>${configBean.name}</td>
                        <td>${configBean.value}</td>
                        <td>${configBean.describe}</td>
                        <td>
                            <s:url var="toedit" value="/manage/toeditcfg/${configBean.key}"/>
                            <a href="${toedit}" class="btn btn-info">修改</a>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </form:form>
</div>

<hr/>

<div class="container">
    <h3>系统配置</h3>
    <form:form modelAttribute="userCommand" method="post" id="form1">
        <table class="table table-bordered table-striped table-condensed">
            <thead>
            <tr>
                <th><a href="#">key</a></th>
                <th><a href="#">name</a></th>
                <th><a href="#">value</a></th>
                <th><a href="#">describe</a></th>
                <th><a href="#">修改</a></th>
            </tr>
            </thead>

            <tbody>
            <c:forEach items="${configmaps}" var="entry" varStatus="st">
                <c:set var="configBean" value="${entry.value}"/>
                <c:if test="${!fn:startsWith(entry.key,'access_') && !fn:startsWith(entry.key,'excel_')}">
                    <tr>
                        <td>${configBean.key}</td>
                        <td>${configBean.name}</td>
                        <td>${configBean.value}</td>
                        <td>${configBean.describe}</td>
                        <td>
                            <s:url var="toedit" value="/manage/toeditcfg/${configBean.key}"/>
                            <a href="${toedit}" class="btn btn-info">修改</a>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </form:form>


</body>
</html>
