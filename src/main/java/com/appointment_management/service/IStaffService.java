package com.appointment_management.service;

import com.appointment_management.dto.StaffDTO;
import com.appointment_management.entity.Staff;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IStaffService {
    long createStaff(StaffDTO staffDTO);
    StaffDTO fetchStaff(long staffId);
    boolean updateStaff(StaffDTO staffDTO);
    boolean deleteByStaffId(long staffId);
    Optional<Staff> findByStaffId(long staffId);
    StaffDTO fetchStaffByEmail(String email);
    StaffDTO fetchStaffByPhoneNumber(String email);
    String fetchStaffNameByStaffId(long staffId);
    List<StaffDTO> fetchAllStaff();
}

