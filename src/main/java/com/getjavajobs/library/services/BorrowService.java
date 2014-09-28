package com.getjavajobs.library.services;

import com.getjavajobs.library.dao.BorrowDao;
import com.getjavajobs.library.dao.DaoFactory;
import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Borrow;
import com.getjavajobs.library.services.validators.BorrowValidator;

import java.util.List;

/**
 * Created by Глеб on 23.08.2014.
 */
public class BorrowService  {
    private BorrowDao bd = DaoFactory.getBorrowDao();

    public Borrow get(int id) throws ServiceException {
        try {
            Borrow borrow = bd.get(id);
            if(!new BorrowValidator(borrow).validate()){
                return null;
            }

            return bd.get(id);
        }catch (DAOException e){
            throw new ServiceException(e.getMessage(),e);
        }
    }
    public Borrow add(Borrow borrow) throws ServiceException {
        if(!new BorrowValidator(borrow).validate()) {
            return null;
        }
            try {
                return bd.add(borrow);
            } catch (DAOException e) {
                throw new ServiceException(e.getMessage(), e);
            }

    }

    public void delete(int id) throws ServiceException {
        try {
            bd.delete(id);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage(),e);
        }
    }

    public Borrow update(Borrow borrow) throws ServiceException {
        if(!new BorrowValidator(borrow).validate()) {
            return null;
        }
        try {
                return bd.update(borrow);
        } catch (DAOException e){
            throw new ServiceException(e.getMessage(),e);
        }

    }

    public List<Borrow> getAll() throws ServiceException {
        try {
            return bd.getAll();
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage(),e);
        }
    }

}
