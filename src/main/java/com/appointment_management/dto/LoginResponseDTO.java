package com.appointment_management.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponseDTO {
    private int statusCode;
    private String error;
    private String message;
    private String token;
    private String role;
    private String refreshToken;
    private String expirationTime;
    private Long userId;
    private String name;
    private String email;
    private String password;
}
