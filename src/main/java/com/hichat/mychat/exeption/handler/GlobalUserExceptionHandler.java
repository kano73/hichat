package com.hichat.mychat.exeption.handler;

import com.hichat.mychat.exeption.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalUserExceptionHandler {

    @ExceptionHandler(UserNeedsToRegisterWithThisEmail.class)
    public String UserNeedsToRegisterWithThisEmail (){
        return "redirect:/logout";
    }

    @ExceptionHandler(UsernameAlreadyExists.class)
    public ResponseEntity<String> usernameAlreadyExistsHandler (UsernameAlreadyExists exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailAlreadyExists.class)
    public ResponseEntity<String> emailAlreadyExistsHandler (EmailAlreadyExists exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundExceptionHandler (UserNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> methodArgumentNotValidExceptionHandler (MethodArgumentNotValidException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InviteAlreadyExists.class)
    public ResponseEntity<String> inviteAlreadyExistsHandler (InviteAlreadyExists exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserOpenedHisPublicProfile.class)
    public String userOpenedHisPublicProfileHandler (){
        return "redirect:/profile";
    }

    @ExceptionHandler(RequestNotFound.class)
    public ResponseEntity<String> requestNotFoundHandler (RequestNotFound exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ChatNotFound.class)
    public ResponseEntity<String> chatNotFoundHandler (ChatNotFound exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordsNotMatches.class)
    public ResponseEntity<String> passwordsNotMatchesHandler (PasswordsNotMatches exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(NoRecoveryLinkFound.class)
    public ResponseEntity<String> noRecoveryLinkFoundHandler (NoRecoveryLinkFound exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RecoveryCodesNotMatches.class)
    public ResponseEntity<String> recoveryCodesNotMatchesHandler (RecoveryCodesNotMatches exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmailNotVerified.class)
    public ResponseEntity<String> emailNotVerifiedHandler (EmailNotVerified exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(FileExeption.class)
    public ResponseEntity<String> fileExceptionHandler (FileExeption exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
    }

}
