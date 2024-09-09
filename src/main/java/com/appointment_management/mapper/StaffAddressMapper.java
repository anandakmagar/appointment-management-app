package com.appointment_management.mapper;


import com.appointment_management.dto.StaffAddressDTO;
import com.appointment_management.entity.StaffAddress;

public class StaffAddressMapper {
    public static StaffAddressDTO mapToAddressDTO(StaffAddress staffAddress) {
        StaffAddressDTO staffAddressDTO = new StaffAddressDTO();
        staffAddressDTO.setStreetAddress(staffAddress.getStreetAddress());
        staffAddressDTO.setCity(staffAddress.getCity());
        staffAddressDTO.setState(staffAddress.getState());
        staffAddressDTO.setPostalCode(staffAddress.getPostalCode());
        return staffAddressDTO;
    }

    public static StaffAddress mapToAddress(StaffAddressDTO staffAddressDTO) {
        StaffAddress staffAddress = new StaffAddress();
        staffAddress.setStreetAddress(staffAddressDTO.getStreetAddress());
        staffAddress.setCity(staffAddressDTO.getCity());
        staffAddress.setState(staffAddressDTO.getState());
        staffAddress.setPostalCode(staffAddressDTO.getPostalCode());
        return staffAddress;
    }
}
