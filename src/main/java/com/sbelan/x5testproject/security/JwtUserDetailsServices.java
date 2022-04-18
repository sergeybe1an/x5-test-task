package com.sbelan.x5testproject.security;


import com.sbelan.x5testproject.model.auth.User;
import com.sbelan.x5testproject.security.jwt.JwtUser;
import com.sbelan.x5testproject.security.jwt.JwtUserFactory;
import com.sbelan.x5testproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsServices implements UserDetailsService {

    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User with username " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);

        log.info("JwtUserDetailsServices.loadUserByUsername - user with username: {} successfully loaded", username);

        return jwtUser;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
