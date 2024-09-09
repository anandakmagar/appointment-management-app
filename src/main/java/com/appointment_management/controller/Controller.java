package com.appointment_management.controller;

import com.appointment_management.dto.*;
import com.appointment_management.exception.UsernameNotFoundException;
import com.appointment_management.security.ResetCodeService;
import com.appointment_management.security.UserManagementService;
import com.appointment_management.service.IAppointmentService;
import com.appointment_management.service.IClientService;
import com.appointment_management.service.IStaffService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class Controller {
    @Autowired
    private IAppointmentService iAppointmentService;

    @Autowired
    private IClientService iClientService;

    @Autowired
    private IStaffService iStaffService;

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private ResetCodeService resetCodeService;

    // Appointment
    @PostMapping("/admin/appointment/create")
    public ResponseEntity<CreateAppointmentResponseDTO> createAppointment(@Valid @RequestBody AppointmentDTO appointmentDTO) {
        long appointmentNumber = iAppointmentService.createAppointment(appointmentDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreateAppointmentResponseDTO("201", "Appointment created successfully and your appointment number is given below", appointmentNumber));
    }

    @DeleteMapping("/admin/appointment/delete")
    public ResponseEntity<ResponseDTO> deleteByAppointmentNumber(@RequestParam long appointmentNumber) {
        boolean isDeleted = iAppointmentService.deleteByAppointmentNumber(appointmentNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO("200", "Appointment deleted successfully"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("500", "An error occurred! Please try again later."));
        }
    }

    @PutMapping("/admin/appointment/update")
    public ResponseEntity<ResponseDTO> update(@RequestBody AppointmentDTO appointmentDTO) {
        boolean isUpdated = iAppointmentService.updateAppointment(appointmentDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO("200", "Appointment updated successfully"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("500", "An error occurred! Please try again later."));
        }
    }

    @GetMapping("/appointment/fetch")
    public ResponseEntity<AppointmentDTO> fetchAppointment(@RequestParam long appointmentNumber) {
        AppointmentDTO appointmentDTO = iAppointmentService.fetchAppointment(appointmentNumber);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTO);
    }

    @GetMapping("/appointment/fetchByClientId")
    public ResponseEntity<List<AppointmentDTO>> fetchByClientId(@RequestParam long clientId) {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsByClientId(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    @GetMapping("/appointment/fetchByStaffId")
    public ResponseEntity<List<AppointmentDTO>> fetchByStaffId(@RequestParam long staffId) {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsByStaffId(staffId);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    @GetMapping("/appointment/fetchByClientEmail")
    public ResponseEntity<List<AppointmentDTO>> fetchByClientEmail(@RequestParam String email) {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsByClientEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    @GetMapping("/appointment/fetchByClientPhoneNumber")
    public ResponseEntity<List<AppointmentDTO>> fetchByClientPhoneNumber(@RequestParam String phoneNumber) {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsByClientPhoneNumber(phoneNumber);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    @GetMapping("/appointment/fetchByStaffEmail")
    public ResponseEntity<List<AppointmentDTO>> fetchByStaffEmail(@RequestParam String email) {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsByStaffEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    //
    @GetMapping("/appointment/fetchByStaffPhoneNumber")
    public ResponseEntity<List<AppointmentDTO>> fetchByStaffPhoneNumber(@RequestParam String phoneNumber) {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsByStaffPhoneNumber(phoneNumber);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    @GetMapping("/appointment/fetchAllAppointmentStatus")
    public List<String> fetchAllAppointmentStatus() {
        return iAppointmentService.fetchAllAppointmentStatus();
    }

    @GetMapping("/appointment/fetchAllAppointmentType")
    public List<String> fetchAllAppointmentType() {
        return iAppointmentService.fetchAllAppointmentType();
    }

    @GetMapping("/appointment/fetchAppointmentsBySingleDate")
    public ResponseEntity<List<AppointmentDTO>> fetchAppointmentsBySingleDate(@RequestParam LocalDate date) {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsBySingleDate(date);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    @GetMapping("/appointment/fetchAppointmentsByDoubleDates")
    public ResponseEntity<List<AppointmentDTO>> fetchAppointmentsByDoubleDates(@RequestParam LocalDate date1, @RequestParam LocalDate date2) {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsByDoubleDates(date1, date2);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    @GetMapping("/appointment/fetchAppointmentsByAppointmentStatus")
    public ResponseEntity<List<AppointmentDTO>> fetchAppointmentsByAppointmentStatus(@RequestParam String appointmentStatus) {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsByAppointmentStatus(appointmentStatus);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    @GetMapping("/appointment/fetchAppointmentsByAppointmentType")
    public ResponseEntity<List<AppointmentDTO>> fetchAppointmentsByAppointmentType(@RequestParam String appointmentType) {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsByAppointmentType(appointmentType);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    @GetMapping("/appointment/fetchAppointmentsForToday")
    public ResponseEntity<List<AppointmentDTO>> fetchAppointmentsForToday() {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsForToday();
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    @GetMapping("/appointment/fetchAppointmentsByAppointmentTypeAndSingleDate")
    public ResponseEntity<List<AppointmentDTO>> fetchAppointmentsByAppointmentTypeAndSingleDate(@RequestParam String appointmentType, @RequestParam LocalDate date) {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsByAppointmentTypeAndSingleDate(appointmentType, date);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    @GetMapping("/appointment/fetchAppointmentsByAppointmentTypeAndDoubleDates")
    public ResponseEntity<List<AppointmentDTO>> fetchAppointmentsByAppointmentTypeAndDoubleDates(@RequestParam String appointmentType, @RequestParam LocalDate date1, @RequestParam LocalDate date2) {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsByAppointmentTypeAndDoubleDates(appointmentType, date1, date2);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    @GetMapping("/appointment/fetchAppointmentsByAppointmentStatusAndSingleDate")
    public ResponseEntity<List<AppointmentDTO>> fetchAppointmentsByAppointmentStatusAndSingleDate(@RequestParam String appointmentStatus, @RequestParam LocalDate date) {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsByAppointmentStatusAndSingleDate(appointmentStatus, date);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    @GetMapping("/appointment/fetchAppointmentsByAppointmentStatusAndDoubleDates")
    public ResponseEntity<List<AppointmentDTO>> fetchAppointmentsByAppointmentStatusAndDoubleDates(@RequestParam String appointmentStatus, @RequestParam LocalDate date1, @RequestParam LocalDate date2) {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsByAppointmentStatusAndDoubleDates(appointmentStatus, date1, date2);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    @GetMapping("/appointment/fetchAppointmentsByStatusForToday")
    public ResponseEntity<List<AppointmentDTO>> fetchAppointmentsByStatusForToday(@RequestParam String appointmentStatus) {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsByStatusForToday(appointmentStatus);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    @GetMapping("/appointment/fetchAppointmentsByTypeForToday")
    public ResponseEntity<List<AppointmentDTO>> fetchAppointmentsByTypeForToday(@RequestParam String appointmentType) {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsByTypeForToday(appointmentType);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    @GetMapping("/appointment/fetchAppointmentsByStatusAndTypeForToday")
    public ResponseEntity<List<AppointmentDTO>> fetchAppointmentsByStatusAndTypeForToday(@RequestParam String appointmentStatus, @RequestParam String appointmentType) {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAppointmentsByStatusAndTypeForToday(appointmentStatus, appointmentType);
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    @GetMapping("/appointment/fetchAllAppointments")
    public ResponseEntity<List<AppointmentDTO>> fetchAllAppointments() {
        List<AppointmentDTO> appointmentDTOs = iAppointmentService.fetchAllAppointments();
        return ResponseEntity.status(HttpStatus.OK).body(appointmentDTOs);
    }

    // Staff
    @PostMapping("/admin/client/create")
    public ResponseEntity<CreateClientResponseDTO> createClient(@Valid @RequestBody ClientDTO clientDTO) {
        long clientId = iClientService.createClient(clientDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreateClientResponseDTO("201", "Client created successfully and your client Id is given below", clientId));
    }

    @DeleteMapping("/admin/client/delete")
    public ResponseEntity<ResponseDTO> deleteByClientId(@RequestParam long clientId) {
        boolean isDeleted = iClientService.deleteByClientId(clientId);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO("200", "Client deleted successfully"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("500", "An error occurred! Please try again later."));
        }
    }

    @PutMapping("/admin/client/update")
    public ResponseEntity<ResponseDTO> update(@RequestBody ClientDTO clientDTO) {
        boolean isUpdated = iClientService.updateClient(clientDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO("200", "Client updated successfully"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("500", "An error occurred! Please try again later."));
        }
    }

    @GetMapping("/client/fetch")
    public ResponseEntity<ClientDTO> fetchClient(@RequestParam long clientId) {
        ClientDTO clientDTO = iClientService.fetchClient(clientId);
        return ResponseEntity.status(HttpStatus.OK).body(clientDTO);
    }

    @GetMapping("/client/fetchClientByClientId")
    public ClientDTO fetchClientByClientId(@RequestParam long clientId) {
        return iClientService.fetchClient(clientId);
    }

    @GetMapping("/client/fetchClientByEmail")
    public ClientDTO fetchClientByEmail(@RequestParam String email) {
        return iClientService.fetchClientByEmail(email);
    }

    @GetMapping("/client/fetchClientByPhoneNumber")
    public ClientDTO fetchClientByPhoneNumber(@RequestParam String phoneNumber) {
        return iClientService.fetchClientByPhoneNumber(phoneNumber);
    }

    @GetMapping("/client/fetchClientNameByClientId")
    public String fetchClientNameByClientId(@RequestParam long clientId) {
        return iClientService.fetchClientNameByClientId(clientId);
    }

    @GetMapping("/client/fetchAllClients")
    public List<ClientDTO> fetchAllClients() {
        return iClientService.fetchAllClients();
    }

    // Staff
    @PostMapping("/admin/staff/create")
    public ResponseEntity<CreateStaffResponseDTO> createStaff(@Valid @RequestBody StaffDTO staffDTO) {
        long staffId = iStaffService.createStaff(staffDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new CreateStaffResponseDTO("201", "Staff created successfully and your staff Id is given below", staffId));
    }

    @DeleteMapping("/admin/staff/delete")
    public ResponseEntity<ResponseDTO> deleteByStaffId(@RequestParam long staffId) {
        boolean isDeleted = iStaffService.deleteByStaffId(staffId);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO("200", "Staff deleted successfully"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("500", "An error occurred! Please try again later."));
        }
    }

    @PutMapping("/admin/staff/update")
    public ResponseEntity<ResponseDTO> update(@RequestBody StaffDTO staffDTO) {
        boolean isUpdated = iStaffService.updateStaff(staffDTO);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDTO("200", "Staff updated successfully"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("500", "An error occurred! Please try again later."));
        }
    }

    @GetMapping("/staff/fetch")
    public ResponseEntity<StaffDTO> fetchStaff(@RequestParam long staffId) {
        StaffDTO staffDTO = iStaffService.fetchStaff(staffId);
        return ResponseEntity.status(HttpStatus.OK).body(staffDTO);
    }

    @GetMapping("/staff/fetchStaffByStaffId")
    public StaffDTO fetchStaffByStaffId(@RequestParam long staffId) {
        return iStaffService.fetchStaff(staffId);
    }

    @GetMapping("/staff/fetchStaffByEmail")
    public StaffDTO fetchStaffByEmail(@RequestParam String email) {
        return iStaffService.fetchStaffByEmail(email);
    }

    @GetMapping("/staff/fetchStaffByPhoneNumber")
    public StaffDTO fetchStaffByPhoneNumber(@RequestParam String phoneNumber) {
        return iStaffService.fetchStaffByPhoneNumber(phoneNumber);
    }

    @GetMapping("/staff/fetchNameByStaffId")
    public String fetchStaffNameByStaffId(@RequestParam long staffId) {
        return iStaffService.fetchStaffNameByStaffId(staffId);
    }

    @GetMapping("/staff/fetchAllStaff")
    public List<StaffDTO> fetchAllStaff() {
        return iStaffService.fetchAllStaff();
    }

    // Security
    @PostMapping("/admin/security/register")
    public ResponseEntity<RegisterResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        boolean isRegistered = userManagementService.register(registerRequestDTO);
        if (isRegistered) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new RegisterResponseDTO(200, "User registered successfully"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new RegisterResponseDTO(400, "Registration failed: User already exists or invalid data provided"));
        }
    }

    @PostMapping("/admin/security/registerAdmin")
    public ResponseEntity<RegisterResponseDTO> registerAdmin(@RequestBody RegisterRequestDTO registerRequestDTO) {
        boolean isRegistered = userManagementService.registerAdmin(registerRequestDTO);
        if (isRegistered) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new RegisterResponseDTO(200, "Admin registered successfully"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new RegisterResponseDTO(400, "Registration failed: User already exists or invalid data provided"));
        }
    }

    @PostMapping("/security/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        LoginResponseDTO response = userManagementService.login(loginRequestDTO);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @PostMapping("/security/logout")
    public ResponseEntity<String> logout() {
        userManagementService.logout();
        return ResponseEntity.ok("Logout successful");
    }

    @PostMapping("/security/refresh-token")
    public ResponseEntity<LoginResponseDTO> refreshToken(@RequestBody LoginResponseDTO refreshTokenRequest) {
        LoginResponseDTO response = userManagementService.refreshToken(refreshTokenRequest);
        return ResponseEntity
                .status(response.getStatusCode())
                .body(response);
    }

    @GetMapping("/security/send-reset-code")
    public ResponseEntity<String> sendPasswordResetCode(@RequestParam String email) {
        boolean result = resetCodeService.sendPasswordResetCode(email);
        if (result) {
            return ResponseEntity.ok("Password reset code sent successfully.");
        } else {
            return ResponseEntity.badRequest().body("Failed to send password reset code.");
        }
    }

    @PostMapping("/security/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ResetPasswordDTO resetPasswordDTO) {
        try {
            boolean result = resetCodeService.changePassword(resetPasswordDTO);
            if (result) {
                return ResponseEntity.ok("Password changed successfully.");
            } else {
                return ResponseEntity.badRequest().body("Invalid reset code or email.");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body("Username/email not found!!");
        }
    }

    @DeleteMapping("/admin/security/delete")
    public ResponseEntity<String> deleteUser(@RequestParam long userId) {
        boolean isDeleted = userManagementService.deleteByUserId(userId);
        if (isDeleted) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete user.");
        }
    }

}
