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

    <script type="text/javascript">
        function changeProductTypeInput() {
            var selectVal = $("select[name=productType]").val().trim();
            $("input[name=productType]").val(selectVal);
        }

        function checkValue() {
            var inputVal = $("input[name=productType]").val().trim();
            var selectVal = $("select[name=productType]").val().trim();
            if (inputVal != selectVal) {
                if (selectVal != "") {
                    $("input[name=productType]").val(selectVal);
                }
            }
        }

    </script>

</head>
<body style="padding-top: 60px">

<jsp:include page="../../common/breadcrumb.jsp" />


<div class="container">
    <h3>明细表导入</h3>
    <c:url var="importDetail" value="/manage/importdetail"/>
    <form id="importDetail" class="well form-inline"
          enctype="multipart/form-data" action="${importDetail}" method="post">

        <h5>上传文件：</h5>

        <input type="hidden" name="tableType" value="明细表">

        <label class="label"> 年月:
            <select name="year" class="input-mini">
                <option value="">--</option>
                <c:forEach var="year" begin="2000" end="2050" step="1">
                    <option value="${year}">${year}</option>
                </c:forEach>
            </select>年
            <select name="month" class="input-mini">
                <option value="">--</option>
                <c:forEach var="month" begin="1" end="12" step="1">
                    <option value="${month}">${month}</option>
                </c:forEach>
            </select>月
        </label>

        <br/>

        <label class="label">zip或rar的Access数据包文件包:</label>
        <input name="file" type="file">


        <br/>

        <label class="label"> 进出口类型:
            <select name="impExpType" class="input-mini">
                <option value="">--</option>
                <option value="0">进口</option>
                <option value="1">出口</option>
            </select>
        </label>

        <%--<label class="label"> 导入方式:--%>
        <%--<select name="importType" class="input-mini">--%>
        <%--<option value="0" selected="selected">自动</option>--%>
        <%--<option value="1">手动</option>--%>
        <%--</select>--%>
        <%--</label>--%>

        <br/> <br/>

        <div class="row">
            <div class="span1">
                <input type="submit" class="btn-success" value="提交">
            </div>

            <div class="span1">
                <a class="span1 btn btn-small btn-primary"
                   href="${pageContext.request.contextPath}/manage/listlog/0/1">查看日志</a>
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
                <option value="">--</option>
                <c:forEach var="year" begin="2000" end="2050" step="1">
                    <option value="${year}">${year}</option>
                </c:forEach>
            </select>年
            <select name="month" class="input-mini">
                <option value="">--</option>
                <c:forEach var="month" begin="1" end="12" step="1">
                    <option value="${month}">${month}</option>
                </c:forEach>
            </select>月
        </label>

        <br/>

        <label class="label">zip或rar格式的excel数据包文件:</label>
        <input name="file" type="file">

        <br/>

        <label class="label"> 产品类型:
            <input type="text" name="sumType" class="input-small" onblur="checkValue()">
            <select name="sumType" class="input-mini" onchange="changeProductTypeInput()">
                <option value="" selected="selected">--</option>
                <c:forEach var="sumType" items="${sumTypeList}">
                    <option value="${sumType.sumType}">
                            ${sumType.sumType}</option>
                </c:forEach>
            </select>
        </label>

        <label class="label">进出口类型:
            <select name="impExpType" class="input-mini">
                <option value="">--</option>
                <option value="0">进口</option>
                <option value="1">出口</option>
            </select>
        </label>

        <%--<label class="label">导入方式:--%>
        <%--<select name="importType" class="input-mini">--%>
        <%--<option value="0" selected="selected">自动</option>--%>
        <%--<option value="1">手动</option>--%>
        <%--</select>--%>
        <%--</label>--%>

        <br/><br/>

        <div class="row">
            <div class="span1">
                <input type="submit" class="btn-success" value="提交">
            </div>

            <div class="span1">
                <a class="span1 btn btn-small btn-primary"
                   href="${pageContext.request.contextPath}/manage/listlog/1/1">查看日志</a>
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