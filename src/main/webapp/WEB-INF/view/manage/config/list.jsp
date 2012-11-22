<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="p" uri="/WEB-INF/tag/page" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>配置管理</title>

    <script type="text/javascript">

        function init() {
            if (confirm("确实要手动执行生成最新最热数据及快照吗？")) {
                return true;
            }
            return false;
        }

    </script>
</head>
<body>

<ul class="breadcrumb" style="border-top:none; border-left: none; padding-left: 25px;">
    <li>
        <a href="#">配置管理</a> <span class="divider">/</span>
    </li>
    <li class="active">配置管理</li>
</ul>


<div class="wrapper">
        <form:form modelAttribute="userCommand" method="post" id="form1">
            <table class="table table-bordered table-striped table-condensed">
                <thead>
                <tr>
                    <th>
                        <a href="#" id="key" >key</a>
                    </th>
                    <th>
                        <a href="#" id="name" >name</a>
                    </th>
                    <th>
                        <a href="#" id="value" >value</a>
                    </th>
                    <th>
                        <a href="#" id="describe" >describe</a>
                    </th>
                    <th>
                        <a href="#" id="update" >修改</a>
                    </th>

                </tr>
                </thead>
                <tbody>
                    <c:forEach items="${configmaps}" var="entry" varStatus="st">
                        <c:set var="configBean" value="${entry.value}"/>
                        <tr>
                            <td>${configBean.key}</td>
                            <td>${configBean.name}</td>
                            <td>${configBean.value}</td>
                            <td>${configBean.describe}</td>
                            <td>
                                <s:url var="toedit" value="/manage/toeditcfg/${configBean.key}">
                                    <s:param name="key" value="${configBean.key}"/>
                                </s:url>
                                <a href="${toedit}" class="btn btn-info">修改</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        </form:form>
</div>

</body>
</html>
