<%@ page import="com.getjavajobs.library.services.AuthorService" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
    <head>
        <title>Author page</title>
        <%@ include file="head_content.jsp"%>
    </head>
    <body>

        <%@ include file="strelHeader.jsp"%>

        <jsp:useBean id="authorService" class="com.getjavajobs.library.services.AuthorService" scope="session"/>
        <c:set var="authors" value="${authorService.getAll()}" />

        <h1>Publishers table</h1>

        <table>
            <tr>
                <th>NAME</th>
                <th>SURNAME</th>
                <th>PATRONIMIC</th>
                <th>BIRTH_DATE</th>
                <th>BIRTH_PLACE</th>
                <th colspan="2">Actions</th>
            </tr>
            <c:forEach var="author" items="${authors}">
                <tr>
                    <td>${author.getName()}</td>
                    <td>${author.getSurname()}</td>
                    <td>${author.getPatronymic()}</td>
                    <td>${author.setBirthDate()}</td>
                    <td>${author.getBirthPlace()}</td>

                    <td>
                        <form method="post" action="authorsAdd.jsp"/>
                            <input type="hidden" name ="authorId" value="${author.getId()}" />
                            <input type="submit" value="Update" />
                        </form>
                    </td>
                    <td>
                        <form method="post" action="authorsAdd.jsp"/>
                            <input type="hidden" name ="authorId" value="${authorService.delete(author.getId())}" />
                            <input type="hidden" name ="commandType" value="delete" />
                            <input type="submit" value="Delete" />
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <br>

        <input type="button" value="Add author" onClick="document.location='authorAdd'">

        <%@ include file="strelFooter.jsp"%>

    </form>
</body>
</html>
