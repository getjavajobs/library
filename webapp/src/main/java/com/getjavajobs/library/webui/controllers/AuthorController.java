package com.getjavajobs.library.webui.controllers;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Author;
import com.getjavajobs.library.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Виталий on 13.10.2014.
 */
@Controller
public class AuthorController {

    @Autowired
    private AuthorService autorService;

    @RequestMapping("/authors")
    public String view(
            HttpServletRequest request)
    {
        try {
            List<Author> authorList = autorService.getAll();
            request.setAttribute("authorList", authorList);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "/WEB-INF/jsp/authors.jsp";
    }
}
