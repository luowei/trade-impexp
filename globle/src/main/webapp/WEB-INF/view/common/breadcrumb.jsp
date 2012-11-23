<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<ul class="breadcrumb" style="border-top:none; border-left: none; padding-left: 25px;">
    <li>
        <a href="${pageContext.request.contextPath}/manage/configlist">配置管理</a> <span class="divider">|</span>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/manage/import">导入</a> <span class="divider">|</span>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/manage/listdetail/1">明细表</a> <span class="divider">|</span>
    </li>
    <li>
        <a href="${pageContext.request.contextPath}/manage/listsum/1">总表</a> <span class="divider">|</span>
    </li>
    <li>
        <a href="javascript:" onclick="history.back();">返回</a>
    </li>
</ul>

<c:if test="${message ne null}">
    <div class="container">
        <h3 style="color: #4169e1;">${message}</h3>
    </div>
</c:if>