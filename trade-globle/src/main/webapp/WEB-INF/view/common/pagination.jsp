<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="r" value="${pageContext.request}"/>

<c:url var="firstUrl" value="${contextUrl}/1"/>
<c:url var="lastUrl" value="${contextUrl}/${totalPages}"/>
<c:url var="prevUrl" value="${contextUrl}/${currentIndex - 1}"/>
<c:url var="nextUrl" value="${contextUrl}/${currentIndex + 1}"/>

<script type="text/javascript">
    function subform(obj){
        $("#form1").attr("action",$(obj).attr("href1")).submit();
    }

</script>

<div class="pagination">
    <ul>
        <c:choose>
            <c:when test="${currentIndex == 1}">
                <li class="disabled"><a href="#">&lt;&lt;</a></li>
                <li class="disabled"><a href="#">&lt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href1="${firstUrl}" href="#" onclick="subform(this)">&lt;&lt;</a></li>
                <li><a href1="${prevUrl}" href="#" onclick="subform(this)">&lt;</a></li>
            </c:otherwise>
        </c:choose>
        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
            <c:url var="pageUrl" value="${contextUrl}/${i}"/>
            <c:choose>
                <c:when test="${i == currentIndex}">
                    <li class="active"><a href1="${pageUrl}" href="#"><c:out value="${i}"/></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href1="${pageUrl}" href="#" onclick="subform(this)"><c:out value="${i}"/></a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:choose>
            <c:when test="${currentIndex == totalPages}">
                <li class="disabled"><a href="#">&gt;</a></li>
                <li class="disabled"><a href="#">&gt;&gt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href1="${nextUrl}" href="#" onclick="subform(this)">&gt;</a></li>
                <li><a href1="${lastUrl}" href="#" onclick="subform(this)">&gt;&gt;</a></li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>

<div class="bottom">
    总页数:${totalPages}&nbsp;&nbsp;总记录数:${totalElements}

</div>

<%--<div>--%>
    <%--request.contextPath = ${r.contextPath} <br/>--%>
    <%--request.URI = ${r.requestURI} <br/>--%>
    <%--request.URL = ${r.requestURL} <br/>--%>

<%--</div>--%>