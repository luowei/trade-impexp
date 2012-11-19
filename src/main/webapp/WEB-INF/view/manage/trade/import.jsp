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
<c:if test="${message ne null}">
    <h3 style="color: #4169e1;">${message}</h3>
</c:if>

<h1>明细导入</h1>

<h2>上传文件：</h2>
<c:url var="importDetail" value="/manage/importdetail"/>
<form id="importDetail" enctype="multipart/form-data" action="${importDetail}" method="post">
    进出口类型:
    <input type="hidden" name="tableType" value="明细表">
    <select name="impExpType">
        <option value="0">进口</option>
        <option value="1">出口</option>
    </select>

    <br/>年月:
    <select name="year">
        <c:forEach var="year" begin="2000" end="2050" step="1">
            <option value="${year}">${year}</option>
        </c:forEach>
    </select>
    年
    <select name="month">
        <c:forEach var="month" begin="1" end="12" step="1">
            <option value="${month}">${month}</option>
        </c:forEach>
    </select>
    月

    <br/>zip或rar的Access数据文件包: <br/>
    <input name="file" type="file">
    <br/>上传后手动导入,还是立即后台自动导入:
    <select name="importType">
        <option value="0" selected="selected">自动</option>
        <option value="1">手动</option>
    </select>
    <br/> <br/>
    <input type="submit" value="提交">
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="/manage/trade/log/listlog">查看日志</a>
</form>


<hr/>
<h1>总表导入</h1>

<h2>上传文件:</h2>
<c:url var="importSum" value="/manage/importsum"/>
<form id="importSum" enctype="multipart/form-data" action="${importSum}" method="post">
    进出口类型:
    <input type="hidden" name="tableType" value="总表">
    <select name="impExpType">
        <option value="0">进口</option>
        <option value="1">出口</option>
    </select>

    <br/>年月:
    <select name="year">
        <c:forEach var="year" begin="2000" end="2050" step="1">
            <option value="${year}">${year}</option>
        </c:forEach>
    </select>
    年
    <select name="month">
        <c:forEach var="month" begin="1" end="12" step="1">
            <option value="${month}">${month}</option>
        </c:forEach>
    </select>
    月

    <script language="javascript" type="text/javascript" src="resources/js/textselect.js"></script>
    <br/>产品类型:
    <input type="text" name="" size="10" onfocus="javascript:showSelect(this,'productType')">
    <select name="productType" >
        <c:forEach var="productType" items="${productTypeList}">
            <option value="${productType.productType}">${productType.productType}</option>
        </c:forEach>
    </select>

    <br/>
    zip或rar或xsl格式的数据文件: <br/>
    <input name="file" type="file">

    <br/>上传手手动导入,还是立即后台自动导入:
    <select name="importType">
        <option value="0" selected="selected">自动</option>
        <option value="1">手动</option>
    </select>
    <br/> <br/>
    <input type="submit" value="提交">
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    <a href="/manage/trade/log/listlog">查看日志</a>
</form>


</body>
</html>