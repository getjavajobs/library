<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавление выдачи</title>
</head>
<body>

<jsp:useBean id ="bookService" class="com.getjavajobs.library.services.BookService"/>
<jsp:useBean id="readerService" class="com.getjavajobs.library.services.ReaderService"/>
<jsp:useBean id="employeeService" class="com.getjavajobs.library.services.EmployeeService"/>
<jsp:useBean id = "currentDate" class = "java.util.Date"/>
<jsp:useBean id = "dateFormat" class = "java.text.SimpleDateFormat"/>

<form action="insertborrow" method = "post">
    <ul align = "center">
        <li>Книга  <select  name = "book" required>
            <option></option>
            <c:forEach var="book" items="${freeBooks}">
                <option value="${book.getId()}"> ${book.getName()} </option>
            </c:forEach> </select>  </li>

        <li>Читатель  <select name = "reader" required>
            <option></option>
            <c:forEach var="reader" items="${readers}">
                <option value="${reader.getReaderId()}"> ${reader.getSecondName()} &emsp; ${reader.getFirstName()} </option>
            </c:forEach> </select>  </li>

        <li> Работник <select name = "employee" required>
            <option></option>
            <c:forEach var="employee" items="${employees}">
                <option value="${employee.getId()}"> ${employee.getName()} </option>
            </c:forEach> </select>  </li>

        <li> Дата возврата <input type ="date" name = "dateOfReturn" min = <fmt:formatDate value="${now}"/> /> </li>
        <input type = "submit" value="Добавить">
    </ul>

</form>


</body>
</html>
