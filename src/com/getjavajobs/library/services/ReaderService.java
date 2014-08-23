/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.getjavajobs.library.services;

import com.getjavajobs.library.dao.ReaderDao;
import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Reader;
import com.getjavajobs.library.services.validators.ReaderValidator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Виталий
 */
public class ReaderService {

    public Reader get(int id) throws SQLException {
        Reader tempReader = new Reader();
        tempReader = new ReaderDao().get(id);
        if (new ReaderValidator(tempReader).readerValidate()) {
            return tempReader;
        } else {
            //throw new DAOException("ReaderValidateFall");
        }
        return tempReader;   // remove after getting DAOException class  
    }

    public List<Reader> getAll() throws SQLException {
        List<Reader> tempListReader = new ArrayList<>();

        return tempListReader = new ReaderDao().getAll();

    }

    public Reader add(Reader r) throws SQLException {
        if (new ReaderValidator(r).readerValidate()) {

            return new ReaderDao().add(r);

        } else {
            throw new DAOException("ReaderValidateFall");
        }

    }

    public Reader update(Reader r) throws SQLException {
        if (new ReaderValidator(r).readerValidate()) {

            return new ReaderDao().update(r);

        } else {
            throw new DAOException("ReaderValidateFall");
        }

    }

    public void delete(int id) throws SQLException {

        new ReaderDao().delete(id);
        System.out.println("Reader with id " + id + "successfully deleted");

    }
}
