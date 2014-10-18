package com.getjavajobs.library.webui.controllers;

import com.getjavajobs.library.dao.DaoFactory;
import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Book;
import com.getjavajobs.library.model.Genre;
import com.getjavajobs.library.services.AuthorService;
import com.getjavajobs.library.services.BookService;
import com.getjavajobs.library.services.GenreService;
import com.getjavajobs.library.services.PublisherService;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 18.10.2014.
 */
public class BookController {
    @RequestMapping("/books")
    public String showAll() {
        return "/WEB-INF/jsp/books.jsp";
    }
    @RequestMapping("/books/edit")
    public String editForm() {
        return "/WEB-INF/jsp/addbook.jsp";
    }
    @RequestMapping("/books/do_action")
    public String edit(HttpServletRequest request) throws ServiceException {
        String action = request.getParameter("action");
        BookService bookService = new BookService();
        if ("add".equals(action) | "update".equals(action)) {
            AuthorService authorService = new AuthorService(DaoFactory.getAuthorDao());
            PublisherService publisherService = new PublisherService(DaoFactory.getPublisherDao());
            GenreService genreService = new GenreService();
            Book book = new Book();
            book.setName(request.getParameter("name"));
            book.setAuthor(authorService.get(Integer.valueOf(request.getParameter("author"))));
            book.setPublisher(publisherService.get(Integer.valueOf(request.getParameter("publisher"))));
            List<Genre> genres = new ArrayList<>();
            for (String s : request.getParameterValues("genres[]")) {
                genres.add(genreService.get(Integer.valueOf(s)));
            }
            book.setGenreList(genres);
            book.setYear(Integer.valueOf(request.getParameter("year")));
            book.setPagesNumber(Integer.valueOf(request.getParameter("pagesNumber")));
            book.setPrice(Double.valueOf(request.getParameter("price")));
            if ("add".equals(action)) {
                bookService.add(book);
            } else {
                book.setId(Integer.valueOf(request.getParameter("bookid")));
                bookService.update(book);
            }
        } else if ("delete".equals(action)) {
            bookService.delete(Integer.valueOf(request.getParameter("bookid")));
        }
        return "redirect:/books";
    }
}
