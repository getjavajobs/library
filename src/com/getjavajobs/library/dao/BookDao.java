package com.getjavajobs.library.dao;

import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 21.08.2014.
 */
//захват, освобождение коннекшена.
public class BookDao {
    private ConnectionHolder connectionHolder;
    private AuthorDao authorDao;
    private PublisherDao publisherDao;

    public void setConnectionHolder(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }
    public void setAuthorDao(AuthorDao authorDao) {
        this.authorDao = authorDao;
    }
    public void setPublisherDao(PublisherDao publisherDao) {
        this.publisherDao = publisherDao;
    }

    public Book add(Book book) throws DAOException {
        Connection connection = connectionHolder.getConnection();
        boolean success = true;
        String command =
                "INSERT INTO " +
                "books(title, author_id, publishing_id, year, pagenumber, price) " +
                "values(?,?,?,?,?,?);";

        try (PreparedStatement addStatement = connection.prepareStatement(command)){
            bookToStatement(addStatement,book);
            addStatement.execute();
            book.setId(getLastId(connection));
        } catch (SQLException e) {
            success = false;
            throw new DAOException(e.getMessage(), e);
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
                throw new DAOException(e.getMessage(), e);
            } finally {
                connectionHolder.releaseConnection(connection);
            }
        }

        return book;
    }

    public Book get(int id) throws DAOException {
        Connection connection = connectionHolder.getConnection();
        boolean success = true;
        try (PreparedStatement statement = connection.prepareStatement("select * from books where id=?;")){
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.next()){
                return null;
            }
            return resultsSetToBook(resultSet);
        } catch (SQLException e) {
            success = false;
            throw new DAOException(e.getMessage(), e);
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
                throw new DAOException(e.getMessage(), e);
            }finally {
                connectionHolder.releaseConnection(connection);
            }
        }
    }
    public Book update(Book book) throws DAOException {
        Connection connection = connectionHolder.getConnection();
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
            throw new DAOException(e);
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
                throw new DAOException(e.getMessage(), e);
            }finally {
                connectionHolder.releaseConnection(connection);
            }
        }
        Book returnedBook = get(book.getId());
        if(returnedBook==null) {
            throw new DAOException("IncorrectId");
        }
        return returnedBook;
    }
    public void delete(int id) throws DAOException {
        Connection connection = connectionHolder.getConnection();
        boolean success = true;
        try (PreparedStatement statement = connection.prepareStatement("delete from books where id=?;")){
            statement.setInt(1,id);
            statement.execute();
        } catch (SQLException e) {
            success = false;
            throw new DAOException(e.getMessage(), e);
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
                throw new DAOException(e.getMessage(), e);
            }finally {
                connectionHolder.releaseConnection(connection);
            }
        }

    }
    public List<Book> getAll() throws DAOException {
        Connection connection = connectionHolder.getConnection();
        boolean success = true;
        List<Book> books = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books;");
            while (resultSet.next()){
                books.add(resultsSetToBook(resultSet));
            }
        } catch (SQLException e){
            success = false;
            throw new DAOException(e.getMessage(), e);
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
                throw new DAOException(e.getMessage(), e);
            }finally {
                connectionHolder.releaseConnection(connection);
            }
        }
        connectionHolder.releaseConnection(connection);
        return books;
    }
    private int getLastId(Connection connection) throws SQLException {
        Statement lastIdStatement = connection.createStatement();
        ResultSet resultSet = lastIdStatement.executeQuery("SELECT last_insert_id();");
        resultSet.next();
        int lastId=resultSet.getInt(1);
        lastIdStatement.close();
        return lastId;
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
