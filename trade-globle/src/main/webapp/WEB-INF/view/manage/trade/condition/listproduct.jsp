<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 12-12-6
  Time: 上午9:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>明细产品表</title>
</head>
<body>

<div class="navbar navbar-fixed-top ">
    <div class="navbar-inner">

        <div class="wrapper">
            <a class="brand" href="#">进出口管理</a>

            <div class="nav-collapse collapse navbar-responsive-collapse">
                <ul class="nav nav-tabs">
                    <li><a href="${pageContext.request.contextPath}/manage/listProduct/1">明细产品表</a></li>

                    <li><a href="javascript:" onclick="history.back();">返回</a></li>
                </ul>
            </div>

        </div>
    </div>
</div>

<h2>进出口明细产品列表</h2>
<form:form id="form1" modelAttribute="product"
           action="${pageContext.request.contextPath}${contextUrl}/1"
           method="post" cssClass="well form-inline">
    <input type="hidden" id="pageSize" name="pageSize" value="${pageSize}"/>
    <input type="hidden" id="sort" name="sort" value="${sort}"/>

    <label class="label">产品代码:
        <input name="productCode" cssClass="input-small search-query"  style="width: 80px"
               <c:if test='${productCode ne null}'>value="${productCode}" </c:if> />
    </label>

    <label class="label">产品名称:
        <input name="productName" cssClass="input-small search-query"
                    <c:if test='${productName ne null}'>value="${productName}" </c:if> />
    </label>

    <label class="label">类型代码:
        <input name="typeCode" cssClass="input-small search-query" style="width: 50px"
               <c:if test='${typeCode ne null}'>value="${typeCode}" </c:if> />
    </label>

    <label class="label">产品类型:
        <input name="productType" cssClass="input-small search-query"
               <c:if test='${productType ne null}'>value="${productType}" </c:if> />
    </label>

    &nbsp;&nbsp;
    <button type="submit" class="btn btn-success">
        <i class="icon-search icon-white"></i>查询
    </button>

    &nbsp;&nbsp;
    <a href="#" id="dialog_link" class="btn btn-primary">导出数据</a>

    <div class="pagination-centered">
        <jsp:include page="../../../common/pagination.jsp"/>
    </div>

    <h4><span> 过滤条件:</span>
        <c:if test="${productCode ne null}">
            <label class="label">产品代码: <span style="color: yellow;">${productCode}</span> </label>
        </c:if>
        <c:if test="${productName ne null}">
            <label class="label">产品名称: <span style="color: yellow;">${productName}</span> </label>
        </c:if>
        <c:if test="${typeCode ne null}">
            <label class="label">类型代码: <span style="color: yellow;">${typeCode}</span> </label>
        </c:if>
        <c:if test="${productType ne null}">
            <label class="label">产品类型: <span style="color: yellow;">${productType}</span> </label>
        </c:if>
    </h4>

    <table class="table table-hover table-bordered table-striped table-condensed">
        <thead>
        <tr>
            <th>id
                <a href="javascript:void(0)" onclick="ascOrder('id','form1')"><i class="icon-chevron-up"></i></a>
                <a href="javascript:void(0)" onclick="descOrder('id','form1')"><i class="icon-chevron-down"></i></a>
            </th>
            <th>产品代码
                <a href="javascript:void(0)" onclick="ascOrder('productCode','form1')"><i class="icon-chevron-up"></i></a>
                <a href="javascript:void(0)" onclick="descOrder('productCode','form1')"><i class="icon-chevron-down"></i></a>
            </th>
            <th>产品名称
                <a href="javascript:void(0)" onclick="ascOrder('productName','form1')"><i class="icon-chevron-up"></i></a>
                <a href="javascript:void(0)" onclick="descOrder('productName','form1')"><i class="icon-chevron-down"></i></a>
            </th>
            <th>类型代码
                <a href="javascript:void(0)" onclick="ascOrder('typeCode','form1')"><i class="icon-chevron-up"></i></a>
                <a href="javascript:void(0)" onclick="descOrder('typeCode','form1')"><i class="icon-chevron-down"></i></a>
            </th>
            <th>产品类型
                <a href="javascript:void(0)" onclick="ascOrder('productType','form1')"><i class="icon-chevron-up"></i></a>
                <a href="javascript:void(0)" onclick="descOrder('productType','form1')"><i class="icon-chevron-down"></i></a>
            </th>
            <%--<th>操作</th>--%>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${productList.content}" var="product" varStatus="st">
            <tr <c:if test="${st.index%2 eq 1}">class="warning" </c:if>>
                <td>${product.id}</td>
                <td>${product.productCode}</td>
                <td>${product.productName}</td>
                <td>${product.typeCode}</td>
                <td>${product.productType}</td>
                <%--<td>--%>
                    <%--&lt;%&ndash;<a href="${pageContext.request.contextPath}/manage/toUpdateProduct/${product.id}'">修改</a>&ndash;%&gt;--%>
                    <%--<a href="javascript:if(confirm('您确定要删除这个产品吗?'))location.href='${pageContext.request.contextPath}/manage/delProduct/${product.id}'">删除</a>--%>
                <%--</td>--%>
            </tr>
        </c:forEach>

        </tbody>
    </table>

    <div class="pagination-centered">
        <jsp:include page="../../../common/pagination.jsp"/>
    </div>
</form:form>

<input type="hidden" id="expAction" name="expAction" value="${pageContext.request.contextPath}/manage/exportProduct">
<jsp:include page="../../../common/exportdata.jsp"/>

</body>
</html>