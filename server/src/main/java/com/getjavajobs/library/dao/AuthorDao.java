package com.getjavajobs.library.dao;

import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Roman on 23.08.14.
 */
@Repository
public class AuthorDao implements GenericDao<Author> {

    @Autowired
    private SessionFactory AuthorSessionFactory;

    @Transactional
    public Author add(Author author) throws DAOException {
        Session session = AuthorSessionFactory.getCurrentSession();
        try {
            int id = (Integer) session.save(author);
            author.setId(id);
            return author;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }
    @Transactional
    public void delete(int id) throws DAOException {
        Session session = AuthorSessionFactory.getCurrentSession();
        try {
            session.delete(get(id));
        }catch (Exception e){
            throw new DAOException(e);
        }
    }

    @Transactional
    public Author get(int id) throws DAOException {
        Session session = AuthorSessionFactory.getCurrentSession();
        try{
            return (Author)session.get(Author.class,id);
        }catch (Exception e){
            throw new DAOException(e);
        }
    }
    @Transactional
    public Author update(Author author) throws DAOException {
        Session session = AuthorSessionFactory.getCurrentSession();
        try{
            session.update(author);
            return author;
        }catch (Exception e){
            throw new DAOException(e);
        }
    }
    @Transactional
    public List<Author> getAll() throws DAOException {
        Session session = AuthorSessionFactory.getCurrentSession();
        try {
            return (List<Author>)session.createCriteria(Author.class);
        }catch (Exception e){
            throw new DAOException(e);
        }
    }

}
