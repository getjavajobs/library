/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.getjavajobs.library.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LikeExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Reader;

/**
 *
 * @author Виталий
 */
@Repository
public class ReaderDao implements GenericDao<Reader> {

	@Autowired
	private SessionFactory sessionFactory;

    @Override
    public Reader get(int id) throws DAOException {
    	Session session = sessionFactory.getCurrentSession();
        try {
        	return (Reader) session.get(Reader.class, id);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

	@Override
    @SuppressWarnings("unchecked")
    public List<Reader> getAll() throws DAOException {
    	Session session = sessionFactory.getCurrentSession();
        try {
        	return (List<Reader>) session.createCriteria(Reader.class).list();
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Reader update(Reader reader) throws DAOException {
    	Session session = sessionFactory.getCurrentSession();
        try {
        	session.update(reader);
        	return reader;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void delete(int id) throws DAOException {
    	Session session = sessionFactory.getCurrentSession();
        try {
        	session.delete(get(id));
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }
    
    public void addReaders(List<Reader> readers) {
    	for (Reader reader : readers) {
    		add(reader);
    	}
    }

    @Override
    public Reader add(Reader reader) throws DAOException {
    	Session session = sessionFactory.getCurrentSession();
        try {
        	int id = (Integer) session.save(reader);
        	reader.setReaderId(id);
        	return reader;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

}