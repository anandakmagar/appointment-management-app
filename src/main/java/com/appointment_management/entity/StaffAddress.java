package com.appointment_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long addressId;
    private String streetAddress;
    private String city;
    private String state;
    private String postalCode;

    // The below constructor is just to create the instance for JUnit testing
    public StaffAddress(String streetAddress, String city, String state, String postalCode) {
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
    }

}
