package com.sbelan.x5testproject.repository;

import com.sbelan.x5testproject.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String name);

    User findByEmail(String email);
}
