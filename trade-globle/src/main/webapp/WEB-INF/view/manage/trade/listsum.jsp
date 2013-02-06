<%--
  Created by IntelliJ IDEA.
  User: luowei
  Date: 12-11-9
  Time: 上午8:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>进出口总表</title>

    <script type="text/javascript">
        $(function () {
            checkAll("all", "codes");
        });

        function genSumChart() {
            $("#form1").attr("target", "_blank")
                    .attr("action", "${pageContext.request.contextPath}/manage/sumchart")
                    .submit();
            $("#form1").attr("target", "_self")
                    .attr("action", "${pageContext.request.contextPath}${contextUrl}/1")
        }
        function showSumChart() {
            $("#form1").attr("target", "_blank")
                    .attr("action", "${pageContext.request.contextPath}/manage/hchart/sumChart")
                    .submit();
            $("#form1").attr("target", "_self")
                    .attr("action", "${pageContext.request.contextPath}${contextUrl}/1")

        }
    </script>


</head>

<body>

<div class="navbar navbar-fixed-top ">
    <div class="navbar-inner">

        <div class="wrapper">
            <a class="brand" href="#">进出口管理</a>

            <div class="nav-collapse collapse navbar-responsive-collapse">
                <ul class="nav nav-tabs">
                    <li><a href="${pageContext.request.contextPath}/manage/listsum/1">总表</a></li>
                    <li><a href="javascript:" onclick="history.back();">返回</a></li>
                </ul>
            </div>

        </div>
    </div>
</div>

<h2>进出口总表</h2>
<%--<c:url var="action" value="${contextUrl}/1"/>--%>
<form:form id="form1" modelAttribute="tradeSum" action="${pageContext.request.contextPath}${contextUrl}/1"
           method="post" cssClass="well form-inline">
    <input type="hidden" id="sort" name="sort" value="${sort}"/>

    <label class="label">起始年月:
        <select name="lowYear" class="input-small">
            <option value="" selected="selected">--</option>
            <c:forEach var="lyr" begin="2000" end="2020" step="1">
                <option value="${lyr}" <c:if test="${lowYear eq lyr}">selected="selected" </c:if>>
                        ${lyr}</option>
            </c:forEach>
        </select> 年&nbsp;
        <select name="lowMonth" class="input-small">
            <option value="" selected="selected">--</option>
            <c:forEach var="lmth" begin="1" end="12" step="1">
                <option value="${lmth}" <c:if test="${lowMonth eq lmth}">selected="selected" </c:if>>
                        ${lmth}</option>
            </c:forEach>
        </select>月
    </label>

    <label class="label">结束年月:

        <select name="highYear" class="input-small">
            <option value="" selected="selected">--</option>
            <c:forEach var="hyr" begin="2000" end="2020" step="1">
                <option value="${hyr}" <c:if test="${highYear eq hyr}">selected="selected" </c:if>>
                        ${hyr}</option>
            </c:forEach>
        </select> 年&nbsp;
        <select name="highMonth" class="input-small">
            <option value="" selected="selected">--</option>
            <c:forEach var="hmth" begin="1" end="12" step="1">
                <option value="${hmth}" <c:if test="${highMonth eq hmth}">selected="selected" </c:if>>
                        ${hmth}</option>
            </c:forEach>
        </select>月
    </label>

    <label class="label">月同期查询:
        <select name="month" class="input-small">
            <option value="" selected="selected">--</option>
            <c:forEach var="mth" begin="1" end="12" step="1">
                <option value="${mth}" <c:if test="${month eq mth}">selected="selected" </c:if>>
                        ${mth}</option>
            </c:forEach>
        </select>月
    </label>

    <br/>

    <label class="label">
        产品名称:
        <input name="productName" cssClass="input-mini search-query"
               <c:if test='${productName ne null}'>value="${productName}" </c:if> />
        <label><input type="radio" name="nameSelType" value="EQ"
                      <c:if test="${nameSelType eq 'EQ' or nameSelType eq null}">checked="checked"</c:if> />等于</label>
        <label><input type="radio" name="nameSelType" value="LIKE"
                      <c:if test="${nameSelType eq 'LIKE'}">checked="checked"</c:if> />包含</label>
    </label>

    <label class="label">产品类型:
        <select name="sumType" class="input-small">
            <option value="" selected="selected">--</option>
            <c:forEach var="prodType" items="${sumTypeList}">
                <option value="${prodType.sumType}"
                        <c:if test="${sumType eq prodType.sumType}">selected="selected" </c:if>>
                        ${prodType.sumType}</option>
            </c:forEach>
        </select>
    </label>

    <br/>
    <label class="label">进出口类型:
        <label><input type="radio" name="impExpType" value="0"
                      <c:if test="${impExpType eq 0}">checked="checked"</c:if> />进口</label>
        &nbsp;
        <label><input type="radio" name="impExpType" value="1"
                      <c:if test="${impExpType eq 1}">checked="checked"</c:if> />出口</label>
    </label>

    <button type="submit" class="btn btn-success">
        <i class="icon-search icon-white"></i>查询
    </button>
    <!--
    <input id="chart" type="button" class="btn-small btn-primary" value="生成曲线" onclick="genSumChart()"/>
    -->
    &nbsp;&nbsp;
    <input id="chart" type="button" class="btn btn-primary" value="查看曲线" onclick="showSumChart()"/>
    &nbsp;&nbsp;
    <%--<a href="#" id="dialog_link" class="btn btn-primary">导出数据</a>--%>
    <%--<a  href="" target="_blank" class="btn btn-small btn-primary" onclick="genSumChart()">生成曲线</a>--%>

    <div class="pagination-centered">
        <jsp:include page="../../common/pagination.jsp"/>
    </div>

    <h4><span> 过滤条件:</span>
        <label class="label"> 进出口类型:
            <c:if test="${impExpType eq 0}"><span style="color: yellow;">进口</span></c:if>
            <c:if test="${impExpType eq 1}"><span style="color: yellow;">出口</span></c:if> </label>
        <c:if test="${lowYear ne null and lowMonth ne null}">
            <label class="label"> 起始年月:<span style="color: yellow;">${lowYear}-${lowMonth} </span> </label>
        </c:if>
        <c:if test="${highYear ne null}">
            <label class="label">结束年月: <span style="color: yellow;">${highYear}-${highMonth} </span> </label>
        </c:if>
        <c:if test="${month ne null}">
            <label class="label"> 月同期: <span style="color: yellow;">${month} </span> </label>
        </c:if>
        <c:if test="${sumType ne null}">
            <label class="label"> 企业性质: <span style="color: yellow;">${sumType} </span> </label>
        </c:if>
        <c:if test="${productName ne null}">
            <label class="label">产品名称: <span style="color: yellow;">${productName}</span> </label>
        </c:if>

    </h4>

    <table class="table table-hover table-bordered table-striped table-condensed">
        <thead>
        <tr>
            <th><label><input type="checkbox" id="all"/>曲线</label></th>
            <%--<th>Id--%>
                <%--<a href="javascript:void(0)" onclick="ascOrder('id','form1')"><i class="icon-chevron-up"></i></a>--%>
                <%--<a href="javascript:void(0)" onclick="descOrder('id','form1')"><i class="icon-chevron-down"></i></a>--%>
            <%--</th>--%>
            <th>年月
                <a href="javascript:void(0)" onclick="ascOrder('yearMonth','form1')"><i class="icon-chevron-up"></i></a>
                <a href="javascript:void(0)" onclick="descOrder('yearMonth','form1')"><i class="icon-chevron-down"></i></a>
            </th>
            <th>产品名称
                <a href="javascript:void(0)" onclick="ascOrder('numSum','form1')"><i class="icon-chevron-up"></i></a>
                <a href="javascript:void(0)" onclick="descOrder('numSum','form1')"><i class="icon-chevron-down"></i></a>
            </th>
            <th>产品类型</th>
            <th>当月数量(T)
                <a href="javascript:void(0)" onclick="ascOrder('numMonth','form1')"><i class="icon-chevron-up"></i></a>
                <a href="javascript:void(0)" onclick="descOrder('numMonth','form1')"><i
                        class="icon-chevron-down"></i></a>
            </th>
            <th>累计总数量(T)
                <a href="javascript:void(0)" onclick="ascOrder('numSum','form1')"><i class="icon-chevron-up"></i></a>
                <a href="javascript:void(0)" onclick="descOrder('numSum','form1')"><i class="icon-chevron-down"></i></a>
            </th>
            <th>当月金额(万美元)
                <a href="javascript:void(0)" onclick="ascOrder('moneyMonth','form1')"><i
                        class="icon-chevron-up"></i></a>
                <a href="javascript:void(0)" onclick="descOrder('moneyMonth','form1')"><i class="icon-chevron-down"></i></a>
            </th>
            <th>累计总金额(万美元)
                <a href="javascript:void(0)" onclick="ascOrder('moneySum','form1')"><i class="icon-chevron-up"></i></a>
                <a href="javascript:void(0)" onclick="descOrder('moneySum','form1')"><i
                        class="icon-chevron-down"></i></a>
            </th>
            <th>当月平均价格(美元/T)
                <a href="javascript:void(0)" onclick="ascOrder('avgPriceMonth','form1')"><i class="icon-chevron-up"></i></a>
                <a href="javascript:void(0)" onclick="descOrder('avgPriceMonth','form1')"><i
                        class="icon-chevron-down"></i></a>
            </th>
            <th>累计平均价格(美元/T)
                <a href="javascript:void(0)" onclick="ascOrder('avgPriceSum','form1')"><i
                        class="icon-chevron-up"></i></a>
                <a href="javascript:void(0)" onclick="descOrder('avgPriceSum','form1')"><i
                        class="icon-chevron-down"></i></a>
            </th>
            <th>与上月数量增长比(%)
                <a href="javascript:void(0)" onclick="ascOrder('pm','form1')"><i class="icon-chevron-up"></i></a>
                <a href="javascript:void(0)" onclick="descOrder('pm','form1')"><i class="icon-chevron-down"></i></a>
            </th>
            <th>与上年同月数量增长比(%)
                <a href="javascript:void(0)" onclick="ascOrder('py','form1')"><i class="icon-chevron-up"></i></a>
                <a href="javascript:void(0)" onclick="descOrder('py','form1')"><i class="icon-chevron-down"></i></a>
            </th>
            <th>与上年同期数量增长比(%)
                <a href="javascript:void(0)" onclick="ascOrder('pq','form1')"><i class="icon-chevron-up"></i></a>
                <a href="javascript:void(0)" onclick="descOrder('pq','form1')"><i class="icon-chevron-down"></i></a>
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${tradeSumList.content}" var="sum" varStatus="st">
            <tr <c:if test="${st.index%2 eq 1}">class="warning" </c:if>>
                <td><input type="checkbox" name="codes" value="${sum.productName}"/></td>
                <%--<td>${sum.id}</td>--%>
                <td>${sum.yearMonth}</td>
                <td>${sum.productName}</td>
                <td>${sum.sumType}</td>
                <td>${sum.numMonth}</td>
                <td>${sum.numSum}</td>
                <td>${sum.moneyMonth}</td>
                <td>${sum.moneySum}</td>
                <td>${sum.avgPriceMonth}</td>
                <td>${sum.avgPriceSum}</td>
                <td>${sum.pm}</td>
                <td>${sum.py}</td>
                <td>${sum.pq}</td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</form:form>

<input type="hidden" id="expAction" name="expAction" value="${pageContext.request.contextPath}/manage/exportSum">
<jsp:include page="../../common/exportdata.jsp"/>

<div class="pagination-centered">
    <jsp:include page="../../common/pagination.jsp"/>
</div>

</body>
</html>