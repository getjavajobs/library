package com.getjavajobs.library.services;

import com.getjavajobs.library.dao.BorrowDao;
import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Borrow;

/**
 * Created by Глеб on 23.08.2014.
 */
public class BorrowService  {
    public BorrowDao bd = new BorrowDao();

    public Borrow get(int id)throws ServiceException{
        try {
            return bd.get(id);
        }catch (DAOException e){
            throw new ServiceException(e.getMessage(),e);
        }
    }
    public Borrow add(Borrow borrow)throws ServiceException{
        try {
            return bd.add(borrow);
        }catch (DAOException e){
            throw new ServiceException(e.getMessage(),e);
        }
    }
    public void delete(int id)throws ServiceException{
        try {
            bd.delete(id);
        }catch (DAOException e){
            throw new ServiceException(e.getMessage(),e);
        }
    }
    public Borrow update(Borrow borrow)throws ServiceException{
        try {
            return bd.update(borrow);
        }catch (DAOException e){
            throw new ServiceException(e.getMessage(),e);
        }

    }

}
