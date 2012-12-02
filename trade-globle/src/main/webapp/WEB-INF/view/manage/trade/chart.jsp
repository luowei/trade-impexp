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

</head>
<body onload="embed($('chartType').value);">

<jsp:include page="../../common/breadcrumb.jsp"/>

<div class="container">
    <label class="label"> 图表类型
        <select id="chartType" name="chartType">
             <option value = "lineChart" selected="selected">折线图</option>
            <option value="barChart">柱状图</option>
        </select>
    </label>
</div>

<%--<c:forEach var="idx" begin="1" end="${idx}" step="1">--%>

    ${chart}
    <br/>
    ==========================================
    <br/>
    <hr/>

<script type="text/javascript" src="js/swfobject.js"></script>
<script type="text/javascript">

    swfobject.embedSWF(
            "/resources/swf/open-flash-chart-2alpha8.swf", "pie_chart",
            "300", "300", "9.0.0", "expressInstall.swf",
            {"data-file":"/manage/gdchart/1"} );


    swfobject.embedSWF(
            "/resources/swf/open-flash-chart-2alpha8.swf", "line_chart",
            "450", "300", "9.0.0", "expressInstall.swf",
            {"data-file":"/manage/gdchart/2"} );


</script>

<div id="pie_chart" class="container"></div>

<div id="line_chart" class="container"></div>

<div class="container">
    <%--<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000"--%>
            <%--codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0"--%>
            <%--width="${width}" height="${height}" id="ie_chart"--%>
            <%--align="middle">--%>
        <%--<param name="allowScriptAccess" value="sameDomain" />--%>
        <%--<param name="movie"--%>
               <%--value="/resources/swf/open-flash-chart-2alpha8.swf?width=${width}&height=${width}&data=http://localhost:9090/trade-globle/manage/gdchart/1" />--%>
        <%--<param name="quality" value="high" />--%>
        <%--<param name="bgcolor" value="#FFFFFF" />--%>
        <%--<embed src="/resources/swf/open-flash-chart.swf?data=http://localhost:9090/trade-globle/manage/gdchart/1"--%>
               <%--quality="high"--%>
               <%--bgcolor="#FFFFFF"--%>
               <%--width="${width}"--%>
               <%--height="${width}"--%>
               <%--name="chart"--%>
               <%--align="middle"--%>
               <%--allowScriptAccess="sameDomain"--%>
               <%--type="application/x-shockwave-flash"--%>
               <%--pluginspage="http://www.macromedia.com/go/getflashplayer"--%>
               <%--id="chart" />--%>
    <%--</object>--%>

</div>
<%--</c:forEach>--%>

</body>
</html>