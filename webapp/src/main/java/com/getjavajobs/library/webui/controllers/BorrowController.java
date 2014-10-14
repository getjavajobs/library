package com.getjavajobs.library.webui.controllers;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Book;
import com.getjavajobs.library.model.Borrow;
import com.getjavajobs.library.model.Employee;
import com.getjavajobs.library.model.Reader;
import com.getjavajobs.library.services.BookService;
import com.getjavajobs.library.services.BorrowService;
import com.getjavajobs.library.services.EmployeeService;
import com.getjavajobs.library.services.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Администратор on 10.10.2014.
 */

@Controller
public class BorrowController {
    @Autowired
    private ReaderService readerService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private BookService bookService;
    @Autowired
    private BorrowService borrowService;

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
        ModelAndView result = new ModelAndView("/WEB-INF/jsp/addBorrow.jsp");
        result.addObject("freeBooks",bookList);
        result.addObject("employees",employeeList);
        result.addObject("readers",readerList);
        return result;
    }
    @RequestMapping(value = "/borrows")
    public ModelAndView getListOfBorrows(){
        List<Borrow> borrowList = null;
        try{
            borrowList = borrowService.getAll();
        }catch (ServiceException e){
            e.printStackTrace();
        }
        ModelAndView result = new ModelAndView("/WEB-INF/jsp/borrow.jsp");
        result.addObject("borrows",borrowList);
        return result;
    }
    @RequestMapping(value = "/deleteBorrow")
    public String delBorrow(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("borrowID"));
        try {
            bookService.delete(id);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "/WEB-INF/jsp/borrow.jsp";
    }
    @RequestMapping(value = "/updateBorrow")
    public String updateBorrow(HttpServletRequest request){
        Date newDate = null;
        int id = Integer.parseInt(request.getParameter("borrowID"));
        try {
            newDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateOfBorrow"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Borrow borrow = borrowService.get(id);
            borrow.setDateOfReturn(newDate);
            borrowService.update(borrow);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return "/WEB-INF/jsp/borrow.jsp";
    }

}
