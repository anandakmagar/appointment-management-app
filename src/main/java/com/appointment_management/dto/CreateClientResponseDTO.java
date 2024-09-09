package com.appointment_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateClientResponseDTO {
    private String statusCode;
    private String statusMsg;
    private long clientId;
}
