package com.appointment_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ClientDTO {
    private Long clientId;
    private String name;
    private String email;
    private String phoneNumber;
    private ClientAddressDTO clientAddressDTO;
    private LocalDateTime createdAt;
    private  LocalDateTime updatedAt;
}
