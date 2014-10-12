package com.getjavajobs.library.dao;

import com.getjavajobs.library.exceptions.DAOException;
import com.getjavajobs.library.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Roman on 23.08.14.
 */
@Repository
public class AuthorDao implements GenericDao<Author> {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private  AuthorRowMapper authorRowMapper;

    @Transactional
    public Author add(Author author) throws DAOException {
        String script = "INSERT INTO Author " +
                "(name,surname,patronymic,dateofbirth,country) VALUES  (?,?,?,?,?)";
        jdbcTemplate.update(script, new Object[]{author.getName(),author.getSurname() ,author.getPatronymic(),new java.sql.Date(author.getBirthDate().getTime()),author.getBirthPlace()});
        return author;
    }
    @Transactional
    public void delete(int id) throws DAOException {
        String script = "DELETE FROM Author WHERE id = ?";
        jdbcTemplate.update(script);
    }

    @Transactional
    public Author get(int id) throws DAOException {
        String script = "SELECT * FROM Author WHERE id = ?";
        Author author = (Author)jdbcTemplate.queryForObject(script, authorRowMapper);
        return author;
    }

    public Author update(Author author) throws DAOException {
        String script = "UPDATE Author SET " +
                "name = ?, surname = ?, patronymic = ?, dateofbirth = ?, country = ?" +
                "WHERE id = ?";
        author = (Author)jdbcTemplate.queryForObject(script, authorRowMapper);
        return author;
    }

    public List<Author> getAll() throws DAOException {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM AUTHOR");
        List<Author> authors = new ArrayList<>();
        for (Map row : rows) {
            Author author = new Author();
            author.setId((Integer) (row.get("Id")));
            author.setName((String) row.get("name"));
            author.setSurname((String) row.get("surname"));
            author.setPatronymic((String) row.get("patronymic"));
            author.setBirthDate((Date) (row.get("dateofbirth")));
            author.setBirthPlace((String) row.get("country"));
            authors.add(author);
        }
        return authors;
    }

}
