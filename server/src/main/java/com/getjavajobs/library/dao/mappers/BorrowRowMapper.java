package com.getjavajobs.library.dao.mappers;

import com.getjavajobs.library.dao.BookDao;
import com.getjavajobs.library.dao.EmployeeDao;
import com.getjavajobs.library.dao.ReaderDao;
import com.getjavajobs.library.model.Borrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Администратор on 09.10.2014.
 */
@Component
public class BorrowRowMapper implements RowMapper {
    @Autowired
    private EmployeeDao employeeDAO;
    @Autowired
    private BookDao bookDao;
    @Autowired
    private ReaderDao readerDao;
    @Override
    public Borrow mapRow(ResultSet resultSet, int i) throws SQLException {
        Borrow borrow = new Borrow();
        borrow.setBorrowId(resultSet.getInt("id"));
        borrow.setDateOfReturn(resultSet.getDate("date_of_return"));
        borrow.setDateOfBorrow(resultSet.getDate("date_of_borrow"));
        borrow.setEmployee(employeeDAO.get(resultSet.getInt("employee_id")));
        borrow.setReader(readerDao.get(resultSet.getInt("readers_id")));
        borrow.setBook(bookDao.get(resultSet.getInt("books_id")));
        return borrow;
    }
}
