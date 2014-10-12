package com.getjavajobs.library.services;

import com.getjavajobs.library.dao.AuthorDao;
import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Author;
import com.getjavajobs.library.services.validators.AuthorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Roman on 23.08.14.
 */
@Service
public class AuthorService {
    @Autowired
    private AuthorDao authorDao;

    public AuthorService(){};

    @Autowired
    private AuthorValidator authorValidator;

    public AuthorService(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Transactional
    public Author add(Author author) throws ServiceException {
         if(authorValidator.validate()) {
             return authorDao.add(author);
         }else{
            throw new ServiceException("Fall at authorAdd");
        }
    }
    @Transactional
    public Author get(int id) throws ServiceException {
        if(authorValidator.validate()) {
            return authorDao.get(id);
        }else{
            throw new ServiceException("Fall at authorGet");
        }
    }
    @Transactional
    public Author update(Author author) throws ServiceException {
        if(authorValidator.validate()) {
            return authorDao.update(author);
        }else{
            throw new ServiceException("Fall at authorUpdate");
        }
    }
    @Transactional
    public void delete(int id) throws ServiceException{
        if(authorValidator.validate()) {
            authorDao.delete(id);
        }else{
            throw new ServiceException("Fall at authorDelete");
        }
    }
    @Transactional
    public List<Author> getAll() throws ServiceException{
        if(authorValidator.validate()) {
            return authorDao.getAll();
        }else{
            throw new ServiceException("Fall at authorGetAll");
        }
    }

}
