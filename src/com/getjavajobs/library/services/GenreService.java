package com.getjavajobs.library.services;

import com.getjavajobs.library.dao.GenreDAO;
import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Genre;
import com.getjavajobs.library.services.validators.GenreValidator;

import java.util.List;

public class GenreService {

    private GenreDAO genreDao;

    public GenreService() {
        genreDao = new GenreDAO();
    }

    public Genre add(Genre book) throws ServiceException {
        GenreValidator validator = new GenreValidator(book);
        if (!validator.validate()) {
            return null;
        }

        try {
            return genreDao.add(book);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Genre get(int id) throws ServiceException {
        try {
            return genreDao.get(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public Genre update(Genre book) throws ServiceException {
        GenreValidator validator = new GenreValidator(book);
        if (!validator.validate()) {
            return null;
        }

        try {
            return genreDao.update(book);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public void delete(int id) throws ServiceException {
        try {
            genreDao.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    public List<Genre> getAll() throws ServiceException {
        try {
            return genreDao.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
