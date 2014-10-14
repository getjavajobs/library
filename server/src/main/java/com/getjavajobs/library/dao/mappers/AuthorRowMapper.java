package com.getjavajobs.library.dao.mappers;

import com.getjavajobs.library.dao.AuthorDao;
import com.getjavajobs.library.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Виталий on 12.10.2014.
 */
@Component
public class AuthorRowMapper implements RowMapper {
    @Autowired
    private AuthorDao authorDao;

    @Override
    public Author mapRow(ResultSet resultSet, int i) throws SQLException {
        Author author = new Author();
        author.setId(resultSet.getInt("id"));
        author.setName(resultSet.getString("name"));
        author.setSurname(resultSet.getString("surname"));
        author.setPatronymic(resultSet.getString("patronymic"));
        author.setBirthDate(new java.util.Date(resultSet.getDate("dateofbirth").getTime()));
        author.setBirthPlace(resultSet.getString("country"));
        return author;
    }
}
