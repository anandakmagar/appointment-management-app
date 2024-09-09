package com.appointment_management.service;

import com.appointment_management.dto.RegisterRequestDTO;
import com.appointment_management.dto.StaffDTO;
import com.appointment_management.entity.Staff;
import com.appointment_management.exception.ResourceNotFoundException;
import com.appointment_management.mapper.StaffAddressMapper;
import com.appointment_management.mapper.StaffMapper;
import com.appointment_management.repository.StaffRepository;
import com.appointment_management.security.UserManagementService;
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
public class StaffServiceImpl implements IStaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private UserManagementService userManagementService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    @Transactional
    public long createStaff(StaffDTO staffDTO) {
        Staff staff = StaffMapper.mapToStaff(staffDTO);
        Long staffId = createStaffId();
        staff.setStaffId(staffId);
        staff.setCreatedAt(LocalDateTime.now());
        Staff savedStaff = staffRepository.save(staff);

        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO();
        registerRequestDTO.setUserId(savedStaff.getStaffId());
        registerRequestDTO.setName(savedStaff.getName());
        registerRequestDTO.setEmail(savedStaff.getEmail());
        registerRequestDTO.setPassword("password"); // This will be the default password
        registerRequestDTO.setRole("STAFF");

        userManagementService.register(registerRequestDTO);

        String message = String.format("Email: [%s], Your staff registration has been completed and your staff id is %d", savedStaff.getEmail(), staffId);
        kafkaTemplate.send("staff-registration", savedStaff.getEmail(), message);

        return staffId;
    }

    @Override
    @Transactional
    public StaffDTO fetchStaff(long staffId) {
        Staff staff = staffRepository.findByStaffId(staffId).orElseThrow(
                () -> new ResourceNotFoundException("Staff", "staffId", staffId)
        );
        return StaffMapper.mapToStaffDTO(staff);
    }

    @Override
    @Transactional
    public boolean updateStaff(StaffDTO staffDTO) {
        long staffId = staffDTO.getStaffId();
        Staff staff = findByStaffId(staffId).orElseThrow(
                () -> new ResourceNotFoundException("Staff", "StaffId", staffId)
        );
        boolean isUpdated = false;
        Staff updatedStaff = null;
        if (staff != null) {
            staff.setEmail(staffDTO.getEmail());
            staff.setName(staffDTO.getName());
            staff.setPhoneNumber(staffDTO.getPhoneNumber());
            staff.setStaffAddress(StaffAddressMapper.mapToAddress(staffDTO.getStaffAddressDTO()));
            staff.setUpdatedAt(LocalDateTime.now());
            updatedStaff = staffRepository.save(staff);

            isUpdated = true;
        }

        assert updatedStaff != null;
        StaffDTO updatedStaffDTO = StaffMapper.mapToStaffDTO(updatedStaff);
        String message = String.format("Email: [%s], Your staff record has been updated at %s. The current record is as follows:\n%s",
                updatedStaff.getEmail(), updatedStaff.getUpdatedAt().toString(), updatedStaffDTO);

        kafkaTemplate.send("staff-update", updatedStaff.getEmail(), message);

        return isUpdated;
    }

    @Override
    @Transactional
    public boolean deleteByStaffId(long staffId) {
        Staff staff = staffRepository.findByStaffId(staffId).orElseThrow(
                () -> new ResourceNotFoundException("Staff", "StaffId", staffId)
        );

        StaffDTO staffDTO = StaffMapper.mapToStaffDTO(staff);

        staffRepository.deleteByStaffId(staffId);

        userManagementService.deleteByUserId(staffId);

        String message = String.format("Email: [%s], You have been removed from our staff database.", staffDTO.getEmail());
        kafkaTemplate.send("staff-deletion", staffDTO.getEmail(), message);

        return true;
    }


    @Override
    public Optional<Staff> findByStaffId(long staffId) {
        return staffRepository.findByStaffId(staffId);
    }


    private Long createStaffId() {
        while (true) {
            long randomNumber = 1000000000L + new Random().nextInt(900000000);
            if (isUnique(randomNumber)) {
                return randomNumber;
            }
        }
    }

    private boolean isUnique(long randomNumber){
        return findByStaffId(randomNumber).isEmpty();
    }

    @Override
    @Transactional
    public StaffDTO fetchStaffByEmail(String email) {
        Staff staff = staffRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Staff", "email", email)
        );
        return StaffMapper.mapToStaffDTO(staff);
    }

    @Override
    @Transactional
    public StaffDTO fetchStaffByPhoneNumber(String phoneNumber) {
        Staff staff = staffRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new ResourceNotFoundException("Staff", "phoneNumber", phoneNumber)
        );
        return StaffMapper.mapToStaffDTO(staff);
    }

    @Override
    @Transactional
    public String fetchStaffNameByStaffId(long staffId){
        Staff staff = staffRepository.findByStaffId(staffId).orElseThrow(
                () -> new ResourceNotFoundException("Staff", "staffId", staffId)
        );
        return staff.getName();
    }

    @Override
    public List<StaffDTO> fetchAllStaff() {
        return staffRepository.findAll().stream()
                .map(StaffMapper::mapToStaffDTO)
                .collect(Collectors.toList());
    }

}

