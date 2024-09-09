package com.appointment_management.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientAddressDTO {
    @NotEmpty(message = "Facility name field cannot be null or empty")
    private String streetAddress;
    @NotEmpty(message = "Facility name field cannot be null or empty")
    private String city;
    @NotEmpty(message = "Facility name field cannot be null or empty")
    private String state;
    @NotEmpty(message = "Facility name field cannot be null or empty")
    private String postalCode;
}

