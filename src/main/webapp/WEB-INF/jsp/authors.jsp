<%@page import="com.getjavajobs.library.model.Author"%>
<%@page import="java.util.List" %>
<%@page import="com.getjavajobs.library.services.AuthorService" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
    <head>
        <title>Author</title>
    </head>
    <body>
        AUTHORS<br/>
        <table>
            <jsp:useBean id="authorService" class="com.getjavajobs.library.services.AuthorService" scope="page" />
            <c:set var="authors" value="${authorService.getAll()}" />
            <tr>
                <th>
                    NAME
                </th>
                <th>
                    SURNAME
                </th>
                <th>
                    PATRONIMIC
                </th>
                <th>
                    BIRTH_DATE
                </th>
                <th>
                    BIRTH_PLACE
                </th>
                <th>
                    CHANGE
                </th>
            </tr>
            <c:forEach var="author" items="${authors}">
                <tr>                    <td>
                        ${author.getName()}
                    </td>
                    <td>
                        ${author.getSurname()}
                    </td>
                    <td>
                        ${author.getPatronymic()}
                    </td>
                    <td>
                        ${author.setBirthDate()}
                    </td>
                    <td>
                        ${author.getBirthPlace()}
                    </td>
                    <td>
                        <form method="post" action="'main.webapp.WEB-INF.jsp.authorsADD'"/>
                        <input type="hidden" name ="authorId" value="${author.getId()}" />
                        <input type="submit" value="change" />
                        </form>
                    </td>
                    <td>
                        <form/>
                        <input type="hidden" name ="authorId" value="${authorService.delete(author.getId())}" />
                        <input type="submit" value="delete" />
                        
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <form method="post" action="'main.webapp.WEB-INF.jsp.authorsAdd'"/>
        <input type="hidden" name ="authorId" value="0" />
        <input type="button" value="add" />
    </form>
</body>
</html>
