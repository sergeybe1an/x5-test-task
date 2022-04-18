package com.sbelan.x5testproject.controller;

import com.sbelan.x5testproject.model.auth.User;
import com.sbelan.x5testproject.model.dto.AuthenticationRequestDto;
import com.sbelan.x5testproject.security.jwt.JwtTokenProvider;
import com.sbelan.x5testproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/auth")
@Tag(name = "Authentication controller", description = "Authentication controller")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    public ResponseEntity<Map<Object, Object>> login(@RequestBody AuthenticationRequestDto requestDto) {
        Map<Object, Object> response = new HashMap<>();
        try {
            final String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            final User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            log.error("Error {} with message {}", e, e.getMessage());
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }
    }
}
