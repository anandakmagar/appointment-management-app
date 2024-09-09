package com.appointment_management.service;

import com.appointment_management.dto.ClientDTO;
import com.appointment_management.entity.Client;
import com.appointment_management.exception.ResourceNotFoundException;
import com.appointment_management.mapper.ClientAddressMapper;
import com.appointment_management.mapper.ClientMapper;
import com.appointment_management.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements IClientService {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public long createClient(ClientDTO clientDTO) {
        Client client = ClientMapper.mapToClient(clientDTO);
        Long clientId = createClientId();
        client.setClientId(clientId);
        client.setCreatedAt(LocalDateTime.now());
        Client savedClient = clientRepository.save(client);
        ClientDTO savedClientDTO = ClientMapper.mapToClientDTO(savedClient);

        String message = String.format("Email: [%s], Your client registration has been completed and your client id is %d", savedClientDTO.getEmail(), savedClientDTO.getClientId());
        kafkaTemplate.send("client-registration", savedClientDTO.getEmail(), message);

        return clientId;
    }

    @Override
    @Transactional
    public ClientDTO fetchClient(long clientId) {
        Client client = clientRepository.findByClientId(clientId).orElseThrow(
                () -> new ResourceNotFoundException("Client", "clientId", clientId)
        );
        return ClientMapper.mapToClientDTO(client);
    }

    @Override
    public boolean updateClient(ClientDTO clientDTO) {
        long clientId = clientDTO.getClientId();
        Client client = findByClientId(clientId).orElseThrow(
                () -> new ResourceNotFoundException("Client", "ClientId", clientId)
        );
        boolean isUpdated = false;
        if (client != null) {
            client.setName(clientDTO.getName());
            client.setEmail(clientDTO.getEmail());
            client.setPhoneNumber(clientDTO.getPhoneNumber());
            client.setClientAddress(ClientAddressMapper.mapToAddress(clientDTO.getClientAddressDTO()));
            client.setUpdatedAt(LocalDateTime.now());
            Client updatedClient = clientRepository.save(client);

            ClientDTO updatedClientDTO = ClientMapper.mapToClientDTO(updatedClient);
            String message = String.format("Email: [%s], Your client record has been updated at %s. The current record is as follows:\n%s",
                    updatedClientDTO.getEmail(), updatedClientDTO.getUpdatedAt().toString(), updatedClientDTO);

            kafkaTemplate.send("client-update", updatedClientDTO.getEmail(), message);

            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteByClientId(long clientId) {
        Client client = clientRepository.findByClientId(clientId).orElseThrow(
                () -> new ResourceNotFoundException("Client", "ClientId", clientId)
        );

        String clientEmail = client.getEmail();

        clientRepository.deleteByClientId(clientId);

        String message = String.format("Email: [%s], You have been removed from our client database.", clientEmail);
        kafkaTemplate.send("client-deletion", clientEmail, message);
        return true;
    }

    @Override
    public Optional<Client> findByClientId(long clientId) {
        return clientRepository.findByClientId(clientId);
    }

    @Override
    @Transactional
    public ClientDTO fetchClientByEmail(String email) {
        Client client = clientRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Client", "email", email)
        );
        return ClientMapper.mapToClientDTO(client);
    }

    @Override
    @Transactional
    public ClientDTO fetchClientByPhoneNumber(String phoneNumber) {
        Client client = clientRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new ResourceNotFoundException("Client", "phoneNumber", phoneNumber)
        );
        return ClientMapper.mapToClientDTO(client);
    }

    @Override
    @Transactional
    public String fetchClientNameByClientId(long clientId){
        Client client = clientRepository.findByClientId(clientId).orElseThrow(
                () -> new ResourceNotFoundException("Client", "clientId", clientId)
        );
        return client.getName();
    }

    @Override
    public List<ClientDTO> fetchAllClients() {
        return clientRepository.findAll().stream()
                .map(ClientMapper::mapToClientDTO)
                .collect(Collectors.toList());
    }



    private Long createClientId() {
        while (true) {
            long randomNumber = 1000000000L + new Random().nextInt(900000000);
            if (isUnique(randomNumber)) {
                return randomNumber;
            }
        }
    }

    private boolean isUnique(long randomNumber){
        return findByClientId(randomNumber).isEmpty();
    }

}
