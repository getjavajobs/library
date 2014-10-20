package com.getjavajobs.library.dao;

import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Borrow;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BorrowDao implements GenericDao<Borrow> { //interface Dao( тут будут объявлены все методы).

    @Autowired
	private SessionFactory sessionFactory;


    public Borrow add(Borrow borrow) throws DAOException {
        Session session = sessionFactory.getCurrentSession();
        Borrow added = borrow;

        int id = (Integer) session.save(added);
        added.setBorrowId(id);
        return added;
    }

    public Borrow get(int id) {
        Session session = sessionFactory.getCurrentSession();
        Borrow borrow = (Borrow)session.get(Borrow.class,id);
        return borrow;
    }

    public void delete(int id) {
     Session session = sessionFactory.getCurrentSession();
        session.delete(get(id));
    }

    public Borrow update(Borrow borrow) {
        Session session = sessionFactory.getCurrentSession();
        session.update(borrow);
        return borrow;
    }

    public List<Borrow> getAll(){
        Session session = sessionFactory.getCurrentSession();
        return (List<Borrow>)session.createCriteria(Borrow.class).list();
    }
}



