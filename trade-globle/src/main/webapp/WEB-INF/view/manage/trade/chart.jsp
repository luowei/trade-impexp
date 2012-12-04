<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<head>
    <title>图表</title>

    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/bootstrap/css/bootstrap.min.css' />"/>
    <link rel="stylesheet" type="text/css"
          href="<c:url value='/resources/bootstrap/css/bootstrap-responsive.min.css' />"/>
    <script type="text/javascript" src="<c:url value="/resources/js/jquery/1.7.2/jquery.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/openflashchart/json/json2.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/openflashchart/swfobject.js" />"></script>
    <script type="text/javascript" src="<c:url value="/resources/openflashchart/prototype.js" />"></script>


</head>
<body onload="showChart()"  style="padding-top: 60px">

<jsp:include page="../../common/breadcrumb.jsp"/>

<c:url var='chart' value='/manage/gdchart/'/>

<%--<h2>${idx}</h2>--%>

<script type="text/javascript">

    function showChart() {
        for (idx = 1; idx <=${idx}; idx = idx + 1) {
            var chart = "${chart}" + idx;
            swfobject.embedSWF(
                    "<c:url value="/resources/openflashchart/open-flash-chart-2alpha8.swf"/>",
                    "chart_" + idx,
                    "${width}", "${height}", "9.0.0", "expressInstall.swf",
                    {"data-file":chart}
            );
        }
    }

</script>

<div class="container well inline">
    <label class="label "> 图表类型
        <select id="chartType" name="chartType">
            <option value="lineChart" selected="selected">折线图</option>
            <option value="barChart">柱状图</option>
        </select>
    </label>
</div>


<c:forEach var="idx" begin="1" end="${idx}" step="1">
    <div class="container">
        <div id="chart_${idx}" class="container"></div>
    </div>
    <hr/>
</c:forEach>

</body>
</html>