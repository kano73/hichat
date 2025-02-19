package com.hichat.mychat.service.mail;

import org.springframework.stereotype.Service;

@Service
public class MailMessageService {
    public String generateEmailConfirmationLetter(String link, String secretCode) {
        return """
        This link expires in 12 hours\n
            To confirm your email, please go to :%s\n
            And use this secret code: %s\n
            If you haven't do this action just ignore this mail
        """.formatted(link, secretCode);
    }

    public String generateEmailChangePasswordLink(String link, String secretCode) {
        return """
            To change password go to link: %s\n
            And use this secret code: %s\n
            If you haven't do this action just ignore this mail\n
            If you haven't do this action, you better change your username and password
        """.formatted(link,  secretCode);
    }

}
