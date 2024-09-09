package com.appointment_management.service;

import com.appointment_management.dto.ClientDTO;
import com.appointment_management.entity.Client;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IClientService {
    long createClient(ClientDTO clientDTO);
    ClientDTO fetchClient(long clientId);
    boolean updateClient(ClientDTO clientDTO);
    boolean deleteByClientId(long clientId);
    Optional<Client> findByClientId(long clientId);
    ClientDTO fetchClientByEmail(String email);
    ClientDTO fetchClientByPhoneNumber(String email);
    String fetchClientNameByClientId(long clientId);
    List<ClientDTO> fetchAllClients();
}
