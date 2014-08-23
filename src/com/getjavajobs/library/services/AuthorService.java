package com.getjavajobs.library.services;

import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Author;
import com.getjavajobs.library.model.Book;
import com.getjavajobs.library.services.validators.AuthorValidator;
import com.getjavajobs.library.services.validators.BookValidator;

import java.util.List;


/**
 * Created by Vlad on 24.08.2014.
 */
public class AuthorService {
    private AuthorDao authorDao;
    public void setAuthorDao(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    public Author add(Author author) throws ServiceException{
        AuthorValidator validator = new AuthorValidator(author);
        if (!validator.validate()) {
            return null;
        }
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
        BookValidator validator = new BookValidator(author);
        if (!validator.validate()) {
            return null;
        }

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
    public List<Book> getAll() throws ServiceException{
        try {
            return authorDao.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
