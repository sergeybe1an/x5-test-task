package com.sbelan.x5testproject.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sbelan.x5testproject.model.auth.Role;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto {

    private Long id;
    private String name;

    public Role toRole() {
        Role role = new Role();
        role.setId(id);
        role.setName(name);

        return role;
    }

    public static RoleDto fromRole(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setName(role.getName());

        return roleDto;
    }
}
