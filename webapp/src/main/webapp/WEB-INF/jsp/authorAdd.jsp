<%@ page import = "com.getjavajobs.library.model.Author" %>
<%@ page import ="java.util.List" %>
<%@ page import ="com.getjavajobs.library.services.AuthorService" %>

<html>
    <head>
        <title>Author</title>
    </head>
    <body>
        <table>
            <jsp:useBean id="authorServlet" class="com.getjavajobs.library.webui.AuthorServlet" scope="session" />
            <c:set var="authorId" scope ="page" value="${Integer.parseInt(requestScope.authorId)}"/>
            <c:set var="author" value="${authorServlet.get(authorId)}" scope ="page" />
            <tr>
                <th>
                    NAME
                <th>
                <th>
                    SURNAME
                <th>
                <th>
                    PATRONIMIC
                <th>
                <th>
                    BIRTH_DATE
                <th>
                <th>
                    BIRTH_PLACE
                <th>

            <tr>
            <tr>
                <td>
                            <input type="text" name="authorName" value="${(authorId>0)? author.getName():""}"/> 
                        </td>
                        <td>
                          <input type="text" name="authorSurname" value="${(authorId>0)? author.getSurname():""}"/> 
                        </td>
                        <td>
                            <input type="text" name="authorPatronimic" value="${(authorId>0)? author.getPatronymic():""}"/>
                        </td>
                        <td>
                            <input type="text" name="authorBirthDay" value="${(authorId>0)? author.getBirthDate():""}"/> 
                        </td>
                        <td>
                            <input type="text" name="authorBirthDay" value="${(authorId>0)? author.getBirthPlace():""}"/> 
                        </td>
                    </tr>
                </table>
                    <input type="submit" value="${(authorId>0)?"update":"add"}" >
                </c:if>
            </body>
        </html>
