<%--
  Created by IntelliJ IDEA.
  User: luowei
  Date: 12-12-13
  Time: 下午4:20
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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <link href="<c:url value="/resources/js/autocomplete/styles.css" />" rel="stylesheet"/>
    <script type="text/javascript" src="<c:url value="/resources/js/autocomplete/jquery.autocomplete.js" />"></script>
    <script type="text/javascript">
        $(function () {
            'use strict';
            $.ajax({
                url: '${pageContext.request.contextPath}/manage/getProduct',
                dataType: 'json'
            }).done(function (data) {
                        $('#autoproductCode').autocomplete({
                            minChars: 2,
                            maxHeight: 200,
                            width: 800,
                            lookup: data
                        });
                    });
        });

        function showNMChart() {
            var condition = $('input[name=condition]:checked', '#form1').val();
            if (condition == null || condition == '') {
                alert('请选择统计条件!')
                return
            }
            if ($('#autoproductCode').val() == '') {
                alert('请输入并选择要统计的产品')
                return;
            }
            if ($('#hhmmssRand').val() == null || $('#hhmmssRand').val() == '') {
                alert('请选择统计条件，生成统计数据，再查看曲线')
                return;
            }

            window.open('${pageContext.request.contextPath}/manage/hchart/showNMChart/' + $('#autoproductCode')
                    .val().split('->')[0].trim() + '/' + $('#hhmmssRand').val());
        }


    </script>

    <title>明细统计</title>
</head>
<body>

<div class="navbar navbar-fixed-top ">
    <div class="navbar-inner">

        <div class="wrapper">
            <a class="brand" href="#">进出口管理</a>

            <div class="nav-collapse collapse navbar-responsive-collapse">
                <ul class="nav nav-tabs">
                    <li><a href="${pageContext.request.contextPath}/manage/hchart/toNMDetail">明细统计</a></li>
                    <li><a href="javascript:" onclick="history.back();">返回</a></li>
                </ul>
            </div>

        </div>
    </div>
</div>

<h3>明细统计</h3>
<form:form id="form1" modelAttribute="productCount"
           action="${pageContext.request.contextPath}/manage/hchart/NMDetail"
           method="post" cssClass="well form-inline">
<input type="hidden" name="pageSize" value="${pageSize}"/>
<input type="hidden" name="sort" value="${sort}"/>
<input type="hidden" name="highValue" value=""/>
<input type="hidden" name="lowValue" value=""/>
    <%--<input type="hidden" id="countType" name="countType" value="nature"/>--%>

<label class="label">
    请输入产品代码,并选择关联的产品:<input id="autoproductCode" name="autoproductCode"
        <c:if test="${productCount.autoproductCode ne null or productCount.autoproductCode ne ''}">
            value="${productCount.autoproductCode}"</c:if>
                            cssClass="input-mini search-query autocomplete" style="width: 500px"/>
</label>
<br/><hr/>

<label class="label">进出口类型:&nbsp;
    <label><input type="radio" name="impExpType" value="0"
                  onclick="$('input[name=impExpType][value=0]').attr('checked',true);"
                  <c:if test="${impExpType eq 0}">checked="checked"</c:if> />
        <input type="button" class="btn" style="width: 60px" value="进口"></label>
    <label><input type="radio" name="impExpType" value="1"
                  onclick="$('input[name=impExpType][value=1]').attr('checked',true);"
                  <c:if test="${impExpType eq 1}">checked="checked"</c:if> />
        <input type="button" class="btn" style="width: 60px" value="出口"></label>
</label>

<label class="label">统计条件:&nbsp;
    <label><input type="radio" name="condition" value="tradeType"
                  onclick="$('input[name=condition][value=tradeType]').attr('checked',true);"
                  <c:if test="${productCount.condition eq 'tradeType'}">checked="checked"</c:if> />
        <input type="button" class="btn" style="width: 80px" value="贸易方式"></label> &nbsp;
    <label><input type="radio" name="condition" value="companyType"
                  onclick="$('input[name=condition][value=companyType]').attr('checked',true);"
                  <c:if test="${productCount.condition eq 'companyType'}">checked="checked"</c:if> />
        <input type="button" class="btn" style="width: 80px" value="企业性质"></label> &nbsp;
    <label><input type="radio" name="condition" value="transportation"
                  onclick="$('input[name=condition][value=transportation]').attr('checked',true);"
                  <c:if test="${productCount.condition eq 'transportation'}">checked="checked"</c:if> />
        <input type="button" class="btn" style="width: 80px" value="运输方式"></label> &nbsp;
    <label><input type="radio" name="condition" value="customs"
                  onclick="$('input[name=condition][value=customs]').attr('checked',true);"
                  <c:if test="${productCount.condition eq 'customs'}">checked="checked"</c:if> />
        <input type="button" class="btn" style="width: 60px" value="海关"></label> &nbsp;
    <label><input type="radio" name="condition" value="country"
                  onclick="$('input[name=condition][value=country]').attr('checked',true);"
                  <c:if test="${productCount.condition eq 'country'}">checked="checked"</c:if> />
        <input type="button" class="btn" style="width: 80px" value="产销国家"></label>&nbsp;
    <label><input type="radio" name="condition" value="city"
                  onclick="$('input[name=condition][value=city]').attr('checked',true);"
                  <c:if test="${productCount.condition eq 'city'}">checked="checked"</c:if> />
        <input type="button" class="btn" style="width: 60px" value="城市"></label> &nbsp;
</label>
<br/> <hr/>

<h4>选择自然统计年月:</h4>
<label class="label ">起始年月:
    <select name="lowYear" class=" input-small" onchange="">
        <option value="" selected="selected">--</option>
        <c:forEach var="lyr" begin="2000" end="2020" step="1">
            <option value="${lyr}" <c:if test="${lowYear eq lyr}">selected="selected" </c:if>>
                    ${lyr}</option>
        </c:forEach>
    </select> 年&nbsp;
    <select name="lowMonth" class=" input-small">
        <option value="" selected="selected">--</option>
        <c:forEach var="lmth" begin="1" end="12" step="1">
            <option value="${lmth}" <c:if test="${lowMonth eq lmth}">selected="selected" </c:if>>
                    ${lmth}</option>
        </c:forEach>
    </select>月
</label>

<label class="label">结束年月:
    <select name="highYear" class=" input-small" onchange="">
        <option value="" selected="selected">--</option>
        <c:forEach var="hyr" begin="2000" end="2020" step="1">
            <option value="${hyr}" <c:if test="${highYear eq hyr}">selected="selected" </c:if>>
                    ${hyr}</option>
        </c:forEach>
    </select> 年&nbsp;
    <select name="highMonth" class=" input-small">
        <option value="" selected="selected">--</option>
        <c:forEach var="hmth" begin="1" end="12" step="1">
            <option value="${hmth}" <c:if test="${highMonth eq hmth}">selected="selected" </c:if>>
                    ${hmth}</option>
        </c:forEach>
    </select>月
</label>  &nbsp;&nbsp;
<input type="button" class="btn btn-primary" value="计算自然月" onclick="$('#form1').submit()">

<br/>
<h4>选择累计年月:</h4>
<label class="label">指定年份:
    <select name="sumYear" class=" input-small" onchange="">
        <option value="" selected="selected">--</option>
        <c:forEach var="hyr" begin="2000" end="2020" step="1">
            <option value="${hyr}" <c:if test="${sumYear eq hyr}">selected="selected" </c:if>>
                    ${hyr}</option>
        </c:forEach>
    </select> 年&nbsp;
    <select name="beginSumMonth" class=" input-small">
        <option value="" selected="selected">--</option>
        <c:forEach var="bmth" begin="1" end="12" step="1">
            <option value="${bmth}" <c:if test="${beginSumMonth eq bmth}">selected="selected" </c:if>>
                    ${bmth}</option>
        </c:forEach>
    </select>月&nbsp;-&nbsp;到
    <select name="endSumMonth" class=" input-small">
        <option value="" selected="selected">--</option>
        <c:forEach var="emth" begin="1" end="12" step="1">
            <option value="${emth}" <c:if test="${endSumMonth eq emth}">selected="selected" </c:if>>
                    ${emth}</option>
        </c:forEach>
    </select>月
</label>  &nbsp;&nbsp;
<input type="button" class="btn btn-primary" value="计算累计月" onclick="function sumMonthSubmit() {
        //        $('#countType').val('sum');
        $('#form1').attr('action', '${pageContext.request.contextPath}/manage/hchart/sumMonthdetail').submit();
        $('#form1').attr('action', '${pageContext.request.contextPath}/manage/hchart/NMDetail')
        }
        sumMonthSubmit()">
<br/><hr/>

<h4>
    <span>
        <c:if test="${contentTitle ne null}">${fn:substringBefore(contentTitle,'图表')}</c:if>
    </span><br/>
    <%--<c:if test="${impExpType ne null && impExpType ne ''}">--%>
        <label class="label"> 进出口类型为:
            <c:if test="${impExpType eq 0 || impExpType eq '0'}"><span style="color: yellow;">进口</span></c:if>
            <c:if test="${impExpType eq 1 || impExpType eq '1'}"><span style="color: yellow;">出口</span></c:if>
        </label>
    <%--</c:if>--%>
    <c:if test="${lowYear ne null and lowMonth ne null and countType ne 'sumCount'}">
        <label class="label"> 起始年月为:<span style="color: yellow;">${lowYear}-${lowMonth} </span> </label>
    </c:if>
    <c:if test="${highYear ne null and countType ne 'sumCount'}">
        <label class="label">结束年月为: <span style="color: yellow;">${highYear}-${highMonth} </span> </label>
    </c:if>
    <c:if test="${sumYear ne null && countType eq 'sumCount'}">
        <label class="label">累计年月为: <span style="color: yellow;">${sumYear}年${beginSumMonth}月-${endSumMonth}月</span> </label>
    </c:if>
    <c:if test="${productCount.condition eq 'tradeType'}">
        <label class="label">统计条件为: <span style="color: yellow;">贸易方式</span> </label>
    </c:if>
    <c:if test="${productCount.condition eq 'companyType'}">
        <label class="label">统计条件为: <span style="color: yellow;">企业性质</span> </label>
    </c:if>
    <c:if test="${productCount.condition eq 'transportation'}">
        <label class="label">统计条件为: <span style="color: yellow;">运输方式</span> </label>
    </c:if>
    <c:if test="${productCount.condition eq 'customs'}">
        <label class="label">统计条件为: <span style="color: yellow;">海关</span> </label>
    </c:if>
    <c:if test="${productCount.condition eq 'country'}">
        <label class="label">统计条件为: <span style="color: yellow;">产销国家</span> </label>
    </c:if>
    <c:if test="${productCount.condition eq 'city'}">
        <label class="label">统计条件为: <span style="color: yellow;">城市</span></label>
    </c:if>
    <c:if test="${productCode ne null}">
        <label class="label">产品代码为: <span style="color: yellow;">${productCode}</span> </label>
    </c:if>
    <c:if test="${productName ne null}">
        <label class="label">产品名称为: <span style="color: yellow;">${productName}</span> </label>
    </c:if>
    <c:if test="${countType ne 'sumCount'}">
        <input id="chart" type="button" class="btn-small btn-primary" value="查看曲线" onclick="showNMChart()"/>
    </c:if>
</h4>


<c:if test="${countType ne 'sumCount'}">
<input id="hhmmssRand" name="hhmmssRand" type="hidden" value="<c:if test='${hhmmssRand ne null}'>${hhmmssRand}</c:if>">
<table class="table table-hover table-bordered table-striped table-condensed">
    <tr>
        <th>年月</th>
        <th>产品代码</th>
        <th>产品名称</th>
        <th>
            <c:if test="${productCount.condition eq 'tradeType'}">贸易方式</c:if>
            <c:if test="${productCount.condition eq 'companyType'}">企业性质</c:if>
            <c:if test="${productCount.condition eq 'transportation'}">运输方式</c:if>
            <c:if test="${productCount.condition eq 'customs'}">海关</c:if>
            <c:if test="${productCount.condition eq 'country'}">产销国家</c:if>
            <c:if test="${productCount.condition eq 'city'}">城市</c:if>
            <c:if test="${productCount.condition eq null or productCount.condition eq '' }">类型</c:if>
        </th>
        <th>数量(${unit})</th>
        <th>美元价值</th>
        <th>均价</th>
    </tr>
    <c:set var="flagRow" value=""></c:set>
    <c:forEach items="${productCountList}" var="product" varStatus="st">
        <tr <c:if test="${st.index%2 eq 1}">class="warning" </c:if>
            <c:if test="${flagRow ne product.yearMonth && clr ne 'blue'}">style="color: blue;"
                <c:set var="flagRow" value="${product.yearMonth}"></c:set>
            </c:if>
            <c:if test="${flagRow eq product.yearMonth && clr eq 'blue'}">style="color: blue;"
                <c:set var="flagRow" value="${product.yearMonth}"></c:set>
            </c:if>>
            <td>${product.yearMonth}</td>
            <td>${product.productCode}</td>
            <td>
                <c:if test="${fn:contains(product.productName, '(')}">${fn:substringBefore(product.productName,'(')}</c:if>
                <c:if test="${!fn:contains(product.productName, '(')}">${product.productName}</c:if>
            </td>
            <td>${product.condition}</td>
            <td>${product.num}</td>
            <td>${product.money}</td>
            <td>${product.unitPrice}</td>
        </tr>
    </c:forEach>
</table>
</c:if>
<c:if test="${countType eq 'sumCount'}">
<table class="table table-hover table-bordered table-striped table-condensed">
    <tr>
        <th>年月</th>
        <th>
            <c:if test="${productCount.condition eq 'tradeType'}">贸易方式</c:if>
            <c:if test="${productCount.condition eq 'companyType'}">企业性质</c:if>
            <c:if test="${productCount.condition eq 'transportation'}">运输方式</c:if>
            <c:if test="${productCount.condition eq 'customs'}">海关</c:if>
            <c:if test="${productCount.condition eq 'country'}">产销国家</c:if>
            <c:if test="${productCount.condition eq 'city'}">城市</c:if>
            <c:if test="${productCount.condition eq null or productCount.condition eq '' }">类型</c:if>
        </th>
        <th>当月数量(${unit})</th>
        <th>当月所占比</th>
        <th>累计数量(${unit})</th>
        <th>累计所占比</th>
        <th>当月金额(美元)</th>
        <th>当月均价(美元/${unit})</th>
    </tr>
    <c:set var="flagRow" value=""></c:set>
    <c:forEach items="${productCountList}" var="product" varStatus="st">
        <tr <c:if test="${st.index%2 eq 1}">class="warning" </c:if>
        <c:if test="${flagRow ne product.yearMonth && clr ne 'blue'}">style="color: blue;"
            <c:set var="flagRow" value="${product.yearMonth}"></c:set>
        </c:if>
        <c:if test="${flagRow eq product.yearMonth && clr eq 'blue'}">style="color: blue;"
                <c:set var="flagRow" value="${product.yearMonth}"></c:set>
        </c:if> >
            <td>${product.yearMonth}</td>
            <td>${product.condition}</td>
            <td>${product.num}</td>
            <td>${product.monthNumRatio}%</td>
            <td>${product.currentSumNum}</td>
            <td>${product.currentSumNumRato}%</td>
            <td>${product.money}</td>
            <td>${product.unitPrice}</td>
        </tr>
    </c:forEach>
</table>
    <hr/>
    <p>
        <b>计算说明(以[进口&nbsp; 洗衣皂 &nbsp; 2011年 8月-10月&nbsp;一般贸易]为例):</b><br/>
        <br/>
        2011年9月当月数量 = 累计(洗衣皂&nbsp;  进口&nbsp; 一般贸易&nbsp; 2011年 9月)<br/>
        2011年9月当月所占比 = 2011年9月当月数量 /累计(洗衣皂&nbsp;  进口&nbsp; 所有贸易&nbsp; 2011年 9月)<br/>
        <br/>
        2011年9月累计数量 = 累计(洗衣皂&nbsp;  进口&nbsp; 一般贸易&nbsp; 2011年 8月-9月)<br/>
        2011年10月累计数量 = 累计(洗衣皂&nbsp;  进口&nbsp; 一般贸易&nbsp; 2011年 8月-10月)<br/>
        2011年10月累计所占比 = 2011年10月累计数量 / 累计(洗衣皂&nbsp;  进口&nbsp; 所有贸易&nbsp; 2011年 8月-10月)<br/>
        2011年10月累计所占比 = 2011年10月累计数量 / 累计(洗衣皂&nbsp;  进口&nbsp; 所有贸易&nbsp; 2011年 8月-10月)<br/>
    </p>
</c:if>

</form:form>

</body>
</html>