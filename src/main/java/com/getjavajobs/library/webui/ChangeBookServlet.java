package com.getjavajobs.library.webui;

import com.getjavajobs.library.dao.DaoFactory;
import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Book;
import com.getjavajobs.library.model.Genre;
import com.getjavajobs.library.services.AuthorService;
import com.getjavajobs.library.services.BookService;
import com.getjavajobs.library.services.GenreService;
import com.getjavajobs.library.services.PublisherService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 26.09.2014.
 */
public class ChangeBookServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            BookService bookService = new BookService();
            if ("add".equals(action) | "update".equals(action)) {
                AuthorService authorService = new AuthorService(DaoFactory.getAuthorDao());
                PublisherService publisherService = new PublisherService(DaoFactory.getPublisherDao());
                GenreService genreService = new GenreService();
                Book book = new Book();
                book.setName(req.getParameter("name"));
                book.setAuthor(authorService.get(Integer.valueOf(req.getParameter("author"))));
                book.setPublisher(publisherService.get(Integer.valueOf(req.getParameter("publisher"))));
                List<Genre> genres = new ArrayList<>();
                for (String s : req.getParameterValues("genres[]")) {
                    genres.add(genreService.get(Integer.valueOf(s)));
                }
                book.setGenreList(genres);
                book.setYear(Integer.valueOf(req.getParameter("year")));
                book.setPagesNumber(Integer.valueOf(req.getParameter("pagesNumber")));
                book.setPrice(Double.valueOf(req.getParameter("price")));
                if ("add".equals(action)) {
                    book = bookService.add(book);
                } else {
                    book.setId(Integer.valueOf(req.getParameter("bookid")));
                    bookService.update(book);
                }
            } else if ("delete".equals(action)) {
                bookService.delete(Integer.valueOf(req.getParameter("bookid")));
            }
            resp.sendRedirect("books");
        } catch (ServiceException e) {
            resp.sendRedirect("error");
        }
    }
}
