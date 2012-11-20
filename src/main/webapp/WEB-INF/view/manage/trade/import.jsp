<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>数据导入</title>

    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap/css/bootstrap.min.css' />"/>
    <link rel="stylesheet" type="text/css"
          href="<c:url value='/resources/bootstrap/css/bootstrap-responsive.min.css' />"/>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/1.7.2/jquery.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/js/trade.js"/> "></script>


</head>
<body>
<c:if test="${message ne null}">
    <h3 style="color: #4169e1;">${message}</h3>
</c:if>



<div class="container">
    <h3>明细表导入</h3>
    <c:url var="importDetail" value="/manage/importdetail"/>
    <form id="importDetail" class="well form-inline"
          enctype="multipart/form-data" action="${importDetail}" method="post">

        <h5>上传文件：</h5>

        <input type="hidden" name="tableType" value="明细表">

        <label class="label"> 年月:
            <select name="year" class="input-mini">
                <c:forEach var="year" begin="2000" end="2050" step="1">
                    <option value="${year}">${year}</option>
                </c:forEach>
            </select>年
            <select name="month" class="input-mini">
                <c:forEach var="month" begin="1" end="12" step="1">
                    <option value="${month}">${month}</option>
                </c:forEach>
            </select>月
        </label>

        <br/>

        <label class="label">zip或rar的Access数据文件包:</label>
            <input name="file" type="file">


        <br/>

        <label class="label"> 进出口类型:
            <select name="impExpType" class="input-mini">
                <option value="0">进口</option>
                <option value="1">出口</option>
            </select>
        </label>

        <label class="label"> 导入方式:
            <select name="importType" class="input-mini">
                <option value="0" selected="selected">自动</option>
                <option value="1">手动</option>
            </select>
        </label>

        <br/> <br/>

        <div class="row">
            <div class="span1">
                <input type="submit" class="btn-success" value="提交">
            </div>

            <div class="span1">
                <a class="span1 btn btn-small btn-primary"
                   href="${pageContext.request.contextPath}/manage/listlog/1">查看日志</a>
            </div>

            <div class="span1 offset1">
                <a class="span1 btn btn-small btn-primary"
                   href="${pageContext.request.contextPath}/manage/listdetail/1">查看明细表</a>
            </div>

        </div>

    </form>

</div>

<hr/>


<div class="container">
    <h3>总表导入</h3>

    <c:url var="importSum" value="/manage/importsum"/>
    <form id="importSum" class="well form-inline"
          enctype="multipart/form-data" action="${importSum}" method="post">

        <h5>上传文件:</h5>

        <input type="hidden" name="tableType" value="总表">


        <label class="label">年月:
            <select name="year" class="input-mini">
                <c:forEach var="year" begin="2000" end="2050" step="1">
                    <option value="${year}">${year}</option>
                </c:forEach>
            </select>年
            <select name="month" class="input-mini">
                <c:forEach var="month" begin="1" end="12" step="1">
                    <option value="${month}">${month}</option>
                </c:forEach>
            </select>月
        </label>

        <br/>

        <label class="label">zip或rar或xsl格式的数据文件:</label>
            <input name="file" type="file">

        <br/>

        <label class="label"> 产品类型:
            <input type="text" name="" class="input-small"
                   onfocus="javascript:showSelect(this,'productType')">
            <select name="productType" class="input-mini">
                <c:forEach var="productType" items="${productTypeList}">
                    <option value="${productType.productType}">${productType.productType}</option>
                </c:forEach>
            </select>
        </label>

        <label class="label">进出口类型:
            <select name="impExpType" class="input-mini">
                <option value="0">进口</option>
                <option value="1">出口</option>
            </select>
        </label>

        <label class="label">导入方式:
            <select name="importType" class="input-mini">
                <option value="0" selected="selected">自动</option>
                <option value="1">手动</option>
            </select>
        </label>

        <br/><br/>

        <div class="row">
            <div class="span1">
                <input type="submit" class="btn-success" value="提交">
            </div>

            <div class="span1">
                <a class="span1 btn btn-small btn-primary"
                   href="${pageContext.request.contextPath}/manage/listlog/1">查看日志</a>
            </div>

            <div class="span1 offset1">
                <a class="span1 btn btn-small btn-primary"
                   href="${pageContext.request.contextPath}/manage/listsum/1">查看总表</a>
            </div>
        </div>
    </form>

</div>

</body>
</html>