package com.getjavajobs.library.dao;

import com.getjavajobs.library.dao.mappers.BorrowRowMapper;
import com.getjavajobs.library.dao.mappers.IntegerRowMapper;
import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Borrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class BorrowDao implements GenericDao<Borrow> { //interface Dao( тут будут объявлены все методы).

	@Autowired
    private EmployeeDao employeeDAO;
	@Autowired
    private BookDao bookDao;
	@Autowired
    private ReaderDao readerDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IntegerRowMapper integerRowMapper;
    @Autowired
    private BorrowRowMapper borrowRowMapper;
    @Transactional
    public Borrow add(Borrow borrow) throws DAOException {
        Borrow added = borrow;
        String sql = "INSERT INTO BORROW(books_id, date_of_borrow," +
                " date_of_return, readers_id, employee_id) VALUES (?,?,?,?,?)";

        jdbcTemplate.update(sql, new Object[]{borrow.getBook().getId(), borrow.getDateOfBorrow(),borrow.getDateOfReturn(),
                borrow.getReader().getReaderId(),borrow.getEmployee().getId()});
        int id = (Integer)jdbcTemplate.queryForObject("SELECT last_insert_id()",integerRowMapper);
        added.setBorrowId(id);
        return added;
    }

    public Borrow get(int id) {
        Borrow borrow = (Borrow)jdbcTemplate.queryForObject("SELECT * FROM borrow WHERE Id = " + id, borrowRowMapper);
        return borrow;
    }
    @Transactional
    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Borrow WHERE id = "+ id);
    }
    @Transactional
    public Borrow update(Borrow borrow) {
        jdbcTemplate.update("UPDATE borrow SET date_of_return = ? WHERE id = ?",new Object[]{borrow.getDateOfReturn(),borrow.getBorrowId()});
        Borrow updated = (Borrow)jdbcTemplate.queryForObject("SELECT * FROM borrow WHERE Id = " + borrow.getBorrowId(), borrowRowMapper);
        return updated;
    }

    public List<Borrow> getAll(){
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM BORROW");
        List<Borrow> borrowList = new ArrayList<>();
        for (Map row : rows) {
            Borrow borrow = new Borrow();
            borrow.setBorrowId((Integer) (row.get("id")));
            borrow.setEmployee(employeeDAO.get((Integer) row.get("employee_id")));
            borrow.setDateOfReturn((Date) row.get("date_of_return"));
            borrow.setDateOfBorrow((Date) row.get("date_of_borrow"));
            borrow.setBook(bookDao.get((Integer) row.get("books_id")));
            borrow.setReader(readerDao.get((Integer) row.get("readers_id")));
            borrowList.add(borrow);
        }
        return borrowList;
    }




}



