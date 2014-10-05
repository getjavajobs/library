package com.getjavajobs.library.services;

import com.getjavajobs.library.dao.PublisherDao;
import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Publisher;
import com.getjavajobs.library.services.validators.PublisherValidator;

import java.util.List;

/**
 * Created by Roman on 22.08.14.
 */
public class PublisherService {

    private PublisherDao publisherDao;
    private PublisherValidator validator = new PublisherValidator();
    public PublisherService(PublisherDao publisherDao) {
        this.publisherDao = publisherDao;
    }

    public Publisher add(Publisher publisher) throws ServiceException {
        try {
            if (!validator.validate(publisher)) {
                throw new ServiceException("Ошибка валидации");
            }
            return publisherDao.add(publisher);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    public Publisher get(int id) throws ServiceException {
        try {
            return publisherDao.get(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    public Publisher update(Publisher publisher) throws ServiceException {
        try {
            if (!validator.validate(publisher)) {
                throw new ServiceException("Ошибка валидации");
            }
            return publisherDao.update(publisher);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    public void delete(int id) throws ServiceException{
        try {
            publisherDao.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
    public List<Publisher> getAll() throws ServiceException{
        try {
            return publisherDao.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

}
