/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.getjavajobs.library.dao;

import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Reader;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Виталий
 */
public class ReaderDao {

    public Reader get(int id) throws SQLException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        Reader r = new Reader();
        try (PreparedStatement ps = con.prepareStatement("SELECT * FROM readers WHERE Id = ?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                r.setReaderId(Integer.parseInt(rs.getString("Id")));
                r.setSecondName(rs.getString("surname"));
                r.setFirstName(rs.getString("Name"));
                r.setAddress(rs.getString("Address"));
                r.setPasport(rs.getString("passport"));
                r.setPhone((rs.getString("telephone")));

            } else {
                throw new DAOException("ThereIsNoSuchId");
            }
        } catch (SQLException e) {
            con.rollback();

        } finally {
            if (!con.getAutoCommit()) {
                con.commit();
            }
            ConnectionHolder.getInstance().releaseConnection(con);
            return r;
        }
    }

    public List<Reader> getAll() throws SQLException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        List<Reader> readerBase = new ArrayList<>();

        try (Statement ps = con.createStatement()) {
            ResultSet rs = ps.executeQuery("SELECT * FROM readers ");
            if (rs.next()) {
                rs.beforeFirst();
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
            } else {
                throw new DAOException("ThereIsNoSuchReaders");
            }
        } catch (SQLException e) {
            con.rollback();
            throw new DAOException(e);
        } finally {
            if (!con.getAutoCommit()) {
                con.commit();
            }
            ConnectionHolder.getInstance().releaseConnection(con);

        }
        return readerBase;
    }

    public Reader add(Reader reader) throws SQLException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        Reader r = new Reader();
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
            int a = Integer.parseInt(rs.getString("last_insert_id()"));
            r = get(a);
        } catch (SQLException e) {
            con.rollback();
        } finally {

            if (!con.getAutoCommit()) {
                con.commit();
            }
            ConnectionHolder.getInstance().releaseConnection(con);

        }

        return r;
    }

    public Reader update(Reader reader) throws SQLException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        Reader r = new Reader();
        try (PreparedStatement ps = con.prepareStatement("update readers set surname= ?,Name= ? ,Address= ? ,Passport= ? ,telephone=? where Id = ?;")) {

            ps.setString(1, reader.getSecondName());
            ps.setString(2, reader.getFirstName());
            ps.setString(3, reader.getAddress());
            ps.setString(4, reader.getPassport());
            ps.setString(5, reader.getPhone());
            ps.setInt(6, reader.getReaderId());
            ps.executeUpdate();
        } catch (SQLException e) {
            con.rollback();
        } finally {

            if (!con.getAutoCommit()) {
                con.commit();
            }
            ConnectionHolder.getInstance().releaseConnection(con);

        }
        return reader;
    }

    public void delete(int id) throws SQLException {
        Connection con = ConnectionHolder.getInstance().getConnection();
        try (Statement s = con.createStatement()) {
            s.executeUpdate("delete from readers where ID = " + id + ";");
        } catch (SQLException e) {

            con.rollback();

            throw new DAOException(e);
        } finally {

            if (!con.getAutoCommit()) {
                con.commit();
            }
            ConnectionHolder.getInstance().releaseConnection(con);
        }
    }
}

    //to use this connection use : " Connection con = connectBase(); "
   /* private Connection connectBase() throws FileNotFoundException, IOException, SQLException {
 Properties props = new Properties();
 props.load(new FileInputStream("jdbc.properties"));
 String url = props.getProperty("jdbc.url");
 String username = props.getProperty("jdbc.username");
 String password = props.getProperty("jdbc.password");
 Connection con = DriverManager.getConnection(url, username, password);
 return con;
 }*/
