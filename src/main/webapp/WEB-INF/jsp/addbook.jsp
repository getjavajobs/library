<%@ page import="com.getjavajobs.library.services.GenreService" %>
<%@ page import="com.getjavajobs.library.services.AuthorService" %>
<%@ page import="com.getjavajobs.library.dao.DaoFactory" %>
<%@ page import="com.getjavajobs.library.services.PublisherService" %>
<%@ page import="com.getjavajobs.library.model.Author" %>
<%@ page import="com.getjavajobs.library.model.Publisher" %>
<%@ page import="java.util.List" %>
<%@ page import="com.getjavajobs.library.model.Genre" %>
<%@ page import="com.getjavajobs.library.exceptions.ServiceException" %>
<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 24.09.2014
  Time: 16:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <% Integer bookId = request.getParameter("bookid")==null? null:new Integer(request.getParameter("bookid"));
        if (bookId!=null) {%>
            Редактирование книги
        <%} else {%>
            Добавление книги
        <%}%>
    </title>
</head>
<body>
    <%
        List<Genre> genres = null;
        List<Author> authors = null;
        List<Publisher> publishers = null;

        try {
            genres = new GenreService().getAll();
            authors = new AuthorService(DaoFactory.getAuthorDao()).getAll();
            publishers = new PublisherService(DaoFactory.getPublisherDao()).getAll();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    %>
    <% if (bookId==null) {%>
    private int id;
    private String name;
    private Author author;
    private Publisher publisher;
    private List<Genre> genreList;
        private int year;
        private int pagesNumber;
        private double price;
        <form method="post">
            Название: <input name="name">

            <select name="author" size="4">
                <% for(Author author: authors) {%>
                    <option value="<%=author.getName()+author.getSurname()%>"></option>
                <%}%>
            </select>

            <select name="publisher" size="4">
                <% for(Publisher publisher: publishers) {%>
                    <option value="<%=publisher.getName()%>"></option>
                <%}%>
            </select>

            <ul>
                <% for(Genre genre:genres) {%>
                    <li>
                        <input type="checkbox" name="genres" value="<%=genre.getGenreType()%>">
                    </li>
                <%}%>
            </ul>
            Год: <input name="year" type="text" pattern="^[ 0-9]+$">
            Количество страниц: <input name="pagesNumber" type="text" pattern="^[ 0-9]+$">
            Цена: <input name="price" type="text" pattern="\d+(\.\d{2})?">
        </form>
    <%}%>
</body>
</html>
