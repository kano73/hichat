package com.hichat.mychat.service;

import com.hichat.mychat.config.security.AuthenticatedMyUserService;
import com.hichat.mychat.config.storage.TableChatNameStorage;
import com.hichat.mychat.exeption.UserNotFoundException;
import com.hichat.mychat.model.entitie.Message;
import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.repository.nativedb.MessageJdbcRepository;
import com.hichat.mychat.repository.MyUserRepository;
import com.hichat.mychat.repository.nativedb.ChatNativeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final MessageJdbcRepository messageJdbcRepository;
    private final AuthenticatedMyUserService authService;
    private final MyUserRepository myUserRepository;
    private final TableChatNameStorage tableChatNameStorage;
    private final ChatNativeRepository chatNativeRepository;

    public MessageService(MessageJdbcRepository messageJdbcRepository, AuthenticatedMyUserService authService, MyUserRepository myUserRepository, TableChatNameStorage tableChatNameStorage, ChatNativeRepository chatNativeRepository) {
        this.messageJdbcRepository = messageJdbcRepository;
        this.authService = authService;
        this.myUserRepository = myUserRepository;
        this.tableChatNameStorage = tableChatNameStorage;
        this.chatNativeRepository = chatNativeRepository;
    }

    @Transactional
    public boolean save(String messageText, int idOfReceiver) throws UserNotFoundException {
        MyUser authUser = authService.getCurrentUserAuthenticated();
        MyUser receiver = myUserRepository.findById(idOfReceiver).orElseThrow(()->new UserNotFoundException("User not found"));

        tableChatNameStorage.setTableName(authUser.getId(), receiver.getId());
        chatNativeRepository.existsOrThrow(authUser.getId(), receiver.getId());

        Message message = new Message();
        message.setMessage(messageText);
        message.setSender(authUser);

        messageJdbcRepository.save(message);

        tableChatNameStorage.clear();

        return true;
    }
}
