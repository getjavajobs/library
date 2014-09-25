package com.getjavajobs.library.services;

import com.getjavajobs.library.dao.BookDao;
import com.getjavajobs.library.dao.DaoFactory;
import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Book;
import com.getjavajobs.library.services.validators.BookValidator;

import java.util.List;

/**
 * Created by Vlad on 21.08.2014.
 */
public class BookService {
    private BookDao bookDao = DaoFactory.getBookDao();
    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    /*public Book add(Book book) throws DaoException;
        public void delete(int id) throws DaoException;
        public Book get(int id) throws DaoException;
        public Book update(Book book) throws DaoException;
        public List<Book> getAll() throws DaoException;*/
    public Book add(Book book) throws ServiceException {
        BookValidator validator = new BookValidator(book);
        if (!validator.validate()) {
            return null;
        }
        try {
            return bookDao.add(book);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    public Book get(int id) throws ServiceException {
        try {
            return bookDao.get(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    public Book update(Book book) throws ServiceException {
        BookValidator validator = new BookValidator(book);
        if (!validator.validate()) {
            return null;
        }

        try {
            return bookDao.update(book);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    public void delete(int id) throws ServiceException{
        try {
            bookDao.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    public List<Book> getAll() throws ServiceException{
        try {
            return bookDao.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
