/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.getjavajobs.library.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.getjavajobs.library.dao.ReaderDao;
import com.getjavajobs.library.exceptions.ServiceException;
import com.getjavajobs.library.model.Reader;
import com.getjavajobs.library.services.validators.ReaderValidator;

@Service
public class ReaderService {

    @Autowired
	private ReaderDao readerDao; // ReaderDaoPrxy extends ReaderDao
    @Autowired
    private ReaderValidator readerValidator;

	public Reader get(int id) throws ServiceException {
	    return readerDao.get(id);
    }

    public List<Reader> getAll() throws ServiceException {
        return readerDao.getAll();
    }

    @Transactional
    public Reader add(Reader reader) throws ServiceException {
		if (readerValidator.readerValidate(reader)) {
            return readerDao.add(reader);
        } else {
            throw new ServiceException("ReaderValidateFall at add");
        }
    }

    @Transactional
    public Reader update(Reader reader) throws ServiceException {
        if (readerValidator.readerValidate(reader)) {
            return readerDao.update(reader);
        } else {
           throw new ServiceException("ReaderValidateFall at update");
        }
    }

    @Transactional
    public void delete(int id) throws ServiceException {
        readerDao.delete(id);
        System.out.println("Reader with id " + id + "successfully deleted");
    }
    
    @Transactional
    public void addReaders(List<Reader> readers) {
    	for (Reader reader : readers) {
    		readerDao.add(reader);
    	} 	
    }
    
}
