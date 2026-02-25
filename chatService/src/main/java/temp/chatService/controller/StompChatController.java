package temp.chatService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import temp.chatService.model.SendMessageDto;
import temp.chatService.service.ChatMessageService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload SendMessageDto message, Principal principal) {
        chatMessageService.sendMessage(message, principal.getName());
    }
}
