<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <c:set var="ctx" value="${pageContext.request.contextPath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><decorator:title default="进出口管理系统"/></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap/css/bootstrap.min.css' />"/>
    <link rel="stylesheet" type="text/css"  href="<c:url value='/resources/bootstrap/css/bootstrap-responsive.min.css' />"/>

    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/main.css" />"/>
    <link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/message/messages.css" />"/>

    <script type="text/javascript" src="<c:url value="/resources/js/jquery/1.7.2/jquery.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/jquery.json-2.4.js" />"></script>

    <script type="text/javascript" src="<c:url value='/resources/bootstrap/js/bootstrap.min.js' />"></script>
    <script type="text/javascript" src="<c:url value='/resources/bootstrap/js/bootstrap-dropdown.js' />"></script>

    <link rel="stylesheet" href="<c:url value='/resources/jquery-ui/custom-theme/jquery-ui-1.8.16.custom.css'/>" />
    <script src="<c:url value='/resources/jquery-ui/jquery-ui-1.8.16.custom.min.js'/>"></script>

    <script type="text/javascript" src="<c:url value="/resources/js/trade.js"/> "></script>

    <decorator:head />
</head>
<body style="padding-top: 60px;/*background-color: rgb(202, 215, 247)*/">

<%--<jsp:include page="breadcrumb.jsp" />--%>

<div class="wrapper">
    <c:if test="${not empty message or not empty error}">
        <div class="messagePlace"  style="padding-top: 60px">
            <c:if test="${not empty message}">
                <div id="message" class="success" style="text-align: center;">${message}</div>
            </c:if>

            <c:if test="${not empty error}">
                <div id="errorMsg" class="error" style="text-align: center;">${error}</div>
            </c:if>
        </div>
    </c:if>

    <decorator:body/>
</div>

</body>
</html>