package com.appointment_management.entity;

import com.appointment_management.enumeration.AppointmentStatus;
import com.appointment_management.enumeration.AppointmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(updatable = false, nullable = false)
    private Long appointmentNumber;
    @Column(updatable = false, nullable = false)
    private Long clientId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentType appointmentType;
    @Column(nullable = false)
    private Long assignedStaffId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus appointmentStatus;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private AppointmentAddress appointmentAddress;
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    private  LocalDateTime updatedAt;

}