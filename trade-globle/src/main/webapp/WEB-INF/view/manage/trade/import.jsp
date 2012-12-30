<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>数据导入</title>

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

<div class="navbar navbar-fixed-top ">
    <div class="navbar-inner">

        <div class="wrapper">
            <a class="brand" href="#">进出口管理</a>

            <div class="nav-collapse collapse navbar-responsive-collapse">
                <ul class="nav nav-tabs">
                    <%--<li><a target="_blank" href="${pageContext.request.contextPath}/manage/configlist">配置</a></li>--%>
                        <li><a target="_blank" href="${pageContext.request.contextPath}/manage/sysconf">系统配置</a></li>
                        <li><a target="_blank" href="${pageContext.request.contextPath}/manage/acsconf">明细表配置</a></li>
                        <li><a target="_blank" href="${pageContext.request.contextPath}/manage/exlconf">总表配置</a></li>
                    <li><a href="${pageContext.request.contextPath}/manage/import">导入</a></li>
                    <li><a target="_blank" href="${pageContext.request.contextPath}/manage/listlog/0/1">明细表导入日志</a></li>
                    <li><a target="_blank" href="${pageContext.request.contextPath}/manage/listlog/1/1">总表导入日志</a></li>

                    <li> <a target="_blank" href="${pageContext.request.contextPath}/manage/listdetail/1">明细表</a></li>
                    <li><a target="_blank" href="${pageContext.request.contextPath}/manage/listProduct/1">明细产品表</a></li>

                    <li> <a target="_blank" href="${pageContext.request.contextPath}/manage/toGenCount">统计数据生成</a></li>

                    <li><a target="_blank" href="${pageContext.request.contextPath}/manage/list/detailCount/1">明细统计</a></li>
                    <li><a target="_blank" href="${pageContext.request.contextPath}/manage/hchart/toNMDetail">自然月明细统计</a></li>
                    <li><a target="_blank" href="${pageContext.request.contextPath}/manage/hchart/toSMDetail">同期月明细统计</a></li>

                    <li><a target="_blank" href="${pageContext.request.contextPath}/manage/listsum/1">总表</a></li>

                    <li><a target="_blank" href="${pageContext.request.contextPath}/manage/list/companyType">企业性质表</a></li>
                    <li><a target="_blank" href="${pageContext.request.contextPath}/manage/list/tradeType">贸易类型表</a></li>
                    <li><a target="_blank" href="${pageContext.request.contextPath}/manage/list/transportation">运输方式表</a></li>
                    <li><a target="_blank" href="${pageContext.request.contextPath}/manage/list/customs">海关表</a></li>
                    <li><a target="_blank" href="${pageContext.request.contextPath}/manage/list/country">产销国家表</a></li>
                    <li><a target="_blank" href="${pageContext.request.contextPath}/manage/list/city">出口城市表</a></li>

                    <li><a target="_blank" href="${pageContext.request.contextPath}/manage/list/detailType">明细表产品类型表</a></li>
                    <li><a target="_blank" href="${pageContext.request.contextPath}/manage/list/sumType">总表产品类型表</a></li>

                    <li><a href="javascript:" onclick="history.back();">返回</a></li>
                </ul>
            </div>

        </div>
    </div>
</div>


<h3  style="padding-top: 100px">明细表导入</h3>
<c:url var="importDetail" value="/manage/importdetail"/>
<form id="importDetail" class="well form-inline"
      enctype="multipart/form-data" action="${importDetail}" method="post">

    <h5>上传文件：</h5>

    <input type="hidden" name="tableType" value="明细表">

    <label class="label"> 年月:
        <select name="year" class="input-small">
            <option value="">--</option>
            <c:forEach var="year" begin="2000" end="2050" step="1">
                <option value="${year}">${year}</option>
            </c:forEach>
        </select>年
        <select name="month" class="input-small">
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
        <select name="impExpType" class="input-small">
            <option value="">--</option>
            <option value="0">进口</option>
            <option value="1">出口</option>
        </select>
    </label>


    <br/> <br/>

    <div class="row">
        <div class="span1">
            <input type="submit" class="btn btn-small btn-success" value="提交">
        </div>

        <div class="span1">
            <a target="_blank" class="span1 btn btn-small btn-primary"
               href="${pageContext.request.contextPath}/manage/listlog/0/1">查看日志</a>
        </div>

        <div class="span1 offset1">
            <a target="_blank" class="span1 btn btn-small btn-primary"
               href="${pageContext.request.contextPath}/manage/listdetail/1">查看明细表</a>
        </div>

        <div class="span1 offset1">
            <a target="_blank" class="span1 btn btn-small btn-primary"
               href="${pageContext.request.contextPath}/manage/acsconf">明细表配置</a>
        </div>

    </div>

</form>


<hr/>


<h3>总表导入</h3>

<c:url var="importSum" value="/manage/importsum"/>
<form id="importSum" class="well form-inline"
      enctype="multipart/form-data" action="${importSum}" method="post">

    <h5>上传文件:</h5>

    <input type="hidden" name="tableType" value="总表">


    <label class="label">年月:
        <select name="year" class="input-small">
            <option value="">--</option>
            <c:forEach var="year" begin="2000" end="2050" step="1">
                <option value="${year}">${year}</option>
            </c:forEach>
        </select>年
        <select name="month" class="input-small">
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
        <input type="text" name="productType" class="input-small" onblur="checkValue()">
        <select name="productType" class="input-small" onchange="changeProductTypeInput()">
            <option value="" selected="selected">--</option>
            <c:forEach var="sumType" items="${sumTypeList}">
                <option value="${sumType.sumType}">
                        ${sumType.sumType}</option>
            </c:forEach>
        </select>
    </label>

    <label class="label">进出口类型:
        <select name="impExpType" class="input-small">
            <option value="">--</option>
            <option value="0">进口</option>
            <option value="1">出口</option>
        </select>
    </label>

    <%--<label class="label">导入方式:--%>
    <%--<select name="importType" class="input-small">--%>
    <%--<option value="0" selected="selected">自动</option>--%>
    <%--<option value="1">手动</option>--%>
    <%--</select>--%>
    <%--</label>--%>

    <br/><br/>

    <div class="row">
        <div class="span1">
            <input type="submit" class="btn btn-small btn-success" value="提交">
        </div>

        <div class="span1">
            <a target="_blank" class="span1 btn btn-small btn-primary"
               href="${pageContext.request.contextPath}/manage/listlog/1/1">查看日志</a>
        </div>

        <div class="span1 offset1">
            <a target="_blank" class="span1 btn btn-small btn-primary"
               href="${pageContext.request.contextPath}/manage/listsum/1">查看总表</a>
        </div>

        <div class="span1 offset1">
            <a target="_blank" class="span1 btn btn-small btn-primary"
               href="${pageContext.request.contextPath}/manage/exlconf">总表配置</a>
        </div>
    </div>
</form>


</body>
</html>