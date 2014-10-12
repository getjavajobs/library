package com.getjavajobs.library.dao;

import com.getjavajobs.library.dao.mappers.PublisherRowMapper;
import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by Roman on 20.08.14.
 */
@Repository
public class PublisherDao implements GenericDao<Publisher>{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PublisherRowMapper publisherRowMapper;

    public PublisherDao() {

    }

    @Transactional
    public Publisher add(Publisher publisher) throws DAOException {
        try  {
            String insertScript = "INSERT INTO Publishing (name,city,telephone,email,site) VALUES(?,?,?,?,?) ;";
            jdbcTemplate.update(insertScript, new Object[] {
                publisher.getName(), publisher.getCity(), publisher.getPhoneNumber(), publisher.getEmail(),
                    publisher.getSiteAddress()
            });
            return publisher;
        } catch (Exception ex) {
            throw new DAOException(ex);
        }
    }

    @Transactional
    public void delete(int id) throws DAOException {
        try {
            String deleteScript = "DELETE FROM Publishing WHERE id = ?;";
            jdbcTemplate.update(deleteScript, new Object[] { id });
        } catch (Exception ex) {
            throw new DAOException(ex);
        }
    }

    @Transactional
    public Publisher get(int id) throws DAOException {
        try {
            String getScript = "SELECT * FROM Publishing WHERE id = " + id + ";";
            Object publisher = jdbcTemplate.queryForObject(getScript, publisherRowMapper);
            return (Publisher) publisher;
        } catch (Exception ex) {
            throw new DAOException(ex);
        }
    }

    @Transactional
    public Publisher update(Publisher publisher) throws DAOException {
        try {
            String updateScript = "UPDATE Publishing SET name = ?, city = ?, " +
                    "telephone = ?, email = ?, site = ? WHERE id = ?;";
            jdbcTemplate.update(updateScript, new Object[] {
                    publisher.getName(), publisher.getCity(), publisher.getPhoneNumber(),
                    publisher.getEmail(), publisher.getSiteAddress(), publisher.getId()
            });
            return get(publisher.getId());
        } catch (Exception ex) {
            throw new DAOException(ex);
        }
    }

    @Transactional
    public List<Publisher> getAll() throws DAOException {
        String selectScript = "SELECT * FROM Publishing";
        List<Map<String, Object>> databaseRows = jdbcTemplate.queryForList(selectScript);
        List<Publisher> publishers = new ArrayList<>();
        for (Map row: databaseRows) {
            Publisher publisher = publisherRowMapper.mapRow(row);
            publishers.add(publisher);
        }
        return publishers;
    }

}
