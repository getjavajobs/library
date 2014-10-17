package com.getjavajobs.library.dao;

import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Book;
import com.getjavajobs.library.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Vlad on 21.08.2014.
 */
@Repository
public class BookDao implements GenericDao<Book> {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;
    @Autowired
    GenreDao genreDao;
    @Autowired
    AuthorDao authorDao;
    @Autowired
    PublisherDao publisherDao;


    // private AuthorDao authorDao = DaoFactory.getAuthorDao();
    //private PublisherDao publisherDao = DaoFactory.getPublisherDao();
    //private GenreDao genreDao = DaoFactory.getGenreDao();
    /*
    public void setConnectionHolder(ConnectionHolder connectionHolder) {
        this.connectionHolder = connectionHolder;
    }

    public void setGenreDao(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public void setAuthorDao(AuthorDao authorDAO) {
        this.authorDao = authorDAO;
    }

    public void setPublisherDao(PublisherDao publisherDao) {
        this.publisherDao = publisherDao;
    }
    */

    public Book add(Book book) throws DAOException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        boolean success = true;
        String command =
                "INSERT INTO " +
                        "books(title, author_id, publishing_id, year, pagenumber, price) " +
                        "values(?,?,?,?,?,?);";

        try (PreparedStatement addStatement = connection.prepareStatement(command);) {
            bookToStatement(addStatement, book);
            addStatement.execute();
            book.setId(getLastId(connection));
            addGenreList(book.getId(), connection, book.getGenreList());
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
                ConnectionHolder.getInstance().releaseConnection(connection);
            }
        }
        return book;
    }

    public Book get(int id) throws DAOException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        boolean success = true;
        try (PreparedStatement statement = connection.prepareStatement("select * from books where id=?;");) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            Book book = resultsSetToBook(resultSet);
            book.setGenreList(getGenreList(book.getId()));
            return book;
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
                ConnectionHolder.getInstance().releaseConnection(connection);
            }
        }
    }

    public Book update(Book book) throws DAOException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        boolean success = true;
        String command = "UPDATE books SET " +
                "title=?, author_id=?, publishing_id=?, year=?, pagenumber=?, price=?, " +
                "where id=?";
        try (PreparedStatement statement = connection.prepareStatement(command)) {
            bookToStatement(statement, book);
            statement.setInt(7, book.getId());
            deleteGenreList(book.getId(), connection);
            statement.execute();
            addGenreList(book.getId(), connection, book.getGenreList());

            Book returnedBook = get(book.getId());

            if (returnedBook == null) {
                throw new DAOException("IncorrectId");
            }
            return returnedBook;
        } catch (SQLException e) {
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
            } finally {
                ConnectionHolder.getInstance().releaseConnection(connection);
            }
        }
    }

    public void delete(int id) throws DAOException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        boolean success = true;
        try (PreparedStatement statement = connection.prepareStatement("delete from books where id=?;");) {
            statement.setInt(1, id);
            deleteGenreList(id, connection);
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
            } finally {
                ConnectionHolder.getInstance().releaseConnection(connection);
            }
        }

    }

    public List<Book> getAll() throws DAOException {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Book> books = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM books;");
            while (resultSet.next()) {
                Book book = resultsSetToBook(resultSet);
                book.setGenreList(getGenreList(book.getId()));
                books.add(book);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(), e);
        } finally {
            ConnectionHolder.getInstance().releaseConnection(connection);
        }
        return books;
    }

    public List<Book> getFree() throws DAOException {
        List<Book> books = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT DISTINCT * FROM books WHERE id NOT IN (SELECT books_id FROM borrow);");
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
        return books;
    }

    private List<Genre> getGenreList(int bookId) throws SQLException {
        List<Genre> genreList = new ArrayList<>();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (PreparedStatement statement = connection.prepareStatement("select genre_id from Genre_lists where book_id=?;")) {
            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                genreList.add(genreDao.get(resultSet.getInt("genre_id")));
            }
        }
        return genreList;
    }

    private void deleteGenreList(int bookId, Connection connection) throws SQLException {
        try (PreparedStatement delete = connection.prepareStatement("delete from Genre_lists where book_id=?;")) {
            delete.setInt(1, bookId);
            delete.execute();
        }
    }

    private void addGenreList(int bookId, Connection connection, List<Genre> genreList) throws SQLException {
        for (Genre genre : genreList) {
            try (PreparedStatement add = connection.prepareStatement("insert into Genre_lists(book_id, genre_id) values (?, ?);")) {
                add.setInt(1, bookId);
                add.setInt(2, genre.getId());
                add.execute();
            }
        }
    }

    private int getLastId(Connection connection) throws SQLException {
        Statement lastIdStatement = connection.createStatement();
        ResultSet resultSet = lastIdStatement.executeQuery("SELECT last_insert_id();");
        resultSet.next();
        int lastId = resultSet.getInt(1);
        lastIdStatement.close();
        return lastId;
    }

    //перед вызовом отпускать connection
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
        statement.setString(1, book.getName());
        statement.setInt(2, book.getAuthor().getId());
        statement.setInt(3, book.getPublisher().getId());
        statement.setInt(4, book.getYear());
        statement.setInt(5, book.getPagesNumber());
        statement.setDouble(6, book.getPrice());

    }
}