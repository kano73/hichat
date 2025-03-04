package com.hichat.mychat.controller;

import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.hichat.mychat.config.security.AuthenticatedMyUserService;
import com.hichat.mychat.config.storage.TableChatNameStorage;
import com.hichat.mychat.exeption.UserNotFoundException;
import com.hichat.mychat.model.dto.MessageToChatDTO;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.model.enumclass.DataType;
import com.hichat.mychat.model.enumclass.FileType;
import com.hichat.mychat.model.response.MessageResponse;
import com.hichat.mychat.service.AwsServiceImplementation;
import com.hichat.mychat.service.MessageService;
import com.hichat.mychat.service.validation.AccessValidation;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RestController
public class ChatController {

    private final MessageService messageService;
    private final TableChatNameStorage tableChatNameStorage;
    private final AwsServiceImplementation awsServiceImplementation;
    private final SimpMessagingTemplate messagingTemplate;
    private final AuthenticatedMyUserService authService;
    private final AccessValidation accessValidation;

    public ChatController(MessageService messageService, AuthenticatedMyUserService authService,
                          SimpMessagingTemplate messagingTemplate, TableChatNameStorage tableChatNameStorage,
                          AwsServiceImplementation awsServiceImplementation, AccessValidation accessValidation) {
        this.messageService = messageService;
        this.authService = authService;
        this.messagingTemplate = messagingTemplate;
        this.tableChatNameStorage = tableChatNameStorage;
        this.awsServiceImplementation = awsServiceImplementation;
        this.accessValidation = accessValidation;
    }

    @PostMapping("/chatwith")
    public void postMessage(@RequestBody MessageToChatDTO messageDTO) throws UserNotFoundException {
        messageService.save(messageDTO.getMessage(), messageDTO.getIdOfReceiver());

        sendMessage(messageDTO, authService.getCurrentUserAuthenticated());
    }

    @PostMapping("/hichat-users-private-photos/send-photo/{toUser}")
    public void uploadFile(@RequestParam("file") MultipartFile file,
                           @PathVariable("toUser") int toUser) throws Exception{
        String fileName = messageService.uploadPhotoToUserAndGetPhotoId(file,toUser);

        MessageToChatDTO messageDTO = new MessageToChatDTO();
        messageDTO.setContentType(DataType.IMAGE);
        messageDTO.setMessage(fileName);
        messageDTO.setIdOfReceiver(toUser);

        sendMessage(messageDTO, authService.getCurrentUserAuthenticated());
    }

    private void sendMessage(MessageToChatDTO messageDTO, @NotNull MyUser authUser) {
        String destination = "/sp_send/" + tableChatNameStorage.setTableName(authUser.getId(), messageDTO.getIdOfReceiver());

        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setSender(authUser);
        messageResponse.setContentType(messageDTO.getContentType());
        messageResponse.setMessageText(messageDTO.getMessage());

        messagingTemplate.convertAndSend(destination, messageResponse);
    }

    @GetMapping("/hichat-users-private-photos/download/{fileName}")
    public ResponseEntity<?> downloadFile(
            @PathVariable("fileName") String fileName
    ) throws IOException, UserNotFoundException {
        if(!accessValidation.isUserHavePermissionToPrivatePhoto(fileName,authService.getCurrentUserAuthenticated())){
            return null;
        }
        try{
            String bucketName = "hichat-users-private-photos";
            ByteArrayOutputStream body = awsServiceImplementation.downloadFile(bucketName, fileName);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .contentType(FileType.fromFilename(fileName))
                    .body(body.toByteArray());
        }
        catch(AmazonS3Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
