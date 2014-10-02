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

    public final AuthorService authorService = new AuthorService(new AuthorDao());
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            AuthorService authorService= new AuthorService(DaoFactory.getAuthorDao());
            if ("add".equals(action) || "update".equals(action)) {
                Author author = new Author();
                author.setName(req.getParameter("authorName"));
                author.setSurname(req.getParameter("authorSurname"));
                author.setPatronymic(req.getParameter("authorPatronimic"));
                author.setBirthDate(Date.valueOf(req.getParameter("birthDate")));
                author.setBirthPlace(req.getParameter("birthPlace"));
                if ("add".equals(action)) {
                    authorService.add(author);
                } else {
                   author.setId(Integer.parseInt(req.getParameter("authorId")));
                   authorService.update(author);
                }
            } else if ("delete".equals(action)) {
                authorService.delete(Integer.valueOf(req.getParameter("authorId")));
            }
            resp.sendRedirect("author");
        } catch (ServiceException e) {
            resp.sendRedirect("error");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    public List<Author> getAll() {
        List<Author> authors= new ArrayList<>();
        try {
            authors= authorService.getAll();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return authors;
    }

    public Author getById(int id){
        Author author=null;
        try {
          author = authorService.get(id);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return author;
    }
}
