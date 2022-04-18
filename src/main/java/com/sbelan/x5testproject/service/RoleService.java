package com.sbelan.x5testproject.service;

import com.sbelan.x5testproject.model.auth.Role;
import java.util.List;

public interface RoleService {

    List<Role> getAll();

    Role save(Role role);

    void delete(Long id);
}
