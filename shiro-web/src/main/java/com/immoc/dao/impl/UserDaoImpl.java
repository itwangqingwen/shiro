package com.immoc.dao.impl;

import com.immoc.dao.UserDao;
import com.immoc.pojo.User;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public User getPasByName(String name) {
        String sql = "select username,password from users where username = ?";
        List<User> list = jdbcTemplate.query(sql, new String[]{name}, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                User user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                System.out.println(user.getPassword());
                return user;
            }
        });
        if(CollectionUtils.isEmpty(list)){
            return  null;
        }
        return list.get(0);
    }

    @Override
    public List<String> getPermissionByName(String name) {
        String sql = "SELECT permission FROM roles_permissions WHERE role_name = ?";
        return jdbcTemplate.query(sql, new String[]{"admin"}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("permission");
            }
        });
    }

    @Override
    public List<String> getRoleByName(String name) {
        String sql = "select role_name from user_roles where username = ?";
        return jdbcTemplate.query(sql, new String[]{name}, new RowMapper<String>() {
            @Override
            public String mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getString("role_name");
            }
        });
    }
}
