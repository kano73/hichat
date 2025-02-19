package com.hichat.mychat.service.validation;

import com.hichat.mychat.exeption.NoRecoveryLinkFound;
import com.hichat.mychat.exeption.RecoveryCodesNotMatches;
import com.hichat.mychat.model.dto.SecretCodeDTO;
import com.hichat.mychat.model.entitie.EmailValidation;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.repository.EmailValidationRepository;
import com.hichat.mychat.repository.nativedb.MyUserJdbcRepository;
import com.hichat.mychat.service.StringGenerator;
import com.hichat.mychat.service.mail.EmailService;
import com.hichat.mychat.service.mail.MailMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class MailValidationService {
    private final StringGenerator stringGenerator;
    private final EmailValidationRepository emailValidationRepository;
    private final MyUserJdbcRepository myUserJdbcRepository;
    @Value("${myvalue.base-url}")
    private String rawLink;
    private final MailMessageService mailMessageService;
    private final EmailService emailService;

    public MailValidationService(StringGenerator stringGenerator, MailMessageService mailMessageService, EmailService emailService, EmailValidationRepository emailValidationRepository, MyUserJdbcRepository myUserJdbcRepository) {
        this.stringGenerator = stringGenerator;
        this.mailMessageService = mailMessageService;
        this.emailService = emailService;
        this.emailValidationRepository = emailValidationRepository;
        this.myUserJdbcRepository = myUserJdbcRepository;
    }

    @Transactional
    public boolean preparationForEmailValidation(MyUser user) {
        String shortLink = "/confirm_mail/"+  stringGenerator.generateSecretCode12Chars();

        String secretCode = stringGenerator.generateSecretCode12Chars();

        EmailValidation emailValidation = new EmailValidation();
        emailValidation.setSecretCode(secretCode);
        emailValidation.setUser(user);
        emailValidation.setLink(shortLink);
        if(emailValidationRepository.existsByLink(shortLink)) emailValidationRepository.deleteByLink(shortLink);
        emailValidationRepository.save(emailValidation);

        String link = rawLink + shortLink;
        String mail = mailMessageService.generateEmailConfirmationLetter(link, secretCode);
        String email = user.getEmail();
        emailService.sendEmail(email,"password recovery link",mail);

        return true;
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
