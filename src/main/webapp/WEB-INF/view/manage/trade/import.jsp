<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

<h2>上传文件：</h2>
<c:url var="importDetail" value="/manage/trade/importdetail"/>
<form id="importDetail" enctype="multipart/form-data" action="${importDetail}" method="post">
    进出口类型:
    <input type="hidden" name="tableType" value="明细表">
    <select name="impExpType">
        <option value="1">进口</option>
        <option value="2">出口</option>
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
    zip或rar的Access数据文件包: <br/>
    <input name="file" type="file">
    <br/> <br/>
    <input type="submit" value="上传并导入">
</form>

<hr/>
<h1>总表导入</h1>

<h2>上传文件:</h2>
<c:url var="importSum" value="/manage/trade/importsum"/>
<form id="importSum" enctype="multipart/form-data" action="${importSum}" method="post">
    进出口类型:
    <input type="hidden" name="tableType" value="总表">
    <select name="impExpType">
        <option value="1">进口</option>
        <option value="2">出口</option>
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
    zip或rar或xsl格式的数据文件: <br/>
    <input name="file" type="file">
    <br/> <br/>
    <input type="submit" value="上传并导入">
</form>

</body>
</html>