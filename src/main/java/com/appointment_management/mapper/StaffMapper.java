package com.appointment_management.mapper;

import com.appointment_management.dto.StaffDTO;
import com.appointment_management.entity.Staff;

public class StaffMapper {
    public static StaffDTO mapToStaffDTO(Staff staff) {
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setStaffId(staff.getStaffId());
        staffDTO.setName(staff.getName());
        staffDTO.setPhoneNumber(staff.getPhoneNumber());
        staffDTO.setEmail(staff.getEmail());
        staffDTO.setCreatedAt(staff.getCreatedAt());
        staffDTO.setUpdatedAt(staff.getUpdatedAt());

        staffDTO.setStaffAddressDTO(StaffAddressMapper.mapToAddressDTO(staff.getStaffAddress()));
        return staffDTO;
    }

    public static Staff mapToStaff(StaffDTO staffDTO) {
        Staff staff = new Staff();
        staff.setName(staffDTO.getName());
        staff.setPhoneNumber(staffDTO.getPhoneNumber());
        staff.setEmail(staffDTO.getEmail());
        staff.setCreatedAt(staffDTO.getCreatedAt());
        staff.setUpdatedAt(staffDTO.getUpdatedAt());

        staff.setStaffAddress(StaffAddressMapper.mapToAddress(staffDTO.getStaffAddressDTO()));

        return staff;
    }
}
