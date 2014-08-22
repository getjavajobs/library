package com.getjavajobs.library.dao;

import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Book;

import java.util.List;

/**
 * Created by Vlad on 21.08.2014.
 */
public interface BookDao {
    public Book add(Book book) throws DAOException;
    public void delete(int id) throws DAOException;
    public Book get(int id) throws DAOException;
    public Book update(Book book) throws DAOException;
    public List<Book> getAll() throws DAOException;
}
