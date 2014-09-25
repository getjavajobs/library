<%@ page import="com.getjavajobs.library.model.Book" %>
<%@ page import="com.getjavajobs.library.model.Genre" %>
<%@ page import="java.util.List" %>
<%@ page import="com.getjavajobs.library.model.Author" %>
<%@ page import="com.getjavajobs.library.model.Publisher" %>
<%@ page import="com.getjavajobs.library.services.GenreService" %>
<%@ page import="com.getjavajobs.library.services.AuthorService" %>
<%@ page import="com.getjavajobs.library.services.PublisherService" %>
<%@ page import="com.getjavajobs.library.dao.DaoFactory" %>
<%@ page import="com.getjavajobs.library.exceptions.ServiceException" %>
<%@ page import="com.getjavajobs.library.services.BookService" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 25.09.2014
  Time: 3:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>
        <%
            String action = request.getParameter("action");
            if ("add".equals(action)) {
                out.println("Добавление книги");
            } else if ("update".equals(action)) {
                out.println("Редактирование книги");
            } else if ("delete".equals(action)) {
                out.println("Удаление книги");
            }
        %>
    </title>
</head>
<body>
   <%
       BookService bookService = new BookService();
       if ("add".equals(action) | "update".equals(action)) {
           AuthorService authorService = new AuthorService(DaoFactory.getAuthorDao());
           PublisherService publisherService = new PublisherService(DaoFactory.getPublisherDao());
           GenreService genreService = new GenreService();
           Book book = new Book();
           book.setName(request.getParameter("name"));
           book.setAuthor(authorService.get(Integer.valueOf(request.getParameter("author"))));
           book.setPublisher(publisherService.get(Integer.valueOf(request.getParameter("publisher"))));
           List<Genre> genres = new ArrayList();
           for (String s : request.getParameterValues("genres[]")) {
               genres.add(genreService.get(Integer.valueOf(s)));
           }
           book.setGenreList(genres);
           book.setYear(Integer.valueOf(request.getParameter("year")));
           book.setPagesNumber(Integer.valueOf(request.getParameter("pagesNumber")));
           book.setPrice(Integer.valueOf(request.getParameter("price")));
           if ("add".equals(action)) {
               book = bookService.add(book);
               if (book.getId()!=0) {
                   out.println("Книга добавлена.");
               } else {
                   out.println("Ошибка добавления.");
               }
           } else {
               book.setId(Integer.valueOf(request.getParameter("bookid")));
               if (bookService.update(book)!=null){
                   out.println("Книга обновлена.");
               }else {
                   out.println("Ошибка обновления.");
               }

           }
       } else if ("delete".equals(action)) {
           bookService.delete(Integer.valueOf(request.getParameter("bookid")));
           out.println("Книга удалена.");
       }
   %>
</body>
</html>
