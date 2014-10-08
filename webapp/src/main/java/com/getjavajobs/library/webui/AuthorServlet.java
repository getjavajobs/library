package com.getjavajobs.library.webui;

import com.getjavajobs.library.dao.AuthorDao;
import com.getjavajobs.library.dao.DaoFactory;
import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Author;
import com.getjavajobs.library.services.AuthorService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Виталий on 29.09.2014.
 */
public class AuthorServlet extends HttpServlet {

    private  AuthorService authorService;

    public AuthorServlet() {
        try {
            init();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws ServletException {
        AuthorService authorService = new AuthorService(DaoFactory.getAuthorDao());
        this.authorService=authorService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String commandType = request.getParameter("commandType");
            switch (commandType) {
                case "add":
                case "update": {
                    Author author = new Author();
                    author.setName(request.getParameter("authorName"));
                    author.setSurname(request.getParameter("authorSurname"));
                    author.setPatronymic(request.getParameter("authorPatronymic"));
                    author.setBirthDate(Date.valueOf(request.getParameter("birthDate")));
                    author.setBirthPlace(request.getParameter("birthPlace"));

                    if (commandType.equals("add")) {
                        authorService.add(author);
                    } else {
                        int authorId = Integer.valueOf(request.getParameter("authorId"));
                        author.setId(authorId);
                        authorService.update(author);
                    }
                    response.sendRedirect("authors");
                }
                break;
                default: {
                    int authorId = Integer.valueOf(request.getParameter("authorId"));
                    authorService.delete(authorId);
                    response.sendRedirect("authors");
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            response.sendRedirect("error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doPost(request, response);
    }

    public List<Author> getAll() {
        List<Author> authors = new ArrayList<>();
        try {
            System.out.println((authorService == null)? "\n\nNULLLLLLL!!!!": "NOT NULL");
            authors = authorService.getAll();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public Author get(int id) {
        Author author = new Author();
        try {
            author = authorService.get(id);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return author;
    }
}
