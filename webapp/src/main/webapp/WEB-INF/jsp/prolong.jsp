<!doctype html/>
<%@ page import="com.getjavajobs.library.model.Borrow" %>
<%@ page import="com.getjavajobs.library.services.BorrowService" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Продление выдачи</title>
</head>
<body>
<%  ApplicationContext context =  WebApplicationContextUtils.getWebApplicationContext(getServletContext());
    BorrowService borrowService = context.getBean(BorrowService.class);
    Borrow borrow = borrowService.get(Integer.valueOf(request.getParameter("borrowID")));
%>

<form action = "updateborrow" method = "post">
    <input type = "hidden" name = "toDel" value = "prol">
    <input type = "hidden" name = "borrowID" value = <%=borrow.getBorrowId()%>>
    <input type ="date" name = "dateOfBorrow" value="<%=borrow.getDateOfReturn()%>" min = "<%= new SimpleDateFormat("yyyy-MM-dd").format(new Date())%>" />
    <input type = "submit" value="Обновить">

</form>

</body>
</html>
