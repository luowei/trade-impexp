<%--
  Created by IntelliJ IDEA.
  User: luowei
  Date: 12-11-9
  Time: 上午8:58
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
    <title>数据导入</title>
</head>
<body>
<h1>明细导入</h1>
<c:url var="importDetail" enctype="multipart/form-data" value="/manage/trade/importdetail"
    <form id="importDetail" action="${importDetail}" method="post">
    进出口类型:
    <select name="impExpType">
        <option name="1">进口</option>
        <option name="2">出口</option>
    </select>

    年:
    <select name="year">
        <c:forEach var="year" begin="2000" end="2050" step="1">
            <option name="${year}">${year}</option>
        </c:forEach>
    </select>

    月:
    <select name="month">
        <c:forEach var="month" begin="1" end="12" step="1">
            <option name="${month}">${month}</option>
        </c:forEach>
    </select>
    <br/>
    zip或rar的Access数据文件包:
    <input name="file" type="file">
    <br/>
    <input type="submit" value="提交">
</form>

<hr/>
<h1>明细导入</h1>
<c:url var="importSum" value="/manage/trade/importsum"
    <form id="importSum" enctype="multipart/form-data" action="${importSum}" method="post">
    进出口类型:
    <select name="impExpType">
        <option name="1">进口</option>
        <option name="2">出口</option>
    </select>

    年:
    <select name="year">
        <c:forEach var="year" begin="2000" end="2050" step="1">
            <option name="${year}">${year}</option>
        </c:forEach>
    </select>

    月:
    <select name="month">
        <c:forEach var="month" begin="1" end="12" step="1">
            <option name="${month}">${month}</option>
        </c:forEach>
    </select>
    <br/>
    zip或rar或xsl格式的数据文件:
    <input name="file" type="file">
    <br/>
    <input type="submit" value="提交">
</form>

</body>
</html>