package com.getjavajobs.library.dao;

import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Author;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman on 23.08.14.
 */
public class AuthorDao implements GenericDao<Author> {

    public Author add(Author author) throws DAOException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        boolean success = false;
        String script = "INSERT INTO Author " +
                "(name,surname,patronymic,dateofbirth,country) VALUES " +
                "(?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(script)) {
            ps.setString(1,author.getName());
            ps.setString(2,author.getSurname());
            ps.setString(3,author.getPatronymic());
            ps.setDate(4, new java.sql.Date(author.getBirthDate().getTime()));
            ps.setString(5,author.getBirthPlace());
            ps.executeUpdate();
            ResultSet rs = ps.executeQuery("Select last_insert_id()");
            rs.next();
            int lastInsertedId = Integer.parseInt(rs.getString("last_insert_id()"));
            author.setId(lastInsertedId);
            if (!con.getAutoCommit()){
                con.commit();
            }
            success = true;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(),e);
        } finally {
            try {
                if(!success && !con.getAutoCommit()) {
                    con.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(),e);
            } finally {
                ConnectionHolder.getInstance().releaseConnection(con);
            }
        }
        return author;
    }

    public void delete(int id) throws DAOException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        boolean success = false;
        String script = "DELETE FROM Author WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(script)){
            ps.setInt(1,id);
            ps.executeUpdate();
            if (!con.getAutoCommit()){
                con.commit();
            }
            success = true;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(),e);
        } finally {
            try {
                if(!success && !con.getAutoCommit()) {
                    con.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(),e);
            } finally {
                ConnectionHolder.getInstance().releaseConnection(con);
            }
        }
    }

    public Author get(int id) throws DAOException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        boolean success = false;
        String script = "SELECT * FROM Author WHERE id = ?";
        Author author = new Author();
        try(PreparedStatement ps = con.prepareStatement(script)){
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                author.setId(resultSet.getInt("id"));
                author.setName(resultSet.getString("name"));
                author.setSurname(resultSet.getString("surname"));
                author.setPatronymic(resultSet.getString("patronymic"));
                author.setBirthDate(new java.util.Date(resultSet.getDate("dateofbirth").getTime()));
                author.setBirthPlace(resultSet.getString("country"));
                if (!con.getAutoCommit()){
                    con.commit();
                }
                success = true;
            } else {
                throw new DAOException();
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(),e);
        } finally {
            try {
                if(!success && !con.getAutoCommit()) {
                    con.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(),e);
            } finally {
                ConnectionHolder.getInstance().releaseConnection(con);
            }
        }
        return author;
    }

    public Author update(Author author) throws DAOException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        boolean success = false;
        String script = "UPDATE Author SET " +
                "name = ?, surname = ?, patronymic = ?, dateofbirth = ?, country = ?" +
                "WHERE id = ?";
        try(PreparedStatement ps = con.prepareStatement(script)){
            ps.setString(1, author.getName());
            ps.setString(2, author.getSurname());
            ps.setString(3,author.getPatronymic());
            ps.setDate(4, new java.sql.Date(author.getBirthDate().getTime()));
            ps.setString(5, author.getBirthPlace());
            ps.setInt(6,author.getId());
            ps.executeUpdate();
            if (!con.getAutoCommit()){
                con.commit();
            }
            success = true;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(),e);
        } finally {
            try {
                if(!success && !con.getAutoCommit()) {
                    con.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(),e);
            } finally {
                ConnectionHolder.getInstance().releaseConnection(con);
            }
        }
        return author;
    }

    public List<Author> getAll() throws DAOException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        List<Author> authors = new ArrayList<>();
        boolean success = false;
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM Publishing")){
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Author author = new Author();
                author.setId(resultSet.getInt("Id"));
                author.setName(resultSet.getString("name"));
                author.setSurname(resultSet.getString("surname"));
                author.setPatronymic(resultSet.getString("patronymic"));
                author.setBirthDate(new java.util.Date(resultSet.getDate("dateofbirth").getTime()));
                author.setBirthPlace(resultSet.getString("country"));
                authors.add(author);
            }
            if (!con.getAutoCommit()){
                con.commit();
            }
            success = true;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage(),e);
        } finally {
            try {
                if(!success && !con.getAutoCommit()) {
                    con.rollback();
                }
            } catch (SQLException e) {
                throw new DAOException(e.getMessage(),e);
            } finally {
                ConnectionHolder.getInstance().releaseConnection(con);
            }
        }
        return authors;
    }

}
