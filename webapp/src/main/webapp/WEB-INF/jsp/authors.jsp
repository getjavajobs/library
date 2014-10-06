<%@ page import="com.getjavajobs.library.services.AuthorService" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Author page</title>
        <%@ include file="head_content.jsp"%>
    </head>
    <body>

        <%@ include file="strelHeader.jsp"%>

        <jsp:useBean id="authorServlet" class="com.getjavajobs.library.webui.AuthorServlet" scope="session"/>
        <c:set var="authors" value="${authorServlet.getAll()}" />

        <h1>Author table</h1>

        <table border="1px solid black">
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
