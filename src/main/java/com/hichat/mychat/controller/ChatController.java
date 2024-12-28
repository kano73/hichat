package com.hichat.mychat.controller;

import com.hichat.mychat.exeption.UserNotFoundException;
import com.hichat.mychat.model.dto.MessageToChatDTO;
import com.hichat.mychat.service.MessageService;
import com.hichat.mychat.service.PageBuilderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ChatController {

    private final PageBuilderService pageBuilderService;
    private final MessageService messageService;

    public ChatController(PageBuilderService pageBuilderService, MessageService messageService) {
        this.pageBuilderService = pageBuilderService;
        this.messageService = messageService;
    }

    @GetMapping("/chatwith")
    public String chatwith(Model model, @RequestParam int id) throws UserNotFoundException {
        model = pageBuilderService.buildChatWithPage(model,id);

        return "chatwith";
    }

    @PostMapping("/chatwith")
    public @ResponseBody String postMessage(@RequestBody MessageToChatDTO messageDTO) throws UserNotFoundException {
        return messageService.save(messageDTO.getMessage(), messageDTO.getIdOfReceiver()) ? "success" : "fail";
    }
}
