package com.getjavajobs.library.webui;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Borrow;
import com.getjavajobs.library.services.BookService;
import com.getjavajobs.library.services.BorrowService;
import com.getjavajobs.library.services.EmployeeService;
import com.getjavajobs.library.services.ReaderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Администратор on 29.09.2014.
 */
public class BorrowAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int readerId = Integer.parseInt(req.getParameter("reader"));
        int bookId = Integer.parseInt(req.getParameter("book"));
        int employeeId = Integer.parseInt(req.getParameter("employee"));
        Date dateOfReturn = null;
        try {
            dateOfReturn = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("dateOfReturn"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        BorrowService borrowService = new BorrowService();
        ReaderService readerService = new ReaderService();
        BookService bookService = new BookService();
        EmployeeService employeeService = new EmployeeService();

        Borrow borrow = new Borrow();
        try {
            borrow.setDateOfBorrow(new Date());
            borrow.setReader(readerService.get(readerId));
            borrow.setBook(bookService.get(bookId));
            borrow.setEmployee(employeeService.get(employeeId));
            borrow.setDateOfReturn(dateOfReturn);
            borrowService.add(borrow);

            resp.sendRedirect("borrows");
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }
}
