package com.bak.demos.spring.security.jdbc.password.encoder.dao;

import com.bak.demos.spring.security.jdbc.password.encoder.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public boolean addUser(User user) {
        String query = "insert into users(username,password,enabled) values(?,?,?)";
        int i = jdbcTemplate.update(user.getUserName(), user.getPassword(), true);
        if (i >= 1) {
            addAuthorities(user);
            return true;
        } else {
            return false;
        }
    }

    public void addAuthorities(User user){
        String query = "insert into authorities(username,authority) values(?,?)";
        if(user.getAuthorities() != null && !user.getAuthorities().isEmpty()){
            user.getAuthorities().forEach(authority ->{
                jdbcTemplate.update(user.getUserName(), authority);
            });
        }
    }

    public User getUser(String userName) {
        String query = "select username,password,enabled from users where username = ?";
        return jdbcTemplate.queryForObject(query, new Object[] { userName }, new UserRowMapper());
    }

    public class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserName(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEnabled(rs.getBoolean("enabled"));
            return user;
        }

    }


}
