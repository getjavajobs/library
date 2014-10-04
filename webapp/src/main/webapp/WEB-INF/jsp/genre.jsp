<%@ page import="java.util.List" %>
<%@ page import="com.getjavajobs.library.model.Genre" %>
<%@ page import="com.getjavajobs.library.services.GenreService" %>
<%@ page import="com.getjavajobs.library.exceptions.ServiceException" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="head_content.jsp"%>
    <title>Жанры</title>
</head>
<body>
    <%@ include file="strelHeader.jsp"%>

    <%
        GenreService genreService = new GenreService();
        List<Genre> genres = null;
        try {
            genres = genreService.getAll();
        } catch (ServiceException e) {}
    %>
    <table border="1px" frame="1px" width="100%">
        <thead>
            <tr>
                <td>Название</td>                
            </tr>
        </thead>
        <tbody>
            <%for (Genre genre : genres){%>
                <tr>
                    <td><%=genre.getGenreType()%></td>                    
                    <td>
                        <input type="button" value="Изменить" onClick="document.location='changegenre.jsp?genreid=<%=genre.getId() %>'">                        
                    </td>
                    <td>
                        <form action="genresevlet" method="post">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="genreid" value=<%=genre.getId()%>>
                            <input type="submit" value="Удалить">
                        </form>
                    </td>
                </tr>
            <%}%>
        </tbody>
    </table>
    <input type="button" value="Добавить" onClick="document.location='changegenre.jsp'">
    <%@ include file="strelFooter.jsp"%>
</body>
</html>
