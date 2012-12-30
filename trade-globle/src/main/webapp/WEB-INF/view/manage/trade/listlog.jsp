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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>日志列表</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <script type="text/javascript">
        function init() {

        }

    </script>

</head>
<body onload="init()">


<div class="navbar navbar-fixed-top ">
    <div class="navbar-inner">

        <div class="wrapper">
            <a class="brand" href="#">进出口管理</a>

            <div class="nav-collapse collapse navbar-responsive-collapse">
                <ul class="nav nav-tabs">
                    <c:if test="${tableType eq '0'}">
                        <li><a href="${pageContext.request.contextPath}/manage/acsconf">配置</a></li>
                        <li><a href="${pageContext.request.contextPath}/manage/listlog/0/1">明细表导入日志</a></li>
                    </c:if>

                    <c:if test="${tableType eq '1'}">
                        <a href="${pageContext.request.contextPath}/manage/exlconf">配置</a>
                        <li><a href="${pageContext.request.contextPath}/manage/listlog/1/1">总表导入日志</a></li>
                    </c:if>

                    <li><a href="${pageContext.request.contextPath}/manage/import">导入</a></li>


                    <li><a href="javascript:" onclick="history.back();">返回</a></li>
                </ul>
            </div>

        </div>
    </div>
</div>

<h2>日志列表</h2>

<div class="pagination-centered">
    <jsp:include page="../../common/pagination.jsp"/>
</div>

<table class="table table-hover table-bordered table-striped table-condensed">
    <thead>
    <tr>
        <th>id</th>
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
        <tr <c:if test="${st.index%2 eq 1}">class="warning" </c:if>>
            <td>${log.id}</td>
            <td>${log.logType}</td>
            <td>${log.tradeType}</td>
            <td>${log.tableType}</td>
            <td>
                <c:if test="${fn:contains(log.uploadFlg ,'成功')}"><strong
                        style="color: green;">${log.uploadFlg}</strong></c:if>
                <c:if test="${fn:contains(log.uploadFlg ,'正在')}"><strong
                        style="color: blue;">${log.uploadFlg}</strong></c:if>
                <c:if test="${fn:contains(log.uploadFlg , '失败')}"><strong
                        style="color: red;">${log.uploadFlg}</strong></c:if>
            </td>
            <td>
                <c:if test="${fn:contains(log.extractFlag ,'成功')}"><strong
                        style="color: green;">${log.extractFlag}</strong></c:if>
                <c:if test="${fn:contains(log.extractFlag ,'正在')}"><strong
                        style="color: blue;">${log.extractFlag}</strong></c:if>
                <c:if test="${fn:contains(log.extractFlag , '失败')}"><strong
                        style="color: red;">${log.extractFlag}</strong></c:if>
            </td>
            <td>
                <c:if test="${fn:contains(log.importFlag ,'成功')}"><strong
                        style="color: green;">${log.importFlag}</strong></c:if>
                <c:if test="${fn:contains(log.importFlag ,'正在')}"><strong
                        style="color: blue;">${log.importFlag}</strong></c:if>
                <c:if test="${fn:contains(log.importFlag , '失败')}"><strong
                        style="color: red;">${log.importFlag}</strong></c:if>
            </td>
            <td>${log.errorOccur}</td>
            <td>${log.year}-${log.month}</td>
            <td>${log.logTime}</td>
            <td>${log.uploadPath}</td>
            <td>${log.extractPath}</td>
            <td>
                <c:choose>
                    <c:when test="${fn:contains(log.extractFlag, '失败')}">
                        <c:if test="${fn:contains(log.tableType,'明细表')}">
                            <a id="reExtract"
                               href="${pageContext.request.contextPath}/manage/extract/0/${currentIndex}/${log.id}"
                               class="btn btn-small btn-primary">重新解压</a>
                        </c:if>
                        <c:if test="${fn:contains(log.tableType,'总表')}">
                            <a id="reExtract"
                               href="${pageContext.request.contextPath}/manage/extract/1/${currentIndex}/${log.id}"
                               class="btn btn-small btn-primary">重新解压</a>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <a id="reExtract" href="javascript:void(0)" class="btn btn-small disabled">重新解压</a>
                    </c:otherwise>

                </c:choose>


                ==========
                <c:choose>
                    <c:when test="${ fn:contains(log.importFlag, '失败') }">
                        <c:if test="${fn:contains(log.tableType,'明细表')}">
                            <a id="reImport"
                               href="${pageContext.request.contextPath}/manage/import/0/${currentIndex}/${log.id}"
                               data-toggle="modal" class="btn btn-small btn-success">重新导入</a>
                        </c:if>
                        <c:if test="${fn:contains(log.tableType,'总表')}">
                            <a id="reImport"
                               href="${pageContext.request.contextPath}/manage/import/1/${currentIndex}/${log.id}"
                               data-toggle="modal" class="btn btn-small btn-success ">重新导入</a>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <a id="reImport" href="javascript:void(0)" class="btn btn-small disabled">重新导入</a>
                    </c:otherwise>

                </c:choose>


            </td>

        </tr>
    </c:forEach>

    </tbody>
</table>

</div>

<div class="pagination-centered">
    <jsp:include page="../../common/pagination.jsp"/>
</div>

<div class="modal hide fade" id="reExtract_win">
    <div class="modal-header">
        <button class="close" data-dismiss="modal" onclick="javascript:;">x</button>
        <h3>你确定要从新解压吗 ?</h3>
    </div>
    <%--<div class="modal-body">--%>
    <%--<p>重新解压 </p>--%>
    <%--</div>--%>
    <div class="modal-footer">
        <a href="#" class="btn" data-dismiss="modal">取消</a>
        <a href="#" class="btn btn-primary">重新解压</a>
    </div>


</body>
</html>