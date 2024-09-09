package com.appointment_management.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {
    private String email;
    private long resetCode;
    private String newPassword;
}
