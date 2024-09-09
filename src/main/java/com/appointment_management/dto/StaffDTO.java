package com.appointment_management.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class StaffDTO {
    private Long staffId;
    private String name;
    private String email;
    private String phoneNumber;
    private StaffAddressDTO staffAddressDTO;
    private LocalDateTime createdAt;
    private  LocalDateTime updatedAt;
}
