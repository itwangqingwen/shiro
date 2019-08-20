package com.immoc.dao;

import com.immoc.pojo.User;

import java.util.List;

public interface UserDao {

    User getPasByName(String name);

    List<String> getPermissionByName(String name);

    List<String> getRoleByName(String name);
}
