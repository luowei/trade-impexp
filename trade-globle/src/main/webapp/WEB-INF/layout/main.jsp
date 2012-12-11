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
    <script type="text/javascript" src="<c:url value='/resources/bootstrap/js/bootstrap.js' />"></script>
    <script type="text/javascript" src="<c:url value='/resources/bootstrap/js/bootstrap-dropdown.js' />"></script>

    <script type="text/javascript" src="<c:url value="/resources/js/trade.js"/> "></script>

    <decorator:head />
</head>
<body style="padding-top: 60px">

<div class="navbar navbar-fixed-top ">
    <div class="navbar-inner">

        <div class="container">
            <a class="brand" href="#">进出口管理</a>

            <div class="nav-collapse collapse navbar-responsive-collapse">

                <ul class="nav">
                    <li>
                        <a href="${pageContext.request.contextPath}/manage/configlist">配置</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/manage/import">导入</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/manage/listdetail/1">明细表</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/manage/list/detailCount/1">明细统计表</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/manage/listsum/1">总表</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/manage/list/allfilter">条件表</a>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">查看日志 <b class="caret"></b></a>
                        <ul class="dropdown-menu">
                            <li><a href="${pageContext.request.contextPath}/manage/listlog/0/1">明细表导入日志</a></li>
                            <li><a href="${pageContext.request.contextPath}/manage/listlog/1/1">总表导入日志</a></li>
                        </ul>
                    </li>

                    <li>
                        <a href="javascript:" onclick="history.back();">返回</a>
                    </li>
                </ul>
            </div>

        </div>
    </div>
</div>

<div class="container" >
    <c:if test="${not empty message or not empty error}">
        <div class="messagePlace"  style="padding-top: 30px">
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


<%--<div class="footer">--%>
    <!--<div class="footer_inner">-->
    <!--<p class="copyright">design by luowei e-mail:luowei010101@gmail.com</p>-->
    <!--</div>-->

<%--</div>--%>
</body>
</html>