<%@ page import="java.util.List" %>
<%@ page import="com.getjavajobs.library.model.Book" %>
<%@ page import="com.getjavajobs.library.services.BookService" %>
<%@ page import="com.getjavajobs.library.exceptions.ServiceException" %>
<%@ page import="com.getjavajobs.library.model.Genre" %>


<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 24.09.2014
  Time: 1:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Книги</title>
</head>
<body>
    <%
        BookService bookService = new BookService();
        List<Book> books = null;
        try {
            books = bookService.getAll();
        } catch (ServiceException e) {}
    %>
    <table border="1px" frame="1px">
        <thead>
            <tr>
                <td>Название</td>
                <td>Автор</td>
                <td>Издательство</td>
                <td>Жанры</td>
                <td>Год</td>
                <td>Количество страниц</td>
                <td>Цена</td>
            </tr>
        </thead>
        <tbody>
            <%for (Book book: books){%>
                <tr>
                    <td><%=book.getName()%></td>
                    <td><%=book.getAuthor().getName()+" "+book.getAuthor().getSurname()%></td>
                    <td><%=book.getPublisher().getName()%></td>
                    <td>
                        <%
                            List<Genre> genres = book.getGenreList();
                            for (int i=0;i<genres.size();i++){
                        %>
                            <%=genres.get(i).getGenreType()%>
                            <% if (i!=genres.size()-1){%> <%=", "%> <%}%>
                        <%}%>
                    </td>
                    <td><%=book.getYear()%></td>
                    <td><%=book.getPagesNumber()%></td>
                    <td><%=book.getPrice()%></td>
                    <td>
                        <form action="addbook" method="post">
                            <input type="hidden" name="bookid" value=<%=book.getId()%>>
                            <input type="submit" value="Изменить">
                        </form>
                    </td>
                    <td>
                        <form action="changebookresult" method="post">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="bookid" value=<%=book.getId()%>>
                            <input type="submit" value="Удалить">
                        </form>
                    </td>
                </tr>
            <%}%>
        </tbody>
    </table>
    <input type="button" value="Добавить" onClick="document.location='addbook'">
</body>
</html>