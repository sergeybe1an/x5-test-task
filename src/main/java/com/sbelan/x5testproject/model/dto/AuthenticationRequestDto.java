package com.sbelan.x5testproject.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
