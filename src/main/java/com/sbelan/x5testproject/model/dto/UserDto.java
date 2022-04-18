package com.sbelan.x5testproject.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sbelan.x5testproject.model.auth.User;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Long id;
    private String username;
    private String email;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);

        return user;
    }

    public static UserDto fromUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
