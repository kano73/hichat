package com.hichat.mychat.service;

import com.hichat.mychat.model.dto.ResetPasswordDTO;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.model.entitie.PasswordRecovery;
import com.hichat.mychat.repository.PasswordRecoveryRepository;
import com.hichat.mychat.repository.nativedb.MyUserJdbcRepository;
import com.hichat.mychat.service.mail.EmailService;
import com.hichat.mychat.service.mail.MailMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hichat.mychat.exeption.NoRecoveryLinkFound;
import com.hichat.mychat.exeption.RecoveryCodesNotMatches;

import java.util.Objects;

@Service
public class PasswordRecoveryService {
    private final PasswordEncoder passwordEncoder;
    private final MyUserJdbcRepository myUserJdbcRepository;
    private final StringGenerator stringGenerator;
    @Value("${myvalue.base-url}")
    private String rawLink;
    private final PasswordRecoveryRepository passwordRecoveryRepository;
    private final MailMessageService mailMessageService;
    private final EmailService emailService;

    public PasswordRecoveryService(PasswordEncoder passwordEncoder, MyUserJdbcRepository myUserJdbcRepository, PasswordRecoveryRepository passwordRecoveryRepository, MailMessageService mailMessageService, EmailService emailService, StringGenerator stringGenerator) {
        this.passwordEncoder = passwordEncoder;
        this.myUserJdbcRepository = myUserJdbcRepository;
        this.passwordRecoveryRepository = passwordRecoveryRepository;
        this.mailMessageService = mailMessageService;
        this.emailService = emailService;
        this.stringGenerator = stringGenerator;
    }

    //    password manipulations
    @Transactional
    public boolean preparationForPasswordRecovery(MyUser user) {
        String shortLink = "/reset_password/"+  stringGenerator.generateSecretCode12Chars();

        PasswordRecovery passwordRecovery = new PasswordRecovery();
        String secretCode = stringGenerator.generateSecretCode12Chars();
        passwordRecovery.setSecretCode(secretCode);
        passwordRecovery.setUser(user);
        passwordRecovery.setLink(shortLink);

        if(passwordRecoveryRepository.existsByLink(shortLink)) passwordRecoveryRepository.deleteByLink(shortLink);
        passwordRecoveryRepository.save(passwordRecovery);

        String link = rawLink + shortLink;
        String mail = mailMessageService.generateEmailChangePasswordLink(link, secretCode);
        String email = user.getEmail();
        emailService.sendEmail(email,"password recovery link",mail);

        return true;
    }

    @Transactional
    public boolean resetPassword(String linkPath, ResetPasswordDTO resetPasswordDTO){
        PasswordRecovery passwordRecovery = passwordRecoveryRepository.findByLink(linkPath).orElseThrow(() -> new NoRecoveryLinkFound("do not play with me"));

        if(!Objects.equals(passwordRecovery.getSecretCode(), resetPasswordDTO.getSecretCode())){
            throw new RecoveryCodesNotMatches("you better not do this");
        }
        passwordRecoveryRepository.deleteByLink(linkPath);

        String newPassword = passwordEncoder.encode(resetPasswordDTO.getPassword());
        return myUserJdbcRepository.updatePasswordById(newPassword, passwordRecovery.getUser().getId());
    }
}


