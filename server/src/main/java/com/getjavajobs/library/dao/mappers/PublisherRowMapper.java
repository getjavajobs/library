package com.getjavajobs.library.dao.mappers;

import com.getjavajobs.library.model.Publisher;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * 'Publishers' row mapper.
 */
@Component
public class PublisherRowMapper implements RowMapper {

    @Override
    public Publisher mapRow(ResultSet rs, int rowNum) throws SQLException {
        Publisher publisher = new Publisher();
        publisher.setId(rs.getInt("id"));
        publisher.setName(rs.getString("name"));
        publisher.setCity(rs.getString("city"));
        publisher.setPhoneNumber(rs.getString("telephone"));
        publisher.setEmail(rs.getString("email"));
        publisher.setSiteAddress(rs.getString("site"));
        return publisher;
    }

    public Publisher mapRow(Map<String, Object> row) {
        Publisher publisher = new Publisher();
        publisher.setId((Integer) row.get("id"));
        publisher.setName((String) row.get("name"));
        publisher.setCity((String) row.get("city"));
        publisher.setPhoneNumber((String) row.get("telephone"));
        publisher.setEmail((String) row.get("email"));
        publisher.setSiteAddress((String) row.get("site"));
        return publisher;
    }
}
