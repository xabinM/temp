package temp.chatService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import temp.chatService.entity.ChatMessageEntity;
import temp.chatService.model.ChatMessageDto;
import temp.chatService.model.WsDestination;
import temp.chatService.repository.ChatMessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;

    public void sendMessage(ChatMessageDto chatMessage, String senderId) {
        chatMessageRepository.save(ChatMessageEntity.from(chatMessage));
        messagingTemplate.convertAndSend(WsDestination.CHAT_ROOM + chatMessage.getRoomId(), chatMessage);
        notificationService.notifyOfflineMembers(chatMessage, senderId);
    }

    public List<ChatMessageEntity> getMessagesByRoom(Long roomId) {
        return chatMessageRepository.findByRoomIdOrderBySentAtAsc(roomId);
    }
}
