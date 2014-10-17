<!doctype html/>
<%@ page import="com.getjavajobs.library.model.Borrow" %>
<%@ page import="com.getjavajobs.library.services.BorrowService" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Продление выдачи</title>
</head>
<body>


<form action = "prolong" method = "get">
    <input type = "hidden" name = "borrowID" value = ${borrowId}>
    <input type ="date" name = "dateOfReturn" value="${dateOfReturn}" min = <fmt:formatDate value="${now}"/> />
    <input type = "submit" value="Обновить">

</form>

</body>
</html>
