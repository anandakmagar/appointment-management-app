package com.appointment_management.repository;

import com.appointment_management.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Optional<Appointment> findByAppointmentNumber(long appointmentNumber);

    @Transactional
    void deleteByAppointmentNumber(long appointmentNumber);
}
