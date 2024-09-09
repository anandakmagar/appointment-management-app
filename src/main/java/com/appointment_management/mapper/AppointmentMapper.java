package com.appointment_management.mapper;

import com.appointment_management.dto.AppointmentDTO;
import com.appointment_management.entity.Appointment;

public class AppointmentMapper {
    public static AppointmentDTO mapToAppointmentDTO(Appointment appointment) {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setAppointmentNumber(appointment.getAppointmentNumber());
        appointmentDTO.setClientId(appointment.getClientId());
        appointmentDTO.setAppointmentType(appointment.getAppointmentType());
        appointmentDTO.setAssignedStaffId(appointment.getAssignedStaffId());
        appointmentDTO.setAppointmentStatus(appointment.getAppointmentStatus());
        appointmentDTO.setAppointmentDateTime(appointment.getAppointmentDateTime());
        appointmentDTO.setAppointmentAddressDTO(AppointmentAddressMapper.mapToAppointmentAddressDTO(appointment.getAppointmentAddress()));
        appointmentDTO.setCreatedAt(appointment.getCreatedAt());
        appointmentDTO.setUpdatedAt(appointment.getUpdatedAt());

        return appointmentDTO;
    }

    public static Appointment mapToAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = new Appointment();
        appointment.setClientId(appointmentDTO.getClientId());
        appointment.setAppointmentType(appointmentDTO.getAppointmentType());
        appointment.setAssignedStaffId(appointmentDTO.getAssignedStaffId());
        appointment.setAppointmentStatus(appointmentDTO.getAppointmentStatus());
        appointment.setAppointmentDateTime(appointmentDTO.getAppointmentDateTime());
        appointment.setAppointmentAddress(AppointmentAddressMapper.mapToAppointmentAddress(appointmentDTO.getAppointmentAddressDTO()));

        return appointment;
    }
}