package com.getjavajobs.library.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Администратор on 09.10.2014.
 */
@Component
public class IntegerRowMapper implements RowMapper{
    @Override
    public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getInt("last_insert_id()");
    }
}
