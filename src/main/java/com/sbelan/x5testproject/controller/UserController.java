package com.sbelan.x5testproject.controller;

import com.sbelan.x5testproject.model.auth.User;
import com.sbelan.x5testproject.model.dto.UserDto;
import com.sbelan.x5testproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/users")
@Tag(name = "User controller", description = "User controller")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}")
    @Operation(summary = "Get user by id")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long userId) {

        User user;
        try {
            user = userService.findById(userId);
        } catch (HttpClientErrorException e) {
            log.error("UserController.getUserById userId: {}, error: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            log.error("UserController.getUserById userId: {}, error: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        UserDto result = UserDto.fromUser(user);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/register")
    @Operation(summary = "Register user")
    public ResponseEntity<UserDto> register(@Valid @RequestBody User request) {

        try {
            request = userService.register(request);
        } catch (Exception e) {
            log.error("UserController.register error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        UserDto response = UserDto.fromUser(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete user by id")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
