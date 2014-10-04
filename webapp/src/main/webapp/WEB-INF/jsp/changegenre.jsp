<%@ page import="com.getjavajobs.library.services.GenreService" %>
<%@ page import="java.util.List" %>
<%@ page import="com.getjavajobs.library.model.Genre" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@ include file="head_content.jsp"%>
    <title>
        <% Integer genreId = request.getParameter("genreid")==null? null:new Integer(request.getParameter("genreid"));
        if (genreId!=null) {%>
            Редактирование жанра
        <%} else {%>
            Добавление жанра
        <%}%>
    </title>
</head>
<body>
    <%@ include file="strelHeader.jsp"%>
    
    <% if (genreId==null) {%>
        <form method="post" action="genresevlet">
            <input type="hidden" name="action" value="add">
            <P>Название: <input name="name" required>            
            <P><input type="submit" value="Добавить"><input type="reset">
        </form>
    <%} else {
        GenreService genreService = new GenreService();
        Genre genre = genreService.get(genreId);
    %>
    <form method="post" action="genresevlet">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="genreid" value=<%=genreId%>>
        <P>Название: <input name="name" value=<%=genre.getGenreType()%> required>
        <P><input type="submit" value="Изменить"><input type="reset">
    </form>
    <%}%>
    <%@ include file="strelFooter.jsp"%>
</body>
</html>
