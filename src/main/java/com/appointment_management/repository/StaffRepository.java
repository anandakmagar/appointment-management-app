package com.appointment_management.repository;

import com.appointment_management.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
        Optional<Staff> findByStaffId(long staffId);
        @Transactional
        void deleteByStaffId(long staffId);
        Optional<Staff> findByEmail(String email);
        Optional<Staff> findByPhoneNumber(String phoneNumber);

}
