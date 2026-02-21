package temp.chatService.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import temp.chatService.entity.ChatMessageEntity;
import temp.chatService.model.ChatMessageDto;
import temp.chatService.model.WsDestination;
import temp.chatService.service.ChatMessageService;

import java.util.List;

@RequestMapping("/api/chat")
@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    public ChatController(SimpMessagingTemplate messagingTemplate, ChatMessageService chatMessageService) {
        this.messagingTemplate = messagingTemplate;
        this.chatMessageService = chatMessageService;
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(@Payload ChatMessageDto chatMessage) {
        chatMessageService.save(chatMessage);
        messagingTemplate.convertAndSend(WsDestination.CHAT_ROOM + chatMessage.getRoomId(), chatMessage);
    }

    @GetMapping("/rooms/{roomId}/messages")
    @ResponseBody
    public List<ChatMessageEntity> getMessages(@PathVariable Long roomId) {
        return chatMessageService.getMessagesByRoom(roomId);
    }
}
