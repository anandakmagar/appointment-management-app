package com.appointment_management.security;

import com.appointment_management.dto.ResetPasswordDTO;
import com.appointment_management.entity.OurUser;
import com.appointment_management.entity.ResetCode;
import com.appointment_management.exception.UsernameNotFoundException;
import com.appointment_management.mail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ResetCodeService {
    @Autowired
    private ResetCodeRepository resetCodeRepository;

    @Autowired
    private OurUserRepository ourUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public ResetCode findByEmail(String email){
        return resetCodeRepository.findByEmail(email);
    }

    public boolean sendPasswordResetCode(String email) {
        ResetCode existingResetCode = resetCodeRepository.findByEmail(email);
        if (existingResetCode != null) {
            resetCodeRepository.delete(existingResetCode);
        }
        if (ourUserRepository.existsByEmail(email)) {
            long code = 1000000000L + new Random().nextInt(900000000);
            ResetCode resetCode = new ResetCode();
            resetCode.setCode(code);
            resetCode.setEmail(email);
            resetCodeRepository.save(resetCode);

            String message = String.format("%s, your password reset code is %d.", email, code);
            emailService.sendEmail(email, "Password Reset Code Delivery", message);
            return true;
        } else {
            return false;
        }
    }

    public boolean changePassword(ResetPasswordDTO resetPasswordDTO){
        OurUser user = ourUserRepository.findByEmail(resetPasswordDTO.getEmail()).orElseThrow(
                () -> new UsernameNotFoundException("Username/email not found!!")
        );
        ResetCode resetCode1 = findByEmail(resetPasswordDTO.getEmail());
        if (user.getEmail().equals(resetPasswordDTO.getEmail()) && resetCode1.getEmail().equals(resetPasswordDTO.getEmail()) && resetPasswordDTO.getResetCode() == resetCode1.getCode()){
            String encodedPassword = passwordEncoder.encode(resetPasswordDTO.getNewPassword());
            user.setPassword(encodedPassword);
            ourUserRepository.save(user);
            return true;
        }
        return false;
    }

}
