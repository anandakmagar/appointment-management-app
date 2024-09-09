package com.appointment_management.security;

import com.appointment_management.entity.ResetCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetCodeRepository extends JpaRepository<ResetCode, Long> {
    ResetCode findByEmail(String email);
}
