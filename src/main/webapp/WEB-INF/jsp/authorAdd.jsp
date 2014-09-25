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
            <c:set var="authorId" scope ="page" value="${Integer.parseInt(requestScope.authorId)}"/>
            <c:set var="author" value="${authorService.get(authorId)}" scope ="page" />
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
                            <input type="text" name="authorPatronimic" value="${(authorId>0)? author.getPatronimic():""}"/> 
                        </td>
                        <td>
                            <input type="text" name="authorBirthDay" value="${(authorId>0)? author.getBirthDate():""}"/> 
                        </td>
                        <td>
                            <input type="text" name="authorBirthDay" value="${(authorId>0)? author.getBirthPlace():""}"/> 
                        </td>
                    </tr>
                </table>
                <c:if test="$authorId <1 ">
                    <input type="submit" value="????????" >
                    <c:if/>
                    <c:if test="$authorId > 0">
                        <input type="text" value="${author.getname()}"> 
                        <c:if/>

                    </body>
                </html>
