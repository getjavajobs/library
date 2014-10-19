package com.getjavajobs.library.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Book;

/**
 * Created by Vlad on 21.08.2014.
 */
@Repository
public class BookDao implements GenericDao<Book> {

    @Autowired
    private SessionFactory sessionFactory;

    public Book add(Book book) throws DAOException {
    	Session session = sessionFactory.getCurrentSession();
        try {
        	session.save(book);
        	return book;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    public Book get(int id) throws DAOException {
    	Session session = sessionFactory.getCurrentSession();
        try {
        	return (Book) session.get(Book.class, id);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    public Book update(Book book) throws DAOException {
    	Session session = sessionFactory.getCurrentSession();
        try {
        	session.update(book);
        	return book;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    public void delete(int id) throws DAOException {
    	Session session = sessionFactory.getCurrentSession();
        try {
        	session.delete(get(id));
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    @SuppressWarnings("unchecked")
	public List<Book> getAll() throws DAOException {
    	Session session = sessionFactory.getCurrentSession();
        try {
        	return (List<Book>) session.createCriteria(Book.class).list();
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    
    public List<Book> getFree() throws DAOException {
        List<Book> books = new ArrayList<>();
/*        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT DISTINCT * FROM books WHERE id NOT IN (SELECT books_id FROM borrow);");
        for (Map<String, Object> row : rows) {
            Book book = new Book();
            book.setId((Integer) row.get("id"));
            book.setName((String) row.get("title"));
            book.setAuthor(authorDao.get((Integer) row.get("author_id")));
            book.setPublisher(publisherDao.get((Integer) row.get("publishing_id")));
            book.setYear((Integer) row.get("year"));
            book.setPagesNumber((Integer) row.get("pagenumber"));
            book.setPrice((Float) row.get("price"));
            try {
                book.setGenreList(getGenreList(book.getId()));
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            books.add(book);
        }
*/        return books;
    }

}