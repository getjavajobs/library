package com.getjavajobs.library.dao;

import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.*;

import java.sql.*;
import java.sql.Date;
import java.util.*;

public class BorrowDao implements GenericDao<Borrow> { //interface Dao( тут будут объявлены все методы).
    //private dao
    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private BookDao bookDao = new BookDao();
    private ReaderDao readerDao = new ReaderDao();

    public Borrow add(Borrow borrow) throws DAOException {
        Connection conn = ConnectionHolder.getInstance().getConnection();
        Borrow added = null;
        boolean commit = false;
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO BORROW(books_id, date_of_borrow," +
                " date_of_return, readers_id, employee_id) VALUES (?,?,?,?,?)")) {
            ps.setInt(1, borrow.getBook().getId());
            ps.setString(2, borrow.getDateOfBorrow().toString());
            ps.setString(3, borrow.getDateOfReturn().toString());
            ps.setInt(4, borrow.getReader().getReaderId());
            ps.setInt(5, borrow.getEmployee().getId());
            ps.executeUpdate();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT last_insert_id()");
            rs.next();
            int i = Integer.parseInt(rs.getString("last_insert_id()"));
            added = get(i);
            if (!conn.getAutoCommit()) {
                conn.commit();
            }
            commit = true;
        } catch (SQLException e) {

            throw new DAOException(e.getMessage(), e);

        } finally {

            try {
                if (!commit && !conn.getAutoCommit()) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(), e);
            } finally {
                ConnectionHolder.getInstance().releaseConnection(conn);
            }
        }
        return added;
    }

    public Borrow get(int id) {
        Connection conn = ConnectionHolder.getInstance().getConnection();
        Borrow borrow = new Borrow();

        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM borrow WHERE Id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(); //ЗАЛЕЗТЬ В BOOK_DAO  И ВЫЗВАТЬ get ПО ID
            if (rs.next()) {
                borrow.setBorrowId(Integer.parseInt(rs.getString("id")));
                borrow.setBook(bookDao.get(rs.getInt("books_id")));
                borrow.setReader(readerDao.get(rs.getInt("readers_id")));
                borrow.setDateOfBorrow(rs.getDate("date_of_borrow"));
                borrow.setDateOfReturn(rs.getDate("date_of_return"));
                borrow.setEmployee(employeeDAO.get(rs.getInt("employee_id")));
            }

        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            ConnectionHolder.getInstance().releaseConnection(conn);
        }
        return borrow;
    }

    public void delete(int id) {
        Connection conn = ConnectionHolder.getInstance().getConnection();
        boolean commit = false;
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM Borrow WHERE id = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            if(!conn.getAutoCommit()) {
                conn.commit();
            }
            commit = true;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if (!commit && !conn.getAutoCommit()) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(), e);
            } finally {
                ConnectionHolder.getInstance().releaseConnection(conn);
            }
        }
    }

    public Borrow update(Borrow borrow) {
        Connection conn = ConnectionHolder.getInstance().getConnection();
        boolean commit = false;
        Borrow updated = null;
        try (PreparedStatement ps = conn.prepareStatement("UPDATE Borrow SET date_of_return = ?")) {
            ps.setDate(1, ((Date) borrow.getDateOfReturn()));
            ps.executeUpdate();
            updated = get(borrow.getBorrowId());
            if(!conn.getAutoCommit()) {
                conn.commit();
            }
            commit = true;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            try {
                if (!commit && !conn.getAutoCommit()) {
                    conn.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(), e);
            } finally {
                ConnectionHolder.getInstance().releaseConnection(conn);
            }
        }
        return updated;
    }

    public List<Borrow> getAll(){
        Connection conn = ConnectionHolder.getInstance().getConnection();
        List<Borrow> borrowList = new ArrayList<>();
        try(Statement st = conn.createStatement()){
            ResultSet rs = st.executeQuery("SELECT * FROM BORROW");
            while(rs.next()){
                Borrow borrow = new Borrow();
                borrow.setBorrowId(Integer.parseInt(rs.getString("id")));
                borrow.setBook(bookDao.get(rs.getInt("books_id")));
                borrow.setReader(readerDao.get(rs.getInt("readers_id")));
                borrow.setDateOfBorrow(rs.getDate("date_of_borrow"));
                borrow.setDateOfReturn(rs.getDate("date_of_return"));
                borrow.setEmployee(employeeDAO.get(rs.getInt("employee_id")));
                borrowList.add(borrow);
            }
        }catch (SQLException e){
            throw new DAOException(e.getMessage(),e);
        }finally {
            ConnectionHolder.getInstance().releaseConnection(conn);
        }

        return borrowList;
    }


}



