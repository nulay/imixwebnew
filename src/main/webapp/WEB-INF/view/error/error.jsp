<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isErrorPage="true" %>
<%--
  Created by IntelliJ IDEA.
  User: miha
  Date: 06.05.14
  Time: 10:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><spring:message code="errors.error"/></title>
</head>
<body>
<div align="center" style="padding-top: 100px">
    <p style="color: red; font: bold 40px Arial;"><spring:message code="errors.example-first"/></p>
    <p style="font: bold 35px Arial;"><spring:message code="errors.litleerror"/></p>
</div>
</body>
</html>