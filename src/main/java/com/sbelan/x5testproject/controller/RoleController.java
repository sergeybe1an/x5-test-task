package com.sbelan.x5testproject.controller;

import com.sbelan.x5testproject.model.auth.Role;
import com.sbelan.x5testproject.model.dto.RoleDto;
import com.sbelan.x5testproject.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/roles")
@Tag(name = "Role controller", description = "Role controller")
public class RoleController {

    private RoleService roleService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/all")
    @Operation(summary = "Get all roles")
    public ResponseEntity<List<RoleDto>> getAllRoles() {

        List<Role> roles;
        try {
            roles = roleService.getAll();
        } catch (Exception e) {
            log.error("RoleController.getAllRoles error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        List<RoleDto> response = roles.stream()
            .filter(Objects::nonNull)
            .map(RoleDto::fromRole)
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    @Operation(summary = "Save role")
    public ResponseEntity<RoleDto> save(@Valid @RequestBody Role role) {

        try {
            role = roleService.save(role);
        } catch (Exception e) {
            log.error("RoleController.save error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        RoleDto response = RoleDto.fromRole(role);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{roleId}")
    @Operation(summary = "Delete role by id")
    public void deleteRoleById(@PathVariable Long roleId) {
        roleService.delete(roleId);
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}
