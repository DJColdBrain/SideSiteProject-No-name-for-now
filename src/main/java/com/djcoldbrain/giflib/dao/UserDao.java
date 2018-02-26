package com.djcoldbrain.giflib.dao;

import com.djcoldbrain.giflib.model.User;

import java.util.List;

public interface UserDao{

    List<User> findAll();

    User findByUsername(String username);

    User findById(long id);

    void save(User user);

    void delete(User user);

}
