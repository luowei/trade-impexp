<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="r" value="${pageContext.request}"/>

<c:url var="firstUrl" value="${contextUrl}/1"/>
<c:url var="lastUrl" value="${contextUrl}/${totalPages}"/>
<c:url var="prevUrl" value="${contextUrl}/${currentIndex - 1}"/>
<c:url var="nextUrl" value="${contextUrl}/${currentIndex + 1}"/>


<div class="pagination">
    <ul>
        <c:choose>
            <c:when test="${currentIndex == 1}">
                <li class="disabled"><a href="#">&lt;&lt;</a></li>
                <li class="disabled"><a href="#">&lt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${firstUrl}">&lt;&lt;</a></li>
                <li><a href="${prevUrl}">&lt;</a></li>
            </c:otherwise>
        </c:choose>
        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
            <c:url var="pageUrl" value="${contextUrl}/${i}"/>
            <c:choose>
                <c:when test="${i == currentIndex}">
                    <li class="active"><a href="${pageUrl}"><c:out value="${i}"/></a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="${pageUrl}"><c:out value="${i}"/></a></li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:choose>
            <c:when test="${currentIndex == totalPages}">
                <li class="disabled"><a href="#">&gt;</a></li>
                <li class="disabled"><a href="#">&gt;&gt;</a></li>
            </c:when>
            <c:otherwise>
                <li><a href="${nextUrl}">&gt;</a></li>
                <li><a href="${lastUrl}">&gt;&gt;</a></li>
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