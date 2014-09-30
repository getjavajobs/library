<%@ page import="com.getjavajobs.library.services.AuthorService" %>
<html>
    <head>
        <title>Author</title>
    </head>
    <body>

       <jsp:useBean id="authorService" class="com.getjavajobs.library.services.AuthorService" scope="session"/>
        <c:set var="authors" value="${authorService.getAll()}" />

        <table>
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
                        <form method="post" action="authorsAdd.jsp"/>
                        <input type="hidden" name ="authorId" value="${author.getId()}" />
                        <input type="hidden" name ="action" value="update" />
                        <input type="submit" value="update" />
                        </form>
                    </td>
                    <td>
                        <form method="post" action="authorsAdd.jsp"/>
                        <input type="hidden" name ="authorId" value="${authorService.delete(author.getId())}" />
                        <input type="hidden" name ="action" value="delete" />
                        <input type="submit" value="delete" />
                        
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <form method="post" action="authorsAdd.jsp"/>
        <input type="hidden" name ="authorId" value="0" />
        <input type="hidden" name ="action" value="add" />
        <input type="button" value="add" />
    </form>
</body>
</html>
