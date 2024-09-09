package com.appointment_management.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentAddressDTO {
    @NotEmpty(message = "Facility name field cannot be null or empty")
    private String facilityName;
    @NotEmpty(message = "Street address field cannot be null or empty")
    private String streetAddress;
    @NotEmpty(message = "City field cannot be null or empty")
    private String city;
    @NotEmpty(message = "State field cannot be null or empty")
    private String state;
    @NotEmpty(message = "Postal code field cannot be null or empty")
    private String postalCode;

    @Override
    public String toString() {
        return String.format("Facility Name: %s\nStreet Address: %s\nCity: %s\nState: %s\nPostal Code: %s",
                facilityName, streetAddress, city, state, postalCode);
    }
}
