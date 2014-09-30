package com.getjavajobs.library.webui;

import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Borrow;
import com.getjavajobs.library.services.BorrowService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Администратор on 28.09.2014.
 */
public class BorrowUpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws  ServletException, IOException {
        BorrowService borrowService = new BorrowService();
        int borrowID = Integer.parseInt(req.getParameter("borrowID"));

        if(req.getParameter("toDel").equals("del")){
            try {
                borrowService.delete(borrowID);
                System.out.println(req.getParameter("Удалена выдача с ID = "+ borrowID));
            } catch (ServiceException e) {
                e.printStackTrace();
            }
            resp.sendRedirect("borrows");
            return;
        }
        Date newDate = null;
        try {
            newDate = new SimpleDateFormat("yyyy-MM-dd").parse(req.getParameter("dateOfBorrow"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            Borrow borrow = borrowService.get(borrowID);
            borrow.setDateOfReturn(newDate);
            borrowService.update(borrow);
            resp.sendRedirect("borrows");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}
