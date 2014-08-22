package com.getjavajobs.library.dao;

import com.getjavajobs.library.exceptions.DaoException;
import com.getjavajobs.library.model.Book;

import java.util.List;

/**
 * Created by Vlad on 21.08.2014.
 */
public interface BookDao {
    public Book add(Book book) throws DaoException;
    public void delete(int id) throws DaoException;
    public Book get(int id) throws DaoException;
    public Book update(Book book) throws DaoException;
    public List<Book> getAll() throws DaoException;
}
