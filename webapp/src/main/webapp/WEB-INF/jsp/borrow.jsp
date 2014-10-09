<!doctype html/>
<%@ page import="com.getjavajobs.library.model.Borrow" %>
<%@ page import="com.getjavajobs.library.services.BorrowService" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Borrow </title>
</head>
<body>
    <form action="addborrow" method="post" align = "center">
        <input type="submit" value="Добавить"/>
    </form>
    

<table align = "center">
    <tr>
        <th>BorrowID</th>
        <th>Book</th>
        <th>Reader</th>
        <th>DateOfBorrow</th>
        <th>DateOfReturn</th>
        <th>Employee</th>
    </tr>

    <% ApplicationContext context =  WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        BorrowService bs = context.getBean(BorrowService.class);
        List<Borrow> listOfBorrow = bs.getAll();
        for (Borrow borrow : listOfBorrow) { %>
    <tbody>
    <tr>
        <td><%= borrow.getBorrowId() %>
        </td>
        <td><%= borrow.getBook().getName()%>
        </td>
        <td><%= borrow.getReader().getFirstName() + " " + borrow.getReader().getSecondName()%>
        </td>
        <td><%= borrow.getDateOfBorrow().toString()%>
        </td>
        <td><%= borrow.getDateOfReturn().toString()%>
        </td>
        <td><%= borrow.getEmployee().getSurname()%>
        </td>
        <td>
            <form action="updateborrow" method="post">
                <input type="hidden" name = "toDel" value="del">
                <input type="hidden" name="borrowID" value= <%= borrow.getBorrowId() %>>
                <input type="submit" value="Вернуть"/>
            </form>
        </td>
        <td>
            <form action="prolong" method="post">
                <input type="hidden" name="borrowID" value= <%= borrow.getBorrowId() %>>
                <input type="submit" value="Продлить"/>
            </form>
        </td>
    </tr>
    </tbody>
    <%}%>
</table>

</body>

</html>