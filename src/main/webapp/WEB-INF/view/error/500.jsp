<%@ page import="java.io.PrintWriter" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <title>500</title>
</head>
<body>
    服务器内部错误!!
    <%Exception ex=(Exception)request.getAttribute("exception");%>
    <H2>Exception:</H2>
    <%ex.printStackTrace(new PrintWriter(out));%>
    <!-- <c:out value="${exception}"></c:out> --!>
</body>
</html>