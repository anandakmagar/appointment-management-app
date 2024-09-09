package com.appointment_management.service;

import com.appointment_management.dto.AppointmentDTO;
import com.appointment_management.entity.Appointment;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface IAppointmentService {
    long createAppointment(AppointmentDTO appointmentDTO);
    AppointmentDTO fetchAppointment(long appointmentNumber);
    boolean updateAppointment(AppointmentDTO appointmentDTO);
    boolean deleteByAppointmentNumber(long appointmentNumber);
    Optional<Appointment> findByAppointmentNumber(long randomNumber);
    List<String> fetchAllAppointmentStatus();
    List<String> fetchAllAppointmentType();
    List<AppointmentDTO> fetchAppointmentsByClientId(long clientId);
    List<AppointmentDTO> fetchAppointmentsByStaffId(long staffId);
    List<AppointmentDTO> fetchAppointmentsByClientEmail(String email);
    List<AppointmentDTO> fetchAppointmentsByClientPhoneNumber(String phoneNumber);
    List<AppointmentDTO> fetchAppointmentsByStaffEmail(String email);
    List<AppointmentDTO> fetchAppointmentsByStaffPhoneNumber(String phoneNumber);
    List<AppointmentDTO> fetchAppointmentsBySingleDate(LocalDate date);
    List<AppointmentDTO> fetchAppointmentsByDoubleDates(LocalDate date1, LocalDate date2);
    List<AppointmentDTO> fetchAppointmentsByAppointmentStatus(String appointmentStatus);
    List<AppointmentDTO> fetchAppointmentsByAppointmentType(String appointmentType);
    List<AppointmentDTO> fetchAppointmentsForToday();
    List<AppointmentDTO> fetchAppointmentsByAppointmentTypeAndSingleDate(String appointmentStatus, LocalDate date);
    List<AppointmentDTO> fetchAppointmentsByAppointmentTypeAndDoubleDates(String appointmentType, LocalDate date1, LocalDate date2);
    List<AppointmentDTO> fetchAppointmentsByAppointmentStatusAndSingleDate(String appointmentStatus, LocalDate date);
    List<AppointmentDTO> fetchAppointmentsByAppointmentStatusAndDoubleDates(String appointmentStatus, LocalDate date1, LocalDate date2);
    List<AppointmentDTO> fetchAppointmentsByStatusForToday(String appointmentStatus);
    List<AppointmentDTO> fetchAppointmentsByTypeForToday(String appointmentType);
    List<AppointmentDTO> fetchAppointmentsByStatusAndTypeForToday(String appointmentStatus, String appointmentType);
    List<AppointmentDTO> fetchAllAppointments();
    String fetchClientNameByClientId(long clientId);
    String fetchStaffNameByStaffId(long staffId);
}
