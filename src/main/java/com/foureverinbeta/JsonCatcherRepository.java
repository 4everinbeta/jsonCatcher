package com.foureverinbeta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

/**
 * Created by Ryan on 9/8/16.
 */
@Repository
public class JsonCatcherRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(readOnly=true)
    public List<JsonCaught> findAll() {
        return jdbcTemplate.query("select * from jsonCaught",
                new UserRowMapper());
    }

    @Transactional(readOnly=true)
    public JsonCaught findUserById(int id) {
        return jdbcTemplate.queryForObject(
                "select * from jsonCaught where id=?",
                new Object[]{id}, new UserRowMapper());
    }

    public JsonCaught create(final JsonCaught jsonCaught)
    {
        final String sql = "insert into jsonCaught(payload) values(?)";

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, jsonCaught.getPayload());
                return ps;
            }
        }, holder);

        int newId = holder.getKey().intValue();
        jsonCaught.setId(newId);
        return jsonCaught;
    }
}

class UserRowMapper implements RowMapper<JsonCaught>
{
    @Override
    public JsonCaught mapRow(ResultSet rs, int rowNum) throws SQLException {
        JsonCaught jsonCaught = new JsonCaught(rs.getInt("id"), rs.getString("payload"));
        return jsonCaught;
    }
}