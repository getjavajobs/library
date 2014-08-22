package com.getjavajobs.library.dao;

import com.getjavajobs.library.exceptions.DaoException;
import com.getjavajobs.library.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 21.08.2014.
 */
public class SQLBookDao implements BookDao{
    private Connection connection;
    private AuthorDao authorDao;
    private PublisherDao publisherDao;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    public void setAuthorDao(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }
    public void setPublisherDao(PublisherDao publisherDao) {
        this.publisherDao = publisherDao;
    }

    public Book add(Book book) throws DaoException {
        boolean success = true;
        String command =
                "INSERT INTO " +
                "books(title, author_id, publishing_id, year, pagenumber, price) " +
                "values(?,?,?,?,?,?);";

        try (PreparedStatement addStatement = connection.prepareStatement(command);){
            bookToStatement(addStatement,book);
            addStatement.execute();
            book.setId(getLastId());
        } catch (SQLException e) {
            success = false;
            throw new DaoException(e);
        } finally {
            try {
                if (!connection.getAutoCommit()) {
                    if (success) {
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }

        return book;
    }

    private int getLastId() throws SQLException {
        Statement lastIdStatement = connection.createStatement();
        ResultSet resultSet = lastIdStatement.executeQuery("SELECT last_insert_id();");
        resultSet.next();
        int lastId=resultSet.getInt(1);
        lastIdStatement.close();
        return lastId;
    }

    public Book get(int id) throws DaoException {
        boolean success = true;
        try (PreparedStatement statement = connection.prepareStatement("select * from books where id=?;");){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return null;
            }
            return resultsSetToBook(resultSet);
        } catch (SQLException e) {
            success = false;
            throw new DaoException(e);
        } finally {
            try {
                if (!connection.getAutoCommit()) {
                    if (success) {
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
    }
    public Book update(Book book) throws DaoException {
        boolean success =true;
        String command = "UPDATE books SET " +
                "title=?, author_id=?, publishing_id=?, year=?, pagenumber=?, price=?" +
                "where id=?";
        try(PreparedStatement statement = connection.prepareStatement(command)){
            bookToStatement(statement,book);
            statement.setInt(7,book.getId());
            statement.execute();
        }catch (SQLException e){
            success = false;
            throw new DaoException(e);
        } finally {
            try {
                if (!connection.getAutoCommit()) {
                    if (success) {
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }
        return get(book.getId());
    }
    public void delete(int id) throws DaoException {
        boolean success = true;
        try (PreparedStatement statement = connection.prepareStatement("delete from books where id=?;");){
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            success = false;
            throw new DaoException(e);
        } finally {
            try {
                if (!connection.getAutoCommit()) {
                    if (success) {
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }

    }
    public List<Book> getAll() throws DaoException {
        boolean success = true;
        List<Book> books = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books;");
            while (resultSet.next()){
                books.add(resultsSetToBook(resultSet));
            }
        } catch (SQLException e){
            success = false;
            throw new DaoException(e);
        } finally {
            try {
                if (!connection.getAutoCommit()) {
                    if (success) {
                        connection.commit();
                    } else {
                        connection.rollback();
                    }
                }
            } catch (SQLException e) {
                throw new DaoException(e);
            }
        }

        return books;
    }

    private Book resultsSetToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getInt("id"));
        book.setName(resultSet.getString("title"));
        book.setAuthor(authorDao.get(resultSet.getInt("author_id")));
        book.setPublisher(publisherDao.get(resultSet.getInt("publishing_id")));
        book.setYear(resultSet.getShort("year"));
        book.setPagesNumber(resultSet.getInt("pagenumber"));
        book.setPrice(resultSet.getFloat("price"));
        return book;
    }
    private void bookToStatement(PreparedStatement statement, Book book) throws SQLException {
        statement.setString(1,book.getName());
        statement.setInt(2, book.getAuthor().getId());
        statement.setInt(3,book.getPublisher().getId());
        statement.setInt(4,book.getYear());
        statement.setInt(5,book.getPagesNumber());
        statement.setDouble(6, book.getPrice());
    }
}
