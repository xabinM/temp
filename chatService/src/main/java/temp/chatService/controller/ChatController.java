package temp.chatService.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import temp.chatService.entity.ChatMessageEntity;
import temp.chatService.model.ChatMessageDto;
import temp.chatService.service.ChatMessageService;

import java.security.Principal;
import java.util.List;

@RequestMapping("/api/chat")
@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;

    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload ChatMessageDto chatMessage, Principal principal) {
        chatMessageService.sendMessage(chatMessage, principal.getName());
    }

    @GetMapping("/rooms/{roomId}/messages")
    @ResponseBody
    public List<ChatMessageEntity> getMessages(@PathVariable Long roomId) {
        return chatMessageService.getMessagesByRoom(roomId);
    }
}
