<%@ page import="com.getjavajobs.library.services.GenreService" %>
<%@ page import="com.getjavajobs.library.services.AuthorService" %>
<%@ page import="com.getjavajobs.library.dao.DaoFactory" %>
<%@ page import="com.getjavajobs.library.services.PublisherService" %>
<%@ page import="com.getjavajobs.library.model.Author" %>
<%@ page import="com.getjavajobs.library.model.Publisher" %>
<%@ page import="java.util.List" %>
<%@ page import="com.getjavajobs.library.model.Genre" %>
<%@ page import="com.getjavajobs.library.exceptions.ServiceException" %>
<%@ page import="com.getjavajobs.library.services.BookService" %>
<%@ page import="com.getjavajobs.library.model.Book" %>
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
        <form method="post" action="changebookresult">
            <input type="hidden" name="action" value="add">
            <P>Название: <input name="name" required>
            <P>Автор:
            <select name="author" required>
                <% for(Author author: authors) {%>
                    <option value=<%=author.getId()%>>
                        <%=author.getName()+" "+author.getSurname()%>
                    </option>
                <%}%>
            </select>
            <P>Издательство:
            <select name="publisher" required>
                <% for(Publisher publisher: publishers) {%>
                    <option value=<%=publisher.getId()%>>
                                    <%=publisher.getName()%>
                    </option>
                <%}%>
            </select>
            <P>Жанры:
            <p><select multiple name="genres[]" required>
                <% for(Genre genre:genres) {%>
                <option value=<%=genre.getId()%>>
                    <%=genre.getGenreType()%>
                </option>
                <%}%>
            </select>
            <P>Год: <input name="year" type="text" pattern="^[ 0-9]+$" required>
            <P>Количество страниц: <input name="pagesNumber" type="text" pattern="^[ 0-9]+$" required>
            <P>Цена: <input name="price" type="text" pattern="\d+(\.\d{2})?" required>
            <P><input type="submit" value="Добавить"><input type="reset">
        </form>
    <%} else {
        BookService bookService = new BookService();
        Book book = bookService.get(bookId);
    %>
    <form method="post" action="changebookresult">
        <input type="hidden" name="action" value="update">
        <input type="hidden" name="bookid" value=<%=bookId%>>
        <P>Название: <input name="name" value=<%=book.getName()%> required>
        <P>Автор:
            <select name="author" required>
                <% for(Author author: authors) {
                    if(author.getId()==book.getAuthor().getId()) {%>
                        <option selected value=<%=author.getId()%>>
                            <%=author.getName()+" "+author.getSurname()%>
                        </option>
                    <%} else {%>
                        <option value=<%=author.getId()%>>
                            <%=author.getName()+" "+author.getSurname()%>
                        </option>
                    <%}%>
                <%}%>
            </select>
        <P>Издательство:
            <select name="publisher" required>
                <% for(Publisher publisher: publishers) {
                    if(publisher.getId()==book.getPublisher().getId()) {%>
                        <option selected value=<%=publisher.getId()%>>
                            <%=publisher.getName()%>
                        </option>
                    <%} else {%>
                        <option value=<%=publisher.getId()%>>
                            <%=publisher.getName()%>
                        </option>
                    <%}%>
                <%}%>
            </select>
        <P>Жанры:
        <p><select multiple name="genres[]" required>
            <% for(Genre genre:genres) {
                if(book.getGenreList().contains(genre)) {%>
                    <option selected value=<%=genre.getId()%>>
                        <%=genre.getGenreType()%>
                    </option>
                <%} else {%>
                    <option value=<%=genre.getId()%>>
                        <%=genre.getGenreType()%>
                    </option>
                <%}%>
            <%}%>
        </select>
        <P>Год: <input value=<%=book.getYear()%> name="year" type="text" pattern="^[ 0-9]+$" required>
        <P>Количество страниц: <input value=<%=book.getPagesNumber()%> name="pagesNumber" type="text" pattern="^[ 0-9]+$" required>
        <P>Цена: <input value=<%=book.getPrice()%> name="price" type="text" pattern="\d+(\.\d{2})?" required>
        <P><input type="submit" value="Изменить"><input type="reset">
    </form>
    <%}%>
</body>
</html>
