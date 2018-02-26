package com.djcoldbrain.giflib.service;

import com.djcoldbrain.giflib.dao.UserDao;
import com.djcoldbrain.giflib.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findById(long id) {
        return userDao.findById(id);
    }

    @Override
    public User findByUserName(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUserName(username);
        if (user == null){
            throw  new UsernameNotFoundException("User Not found");
        }

        return user;
    }
}
