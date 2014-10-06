/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.getjavajobs.library.services;

import com.getjavajobs.library.dao.ReaderDao;
import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Reader;
import com.getjavajobs.library.services.validators.ReaderValidator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Виталий
 */
@Service
public class ReaderService {

	private ReaderDao readerDao;
    private ReaderValidator readerValidator;

    @Autowired
	public void setReaderDao(ReaderDao readerDao) {
		this.readerDao = readerDao;
	}

    @Autowired
	public void setReaderValidator(ReaderValidator readerValidator) {
		this.readerValidator = readerValidator;
	}

	public Reader get(int id) throws ServiceException {
       return readerDao.get(id);
    }

    public List<Reader> getAll() throws ServiceException {
        List<Reader> tempListReader = new ArrayList<>();
        return tempListReader = readerDao.getAll();
    }

    public Reader add(Reader reader) throws ServiceException {
        if (readerValidator.readerValidate(reader)) {
            return readerDao.add(reader);
        } else {
            throw new ServiceException("ReaderValidateFall at add");
        }
    }

    public Reader update(Reader reader) throws ServiceException {
        if (readerValidator.readerValidate(reader)) {
            return readerDao.update(reader);
        } else {
           throw new ServiceException("ReaderValidateFall at update");
        }
    }

    public void delete(int id) throws ServiceException {
        readerDao.delete(id);
        System.out.println("Reader with id " + id + "successfully deleted");
    }
}
