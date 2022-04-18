package com.sbelan.x5testproject.security.jwt;

import com.sbelan.x5testproject.model.auth.Role;
import com.sbelan.x5testproject.model.auth.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(),
                true, mapToGrantedAuthorities(new ArrayList<>(user.getRoles())), user.getUpdated()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> userRoles) {
        return userRoles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
