package com.hichat.mychat.service.validation;

import com.hichat.mychat.model.entitie.MyUser;
import com.hichat.mychat.repository.nativedb.ChatNativeRepository;
import org.springframework.stereotype.Service;

@Service
public class AccessValidation {

    private final ChatNativeRepository chatNativeRepository;

    public AccessValidation(ChatNativeRepository chatNativeRepository) {
        this.chatNativeRepository = chatNativeRepository;
    }

    public boolean isUserHavePermissionToPrivatePhoto(String fileName, MyUser currentUserAuthenticated) {
        String chatName = fileName.substring(fileName.lastIndexOf("$")+1);
        if(chatName.isEmpty()){
            return false;
        }
        return chatName.contains(currentUserAuthenticated.getId().toString()) && chatNativeRepository.isExistsByName(chatName);
    }
}
