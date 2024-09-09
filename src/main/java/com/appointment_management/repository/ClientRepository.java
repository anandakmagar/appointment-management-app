package com.appointment_management.repository;

import com.appointment_management.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
        Optional<Client> findByClientId(long clientId);
        @Transactional
        void deleteByClientId(long clientId);
        Optional<Client> findByEmail(String email);
        Optional<Client> findByPhoneNumber(String phoneNumber);


}
