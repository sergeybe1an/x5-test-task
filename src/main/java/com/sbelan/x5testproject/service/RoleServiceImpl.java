package com.sbelan.x5testproject.service;

import com.sbelan.x5testproject.model.auth.Role;
import com.sbelan.x5testproject.repository.RoleRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        List<Role> roles = roleRepository.findAll();

        log.info("RoleServiceImpl.getAll roles list size: {}", roles.size());

        return roles;
    }

    @Override
    public Role save(Role role) {

        Role dbRole = roleRepository.findByName(role.getName());
        if (dbRole == null) {
            role.setCreated(LocalDateTime.now());
            role.setUpdated(LocalDateTime.now());

            role = roleRepository.save(role);
            log.info("RoleServiceImpl.save successfully saved new role: {}", role);
        }

        return role;
    }

    @Override
    public void delete(Long roleId) {
        roleRepository.deleteById(roleId);
        log.info("RoleServiceImpl.delete successfully removed role: {}", roleId);
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
}
