package com.appointment_management.mapper;


import com.appointment_management.dto.ClientAddressDTO;
import com.appointment_management.entity.ClientAddress;

public class ClientAddressMapper {
    public static ClientAddressDTO mapToAddressDTO(ClientAddress clientAddress) {
        ClientAddressDTO clientAddressDTO = new ClientAddressDTO();
        clientAddressDTO.setStreetAddress(clientAddress.getStreetAddress());
        clientAddressDTO.setCity(clientAddress.getCity());
        clientAddressDTO.setState(clientAddress.getState());
        clientAddressDTO.setPostalCode(clientAddress.getPostalCode());
        return clientAddressDTO;
    }

    public static ClientAddress mapToAddress(ClientAddressDTO clientAddressDTO) {
        ClientAddress clientAddress = new ClientAddress();
        clientAddress.setStreetAddress(clientAddressDTO.getStreetAddress());
        clientAddress.setCity(clientAddressDTO.getCity());
        clientAddress.setState(clientAddressDTO.getState());
        clientAddress.setPostalCode(clientAddressDTO.getPostalCode());
        return clientAddress;
    }

}