package com.appointment_management.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false, nullable = false)
    private Long staffId;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String phoneNumber;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId")
    private StaffAddress staffAddress;
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    private  LocalDateTime updatedAt;

    // The below constructor is just to create the instance for JUnit testing
    public Staff(Long staffId, String name, String email, String phoneNumber, StaffAddress staffAddress, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.staffId = staffId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.staffAddress = staffAddress;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
