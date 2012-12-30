<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title></title>

</head>
<body>
<h3>执行临时任务</h3>

<form method="post" id="form1" action="${pageContext.request.contextPath}/manage/excute">
    <label class="label">sql语句:</label>  <br/>
    <textarea name="excute" rows="10" cols="200" style="width: 600px"></textarea>
    <br/> &nbsp; &nbsp;&nbsp;&nbsp;&nbsp;
    <input type="submit" class="btn btn-success" value="提交">
</form>
</body>
</html>