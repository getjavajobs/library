/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.getjavajobs.library.webui;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Genre;
import com.getjavajobs.library.services.GenreService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
public class GenreServlet extends HttpServlet{
    
     @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String action = req.getParameter("action");
            GenreService genreService = new GenreService();
            if ("add".equals(action) || "update".equals(action)) {                                
                Genre genre = new Genre();
                genre.setGenreType(req.getParameter("name"));                
                if ("add".equals(action)) {
                    genre = genreService.add(genre);
                } else {
                    genre.setId(Integer.valueOf(req.getParameter("genreid")));
                    genreService.update(genre);
                }
            } else if ("delete".equals(action)) {
                genreService.delete(Integer.valueOf(req.getParameter("bookid")));
            }
            resp.sendRedirect("books");
        } catch (ServiceException e) {
            resp.sendRedirect("error");
        }
    }
}
