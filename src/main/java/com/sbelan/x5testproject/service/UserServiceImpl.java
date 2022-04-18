package com.sbelan.x5testproject.service;

import com.sbelan.x5testproject.model.auth.Role;
import com.sbelan.x5testproject.model.auth.User;
import com.sbelan.x5testproject.repository.RoleRepository;
import com.sbelan.x5testproject.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        User registeredUser = userRepository.save(user);

        log.info("UserServiceImpl.register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Override
    public List<User> findAll() {
        List<User> users = userRepository.findAll();

        log.info("UserServiceImpl.getAll - users list size: {}", users.size());

        return users;
    }

    @Override
    public User findByEmail(String email) {
        User user = userRepository.findByEmail(email);

        log.info("UserServiceImpl.findByUsername - user: {} found by email: {}", user, email);

        return user;
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);

        log.info("UserServiceImpl.findByUsername - user: {} found by username: {}", user, username);

        return user;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format("User %d didn't found!", id)));

        log.info("UserServiceImpl.findById - user: {} found by id: {}", user, id);

        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);

        log.info("UserServiceImpl.deleteById - user successfully removed by id: {}", id);
    }
}
