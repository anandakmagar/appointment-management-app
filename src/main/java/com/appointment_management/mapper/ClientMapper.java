package com.appointment_management.mapper;


import com.appointment_management.dto.ClientDTO;
import com.appointment_management.entity.Client;

public class ClientMapper {
    public static ClientDTO mapToClientDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setClientId(client.getClientId());
        clientDTO.setName(client.getName());
        clientDTO.setPhoneNumber(client.getPhoneNumber());
        clientDTO.setEmail(client.getEmail());
        clientDTO.setCreatedAt(client.getCreatedAt());
        clientDTO.setUpdatedAt(client.getUpdatedAt());

        clientDTO.setClientAddressDTO(ClientAddressMapper.mapToAddressDTO(client.getClientAddress()));
        return clientDTO;
    }

    public static Client mapToClient(ClientDTO clientDTO) {
        Client client = new Client();
        client.setName(clientDTO.getName());
        client.setPhoneNumber(clientDTO.getPhoneNumber());
        client.setEmail(clientDTO.getEmail());
        client.setCreatedAt(clientDTO.getCreatedAt());
        client.setUpdatedAt(clientDTO.getUpdatedAt());

        client.setClientAddress(ClientAddressMapper.mapToAddress(clientDTO.getClientAddressDTO()));

        return client;
    }
}