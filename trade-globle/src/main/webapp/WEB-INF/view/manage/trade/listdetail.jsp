<%--
  Created by IntelliJ IDEA.
  User: luowei
  Date: 12-11-9
  Time: 上午8:57
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
    <title>进出口明细</title>

    <script type="text/javascript">
        $(function () {
            checkAll("all", "codes");
        });

        function genDetailChart() {
            $("#form1").attr("target","_blank")
                    .attr("action", "${pageContext.request.contextPath}/manage/detailchart")
                    .submit();
            $("#form1").attr("target","_self")
                    .attr("action", "${pageContext.request.contextPath}${contextUrl}/1")
        }

        //导出数据
        $(function () {
            // Dialog
            $('#dialog').dialog({
                autoOpen: false,
                width: 600,
                buttons: {
                    "确定": function () {

                        var pageSize = $('#expPageSize').val() == '' ? parseInt(${pageSize}) : parseInt($('#expPageSize').val())
                        var pageNumber = $('#expPageNumber').val() == '' ? 1 : parseInt($('#expPageNumber').val())

                        if (pageSize < 1 || pageSize > 65535 || pageNumber < 1) {
                            alert('请输入合法的页数 或 页大小')
                            return
                        }
                        if (pageNumber > parseInt(${totalElements}) / pageSize + 1) {
                            alert("输入的页大于总页数")
                            return
                        }

                        $('#expForm').attr("target", "_blank").submit()
                        $(this).dialog("close");
                    },
                    "取消": function () {
                        $(this).dialog("close");
                    }
                }
            });

            // Dialog Link
            $('#dialog_link').click(function () {

                var impType = $('input[name=impType]').val()==null?"": $('input[name=impType]').val()
                var lowYear = $('select[name=lowYear]').val()==null?"": $('select[name=lowYear]').val()
                var lowMonth = $('select[name=lowMonth]').val()==null?"":$('select[name=lowMonth]').val()
                var highYear = $('select[name=highYear]').val()==null?"": $('select[name=highYear]').val()
                var highMonth = $('select[name=highMonth]').val()==null?"":$('select[name=highMonth]').val()
                var month = $('select[name=month]').val()==null?"":$('select[name=month]').val()
                var productName = $('#productName').val()==null?"":$('#productName').val()
                var impExpType = $('select[name=impExpType]').val()==null?"0":$('select[name=impExpType]').val()

                var info = new Object();
                info.impType = impType
                info.lowYear = parseInt(lowYear)
                info.lowMonth = parseInt(lowMonth)
                info.highYear = parseInt(highYear)
                info.highMonth = parseInt(highMonth)
                info.month = parseInt(month)
                info.productName = productName
                if (impExpType == '0') {
                    info.impExpType = "进口"
                }
                if (impExpType == '1') {
                    info.impExpType = "出口"
                }

                var title = '导出 ' +
                        lowYear + '-' +
                        lowMonth + " ~ " +
                        highYear + '-' +
                        highMonth + " " +
                        month + " " +
                        productName + " " +
                        info.impExpType + "数据"

                var infoJson = jQuery.toJSON(info)

                $('#info').text(title)
                $('#param').val(infoJson)
                $('#expForm').attr('action',$('#expAction').val())

                $('#dialog').dialog('open');
                return false;
            });
        });

    </script>

</head>
<body >

<div class="navbar navbar-fixed-top ">
    <div class="navbar-inner">

        <div class="wrapper">
            <a class="brand" href="#">进出口管理</a>

            <div class="nav-collapse collapse navbar-responsive-collapse">
                <ul class="nav nav-tabs">
                    <li><a href="${pageContext.request.contextPath}/manage/listdetail/1">明细表</a></li>
                    <li><a href="javascript:" onclick="history.back();">返回</a></li>
                </ul>
            </div>

        </div>
    </div>
</div>


<h2>进出口明细</h2>
<form:form id="form1" modelAttribute="tradeDetail"
           action="${pageContext.request.contextPath}${contextUrl}/1"
           method="post" cssClass="well form-inline">
<input type="hidden" id="pageSize" name="pageSize" value="${pageSize}"/>
<input type="hidden" id="sort" name="sort" value="${sort}"/>
<input type="hidden" id="highValue" name="highValue" value=""/>
<input type="hidden" id="lowValue" name="lowValue" value=""/>

<label class="label ">起始年月:
    <select id="lowYear" name="lowYear" class=" input-mini" onchange="">
        <option value="" selected="selected">--</option>
        <c:forEach var="lyr" begin="2000" end="2020" step="1">
            <option value="${lyr}" <c:if test="${lowYear eq lyr}">selected="selected" </c:if>>
                    ${lyr}</option>
        </c:forEach>
    </select> 年&nbsp;
    <select id="lowMonth" name="lowMonth" class=" input-mini">
        <option value="" selected="selected">--</option>
        <c:forEach var="lmth" begin="1" end="12" step="1">
            <option value="${lmth}" <c:if test="${lowMonth eq lmth}">selected="selected" </c:if>>
                    ${lmth}</option>
        </c:forEach>
    </select>月
</label>

<label class="label">结束年月:
    <select id="highYear" name="highYear" class=" input-mini" onchange="">
        <option value="" selected="selected">--</option>
        <c:forEach var="hyr" begin="2000" end="2020" step="1">
            <option value="${hyr}" <c:if test="${highYear eq hyr}">selected="selected" </c:if>>
                    ${hyr}</option>
        </c:forEach>
    </select> 年&nbsp;
    <select id="highMonth" name="highMonth" class=" input-mini">
        <option value="" selected="selected">--</option>
        <c:forEach var="hmth" begin="1" end="12" step="1">
            <option value="${hmth}" <c:if test="${highMonth eq hmth}">selected="selected" </c:if>>
                    ${hmth}</option>
        </c:forEach>
    </select>月
</label>


<label class="label">月同期查询:
    <select id="month" name="month" class=" input-mini">
        <option value="" selected="selected">--</option>
        <c:forEach var="mth" begin="1" end="12" step="1">
            <option value="${mth}" <c:if test="${month eq mth}">selected="selected" </c:if>>
                    ${mth}</option>
        </c:forEach>
    </select>月
</label>


<br/>

<label class="label">企业性质:
    <select id="companyType" name="companyType" class=" input-mini">
        <option value="">--</option>
        <c:forEach items="${companyTypeList}" var="companyTypeItem">
            <option value="${companyTypeItem.companyType}"
                    <c:if test="${companyTypeItem.companyType eq companyType}">selected="selected" </c:if>>
                    ${companyTypeItem.companyType}
            </option>
        </c:forEach>
    </select>
</label>

<label class="label">贸易方式:
    <select id="tradeType" name="tradeType" class=" input-mini">
        <option value="">--</option>
        <c:forEach items="${tradeTypeList}" var="tradeTypeItem">
            <option value="${tradeTypeItem.tradeType}"
                    <c:if test="${tradeTypeItem.tradeType eq tradeType}">selected="selected" </c:if>>
                    ${tradeTypeItem.tradeType}
            </option>
        </c:forEach>
    </select>
</label>

<label class="label">运输方式:
    <select id="transportation" name="transportation" class=" input-mini">
        <option value="">--</option>
        <c:forEach items="${transportationList}" var="transportationItem">
            <option value="${transportationItem.transportation}"
                    <c:if test="${transportationItem.transportation eq transportation}">selected="selected" </c:if>>
                    ${transportationItem.transportation}
            </option>
        </c:forEach>
    </select>
</label>

<label class="label">海关:
    <select id="customs" name="customs" class=" input-mini">
        <option value="">--</option>
        <c:forEach items="${customsList}" var="customsItem">
            <option value="${customsItem.customs}"
                    <c:if test="${customsItem.customs eq customs}">selected="selected" </c:if>>
                    ${customsItem.customs}
            </option>
        </c:forEach>
    </select>
</label>

<label class="label">产销国家:
    <select id="country" name="country" class=" input-mini">
        <option value="">--</option>
        <c:forEach items="${countryList}" var="countryItem">
            <option value="${countryItem.country}"
                    <c:if test="${countryItem.country eq country}">selected="selected" </c:if>>
                    ${countryItem.country}
            </option>
        </c:forEach>
    </select>
</label>

<label class="label">城市:
    <select id= "city" name="city" class=" input-mini">
        <option value="">--</option>
        <c:forEach items="${cityList}" var="cityItem">
            <option value="${cityItem.city}"
                    <c:if test="${cityItem.city eq city}">selected="selected" </c:if>>
                    ${cityItem.city}
            </option>
        </c:forEach>
    </select>
</label>

<br/>

<label class="label">
    产品代码:<input id="productCode" name="productCode" cssClass="input search-query" style="width: 100px"
                <c:if test='${productCode ne null}'>value="${productCode}" </c:if> />
</label>
<label class="label">
    产品名称:
    <input id="productName" name="productName" cssClass="input search-query"
           <c:if test='${productName ne null}'>value="${productName}" </c:if> />
    <label><input type="radio" name="nameSelType" value="EQ"
                  <c:if test="${nameSelType eq 'EQ' or nameSelType eq null}">checked="checked"</c:if> />等于</label>
    <label><input type="radio" name="nameSelType" value="LIKE"
                  <c:if test="${nameSelType eq 'LIKE'}">checked="checked"</c:if> />包含</label>
</label>

<label class="label">进出口类型:&nbsp;
    <label><input  type="radio" name="impExpType" value="0"
                  <c:if test="${impExpType eq 0}">checked="checked"</c:if> />
        <input type="button" class="btn btn-mini" style="width: 60px" value="进口"></label>
    <label><input type="radio" name="impExpType" value="1"
                  <c:if test="${impExpType eq 1}">checked="checked"</c:if> />
        <input type="button" class="btn btn-mini"  style="width: 60px" value="出口"></label>
</label>


<button type="submit" class="btn btn-success">
    <i class="icon-search icon-white"></i>查询
</button>
&nbsp;&nbsp;
<%--<a href="#" id="dialog_link" class="btn btn-primary">导出数据</a>--%>

<%--<br/>--%>

<%--<label class="label">--%>
    <%--产品代码:<input id="autoproductCode" name="autoproductCode" cssClass="input-mini search-query autocomplete" />--%>
<%--</label>--%>
<%--&lt;%&ndash;<div class="btn-group">&ndash;%&gt;--%>
<%--<input id="chart" type="button" class="btn-small btn-primary" value="生成曲线" onclick="genDetailChart()"/>--%>
<%--&lt;%&ndash;<a  href="javascript:window.open('#')" target="_blank"  class="btn btn-small btn-primary" onclick="genDetailChart()">生成曲线</a>&ndash;%&gt;--%>

<%--</div>--%>

<div class="pagination-centered">
    <jsp:include page="../../common/pagination.jsp"/>
</div>

<h4> <span> 过滤条件:</span>
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
    <c:if test="${companyType ne null}">
        <label class="label"> 企业性质: <span style="color: yellow;">${companyType} </span> </label>
    </c:if>
    <c:if test="${tradeType ne null}">
        <label class="label"> 贸易方式: <span style="color: yellow;">${tradeType}</span> </label>
    </c:if>
    <c:if test="${transportation ne null}">
        <label class="label">运输方式: <span style="color: yellow;">${transportation} </span> </label>
    </c:if>
    <c:if test="${customs ne null}">
        <label class="label">海关: <span style="color: yellow;">${customs}</span> </label>
    </c:if>
    <c:if test="${country ne null}">
        <label class="label">产销国家: <span style="color: yellow;">${country}</span> </label>
    </c:if>
    <c:if test="${city ne null}">
        <label class="label">城市: <span style="color: yellow;">${city} </span> </label>
    </c:if>
    <c:if test="${productCode ne null}">
        <label class="label">产品代码: <span style="color: yellow;">${productCode}</span> </label>
    </c:if>
    <c:if test="${productName ne null}">
        <label class="label">产品名称: <span style="color: yellow;">${productName}</span> </label>
    </c:if>

</h4>

<table class="table table-hover table-bordered table-striped table-condensed">
    <thead>
    <tr>
        <%--<th>Id--%>
            <%--<a href="javascript:void(0)" onclick="ascOrder('id','form1')"><i class="icon-chevron-up"></i></a>--%>
            <%--<a href="javascript:void(0)" onclick="descOrder('id','form1')"><i class="icon-chevron-down"></i></a>--%>
        <%--</th>--%>
        <th>年月
            <a href="javascript:void(0)" onclick="ascOrder('yearMonth','form1')"><i class="icon-chevron-up"></i></a>
            <a href="javascript:void(0)" onclick="descOrder('yearMonth','form1')"><i class="icon-chevron-down"></i></a>
        </th>
        <th>产品代码
            <a href="javascript:void(0)" onclick="ascOrder('productCode','form1')"><i class="icon-chevron-up"></i></a>
            <a href="javascript:void(0)" onclick="descOrder('productCode','form1')"><i class="icon-chevron-down"></i></a>
        </th>
        <th>产品名称
            <a href="javascript:void(0)" onclick="ascOrder('productName','form1')"><i class="icon-chevron-up"></i></a>
            <a href="javascript:void(0)" onclick="descOrder('productName','form1')"><i class="icon-chevron-down"></i></a>
        </th>
        <th>贸易方式</th>
        <th>企业性质</th>
        <th>运输方式</th>
        <th>城市</th>
        <th>海关</th>
        <th>产销国家</th>
        <th>数量
            <a href="javascript:void(0)" onclick="ascOrder('amount','form1')"><i class="icon-chevron-up"></i></a>
            <a href="javascript:void(0)" onclick="descOrder('amount','form1')"><i class="icon-chevron-down"></i></a>
        </th>
        <th>单位</th>
        <th>美元价值
            <a href="javascript:void(0)" onclick="ascOrder('amountMoney','form1')"><i class="icon-chevron-up"></i></a>
            <a href="javascript:void(0)" onclick="descOrder('amountMoney','form1')"><i class="icon-chevron-down"></i></a>
        </th>
        <th>单价</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${tradeDetailList.content}" var="detail" varStatus="st">
        <tr <c:if test="${st.index%2 eq 1}">class="warning" </c:if>>
            <%--<td>${detail.id}</td>--%>
            <td>${detail.yearMonth}</td>
            <td>${detail.productCode}</td>
            <td>${detail.productName}</td>
            <td>${detail.tradeType}</td>
            <td>${detail.companyType}</td>
            <td>${detail.transportation}</td>
            <td>${detail.city}</td>
            <td>${detail.customs}</td>
            <td>${detail.country}</td>
            <td>${detail.amount}</td>
            <td>${detail.unit}</td>
            <td>${detail.amountMoney}</td>
            <td>${detail.unitPrice}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</form:form>


<!-- ui-dialog -->
<input type="hidden" id="expAction" name="expAction" value="${pageContext.request.contextPath}/manage/exportDetail">
<div id="dialog" title="导出数据">
    <form:form id="expForm" name="expForm" modelAttribute="detailCount"
               action="${pageContext.request.contextPath}/manage/exportDetail"
               method="post" cssClass="well form-inline">
        <input id="param" type="hidden" name="param" value="">

        <p align="center"><b id="info"></b></p>

        <p align="center">总记录数:${totalElements}&nbsp;&nbsp;默认页大小:${pageSize}&nbsp;总页数:${totalPages}</p>

        <p align="center">

        <div class="input-prepend" >
            <span class="add-on">页大小:</span>
            <input id="expPageSize" name="expPageSize" type="text" style="width: 80px;margin-top: 5px;">
            <span class="add-on">第几页:</span>
            <input id="expPageNumber" name="expPageNumber" type="text" style="width: 80px;margin-top: 5px;">
        </div>

        <br/><br/>


        </p>

    </form:form>
</div>


<div class="pagination-centered">
    <jsp:include page="../../common/pagination.jsp"/>
</div>



</body>
</html>