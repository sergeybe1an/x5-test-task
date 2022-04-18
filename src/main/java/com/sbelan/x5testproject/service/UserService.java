package com.sbelan.x5testproject.service;

import com.sbelan.x5testproject.model.auth.User;
import java.util.List;

public interface UserService {

    User register(User user);

    List<User> findAll();

    User findByUsername(String username);

    User findByEmail(String email);

    User findById(Long id);

    void delete(Long id);
}
