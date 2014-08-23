package com.getjavajobs.library.dao;

import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Roman on 20.08.14.
 */
public class PublisherDao {

    public Publisher add(Publisher publisher) throws DAOException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        boolean success = false;
        String script = "INSERT INTO Publisher " +
                "(name,city,telephone,email,site) VALUES " +
                "(?,?,?,?,?)";
        try (PreparedStatement ps = con.prepareStatement(script)) {
            ps.setString(1,publisher.getName());
            ps.setString(2,publisher.getCity());
            ps.setString(3,publisher.getPhoneNumber());
            ps.setString(4,publisher.getEmail());
            ps.setString(5,publisher.getSiteAddress());
            ps.executeUpdate();
            ResultSet resultSet = ps.executeQuery("SELECT last_insert_id()");
            resultSet.next();
            publisher.setId(resultSet.getInt("id"));
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
        return publisher;
    }

    public void delete(int id) throws DAOException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        boolean success = false;
        String script = "DELETE FROM Publishing WHERE id = ?";
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

    public Publisher get(int id) throws DAOException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        boolean success = false;
        String script = "SELECT * FROM Publishing WHERE id = ?";
        Publisher publisher = new Publisher();
        try(PreparedStatement ps = con.prepareStatement(script)){
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()){
                publisher.setId(resultSet.getInt("id"));
                publisher.setName(resultSet.getString("name"));
                publisher.setCity(resultSet.getString("city"));
                publisher.setPhoneNumber(resultSet.getString("telephone"));
                publisher.setEmail(resultSet.getString("email"));
                publisher.setSiteAddress(resultSet.getString("site"));
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
        return publisher;
    }

    public Publisher update(Publisher publisher) throws DAOException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        boolean success = false;
        String script = "UPDATE Publisher SET " +
                "name = ?, city = ?, telephone = ?, email = ?, site = ?" +
                "WHERE id = ?";
        try(PreparedStatement ps = con.prepareStatement(script)){
            ps.setString(1, publisher.getName());
            ps.setString(2, publisher.getCity());
            ps.setString(3,publisher.getPhoneNumber());
            ps.setString(4, publisher.getEmail());
            ps.setString(5, publisher.getSiteAddress());
            ps.setInt(6,publisher.getId());
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
        return publisher;
    }

    public List<Publisher> getAll() throws DAOException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        List<Publisher> publishers = new ArrayList<>();
        boolean success = false;
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM Publishing")){
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Publisher publisher = new Publisher();
                publisher.setId(resultSet.getInt("id"));
                publisher.setName(resultSet.getString("name"));
                publisher.setCity(resultSet.getString("city"));
                publisher.setPhoneNumber(resultSet.getString("telephone"));
                publisher.setEmail(resultSet.getString("email"));
                publisher.setSiteAddress(resultSet.getString("site"));
                publishers.add(publisher);
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
        return publishers;
    }

}
