package com.hichat.mychat.service;

import com.hichat.mychat.config.security.AuthenticatedMyUserService;
import com.hichat.mychat.config.storage.TableChatNameStorage;
import com.hichat.mychat.exeption.FileExeption;
import com.hichat.mychat.exeption.UserNotFoundException;
import com.hichat.mychat.model.entitie.Message;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.model.enumclass.DataType;
import com.hichat.mychat.repository.nativedb.MessageJdbcRepository;
import com.hichat.mychat.repository.MyUserRepository;
import com.hichat.mychat.repository.nativedb.ChatNativeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class MessageService {
    private final MessageJdbcRepository messageJdbcRepository;
    private final AuthenticatedMyUserService authService;
    private final MyUserRepository myUserRepository;
    private final TableChatNameStorage tableChatNameStorage;
    private final ChatNativeRepository chatNativeRepository;
    private final AwsServiceImplementation awsService;

    public MessageService(MessageJdbcRepository messageJdbcRepository, AuthenticatedMyUserService authService, MyUserRepository myUserRepository, TableChatNameStorage tableChatNameStorage, ChatNativeRepository chatNativeRepository, AwsServiceImplementation awsService) {
        this.messageJdbcRepository = messageJdbcRepository;
        this.authService = authService;
        this.myUserRepository = myUserRepository;
        this.tableChatNameStorage = tableChatNameStorage;
        this.chatNativeRepository = chatNativeRepository;
        this.awsService = awsService;
    }

    @Transactional
    public boolean save(String messageText, int idOfReceiver) throws UserNotFoundException {
        MyUser authUser = authService.getCurrentUserAuthenticated();
        MyUser receiver = myUserRepository.findById(idOfReceiver).orElseThrow(()->new UserNotFoundException("User not found"));

        tableChatNameStorage.setTableName(authUser.getId(), receiver.getId());
        chatNativeRepository.existsOrThrow(authUser.getId(), receiver.getId());

        Message message = new Message();
        message.setContentType(DataType.TEXT);
        message.setMessage(messageText);
        message.setSender(authUser);

        messageJdbcRepository.save(message);

        tableChatNameStorage.clear();

        return true;
    }

    @Transactional
    public String uploadPhotoToUserAndGetPhotoId(MultipartFile file, int toUserId) throws UserNotFoundException, IOException {
        if (file.isEmpty()) {
            throw new FileExeption("empty file");
        }
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new FileExeption("file is too big, max size is 5MB");
        }

        MyUser receiver = myUserRepository.findById(toUserId).orElseThrow(()->new UserNotFoundException("User not found"));
        MyUser authUser = authService.getCurrentUserAuthenticated();

        tableChatNameStorage.setTableName(authUser.getId(), receiver.getId());
        chatNativeRepository.existsOrThrow(authUser.getId(), receiver.getId());

        String bucketName = "hichat-users-private-photos";
//        todo: is it ok to set up accessibility like that
        String fileName = UUID.randomUUID()+"$"+tableChatNameStorage.getTableName();

        String contentType = file.getContentType();
        long fileSize = file.getSize();
        InputStream inputStream = file.getInputStream();

        Message message = new Message();
        message.setContentType(DataType.IMAGE);
        message.setMessage(fileName);
        message.setSender(authUser);

        messageJdbcRepository.save(message);
        awsService.uploadFile(bucketName, fileName, fileSize, contentType, inputStream);

        tableChatNameStorage.clear();

        return fileName;
    }
}
