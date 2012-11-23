<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 12-11-9
  Time: 下午1:40
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
    <title>日志列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap/css/bootstrap.min.css' />"/>
    <link rel="stylesheet" type="text/css"
          href="<c:url value='/resources/bootstrap/css/bootstrap-responsive.min.css' />"/>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/1.7.2/jquery.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/trade.js"/> "></script>

</head>
<body>


<jsp:include page="../../common/breadcrumb.jsp" />

<div class="wrapper">
    <h2>日志列表</h2>

    <table class="table  table-bordered table-striped table-condensed">
        <thead>
        <tr>
            <th>日志类型</th>
            <th>进/出口类型</th>
            <th>表类型</th>
            <th>上传标志</th>
            <th>解压标志</th>
            <th>导入标志</th>
            <th>是否错误</th>
            <th>年-月</th>
            <th>日志时间</th>
            <th>上传后的路径</th>
            <th>解压后的路径</th>
            <th>操作</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach items="${logList.content}" var="log" varStatus="st">
            <tr>
                <td>${log.logType}</td>
                <td>${log.tradeType}</td>
                <td>${log.tableType}</td>
                <td>${log.uploadFlg}</td>
                <td>${log.extractFlag}</td>
                <td>${log.importFlag}</td>
                <td>${log.errorOccur}</td>
                <td>${log.year}-${log.month}</td>
                <td>${log.logTime}</td>
                <td>${log.uploadPath}</td>
                <td>${log.extractPath}</td>
                <td>
                    <a href="" class="btn btn-small btn-primary">重新解压</a>
                    =========
                    <a href="" class="btn btn-small btn-primary btn-success">重新导入</a>
                </td>

            </tr>
        </c:forEach>

        </tbody>
    </table>

</div>

<div class="pagination-centered">
    <jsp:include page="../../common/pagination.jsp"/>
</div>

</body>
</html>