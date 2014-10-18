<!doctype html/>
<%@ page import="com.getjavajobs.library.model.Borrow" %>
<%@ page import="com.getjavajobs.library.services.BorrowService" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title> Borrow </title>
    <%@ include file="head_content.jsp"%>

</head>
<body>

        <%@ include file="strelHeader.jsp" %>


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

        <c:forEach var="borrow" items="${borrows}">

    <tbody>
    <tr>
        <td> ${borrow.getBorrowId()}
        </td>
        <td>${borrow.getBook().getName()}
        </td>
        <td>${borrow.getReader().getFirstName()} &emsp; ${borrow.getReader().getSecondName()}
        </td>
        <td>${borrow.getDateOfBorrow().toString()}
        </td>
        <td>${borrow.getDateOfReturn().toString()}
        </td>
        <td>${borrow.getEmployee().getSurname()}
        </td>
        <td>
            <form action="returnBorrow" method="get">
                <input type="hidden" name="borrowID" value= ${borrow.getBorrowId()}>
                <input type="submit" value="Вернуть"/>
            </form>
        </td>
        <td>
            <form action="updateBorrow" method="get">
                <input type="hidden" name="borrowID" value= ${borrow.getBorrowId()}>
                <input type="submit" value="Продлить"/>
            </form>
        </td>
    </tr>
    </c:forEach>
    </tbody>

</table>
        <%@ include file="strelFooter.jsp"%>
</body>

</html>