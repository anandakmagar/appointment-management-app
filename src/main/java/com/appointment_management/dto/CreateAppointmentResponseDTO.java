package com.appointment_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateAppointmentResponseDTO {
    private String statusCode;
    private String statusMsg;
    private long appointmentNumber;
}
