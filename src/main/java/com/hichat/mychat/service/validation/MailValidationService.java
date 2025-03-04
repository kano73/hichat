package com.hichat.mychat.service.validation;

import com.hichat.mychat.exeption.NoRecoveryLinkFound;
import com.hichat.mychat.exeption.RecoveryCodesNotMatches;
import com.hichat.mychat.model.dto.SecretCodeDTO;
import com.hichat.mychat.model.entitie.EmailValidation;
import com.hichat.mychat.repository.EmailValidationRepository;
import com.hichat.mychat.repository.nativedb.MyUserJdbcRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class MailValidationService {
    private final EmailValidationRepository emailValidationRepository;
    private final MyUserJdbcRepository myUserJdbcRepository;

    public MailValidationService(MyUserJdbcRepository myUserJdbcRepository, EmailValidationRepository emailValidationRepository) {
        this.myUserJdbcRepository = myUserJdbcRepository;
        this.emailValidationRepository = emailValidationRepository;
    }

    @Transactional
    public boolean confirmMail(String linkPath, SecretCodeDTO secretCodeDTO) {
        EmailValidation emailValidation = emailValidationRepository.findByLink(linkPath).orElseThrow(()->new NoRecoveryLinkFound("do not play with me"));

        if(!Objects.equals(secretCodeDTO.getSecretCode(), emailValidation.getSecretCode())) {
            throw new RecoveryCodesNotMatches("you better not do this");
        }
        emailValidationRepository.deleteByLink(linkPath);
        return myUserJdbcRepository.setEmailVerifiedTrue(emailValidation.getUser());
    }
}
