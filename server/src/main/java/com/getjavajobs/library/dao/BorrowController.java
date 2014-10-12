package com.getjavajobs.library.dao;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Book;
import com.getjavajobs.library.model.Employee;
import com.getjavajobs.library.model.Reader;
import com.getjavajobs.library.services.BookService;
import com.getjavajobs.library.services.EmployeeService;
import com.getjavajobs.library.services.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


/**
 * Created by Администратор on 10.10.2014.
 */
@Component
@Controller
public class BorrowController {

    @Autowired
    private ReaderService readerService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/addborrow")
    public ModelAndView getFreeBooks(){
        List<Book> bookList = null;
        List<Employee> employeeList = null;
        List<Reader> readerList = null;
        try {
            bookList = bookService.getFree();
            employeeList = employeeService.getAll();
            readerList = readerService.getAll();
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        ModelAndView result = new ModelAndView("/addborrow");
        result.addObject("freeBooks",bookList);
        result.addObject("employees",employeeList);
        result.addObject("readers",readerList);
        return result;
    }
}
