<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Author page</title>
    <%@ include file="head_content.jsp"%>
</head>
<body>

<%@ include file="strelHeader.jsp"%>

<!--<h1>Author table</h1> -->
<h1>${requestScope.len}</h1>
<table border="1px solid black">
    <tr>
        <th>ID</th>
        <th>NAME</th>
        <th>SURNAME</th>
        <th>PATRONIMIC</th>
        <th>BIRTH_DATE</th>
        <th>BIRTH_PLACE</th>
        <th colspan="2">Actions</th>
    </tr>
    <c:forEach var="authors" items="${requestScope.authorList}">
        <td>${authors.getId()}</td>
        <td>${authors.getName()}</td>
        <td>${authors.getSurname()}</td>
        <td>${authors.getPatronymic()}</td>
        <td>${authors.getBirthDate()}</td>
        <td>${authors.getBirthPlace()}</td>


        <td>
            <form action="authorAdd" method="post">
                <input type="hidden" name="authorId" value=${authors.getId()}>
                <input type="submit" value="Update">
            </form>
        </td>
        <td>
            <form action="authorAdd" method="post">
                <input type="hidden" name="commandType" value="delete">
                <input type="hidden" name="authorId" value=${authors.getId()}>
                <input type="submit" value="Delete">
            </form>
        </td>
    </c:forEach>
</table>

<br>

<input type="button" value="Add new author" onClick="document.location='authorAdd'">


<%@ include file="strelFooter.jsp"%>

</body>
</html>
