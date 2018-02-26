package com.djcoldbrain.giflib.service;

import com.djcoldbrain.giflib.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findById(long id);

    User findByUserName(String username);
}
