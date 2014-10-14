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
         if(authorValidator.validate(author)) {
             return authorDao.add(author);
         }else{
            throw new ServiceException("Fall at authorAdd");
        }
    }
    @Transactional
    public Author get(int id) throws ServiceException {
         try{
            return authorDao.get(id);
        }catch (Exception e){
            throw new ServiceException( "Fall at authorGet",e);
        }
    }
    @Transactional
    public Author update(Author author) throws ServiceException {
        if(authorValidator.validate(author)) {
            return authorDao.update(author);
        }else{
            throw new ServiceException("Fall at authorUpdate");
        }
    }
    @Transactional
    public void delete(int id) throws ServiceException{
        try{
            authorDao.delete(id);
        }catch (Exception e){
            throw new ServiceException("Fall at authorDelete",e);
        }
    }
    @Transactional
    public List<Author> getAll() throws ServiceException{
        try {
            return authorDao.getAll();
        }catch (Exception e){
            throw new ServiceException("Fall at authorGetAll");
        }
    }

}
