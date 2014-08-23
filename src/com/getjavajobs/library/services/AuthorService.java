package com.getjavajobs.library.services;

import com.getjavajobs.library.dao.AuthorDao;
import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Author;

import java.util.List;

/**
 * Created by Roman on 23.08.14.
 */
public class AuthorService {

    private AuthorDao authorDao;

    public AuthorService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public Author add(Author author) throws ServiceException {
        try {
            return authorDao.add(author);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    public Author get(int id) throws ServiceException {
        try {
            return authorDao.get(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    public Author update(Author author) throws ServiceException {
        try {
            return authorDao.update(author);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    public void delete(int id) throws ServiceException{
        try {
            authorDao.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    public List<Author> getAll() throws ServiceException{
        try {
            return authorDao.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
