/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.getjavajobs.library.dao;

import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Виталий
 */
public class ReaderDao implements GenericDao<Reader> {

    @Override
    public Reader get(int id) throws DAOException {
        Connection con = ConnectionHolder.getInstance().getConnection();

        boolean commit = false;
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM readers WHERE Id = ?")) {
            Reader r = new Reader();
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                r.setReaderId(Integer.parseInt(rs.getString("Id")));
                r.setSecondName(rs.getString("surname"));
                r.setFirstName(rs.getString("Name"));
                r.setAddress(rs.getString("Address"));
                r.setPasport(rs.getString("passport"));
                r.setPhone((rs.getString("telephone")));
            }
            return r;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            ConnectionHolder.getInstance().releaseConnection(con);
        }
    }

    @Override
    public List<Reader> getAll() throws DAOException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        List<Reader> readerBase = new ArrayList<>();
        try (Statement ps = con.createStatement()) {
            ResultSet rs = ps.executeQuery("SELECT * FROM readers ");
            while (rs.next()) {
                Reader r = new Reader();
                r.setReaderId(Integer.parseInt(rs.getString("Id")));
                r.setSecondName(rs.getString("surname"));
                r.setFirstName(rs.getString("Name"));
                r.setAddress(rs.getString("Address"));
                r.setPasport(rs.getString("passport"));
                r.setPhone((rs.getString("telephone")));
                readerBase.add(r);
            }
            return readerBase;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            ConnectionHolder.getInstance().releaseConnection(con);
        }
    }

    @Override
    public Reader add(Reader reader) throws DAOException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        boolean commit = false;
        try (PreparedStatement ps = con.prepareStatement("Insert into readers (surname, name, address,passport,telephone) values( ? , ? ,?, ?, ? ) ;")) {

            ps.setString(1, reader.getSecondName());
            ps.setString(2, reader.getFirstName());
            ps.setString(3, reader.getAddress());
            ps.setString(4, reader.getPassport());
            ps.setString(5, reader.getPhone());

            ps.executeUpdate();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery("Select last_insert_id()");
            rs.next();
            int lastInsertedId = Integer.parseInt(rs.getString("last_insert_id()"));
            reader.setReaderId(lastInsertedId);

            if (!con.getAutoCommit()) {
                con.commit();
            }
            commit = true;
            return reader;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                if (!commit && !con.getAutoCommit()) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex);
            } finally {
                ConnectionHolder.getInstance().releaseConnection(con);
            }
        }
    }

    @Override
    public Reader update(Reader reader) throws DAOException {
        Connection con = ConnectionHolder.getInstance().getConnection();

        boolean commit = false;
        try (PreparedStatement ps = con.prepareStatement("update readers set surname= ?,Name= ? ,Address= ? ,Passport= ? ,telephone=? where Id = ?;")) {
            ps.setString(1, reader.getSecondName());
            ps.setString(2, reader.getFirstName());
            ps.setString(3, reader.getAddress());
            ps.setString(4, reader.getPassport());
            ps.setString(5, reader.getPhone());
            ps.setInt(6, reader.getReaderId());
            ps.executeUpdate();

            if (!con.getAutoCommit()) {
                con.commit();
            }
            commit = true;
            return reader;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                if (!commit && !con.getAutoCommit()) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex);
            } finally {
                ConnectionHolder.getInstance().releaseConnection(con);
            }
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        boolean commit = false;
        try (PreparedStatement ps = con.prepareStatement("delete from readers where ID =?;")) {
            ps.setString(1, String.valueOf(id));
            ps.executeUpdate();

            if (!con.getAutoCommit()) {
                con.commit();
            }
            commit = true;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                if (!commit && !con.getAutoCommit()) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex);
            } finally {
                ConnectionHolder.getInstance().releaseConnection(con);
            }
        }
    }
}